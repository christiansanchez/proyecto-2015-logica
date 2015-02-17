package servidor.logica;

/* Clase abstracta encargada de definir las caracteristicas
* generales de los roles que participan en el juego*/
public abstract class Rol {
	
	private int rangoVision;

	public int getRangoVision() {
		return rangoVision;
	}

	public void setRangoVision(int rangoVision) {
		this.rangoVision = rangoVision;
	}
	
	

}
