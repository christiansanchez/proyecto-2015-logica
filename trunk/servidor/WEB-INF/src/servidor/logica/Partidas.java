package servidor.logica;

import java.util.TreeMap;

public class Partidas{

	private TreeMap<String, Partida> partidas;

	public Partidas() {		
		this.partidas = new TreeMap<String, Partida>();
	}

	public boolean member(String nombre) {
		boolean existe = this.partidas.containsKey(nombre);
		return existe;
	}

	public void insert(Partida partida) {	
		this.partidas.put(partida.getNombre(), partida);
	}

	/* Precondicion: Partida debe existir */
	public Partida find(String nombre) {
		return this.partidas.get(nombre);
	}

	/* Precondicion: debe existir la partida */
	public void delete(String nombre) {		
		this.partidas.remove(nombre);
	}
	
	public String getPartidasDisponibles(){
		String listadoPartidas = "";		
		for(Partida partida: this.partidas.values()){
			String partidaStr = "";
			if (partida.hasCreada()){				
				partidaStr += "nombrePartida:" + partida.getNombre() + "," + 
							  "tipoRolDisponible:" + partida.getRolDisponible() + "," +
							  "tipoMapa:" + partida.getTipoMapa() + ";";
			}
			listadoPartidas += partidaStr;
		}
		if (!listadoPartidas.isEmpty()){
			listadoPartidas = listadoPartidas.substring(0, listadoPartidas.length()-1);
		}	
		return listadoPartidas;
	}

}