package servidor.logica;
/*
 *Clase encagadaa del manejo de la figuras de tipo barco,
 *las cuales son utilizadas por el rol barco carguero
 **/
public class Barco extends Figura{
	
	private int mangueras;
	
	public Barco(float posicionX, float posicionY, int cantMangueras){
		super(posicionX, posicionY);
		this.mangueras = cantMangueras;
	}
	
	public void setMangueras(int cantMangueras){
		this.mangueras = cantMangueras;
	}
	
	public int getMangueras(){
		return this.mangueras;
	}
	
	public void mangueraDestruida(){
		this.mangueras = this.mangueras - 1;
	}
}
