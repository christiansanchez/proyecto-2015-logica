package servidor.logica;
/*Clase encargada de manejar el rol Barco Carguero*/
public class BarcoCarguero extends Rol{
	
	private Barco barco;
	
	
	public BarcoCarguero(Barco barco){
		super();
		this.barco = barco;
	}
	
	public Barco getBarco() {
		return barco;
	}

	public void setBarco(Barco barco) {
		this.barco = barco;
	}

	
}
