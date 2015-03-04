package servidor.logica;

/*
 * Clase encargada de modelar un jugador, los jugadores tienen un rol que es él que lo identifica en el juego*/

public class Jugador {
	
	private Rol rol;
	
	public Jugador(Rol rolJugador){
		this.rol = rolJugador;
	}
	
	public Rol getRol(){
		return this.rol;
	}
}
