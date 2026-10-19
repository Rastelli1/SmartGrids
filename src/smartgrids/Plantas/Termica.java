package smartgrids.Plantas;

import java.time.LocalDateTime;

import smartgrid.Monitoreos.Monitoreo;
import smartgrid.Monitoreos.MonitoreoTermico;

public class Termica extends Planta{
	
	private String tipoDeCombustible;
	private double objetivoEmisionCO2hora;
	private double capacidadAlmacenamientoCombustible;
	
	public Termica(String nombre, String direccion, double capacidadMaxima, LocalDateTime fechaPuestaEnFuncionamiento,String tipoDeCombustible, double objetivoEmisionCO2hora,double capacidadAlmacenamientoCombustible ) {
		super(nombre, direccion, capacidadMaxima, fechaPuestaEnFuncionamiento);
		
		if ( tipoDeCombustible==null || tipoDeCombustible.isEmpty()){throw new RuntimeException("El tipo de combustible es obligatoria");}
		
		if ( objetivoEmisionCO2hora<=0){throw new RuntimeException("El objetivo de CO2 tiene que ser mayor a 0");}
		
		if ( capacidadAlmacenamientoCombustible<=0){throw new RuntimeException("La capacidad de almacenamiento del combustible tiene que ser mayor a 0");}
		
		this.tipoDeCombustible=tipoDeCombustible;
		this.objetivoEmisionCO2hora=objetivoEmisionCO2hora;
		this.capacidadAlmacenamientoCombustible=capacidadAlmacenamientoCombustible;
	}
	
	public String tipoCombustible() { return this.tipoDeCombustible;}
	
	public double objetivoEmisionCO2hora () {return this.objetivoEmisionCO2hora;}
	
	public double capacidadAlmacenamientoCombustible() {return this.capacidadAlmacenamientoCombustible;}
	
	@Override
	public double generacionMinimaParaCumplirCuota() {
		return capacidadMaxima/2;
	}
	@Override
	public boolean cumpleCuota(Monitoreo monitoreo) { /*Como Termico tiene una forma distinta de cumplir cuota se sobrescribe la funcion, se verifica que el monitoreo sea si o si del monitoreoTermico*/
		if (monitoreo instanceof MonitoreoTermico==false) {throw new RuntimeException("Una planta termica necesita un monitoreo termico");}
		
		MonitoreoTermico termico= (MonitoreoTermico) monitoreo;
		
		return super.cumpleCuota(monitoreo)&& termico.Co2Emitido()<this.objetivoEmisionCO2hora;
	}

	@Override
	public boolean esRenovable() {
		return false;
	}
	
	

}
