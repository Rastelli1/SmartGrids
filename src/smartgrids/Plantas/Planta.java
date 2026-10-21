package smartgrids.Plantas;
import smartgrid.Monitoreos.Monitoreo;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Planta {
	
	protected String nombre;
	protected String direccion;
	protected double capacidadMaxima;
	protected boolean activa;
	protected LocalDateTime fechaPuestaEnFuncionamiento; /*Por la consigna tiene que ser LocalDateTime*/
	protected List <PeriodosInactividad> periodos;
	
	public Planta (String nombre, String direccion, double capacidadMaxima,LocalDateTime fechaPuestaEnFuncionamiento) {
		
		if (nombre == null || nombre.isEmpty())
            throw new RuntimeException("El nombre es obligatorio");
		
        if (direccion == null || direccion.isEmpty())
            throw new RuntimeException("La direccion es obligatoria");
        
        if (capacidadMaxima <= 0)
            throw new RuntimeException("La capacidad maxima debe ser mayor a 0");
        
        if (fechaPuestaEnFuncionamiento == null)
            throw new RuntimeException("La fecha de puesta en funcionamiento es obligatoria");
        
        
		this.nombre=nombre;
		this.direccion=direccion;
		this.capacidadMaxima=capacidadMaxima;
		this.activa=true;
		this.fechaPuestaEnFuncionamiento=fechaPuestaEnFuncionamiento;
		this.periodos=new ArrayList<>();
	}
	
	protected HashMap<LocalDateTime, Monitoreo> monitoreos = new HashMap<>();
	
	public void registrarMonitoreo(LocalDateTime fechaHora, Monitoreo monitoreo) {
	    if (fechaHora == null || monitoreo == null)
	        throw new RuntimeException("El monitoreo y su fecha son obligatorios");
	    if (!activa)
	        throw new RuntimeException("La planta esta inactiva");
	    if (fechaHora.isBefore(fechaPuestaEnFuncionamiento))
	        throw new RuntimeException("La fecha es anterior a la puesta en funcionamiento");

	    LocalDateTime hora = fechaHora.truncatedTo(ChronoUnit.HOURS);
	    if (monitoreos.containsKey(hora))
	        throw new RuntimeException("Ya existe un registro para esa hora");

	    monitoreos.put(hora, monitoreo);
	}
	
	public String nombre() {return nombre;}
	
	public String direccion() {return direccion;}
	
	public double capacidadMaxima() {return capacidadMaxima;}
	
	public boolean estaActiva() { return this.activa;}
	
	public LocalDateTime fechaPuestaEnFuncionamiento() { return this.fechaPuestaEnFuncionamiento;}
	
	public void desactivar(LocalDateTime fechaHora ) { /*Le pasa el parametro de la fecha para poder calcular el tiempo que esta desactivada la planta despues*/
		
		if (fechaHora == null) { throw new RuntimeException("La fecha es obligatoria"); }
		
		if (activa==false) { throw new RuntimeException("La planta ya esta inactiva"); }
		
		if (!periodos.isEmpty()) {
			PeriodosInactividad ultimo = periodos.get(periodos.size() - 1);
            if (ultimo.esAnteriorAlFin(fechaHora))
                throw new RuntimeException("La fecha es anterior a la ultima activacion");
        }
		activa=false;
		periodos.add(new PeriodosInactividad(fechaHora));
	}
	
	public void activar(LocalDateTime fechaHora) { /*Este metodo se encargara de activar la planta */
		
		if (activa==true){ throw new RuntimeException("La planta ya esta activa"); }
		
		activa=false;
		periodos.get(periodos.size()-1).renudarActividad(fechaHora);
	}
	
	public abstract double  generacionMinimaParaCumplirCuota() ;
	
	
	public boolean cumpleCuota(Monitoreo monitoreo) { /* Este metodo se encarga de verificar si la energia generada por x planta cumple la cuota minima, true si cumple y false si no cumple*/
		return monitoreo.EnergiaGenerada() >= generacionMinimaParaCumplirCuota();
	}
	
	public  abstract boolean esRenovable();
	
    public boolean estuvoInactivaEn(LocalDateTime momento) { /*Este metodo se encarga de buscar en los periodos de inactividad si estuvo inactiva en x momento, si no lo encuentro devuleve false ( estaba activa)*/ 
    	
        for (PeriodosInactividad periodo : periodos) {
        	
            if (periodo.estabaInactivo(momento)) return true;
        }
        return false;
    }
	@Override
    public String toString() {
        return nombre + " (" + (activa ? "ACTIVA" : "INACTIVA") + "): " + fechaPuestaEnFuncionamiento;
    }
}
