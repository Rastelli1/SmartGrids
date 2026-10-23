package smartgrids;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import smartgrid.Monitoreos.Monitoreo;
import smartgrid.Monitoreos.MonitoreoEolico;
import smartgrid.Monitoreos.MonitoreoTermico;
import smartgrids.Plantas.*;


public class SmartGrids implements ISmartGrids {
	
	LinkedHashMap<String, Planta> plantas = new LinkedHashMap<String, Planta>();

	private LocalDateTime parsearhora(String fechaHora) {
		if(fechaHora==null)
			throw new RuntimeException("debe ser una fecha y una hora valida");
		return LocalDateTime.parse(fechaHora);
	}
	private LocalDate parsearFecha(String fecha) {
		if(fecha==null)
			throw new RuntimeException("debe ser una fecha valida");
		return LocalDate.parse(fecha);
	}
	
	private Planta buscarPlanta(String nombre) {
        Planta planta = plantas.get(nombre);
        if (planta == null)
            throw new RuntimeException("No existe la planta " + nombre);
        return planta;
    }
	
	
	private void agregarPlanta(Planta planta) {
		if(plantas.containsKey(planta.nombre()))
			throw new RuntimeException("Ya existe una planta con ese nombre");
		plantas.put(planta.nombre(), planta);
	}
	
	@Override
	public void registrarPlantaSolar(String nombre, String direccion, double capacidadMaxima,
			String fechaHoraPuestaEnFuncionamiento, String fuentePrimaria, double eficienciaPromedio,
			double superficieCubierta, int cantidadPaneles, double horasRadiacionDiaria) {
		agregarPlanta(new Solar(nombre, direccion, capacidadMaxima,parsearhora(fechaHoraPuestaEnFuncionamiento), fuentePrimaria, eficienciaPromedio, superficieCubierta, cantidadPaneles, horasRadiacionDiaria));
	}

	@Override
	public void registrarPlantaEolica(String nombre, String direccion, double capacidadMaxima,
			String fechaHoraPuestaEnFuncionamiento, String fuentePrimaria, double eficienciaPromedio,
			int cantidadAerogeneradores, double alturaTorres, double velocidadMinimaViento) {
		agregarPlanta(new Eolica(nombre, direccion, capacidadMaxima, parsearhora(fechaHoraPuestaEnFuncionamiento), fuentePrimaria, eficienciaPromedio, cantidadAerogeneradores, alturaTorres, velocidadMinimaViento));

	}

	@Override
	public void registrarPlantaHidraulica(String nombre, String direccion, double capacidadMaxima,
			String fechaHoraPuestaEnFuncionamiento, String fuentePrimaria, double eficienciaPromedio,
			int cantidadTurbinas, double capacidadEmbalse) {
		agregarPlanta(new Hidraulica(nombre, direccion, capacidadMaxima, parsearhora(fechaHoraPuestaEnFuncionamiento), fuentePrimaria, eficienciaPromedio, cantidadTurbinas, capacidadEmbalse));

	}

	@Override
	public void registrarPlantaTermica(String nombre, String direccion, double capacidadMaxima,
			String fechaHoraPuestaEnFuncionamiento, String tipoCombustible, double objetivoEmisionesCo2PorHora,
			double capacidadAlmacenamientoCombustible) {
		agregarPlanta(new Termica(nombre, direccion, capacidadMaxima, parsearhora(fechaHoraPuestaEnFuncionamiento), tipoCombustible, objetivoEmisionesCo2PorHora, capacidadAlmacenamientoCombustible));

	}

	@Override
	public List<String> consultarPlantas() {
	        List<String> plantaslista = new ArrayList<>();
	        for (Planta planta : plantas.values())
	            plantaslista.add(planta.toString());
	        return plantaslista;
	}

	@Override
	public double capacidadMinimaCuota(String nombre) {
		 return buscarPlanta(nombre).generacionMinimaParaCumplirCuota();
	}

	@Override
	public void activarPlanta(String nombre, String fechaHora) {
		Planta planta=buscarPlanta(nombre);
		LocalDateTime fechaHora1=parsearhora(fechaHora);
		planta.activar(fechaHora1);

	}

	@Override
	public void desactivarPlanta(String nombre, String fechaHora) {
		Planta planta=buscarPlanta(nombre);
		LocalDateTime fechaHora1=parsearhora(fechaHora);
		planta.desactivar(fechaHora1);

	}

	@Override
	public boolean estaActiva(String nombre) {
		Planta planta=buscarPlanta(nombre);
		return planta.estaActiva();
	}

	
	@Override
	public void registrarMonitoreo(String nombre, String fechaHora, double energiaGenerada, int incidencias) {
		Planta planta = buscarPlanta(nombre);
		LocalDateTime hora = parsearhora(fechaHora);
		planta.registrarMonitoreo(hora, new Monitoreo(planta, hora, energiaGenerada, incidencias));
	}


	@Override
	public void registrarMonitoreo(String nombre, String fechaHora, double energiaGenerada, int incidencias,
			double velocidadViento) {
		Planta planta = buscarPlanta(nombre);
		LocalDateTime hora = parsearhora(fechaHora);
		planta.registrarMonitoreo(hora, new MonitoreoEolico(planta, hora, energiaGenerada, incidencias, velocidadViento));
	}

