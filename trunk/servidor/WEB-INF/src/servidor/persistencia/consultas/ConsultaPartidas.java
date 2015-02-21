package servidor.persistencia.consultas;

public class ConsultaPartidas {

	public String allPartidas() {
		String query = "select * from partidas;";
		return query;
	}

	public String findPartidas() {
		String query = "select * from partidas where alias = ? and encurso = 1;";
		return query;
	}

	public String insertPartidas() {
		String query = "insert into partidas values (?, ?, ?);";
		return query;
	}

	public String deletePartidas() {
		String query = "update partidas set activa = 0 where alias;";
		return query;
	}
}
