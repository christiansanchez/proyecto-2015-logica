package servidor.logica;

import servidor.excepciones.FachadaException;
/*
 * Clase encargada de definir la logica y los metodos utilizados
 * desde el web service.
 */
public class FachadaWService {
	
	public FachadaWService(){
		try {
			FachadaService.getInstancia();
		} 
		catch (FachadaException e) {
			System.out.println("ERROR CONSTRUCTOR");
		}		
	}
	
	public String setGuardarPartida(String dataJuego){
		String resultado = "";
		return resultado;	
	}

	public String getUnirsePartida() throws FachadaException{
		String resultado = "";
		return resultado;		
	}
	
	public String getCargarPartida() throws FachadaException{
		String resultado = "";
		return resultado;	
	}
	
	public String setCargarPartida(String dataJuego) throws FachadaException{
		String resultado = "";
		return resultado;	
	}

}
