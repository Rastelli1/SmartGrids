package smartgrids.Plantas;

import java.time.LocalDateTime;

public class Solar  extends PlantaRenovable{
	
	private double superficieCubierta;
	private int cantidadDePaneles;
	private double horasRadiacionDiaria;
	
	public Solar(String nombre, String direccion, double capacidadMaxima, LocalDateTime fechaPuestaEnFuncionamiento,
			String fuentePrimaria, double eficienciaPromedio, double superficieCubierta,int cantidadDePaneles,double horasRadiacionDiaria) {
		
		super(nombre, direccion, capacidadMaxima, fechaPuestaEnFuncionamiento, fuentePrimaria, eficienciaPromedio);
		
		if(superficieCubierta <=0) {throw new RuntimeException("La superficie debe ser mayor a 0");}
		if(cantidadDePaneles<=0) {throw new RuntimeException("La cantidad de paneles debe ser mayor a 0");}
		if (horasRadiacionDiaria <= 0 || horasRadiacionDiaria > 24) {throw new RuntimeException("Las horas de radiacion deben estar entre 0 y 24");}
		
		this.superficieCubierta=superficieCubierta;
		this.cantidadDePaneles=cantidadDePaneles;
		this.horasRadiacionDiaria=horasRadiacionDiaria;
	}
	public double superficieCubierta() {return this.superficieCubierta;}
	public int cantidadDePaneles() {return this.cantidadDePaneles;}
	public double horasRadiacionDiaria () {return this.horasRadiacionDiaria;}

	@Override
	public double generacionMinimaParaCumplirCuota() { // cuota = 1000 * 0.5 * 12 / 24 = 250     (solar)

		return capacidadMaxima * eficienciaPromedio * horasRadiacionDiaria / 24;
	}
};
