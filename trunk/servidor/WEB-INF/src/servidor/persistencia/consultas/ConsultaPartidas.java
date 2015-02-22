package servidor.persistencia.consultas;

public class ConsultaPartidas {
	
	public String findPartidasCreadas(){
		String query = "select * from partidas where estado = 'Creada';";
		return query;
	}
}
