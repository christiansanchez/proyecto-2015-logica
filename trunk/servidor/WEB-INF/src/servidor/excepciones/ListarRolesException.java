package servidor.excepciones;

public class ListarRolesException extends GeneralException{
	
	private static final long serialVersionUID = 1L;
	
	public ListarRolesException()
	{
		super();
	}
	
	public ListarRolesException(String message) 
	{
		super(message);		
	}	
}