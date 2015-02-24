package db_Juego;

public class ConsultasAgregarCargaDB {
	
	public String insertFiguras()
	{
		String query = "insert into figuras values (?, ?);";		
		return query;
	}
}
