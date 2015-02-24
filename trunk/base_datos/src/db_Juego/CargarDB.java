package db_Juego;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CargarDB {
	
	private Connection conn;
	private ConsultasAgregarCargaDB consultas;
	
	public CargarDB(Connection conn){
		this.conn = conn;
		this.consultas = new ConsultasAgregarCargaDB();
	}
	
	public void cargarFiguras()throws SQLException{
		
		String query = this.consultas.insertFiguras();
		PreparedStatement pstmt = this.conn.prepareStatement(query);				
		
		//figuras
		pstmt.setInt(1, ConstantesDB.ID_MANGUERA1);				
		pstmt.setString(2, ConstantesDB.TIPO_MANGUERA1);	
		pstmt.executeUpdate();
		
		pstmt.setInt(1, ConstantesDB.ID_MANGUERA2);				
		pstmt.setString(2, ConstantesDB.TIPO_MANGUERA2);	
		pstmt.executeUpdate();
				
		pstmt.setInt(1, ConstantesDB.ID_MANGUERA3);				
		pstmt.setString(2, ConstantesDB.TIPO_MANGUERA3);	
		pstmt.executeUpdate();
		
		pstmt.setInt(1, ConstantesDB.ID_MANGUERA4);				
		pstmt.setString(2, ConstantesDB.TIPO_MANGUERA4);	
		pstmt.executeUpdate();
		
		pstmt.setInt(1, ConstantesDB.ID_MANGUERA5);				
		pstmt.setString(2, ConstantesDB.TIPO_MANGUERA5);	
		pstmt.executeUpdate();
		
		pstmt.setInt(1, ConstantesDB.ID_MANGUERA6);				
		pstmt.setString(2, ConstantesDB.TIPO_MANGUERA6);	
		pstmt.executeUpdate();
		
		pstmt.setInt(1, ConstantesDB.ID_MANGUERA7);				
		pstmt.setString(2, ConstantesDB.TIPO_MANGUERA7);	
		pstmt.executeUpdate();
		
		pstmt.setInt(1, ConstantesDB.ID_MANGUERA8);				
		pstmt.setString(2, ConstantesDB.TIPO_MANGUERA8);	
		pstmt.executeUpdate();
		
		pstmt.setInt(1, ConstantesDB.ID_BARCO);				
		pstmt.setString(2, ConstantesDB.TIPO_BARCO);	
		pstmt.executeUpdate();
		
		pstmt.setInt(1, ConstantesDB.ID_LANCHA2);				
		pstmt.setString(2, ConstantesDB.TIPO_LANCHA2);	
		pstmt.executeUpdate();
		
		pstmt.setInt(1, ConstantesDB.ID_LANCHA1);				
		pstmt.setString(2, ConstantesDB.TIPO_LANCHA1);	
		pstmt.executeUpdate();
		
		pstmt.setInt(1, ConstantesDB.ID_LANCHA3);				
		pstmt.setString(2, ConstantesDB.TIPO_LANCHA3);	
		pstmt.executeUpdate();	

		pstmt.close();	
	}
		
	
	
	
}
