package servidor.persistencia.consultas;

public class ConsultaFigurasPartidas {

	public String allFigurasPartidas(){
		String query = "select * from figuras_partidas";
		return query;
	}
	
	public String insertarFigurasPartidas(){
		String query = "insert into figuras_partidas set id_partida = ?, id_figura = ?, posicion_x = ?, posicion_y = ?, " +
					   "impactos_permitidos = ?, mangueras = ?, angulo = ?;";
		return query;
	}

	public String allFigurasPartidasIdPartida(){
		String query = "select * from figuras_partidas where id_partida = ?";
		return query;
	}
}