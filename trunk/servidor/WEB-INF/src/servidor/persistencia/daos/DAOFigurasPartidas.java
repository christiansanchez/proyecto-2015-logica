package servidor.persistencia.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import servidor.excepciones.AgregarFigurasPartidasException;
import servidor.excepciones.FigurasPartidasIdPartidaException;
import servidor.persistencia.consultas.ConsultaFigurasPartidas;
import servidor.persistencia.poolConexiones.Conexion;
import servidor.persistencia.poolConexiones.IConexion;
import servidor.valueObjects.VOFigurasPartidas;

public class DAOFigurasPartidas implements IDAOFigurasPartidas{
	
	private ConsultaFigurasPartidas consultas;
	
	public DAOFigurasPartidas(){		
		this.consultas = new ConsultaFigurasPartidas();			
	}
	
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
	
	public void agregarFigurasPartidas(IConexion iConn, List<VOFigurasPartidas> listaFiguras) throws AgregarFigurasPartidasException{
		try	{
			String query = this.consultas.insertarFigurasPartidas();
			PreparedStatement pstmt = ((Conexion)iConn).getConnection().prepareStatement(query);			
			for(VOFigurasPartidas voFigPart: listaFiguras){
				pstmt.setInt(1, voFigPart.getId_partida());
				pstmt.setInt(2, voFigPart.getId_figura());
				pstmt.setFloat(3, voFigPart.getPosicionX());
				pstmt.setFloat(4, voFigPart.getPosicionY());	
				pstmt.setInt(5, voFigPart.getImpactosPermitidos());	
				pstmt.setBoolean(6, voFigPart.getMangueras());	
				pstmt.setInt(7, voFigPart.getAngulo());	
				pstmt.executeUpdate();
			}
			pstmt.close();
		}
		catch(SQLException e) {
			throw new AgregarFigurasPartidasException();
		}
	}

}
