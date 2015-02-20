package servidor.logica;

import servidor.excepciones.FachadaException;
/*
 * Clase encargada de definir la logica y los metodos utilizados
 * desde el web service.
 */
public class FachadaWService extends Fachada{

	public FachadaWService() throws FachadaException {
		super();
	}
	
	public boolean existePartida(String nombrePartida){
		return false;
	}
	
	public boolean setPartida(String nombrePartida, String rolPartida){		
		return false;
	}
	
	public String getUnirsePartida(){
		return null;
	}
	
	public String getCargarPartida(){
		return null;
	}
	
	public boolean setCargarPartida(String nombrePartida, String rolPartida){
		return false;
	}
	
	public boolean setUnirsePartida(String nombrePartida, String rolPartida){
		return false;
	}
}
