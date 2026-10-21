package smartgrid.Monitoreos;

import java.time.LocalDateTime;

import smartgrids.Plantas.Planta;

public class Monitoreo {
	protected Planta planta;
	protected LocalDateTime fechaHora;
	protected double energiaGenerada;
	protected int cantidadIncidencias;
	
	public Monitoreo(Planta planta, LocalDateTime fechaHora, double energiaGenerada, int cantidadIncidencias) {
		if (planta == null)
			throw new RuntimeException("Debe ingresar una planta valida");
		if (fechaHora.isBefore(planta.fechaPuestaEnFuncionamiento()))
			throw new RuntimeException("La fecha ingresada no debe ser anterior a la hora puesta en funcionamiento");
		if (energiaGenerada<0) 
			throw new RuntimeException("La energia generada no puede ser negativa");
		if (cantidadIncidencias<0)
			throw new RuntimeException("No puede haber una cantidad de incidencias negativas");
		if (energiaGenerada < 0)
			throw new RuntimeException("La energia no puede ser negativa");
		if (cantidadIncidencias < 0)
			throw new RuntimeException("Las incidencias no pueden ser negativas");	
		this.planta = planta;
		this.fechaHora = fechaHora;
		this.energiaGenerada = energiaGenerada;
		this.cantidadIncidencias = cantidadIncidencias;
		
	}

	public Planta Planta() {
		return planta;
	}

	public LocalDateTime FechaHora() {
		return fechaHora;
	}

	public double EnergiaGenerada() {
		return energiaGenerada;
	}

	public int CantidadIncidencias() {
		return cantidadIncidencias;
	}
	
	@Override
	public String toString() {
		return "La Planta:" + planta.nombre() + " registrada a las: " + fechaHora + " genero: " + energiaGenerada + "y tuvo " + (cantidadIncidencias == 1 ? " incidencia":" incidencias"); 
	}
}
