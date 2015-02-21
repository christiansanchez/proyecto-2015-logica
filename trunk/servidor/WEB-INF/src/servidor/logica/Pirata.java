package servidor.logica;

import java.util.TreeMap;

/*Clase encargada de manejar el rol Pirata*/
public class Pirata extends Rol{
	
	private TreeMap<Integer, Lancha> lanchas;
	
	public Pirata(){
		super();
	}
	
	public void pirataNuevo(){
		Lancha lancha = new Lancha();
		this.setLancha(1, lancha);
		this.setLancha(2, lancha);
		this.setLancha(3, lancha);
	}
		
	public void setLancha(int posicion, Lancha lancha){
		this.lanchas.put((Integer)posicion, lancha);
	}
	
	public boolean hasLancha(int posicion){
		Lancha lancha = this.getLancha(posicion); 
		return lancha.getImpactosPermitidos() > 0;
	}
	
	public Lancha getLancha(int posicion){
		return this.lanchas.get((Integer)posicion);
	}
	
	public void impactoLancha(int posicion){
		Lancha lancha = this.getLancha(posicion); 
		lancha.impactoLancha();
	}
}
