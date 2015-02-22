package servidor.logica;

/*Datos de la partida*/
public class Partida {
	
	private String nombre;
	private Jugador barcoCarguero;
	private Jugador lanchaPirata;
	private Mapa mapa;
	private EstadoPartida estadoPartida;
	
	public Partida(String nombre, Jugador barco, Jugador lancha, Mapa mapa)  {		
		this.nombre = nombre;
		this.barcoCarguero = barco;
		this.lanchaPirata = lancha;
		this.mapa = mapa;
		this.estadoPartida = EstadoPartida.CREADA;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Jugador getBarcoCarguero() {
		return barcoCarguero;
	}

	public void setBarcoCarguero(Jugador barcoCarguero) {
		this.barcoCarguero = barcoCarguero;
	}

	public Jugador getLanchaPirata() {
		return lanchaPirata;
	}

	public void setLanchaPirata(Jugador lanchaPirata) {
		this.lanchaPirata = lanchaPirata;
	}

	public Mapa getMapa() {
		return mapa;
	}

	public void setMapa(Mapa mapa) {
		this.mapa = mapa;
	}

	public EstadoPartida getEstadoPartida() {
		return estadoPartida;
	}

	public void setEstadoPartida(EstadoPartida estadoPartida) {
		this.estadoPartida = estadoPartida;
	}
	
	public boolean hasCreada(){
		return this.estadoPartida == EstadoPartida.CREADA;
	}
	
	public String getRolDisponible(){
		String rolDisponible = "";
		if (this.barcoCarguero != null){
			rolDisponible = "BARCOCARGUERO";
		}
		else{
			rolDisponible = "PIRATA";
		}
		return rolDisponible;
	}
	
	public String getTipoMapa(){
		return this.mapa.getTipoMapaStr();
	}
}