	@Override
	public void registrarMonitoreo(String nombre, String fechaHora, double energiaGenerada, int incidencias,
			double co2Emitido, double combustibleUtilizado) {
		Planta planta = buscarPlanta(nombre);
		LocalDateTime hora = parsearhora(fechaHora);
		planta.registrarMonitoreo(hora,
				new MonitoreoTermico(planta, hora, energiaGenerada, incidencias, co2Emitido, combustibleUtilizado));
	}
	
	
	@Override
	public double totalGenerado(String nombre) {
		return buscarPlanta(nombre).totalGenerado();
	}

	

	@Override
	public int[] incidenciasPorHora(String nombre) {
		return buscarPlanta(nombre).incidenciasPorHora();
	}


	@Override
	public int[] histogramaCumplimiento(String nombre, String fechaDesde, String fechaHasta) {
		Planta planta = buscarPlanta(nombre);
		LocalDate desde = parsearFecha(fechaDesde);
		LocalDate hasta = parsearFecha(fechaHasta);
		if (desde.isAfter(hasta))
			throw new RuntimeException("La fecha desde no puede ser posterior a la fecha hasta");
		return planta.histogramaCumplimiento(desde, hasta);
	}


	@Override
	public TreeMap<String, Integer> plantasRenovablesPorFuente() {
		TreeMap<String, Integer> porFuente = new TreeMap<>();
		for (Planta p : plantas.values()) {
			if (p.esRenovable()) {
				String fuente = ((PlantaRenovable)p).fuentePrimaria();
				if (porFuente.containsKey(fuente)) {
				    porFuente.put(fuente, porFuente.get(fuente) + 1);
				} else {
				    porFuente.put(fuente, 1);
				}
			}
		}
		return porFuente;
	}

	@Override
	public List<String> plantasQueNuncaCumplieronCuota() {
		List<String> nombres = new ArrayList<>();
		for (Planta planta : plantas.values())
			if (planta.nuncaCumplioCuota())
				nombres.add(planta.nombre());
		return nombres;
	}


	@Override
	public String horasSinVelocidadMinimaPorPlantaEolica() {
		String texto = "Plantas Eolicas: Horas con poco viento\n";
		for (Planta planta : plantasEolicas().values()) {
			Eolica eolica = (Eolica) planta;
			texto += "- " + eolica.nombre() + ":  " + formatearViento(eolica.velocidadMinimaViento()) + "\n";
			for (Map.Entry<Integer, Double> hora : eolica.horasSinVelocidadMinima().entrySet())
				texto += "       " + String.format("%02d", hora.getKey()) + ": " + formatearViento(hora.getValue()) + "\n";
		}
		return texto;
	}
	
	private HashMap<String, Planta> plantasEolicas() {
		HashMap<String, Planta> eolicas = new HashMap<>();
		for (Planta planta : plantas.values())
			if (planta instanceof Eolica)
				eolicas.put(planta.nombre(), planta);
		return eolicas;
	}
	private String formatearViento(double velocidad) { /*5.0 se muestra como "5 m/s"*/
		String valor = velocidad == Math.rint(velocidad) ? String.valueOf((long) velocidad) : String.valueOf(velocidad);
		return valor + " m/s";
	}




	@Override
	public int limpiarMonitoreosSinDatos() {
		int borrados = 0;
		for (Planta planta : plantas.values())
			borrados += planta.limpiarMonitoreosSinDatos();
		return borrados;
	}


	@Override
	public List<String> plantasOrdenadasPorEnergiaGenerada() {
		List<Planta> ordenadas = new ArrayList<>();
		for (Planta planta : plantas.values()) { /*Insercion ordenada: cada planta se ubica antes de la primera que generó menos*/
			int pos = 0;
			while (pos < ordenadas.size() && ordenadas.get(pos).totalGenerado() >= planta.totalGenerado())
				pos++;
			ordenadas.add(pos, planta);
		}
		List<String> resultado = new ArrayList<>();
		for (Planta planta : ordenadas)
			resultado.add(planta.nombre() + ": " + planta.totalGenerado());
		return resultado;
	}


	
	@Override
	public List<String> plantasInactivasEn(String fechaHora) {
		LocalDateTime momento = parsearhora(fechaHora);
		List<String> nombres = new ArrayList<>();
		for (Planta planta : plantas.values())
			if (planta.estuvoInactivaEn(momento))
				nombres.add(planta.nombre());
		return nombres;
	}

	@Override
	public String toString() {
		StringBuilder texto = new StringBuilder("SmartGrids - plantas registradas: " + plantas.size() + "\n");
		for (Planta planta : plantas.values()) {
			texto.append("\n").append(planta).append("\n");
			texto.append("  ").append(planta.toString()).append("\n");
			if (planta.monitoreos().isEmpty())
				texto.append("  Sin monitoreos\n");
			else
				texto.append("  Monitoreos (").append(planta.monitoreos().size()).append("):\n");
			for (Monitoreo monitoreo : planta.monitoreos().values())
				texto.append("    - ").append(monitoreo).append("\n");
		}
		return texto.toString();
	}
}
