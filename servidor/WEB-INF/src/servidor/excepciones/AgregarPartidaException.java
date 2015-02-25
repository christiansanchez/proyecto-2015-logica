package servidor.excepciones;

public class AgregarPartidaException extends GeneralException{
	
	private static final long serialVersionUID = 1L;

	public AgregarPartidaException(){
		super();
	}
	
	public AgregarPartidaException(String message){
		super(message);		
	}	
}
