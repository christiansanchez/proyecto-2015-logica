package servidor.persistencia.consultas;

public class ConsultaEstadoPartida {

	public String allEstadosPartidas(){
		String query = "select estado from partidas";
		return query;
	}
	
	public String findPartidaNombre(){
		String query = "select * from partidas where = ?";
		return query;
	}
}
