package servidor.valueObjects;

import java.io.Serializable;

import servidor.valueObjects.VOJugador;

public class VOCarguero extends VOJugador implements Serializable {

	private static final long serialVersionUID = 1L;
	private int rangoVis;
	private int resist;
	private int vel;
	private int rangoAtaque;
	private int artilleria;
		
	public VOCarguero(Boolean estaJugando, int rolJugador, int posX, int posY, int rangVis, int resist, int vel, int rangoAtaque, int artilleria) {
		super();
		this.rangoVis = rangoVis;
		this.setResist(resist);
		this.setVel(vel);
		this.setRangoAtaque(rangoAtaque);
		this.artilleria = artilleria;
			
	}

	public int getResist() {
		return resist;
	}


	public void setResist(int resist) {
		this.resist = resist;
	}


	public int getVel() {
		return vel;
	}


	public void setVel(int vel) {
		this.vel = vel;
	}


	public int getRangoAtaque() {
		return rangoAtaque;
	}


	public void setRangoAtaque(int rangoAtaque) {
		this.rangoAtaque = rangoAtaque;
	}



	public int getArtilleria() {
		return artilleria;
	}
		
	public void setArtilleria(int artilleria) {
		this.artilleria = artilleria;
	}

	
	
	
}
	
