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
	
	public String updatePartidaCreadaToEnCurso(){
		String query = "update partidas set estado='EnCurso' where id_partida = ? and nombre = ? and estado='Creada';";
		return query;
	}
	
	public String existePartidaEnCurso(){
		String query = "select * from partidas where nombre = ? and estado = 'EnCurso';";
		return query;
	}

	public String updatePartidaEnCursoToCreada(){
		String query = "update partidas set estado='Creada' where nombre = ? and estado='EnCurso'";
		return query;
	}
	
	public String updatePartidaEnCursoToTerminada(){
		String query = "update partidas set estado='Terminada' where nombre = ? and estado='EnCurso'";
		return query;
	}
	
	public String findPartidasEnCurso(){
		String query = "select * from partidas where estado = 'EnCurso' and nombre = ?;";
		return query;
	}
	
	public String agregarPartidaEnCurso(){
		String query = "insert into partidas values (?, ?, ?);";
		return query;
	}
}
