package smartgrids;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import smartgrids.Plantas.*;

public class SmartGrids implements ISmartGrids {
	
	HashMap<String, Planta> plantas = new HashMap<String, Planta>();

	private LocalDateTime parsearhora(String fechaHora) {
		if(fechaHora==null)
			throw new RuntimeException("debe ser una fecha y una hora valida");
		return LocalDateTime.parse(fechaHora);
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
		// TODO Auto-generated method stub

	}

	@Override
	public void desactivarPlanta(String nombre, String fechaHora) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean estaActiva(String nombre) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void registrarMonitoreo(String nombre, String fechaHora, double energiaGenerada, int incidencias) {
		// TODO Auto-generated method stub

	}

	@Override
	public void registrarMonitoreo(String nombre, String fechaHora, double energiaGenerada, int incidencias,
			double velocidadViento) {
		// TODO Auto-generated method stub

	}

	@Override
	public void registrarMonitoreo(String nombre, String fechaHora, double energiaGenerada, int incidencias,
			double co2Emitido, double combustibleUtilizado) {
		// TODO Auto-generated method stub

	}

	@Override
	public double totalGenerado(String nombre) {
	
		return 0;
	}

	@Override
	public int[] incidenciasPorHora(String nombre) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] histogramaCumplimiento(String nombre, String fechaDesde, String fechaHasta) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String horasSinVelocidadMinimaPorPlantaEolica() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int limpiarMonitoreosSinDatos() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<String> plantasOrdenadasPorEnergiaGenerada() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> plantasInactivasEn(String fechaHora) {
		// TODO Auto-generated method stub
		return null;
	}

}
