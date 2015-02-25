package servidor.logica;

/*
 * Clase encargada de definir las caracteristicas
 * generales de las figuras del juego
 */
public abstract class Figura {
	
	private float posicionX;
	private float posicionY;
	private int angulo;
	
	public Figura(float posicionX, float posicionY){
		this.posicionX = posicionX;
		this.posicionY = posicionY;
		this.angulo = 0;
	}
	
	public int getAngulo(){
		return this.angulo;
	}
	
	public void setAngulo(int angulo){
		this.angulo = angulo;
	}
	
	public void setPosicionX(float posicionX){
		this.posicionX = posicionX;
	}
	
	public void setPosicionY(float posicionY){
		this.posicionY = posicionY;		
	}
	
	public float getPosicionX(){
		return this.posicionX;		
	}
	
	public float getPosicionY(){
		return this.posicionY;
	}

}
