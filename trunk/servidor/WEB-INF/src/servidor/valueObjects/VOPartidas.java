package servidor.valueObjects;


import java.io.Serializable;

import servidor.logica.*;

public class VOPartidas implements Serializable{

		private static final long serialVersionUID = 8715964674695578642L;
		private int idPartida;
		private String nombre;
		private Mapa mapa;
		private EstadoPartida estado;
		
		public VOPartidas(String nombre2) {
			// TODO Auto-generated constructor stub
		}

		public EstadoPartida getEstado() {
			return estado;
		}

		public void setEstado(EstadoPartida estado) {
			this.estado = estado;
		}

		public Mapa getMapa() {
			return mapa;
		}

		public void setMapa(Mapa mapa) {
			this.mapa = mapa;
		}

		
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		private EstadoPartida estadoPartida;
		
		public int getIdPartida() {
			return idPartida;
		}
		
		public void setIdPartida(int idPartida) {
			this.idPartida = idPartida;
		}
		
		public String getNombre() {
			return nombre;
		}
		
		public void setAlias(String alias) {
			this.nombre = alias;
		}
		
		
}
