package servidor.excepciones;

public class EliminarPartidaException extends GeneralException{
	
	private static final long serialVersionUID = 1L;

	public EliminarPartidaException()
	{
		super();
	}
	
	public EliminarPartidaException(String message) 
	{
		super(message);		
	}	
}
