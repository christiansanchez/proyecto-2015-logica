package servidor.logica;

import java.util.TreeMap;

/*
 *Clase encagadaa del manejo de la figuras de tipo barco,
 *las cuales son utilizadas por el rol barco carguero
 **/
public class Barco extends Figura{
	
	private TreeMap<Integer, Boolean> mangueras;
	
	public Barco(){
		super(0, 0);
	}
	
	public void barcoNuevo(){		
		this.setMangueras(1, true);
		this.setMangueras(2, true);
		this.setMangueras(3, true);
		this.setMangueras(4, true);
		this.setMangueras(5, true);
		this.setMangueras(6, true);
		this.setMangueras(7, true);
		this.setMangueras(8, true);
	}
	
	public void setMangueras(int posicion, boolean estado){
		this.mangueras.put((Integer)posicion, (Boolean)estado);
	}
	
	public boolean getMangueras(int posicion){
		return this.mangueras.get((Integer)posicion);
	}
	
	public void mangueraDestruida(int posicion){		
		this.mangueras.remove((Integer)posicion);
		this.mangueras.put((Integer)posicion, (Boolean)false);
	}
}
