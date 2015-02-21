package servidor.excepciones;

public class IngresarPartidasException extends GeneralException{
	
	private static final long serialVersionUID = 1L;

	public IngresarPartidasException()
	{
		super();
	}
	
	public IngresarPartidasException(String message) 
	{
		super(message);		
	}	
}