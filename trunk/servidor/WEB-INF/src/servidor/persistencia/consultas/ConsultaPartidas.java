package servidor.persistencia.consultas;

public class ConsultaPartidas {
	
	public String findPartidasCreadas(){
		String query = "select * from partidas where estado = 'Creada';";
		return query;
	}
	
	public String findPartidasNombre(){
		String query = "select * from partidas where nombre = ? and estado = 'Creada';";
		return query;
	}
	
	public String updatePartidaCreadaToEncurso(){
		String query = "update partidas set estado='EnCurso' where id_partida = ? and nombre = ?;";
		return query;
	}
}
