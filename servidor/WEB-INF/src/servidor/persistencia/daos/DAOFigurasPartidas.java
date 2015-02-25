package servidor.persistencia.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import servidor.logica.*;
import servidor.excepciones.FigurasPartidasIdPartidaException;
import servidor.excepciones.GuardarFigurasPartidasException;
import servidor.excepciones.ListarFigurasPartidasException;
import servidor.valueObjects.*;
import servidor.persistencia.consultas.ConsultaFigurasPartidas;
import servidor.persistencia.poolConexiones.Conexion;
import servidor.persistencia.poolConexiones.IConexion;

public class DAOFigurasPartidas implements IDAOFigurasPartidas{
	
	private ConsultaFigurasPartidas consultas;
	
	public DAOFigurasPartidas(){		
		this.consultas = new ConsultaFigurasPartidas();			
	}
	
	public List<VOFigurasPartidas> listarFigurasPartidas(IConexion iConn) throws ListarFigurasPartidasException 
	{
		try {
			String query = this.consultas.allFigurasPartidas();
			PreparedStatement pstmt = ((Conexion)iConn).getConnection().prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			List<VOFigurasPartidas> list = new LinkedList<VOFigurasPartidas>();			
			while(rs.next()){
				int id = rs.getInt("id");
				int idPartida = rs.getInt("id_partida");
				int idFigura = rs.getInt("id_figura");
				int posicionX = rs.getInt("posicion_x");
				int posicionY = rs.getInt("posicion_y");
				int impactosPermitidos = rs.getInt("impactosPermitidos");
				int mangueras = rs.getInt("mangueras");
//				VOFigurasPartidas voFigPartidas = new VOFigurasPartidas(id, idPartida, idFigura, posicionX, posicionY,
//																		impactosPermitidos, mangueras);
//				list.add(voFigPartidas);
			}
			rs.close();
			pstmt.close();
			return list;
		}
		catch(SQLException eSql){
			throw new ListarFigurasPartidasException();
		}
	}	
	
//	public void insert(IConexion iConn, Figura fig, int idPartida) throws GuardarFigurasPartidasException {				
//		try {
//			String query = this.consultas.insertarFigurasPartidas();
//			PreparedStatement pstmt = ((Conexion)iConn).getConnection().prepareStatement(query);
//			pstmt.setInt(1, idPartida);
////			pstmt.setInt(2, fig.geidFigura());
////			pstmt.setInt(3), fig.getPosicionX());
////			pstmt.setInt(4, fig.getPosicionY());
//			
//			int impactosPermitidos = 0;
//			int mangueras = 0;
//			if(fig.getClass().equals("Barco")) {
////				mangueras = (fig.getMangueras();
//			}
//			else if(fig.getClass().equals("Lancha")){
////				impactosPermitidos = (fig.getImpcatosPermitidos();
//			}
//					  						
//		} 
//		catch (SQLException e) {
//			throw new GuardarFigurasPartidasException();
//		}		
//	}
	
	public boolean member(IConexion iConn){
		return true;
	}
	
	public List<VOFigurasPartidas> listarFigurasPartidasIdPartida(IConexion iConn, int idP) throws FigurasPartidasIdPartidaException{		
		try {
			String query = this.consultas.allFigurasPartidasIdPartida();
			PreparedStatement pstmt = ((Conexion)iConn).getConnection().prepareStatement(query);
			pstmt.setInt(1, idP);
			ResultSet rs = pstmt.executeQuery();
			List<VOFigurasPartidas> list = new LinkedList<VOFigurasPartidas>();			
			while(rs.next()){
				int id = rs.getInt("id");
				int idPartida = rs.getInt("id_partida");
				int idFigura = rs.getInt("id_figura");
				float posicionX = rs.getFloat("posicion_x");
				float posicionY = rs.getFloat("posicion_y");
				int impactosPermitidos = rs.getInt("impactos_permitidos");
				boolean mangueras = rs.getBoolean("mangueras");
				int angulo = rs.getInt("angulo");
				VOFigurasPartidas voFiguraPartida = new VOFigurasPartidas(id, idPartida, idFigura, posicionX, 
						   												  posicionY, impactosPermitidos, mangueras, angulo);
				list.add(voFiguraPartida);
			}
			rs.close();
			pstmt.close();
			return list;
		} catch (SQLException e) {
			throw new FigurasPartidasIdPartidaException();
		}
	}

}
