package servidor.valueObjects;

public class VOFigurasPartidas {

	private static final long serialVersionUID = 5335437815205567414L;
	
	private int id;
	private int id_partida;
	private int id_figura;
	private float posicionX;
	private float posicionY;
	private int impactosPermitidos;
	private boolean mangueras;
	
	public VOFigurasPartidas(int id, int id_partida, int id_figura, float posicionX,
							 float posicionY, int impactosPermitidos, boolean mangueras){
		this.id = id;
		this.id_partida = id_partida;
		this.id_figura = id_figura;
		this.posicionX = posicionX;
		this.posicionY = posicionY;
		this.impactosPermitidos = impactosPermitidos;
		this.mangueras = mangueras;
	}
	
	public int getId_partida() {
		return id_partida;
	}
	
	public void setId_partida(int id_partida) {
		this.id_partida = id_partida;
	}
	
	public int getId_figura() {
		return id_figura;
	}
	
	public void setId_figura(int id_figura) {
		this.id_figura = id_figura;
	}
	
	public float getPosicionX() {
		return posicionX;
	}
	
	public void setPosicionX(float posicionX) {
		this.posicionX = posicionX;
	}
	
	public float getPosicionY() {
		return posicionY;
	}
	
	public void setPosicionY(float posicionY) {
		this.posicionY = posicionY;
	}
	
	public int getImpactosPermitidos() {
		return impactosPermitidos;
	}
	
	public void setImpactosPermitidos(int impactosPermitidos) {
		this.impactosPermitidos = impactosPermitidos;
	}
	
	public boolean getMangueras() {
		return mangueras;
	}
	
	public void setMangueras(boolean mangueras) {
		this.mangueras = mangueras;
	}	
}

