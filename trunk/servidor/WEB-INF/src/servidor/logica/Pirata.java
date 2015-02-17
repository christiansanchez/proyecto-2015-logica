package servidor.logica;
/*Clase encargada de manejar el rol Pirata*/
public class Pirata extends Rol{
	
	private Lancha[] lanchas;
	private int cantMaxLanchas;
	private int topeLanchas;
	
	public Pirata(){
		super();
		this.cantMaxLanchas = 3;
		this.lanchas = new Lancha[cantMaxLanchas];
	}
	
	public void setLancha(Lancha lancha){
		//Aca hay que agregar las lanchas al array de lanchas
	}
	
	public Lancha getLancha(int posLancha){
		return this.lanchas[posLancha];
	}
	
	public void impactoLancha(){
		//aca se debe controlar si la cantidad de impactos dela lancha es igual o menor a cero
		// hay que eliminarla del array, cuando queda el array en cero es que perdio todas las lanchas
	}
}
