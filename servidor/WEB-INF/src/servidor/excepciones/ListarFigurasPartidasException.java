package servidor.excepciones;

public class ListarFigurasPartidasException extends GeneralException{
	
	private static final long serialVersionUID = 1L;

	public ListarFigurasPartidasException()
	{
		super();
	}
	
	public ListarFigurasPartidasException(String message) 
	{
		super(message);		
	}	
}
