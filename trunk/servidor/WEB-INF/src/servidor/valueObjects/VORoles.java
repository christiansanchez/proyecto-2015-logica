package servidor.valueObjects;

import java.io.Serializable;


public class VORoles implements Serializable{


		private static final long serialVersionUID = -572015651277653125L;
		private int idRol;
		private String nombre;
		
		public VORoles(){
			
		}
		
		public VORoles(int id, String nombre){
			this.idRol = id;
			this.nombre = nombre;
		}

		public int getIdRol() {
			return idRol;
		}

		public void setIdRol(int idRol) {
			this.idRol = idRol;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}		
							
}
