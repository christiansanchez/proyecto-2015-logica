package servidor.excepciones;

public class GuardarFigurasPartidasException extends GeneralException{
	
	private static final long serialVersionUID = 1L;
	
	public GuardarFigurasPartidasException()
	{
		super();
	}
	
	public GuardarFigurasPartidasException(String message) 
	{
		super(message);		
	}	
}
