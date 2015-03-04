package servidor.logica;

/*
 * Clase encargada de modelar un mapa para el juego.
 * */

public class Mapa {
	
	private TipoMapa tipoMapa;
	
	public Mapa(TipoMapa tipo){
		this.tipoMapa = tipo;
	}
	
	public void setTipoMapa(TipoMapa tipo){
		this.tipoMapa = tipo;
	}
	
	public TipoMapa getTipoMapa(){
		return this.tipoMapa;
	}
	
	public String getTipoMapaStr(){
		String tipoMapaStr = "";
		if (this.tipoMapa == TipoMapa.ISLAS){
			tipoMapaStr = "ISLAS";
		}
		else {
			tipoMapaStr = "MARABIERTO";
		}
		return tipoMapaStr;
	}
}
