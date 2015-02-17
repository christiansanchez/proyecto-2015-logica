package servidor.persistencia.consultas;

public class ConsultaEstadoPartida {

	public String allEstadosPartidas(){
		String query = "select * from estados_partidas";
		return query;
	}
	
	public String findPartidaNombre(){
		String query = "select * from estados_partidas where = ?";
		return query;
	}
}
