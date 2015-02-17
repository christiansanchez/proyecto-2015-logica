package servidor.excepciones;

public class PersistenciaException extends GeneralException{
	
	private static final long serialVersionUID = 1L;
	
	public PersistenciaException()
	{
		super();
	}
	
	public PersistenciaException(String message) 
	{
		super(message);		
	}	
}