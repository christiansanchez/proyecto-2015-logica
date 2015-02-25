package servidor.excepciones;

public class AgregarFigurasPartidasException extends GeneralException{
	
	private static final long serialVersionUID = 1L;

	public AgregarFigurasPartidasException(){
		super();
	}
	
	public AgregarFigurasPartidasException(String message){
		super(message);		
	}	
}
