package servidor.valueObjects;

import java.io.Serializable;

import servidor.valueObjects.*;

public class VOCrearPartida {

	private static final long serialVersionUID = 8902394133124945751L;
	private String alias;
	private VOJugador jugador1;
	private VOEstadoPartida estado;
	private VOEscenario escenario;
	
	
	public VOCrearPartida(String alias, VOJugador jugador1,VOEstadoPartida estado, VOEscenario escenario) {
		super();
		this.alias = alias;
		this.jugador1 = jugador1;
		this.estado = estado;
		this.escenario = escenario;
	}
	
	public VOCrearPartida(String alias, VOJugador jugador1) {
		super();
		this.alias = alias;
		this.jugador1 = jugador1;
	}

	public String getAlias() {
		return alias;
	}
	
	public void setAlias(String alias){
		this.alias = alias;
	}

	public VOJugador getJugador1() {
		return this.jugador1;
	}

	public void setJugador1(VOJugador jugador1) {
		this.jugador1 = jugador1;
	}

	public VOEstadoPartida getEstado() {
		return estado;
	}

	public void setEstado(VOEstadoPartida estado) {
		this.estado = estado;
	}

	public VOEscenario getEscenario() {
		return escenario;
	}

	public void setEscenario(VOEscenario escenario) {
		this.escenario = escenario;
	}

	
}


