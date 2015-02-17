package servidor.excepciones;

public class ListarEstadoPartidaException extends GeneralException{
	
	private static final long serialVersionUID = 1L;
	
	public ListarEstadoPartidaException()
	{
		super();
	}
	
	public ListarEstadoPartidaException(String message) 
	{
		super(message);		
	}	
}