package smartgrids.Plantas;

import java.time.LocalDateTime;



public class PeriodosInactividad {
	
	private LocalDateTime inicioInactividad;
	private LocalDateTime finInactividad;
	
	
	public PeriodosInactividad(LocalDateTime inicioInactividad) {
		if ( inicioInactividad== null  ) { throw new RuntimeException("Se necesita fehca inicio para proceder"); }
		this.inicioInactividad=inicioInactividad;
		this.finInactividad=null;
	}
	
	public  void renudarActividad (LocalDateTime finInactividad) { /* Este metodo toma como parametro el finInactividad que tiene que ser posterior a la fecha  de Inicio, si esta vacia o si es anterior devuelve uan execpcion */
		if ( finInactividad== null) { throw new RuntimeException("Se necesita fecha de fin de inactividad");}
		
		if ( finInactividad.isBefore(inicioInactividad)) { throw new RuntimeException("La fecha de fin de inactividad es anterior al de inicio");}
		
		this.finInactividad=finInactividad;
	}
	public boolean esAnteriorAlFin(LocalDateTime fechaConsulta) { /*Se consulta sobre si una fecha es anterior al finInactividad*/
		
		if (finInactividad == null) { return false; }
		
		if(fechaConsulta==null){ throw new RuntimeException("Se necesita fecha para la consulta");}
		
		return fechaConsulta.isBefore(finInactividad);
	}
	public boolean estaInactiva() { return finInactividad==null;} /* Este metodo devuelve si el periodo sigue inactivo , si el fin de actividad es null es porque el periodo sigue Inactivo */
	
	public boolean estabaInactivo( LocalDateTime consultaFecha ) { /* Este emtodo se utiliza para saber si en tal fecha estaba inactivo, si la fecha es anterior al inicio de inactividad devuelve false */
		
		if ( consultaFecha.isBefore(inicioInactividad)) { return false;}
		
		return estaInactiva() || consultaFecha.isBefore(finInactividad);
	}
	
	@Override 
	public String toString() {
		return " (Inactiva) "+ inicioInactividad + (estaInactiva()? "]": "-> Hasta: "+finInactividad+" ]");
	}

}
