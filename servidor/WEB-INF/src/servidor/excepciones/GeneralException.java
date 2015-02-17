package servidor.excepciones;
/*
 * GeneralException es la clase base para todas las excepciones
 * del sistema.
 */
public class GeneralException extends Exception{

	/*Atributos*/
	private static final long serialVersionUID = 1L;
	private String message;	

	public GeneralException(){}
	
	public GeneralException(String message){
		this.message = message;
	}	

	public void setMessage(String message){
		this.message = message;
	}

	public String getMensaje(){ 
		return this.message;
	}

}
