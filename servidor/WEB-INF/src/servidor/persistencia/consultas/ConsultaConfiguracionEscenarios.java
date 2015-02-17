package servidor.persistencia.consultas;


public class ConsultaConfiguracionEscenarios {

	public String allConfiguracionesEscenarios(){
		String query = "select * from configuraciones_escenarios;";
		return query;
	}
	
	public String findConfiguracionesEscenarios(){
		String query = "select * from configuraciones_escenarios where id_escenario = ? and id_figura = ?;";
		return query;
	}
}
