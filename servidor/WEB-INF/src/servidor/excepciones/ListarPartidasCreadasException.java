package servidor.excepciones;

public class ListarPartidasCreadasException extends GeneralException{
	
	private static final long serialVersionUID = 1L;

	public ListarPartidasCreadasException()
	{
		super();
	}
	
	public ListarPartidasCreadasException(String message) 
	{
		super(message);		
	}	
}