package smartgrid.Monitoreos;

import java.time.LocalDateTime;

import smartgrids.Plantas.Planta;

public class MonitoreoTermico extends Monitoreo {
	private double co2Emitido;
	private double combustible;

	public MonitoreoTermico(Planta planta, LocalDateTime fechaHora, double energiaGenerada,
			int cantidadIncidencias, double co2Emitido , double combustible) {
		super(planta, fechaHora, energiaGenerada, cantidadIncidencias);
		
		if (co2Emitido<0)
			throw new RuntimeException("El CO2 emitido no puede ser negativo");
		if (combustible<0)
			throw new RuntimeException("El combustible utilizado no puede ser negativo");
		this.co2Emitido=co2Emitido;
		this.combustible=combustible;
	}

	public double Co2Emitido() {
		return co2Emitido;
	}

	public double Combustible() {
		return combustible;
	}


}
