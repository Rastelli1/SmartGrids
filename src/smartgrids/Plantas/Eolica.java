package smartgrids.Plantas;

import java.time.LocalDateTime;

public class Eolica extends PlantaRenovable {
	
	private int cantidadAereogeneradores;
	private double alturaTorre;
	private double velocidadMinimaViento;
	
	public Eolica(String nombre, String direccion, double capacidadMaxima, LocalDateTime fechaPuestaEnFuncionamiento,
			String fuentePrimaria, double eficienciaPromedio,int cantidadAereogeneradores,double alturaTorre,double velocidadMinimaViento) {
		
		super(nombre, direccion, capacidadMaxima, fechaPuestaEnFuncionamiento, fuentePrimaria, eficienciaPromedio);
		
		if (cantidadAereogeneradores<=0){throw new RuntimeException("La cantidad de Aereogeneradores tiene que ser mayor a 0");}
		if( alturaTorre<=0){throw new RuntimeException("La altura de torre tiene que ser mayor a 0");}
		if(velocidadMinimaViento<=0) {throw new RuntimeException("La velocidad minima de viento tiene que ser mayor a 0");}
		
		this.cantidadAereogeneradores=cantidadAereogeneradores;
		this.alturaTorre=alturaTorre;
		this.velocidadMinimaViento=velocidadMinimaViento;
		

	}
	
	public int cantidadAereoGeneradores() {return this.cantidadAereogeneradores;}
	public double alturaTorre() {return this.alturaTorre;}
	public double velocidadMinimaViento() {return this.velocidadMinimaViento;}

	@Override
	public double generacionMinimaParaCumplirCuota() {
		return capacidadMaxima* eficienciaPromedio;
	}

}
