package servidor.valueObjects;

import java.io.Serializable;

public class VORetomarPartida implements Serializable{
	
	private static final long serialVersionUID = -1679255213815173834L;
	private VOJugador jugador1;
	private String alias;
	
	public VOJugador getJugador1() {
		return jugador1;
	}
	
	public void setJugador1(VOJugador jugador1) {
		this.jugador1 = jugador1;
	}
	
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}	
}
