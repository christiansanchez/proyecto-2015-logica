package servidor.valueObjects;

import java.io.Serializable;

public class VOPartida implements Serializable{

		private static final long serialVersionUID = 8715964674695578642L;
		private int idPartida;
		private String alias;
		private boolean activa;
		
		public int getIdPartida() {
			return idPartida;
		}
		
		public void setIdPartida(int idPartida) {
			this.idPartida = idPartida;
		}
		
		public String getAlias() {
			return alias;
		}
		
		public void setAlias(String alias) {
			this.alias = alias;
		}
		
		public boolean isActiva() {
			return activa;
		}
		
		public void setActiva(boolean activa) {
			this.activa = activa;
		}
		
}
