package servidor.excepciones;

public class ExistePartidaEnCursoException extends GeneralException{
	
	private static final long serialVersionUID = 1L;

	public ExistePartidaEnCursoException(){
		super();
	}
	
	public ExistePartidaEnCursoException(String message){
		super(message);		
	}	
}
