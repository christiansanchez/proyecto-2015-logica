package servidor.excepciones;

public class BorrarPartidasException extends GeneralException{
	
	private static final long serialVersionUID = 1L;

	public BorrarPartidasException()
	{
		super();
	}
	
	public BorrarPartidasException(String message) 
	{
		super(message);		
	}	
}