package servidor.logica;

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
}
