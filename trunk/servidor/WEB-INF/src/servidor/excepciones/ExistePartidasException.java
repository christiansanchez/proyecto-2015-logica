package servidor.excepciones;

public class ExistePartidasException extends GeneralException{
	
	private static final long serialVersionUID = 1L;

	public ExistePartidasException()
	{
		super();
	}
	
	public ExistePartidasException(String message) 
	{
		super(message);		
	}	
}