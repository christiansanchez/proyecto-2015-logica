package servidor.valueObjects;

public class VOFigurasPartidas {

	private static final long serialVersionUID = 5335437815205567414L;
	
	private int id_partida;
	private int id_figura;
	private int largo;
	private int impactosPermitidos;
	private int mangueras;
	private VOCarguero carguero;
	private VOLancha lancha;
	private VOFigura figuras;
	
	public int getImpactosPermitidos() {
		return impactosPermitidos;
	}

	public void setImpactosPermitidos(int impactosPermitidos) {
		this.impactosPermitidos = impactosPermitidos;
	}

	public int getMangueras() {
		return mangueras;
	}

	public void setMangueras(int mangueras) {
		this.mangueras = mangueras;
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

