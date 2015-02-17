package servidor.logica;
/*
 *Clase encagadaa del manejo de la figuras de tipo lancha,
 *las cuales son utilizadas por el rol pirata
 **/
public class Lancha extends Figura{

	private int impactosPermitidos;
	
	public Lancha(float posicionX, float posicionY, int impPermitidos) {
		super(posicionX, posicionY);
		this.impactosPermitidos = impPermitidos;
	}
	
	public void setImpactosPermitidos(int impPermitidos){
		this.impactosPermitidos = impPermitidos;
	}
	
	public int getImpactosPermitidos(){
		return this.impactosPermitidos;
	}
	
	public void impactoLancha(){
		this.impactosPermitidos = this.impactosPermitidos - 1;
	}
	
}
