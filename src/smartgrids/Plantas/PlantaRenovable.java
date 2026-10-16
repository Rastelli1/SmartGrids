package smartgrids.Plantas;

import java.time.LocalDateTime;

public abstract class PlantaRenovable extends Planta{
	
	protected String fuentePrimaria;
	protected double eficienciaPromedio;

	public PlantaRenovable(String nombre, String direccion, double capacidadMaxima,
			LocalDateTime fechaPuestaEnFuncionamiento, String fuentePrimaria,double eficienciaPromedio ) {
		
		super(nombre, direccion, capacidadMaxima, fechaPuestaEnFuncionamiento);
		
		if (fuentePrimaria == null || fuentePrimaria.isEmpty()) {throw new RuntimeException("La fuente primaria es obligatoria");}
            
        if (eficienciaPromedio <= 0 || eficienciaPromedio > 1) {throw new RuntimeException("La eficiencia debe estar entre 0 y 1");}
            
        
		this.eficienciaPromedio=eficienciaPromedio;
		this.fuentePrimaria=fuentePrimaria;
		
	}
	
	public  String fuentePrimaria () {return this.fuentePrimaria;}
	
	public double eficienciaPromedio() {return this.eficienciaPromedio;}

    @Override
    public boolean esRenovable() { return true; }


}
