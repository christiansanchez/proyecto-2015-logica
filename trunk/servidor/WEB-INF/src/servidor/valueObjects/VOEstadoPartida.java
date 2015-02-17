package servidor.valueObjects;

import java.io.Serializable;

public class VOEstadoPartida implements Serializable{

		private static final long serialVersionUID = -572015651277653125L;
		private int idEstadoPartida;
		private String nombre;
		
		public VOEstadoPartida(int idEstadoPartida, String nombre) {
			super();
			this.idEstadoPartida = idEstadoPartida;
			this.nombre = nombre;
		}

		public int getIdEstadoPartida() {
			return idEstadoPartida;
		}

		public void setIdEstadoPartida(int idEstadoPartida) {
			this.idEstadoPartida = idEstadoPartida;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}			
}


