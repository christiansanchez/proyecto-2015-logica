package servidor.excepciones;

public class BuscarPartidasException extends GeneralException{
	
	private static final long serialVersionUID = 1L;
	
	public BuscarPartidasException()
	{
		super();
	}
	
	public BuscarPartidasException(String message) 
	{
		super(message);		
	}	
}