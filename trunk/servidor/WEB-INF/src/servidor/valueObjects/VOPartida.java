package servidor.valueObjects;

import java.io.Serializable;

import servidor.logica.EstadoPartida;
import servidor.logica.Mapa;
import servidor.logica.TipoMapa;

public class VOPartida implements Serializable{

	private static final long serialVersionUID = 8715964674695578642L;
	private int idPartida;
	private String nombre;
	private Mapa mapa;
	private EstadoPartida estado;

	public VOPartida(int idPartida, String nombrePartida) {
		this.idPartida = idPartida;
		this.nombre = nombrePartida;
		this.mapa = null;
		this.estado = null;
	}
	
	public int getIdPartida(){
		return this.idPartida;
	}
	
	public void setIdPartida(int idPartida){
		this.idPartida = idPartida;
	}
	
	public String getNombre(){
		return this.nombre;
	}
	
	public void setNombre(String nombrePartida){
		this.nombre = nombrePartida;				
	}	

	public Mapa getMapa(){
		return this.mapa;
	}
	
	public void setMapa(Mapa mapa){
		this.mapa = mapa;
	}
	
	public EstadoPartida getEstado(){
		return this.estado;
	}
	
	public void setEstado(EstadoPartida estado){
		this.estado = estado;
	}
	
	public String getEstadoStr(){
		String estadoStr = "";		
		if(this.estado == EstadoPartida.CREADA){
			estadoStr = "CREADA";
		}
		else if(this.estado == EstadoPartida.ENCURSO){
			estadoStr = "ENCURSO";
		}
		else if(this.estado == EstadoPartida.TERMINADA){
			estadoStr = "TERMINADA";
		}
		return estadoStr;
	}
	
	public String getTipoMapaStr(){
		String tipoMapaStr = "";
		if (this.mapa.getTipoMapa() == TipoMapa.MARABIERTO){;
			tipoMapaStr = "MARABIERTO";
		}
		else{
			tipoMapaStr = "ISLAS";
		}
		return tipoMapaStr;
	}
}
