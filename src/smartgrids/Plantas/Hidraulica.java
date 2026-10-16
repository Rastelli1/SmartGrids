package smartgrids.Plantas;

import java.time.LocalDateTime;

public class Hidraulica extends PlantaRenovable {
	
	private int cantidadDeTurbinas;
	private double capacidadEmbalse;
	
	public Hidraulica(String nombre, String direccion, double capacidadMaxima,
			LocalDateTime fechaPuestaEnFuncionamiento, String fuentePrimaria, double eficienciaPromedio,int cantidadDeTurbinas,double capacidadEmbalse) {
		
		super(nombre, direccion, capacidadMaxima, fechaPuestaEnFuncionamiento, fuentePrimaria, eficienciaPromedio);
		
		if (cantidadDeTurbinas<=0) {throw new RuntimeException("La cantidad de turbinas tiene que ser mayor a 0"); }
		
		if (capacidadEmbalse<=0) {throw new RuntimeException("La capacidad tiene que ser mayor a 0"); }
		
		this.cantidadDeTurbinas=cantidadDeTurbinas;
		this.capacidadEmbalse=capacidadEmbalse;
	}
	
	public int cantidadDeTurbinas() {return this.cantidadDeTurbinas;}
	public double capacidadEmbalse() {return this.capacidadEmbalse;}
	
	@Override
	public double generacionMinimaParaCumplirCuota() {
		return capacidadMaxima*eficienciaPromedio;
	}

}
