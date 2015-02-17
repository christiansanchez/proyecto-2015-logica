package servidor.valueObjects;

import java.io.Serializable;

public class VOEscenario implements Serializable{

	private static final long serialVersionUID = 5335437815205567414L;
	private int idEscenario;
	private int ancho;
	private int largo;
	private VOCarguero carguero;
	private VOLancha lancha;
	private VOFigura figuras_estaticas;
	
	public int getIdEscenario() {
		return idEscenario;
	}
	
	public void setIdEscenario(int idEscenario) {
		this.idEscenario = idEscenario;
	}
	
	public int getAncho() {
		return ancho;
	}
	
	public void setAncho(int ancho) {
		this.ancho = ancho;
	}
	
	public int getLargo() {
		return largo;
	}
	
	public void setLargo(int largo) {
		this.largo = largo;
	}
	
	public VOCarguero getCarguero() {
		return carguero;
	}
	
	public void setCarguero(VOCarguero carguero) {
		this.carguero = carguero;
	}
	
	public VOLancha getLancha() {
		return lancha;
	}
	
	public void setLancha(VOLancha lancha) {
		this.lancha = lancha;
	}		
}