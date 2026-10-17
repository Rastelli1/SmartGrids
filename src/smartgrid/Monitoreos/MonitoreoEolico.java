package smartgrid.Monitoreos;

import java.time.LocalDateTime;

import smartgrids.Plantas.Planta;

public class MonitoreoEolico extends Monitoreo {
	private double velocidadViento;
	
	public MonitoreoEolico(Planta planta, LocalDateTime fechaHora, double energiaGenerada, int cantidadIncidencias, double velocidadViento) {
		super(planta, fechaHora, energiaGenerada, cantidadIncidencias);
		
		if (velocidadViento<0)
			throw new RuntimeException("La velocidad del viento no puede ser negativa");
		this.velocidadViento=velocidadViento;
	}
	
	public double VelocidadViento() {
		return velocidadViento;
	}
}
