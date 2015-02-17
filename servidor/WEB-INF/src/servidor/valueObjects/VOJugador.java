package servidor.valueObjects;

import java.io.Serializable;

public class VOJugador implements Serializable {
	
	private static final long serialVersionUID = 2162466963728124041L;
	private Boolean estaJugando;
	private int rolJugador;
	private int posicionX;
	private int posicionY;
	
	public VOJugador(Boolean estaJugando, int rolJugador, int posX, int posY) {
		super();
		this.estaJugando = estaJugando;
		this.rolJugador = rolJugador;
		this.posicionX = posX;
		this.posicionY = posY;
	}

	public VOJugador() {
		this.estaJugando = false;
		this.rolJugador = 1;
	}
	
	public Boolean getEstaJugando() {
		return estaJugando;
	}
	
	public void setEstaJugando(Boolean estaJugando) {
		this.estaJugando = estaJugando;
	}
	
	public int getrolJugador() {
		return rolJugador;
	}
	
	public void setrolJugador(int rolJugador) {
		this.rolJugador = rolJugador;
	}

	
	public int getPosicionX() {
		return posicionX;
	}

	public void setPosicionX(int posicionX) {
		this.posicionX = posicionX;
	}

	public int getPosicionY() {
		return posicionY;
	}

	public void setPosicionY(int posicionY) {
		this.posicionY = posicionY;
	}
}