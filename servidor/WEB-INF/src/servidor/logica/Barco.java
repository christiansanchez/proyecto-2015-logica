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
		this.mangueras  = new TreeMap<Integer, Boolean>();
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
		this.mangueras.put(posicion, estado);
	}
	
	public boolean getMangueras(int posicion){
		return this.mangueras.get(posicion);
	}
	
	public void mangueraDestruida(int posicion){		
		this.mangueras.remove(posicion);
		this.mangueras.put(posicion, false);
	}
}
