package servidor.persistencia.daos;
	

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import servidor.excepciones.BorrarPartidasException;
import servidor.excepciones.BuscarPartidasException;
import servidor.excepciones.ExistePartidasException;
import servidor.excepciones.IngresarPartidasException;
import servidor.excepciones.ListarPartidasException;
import servidor.valueObjects.VOPartidas;
import servidor.valueObjects.VOFigurasPartidas;
import servidor.persistencia.consultas.ConsultaPartidas;
import servidor.persistencia.poolConexiones.Conexion;
import servidor.persistencia.poolConexiones.IConexion;

public class DAOPartidas implements IDAOPartidas{
	
	private ConsultaPartidas consultas;
	
	public DAOPartidas(){		
		this.consultas = new ConsultaPartidas();			
	}
	
	
	public List<VOPartidas> listarPartidas(IConexion iConn) throws ListarPartidasException 
	{
		try	{
			String query = this.consultas.allPartidas();
			PreparedStatement pstmt = ((Conexion)iConn).getConnection().prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			List<VOPartidas> list = new LinkedList<VOPartidas>();			
			while(rs.next()){
				int idPartida = rs.getInt("id");
				String nombre = rs.getString("nombre");				
				VOPartidas voPartidas = new VOPartidas(nombre);
				voPartidas.setIdPartida(idPartida);								
				list.add(voPartidas);
			}
			rs.close();
			pstmt.close();
			return list;
		}
		catch(SQLException eSql) {
			throw new ListarPartidasException();
		}
	}

	
	public int insert(IConexion iConn, String alias, int idNivel) throws IngresarPartidasException {
		try	{
			if(!member(iConn, alias)) {				
				String query = this.consultas.insertPartidas();
				PreparedStatement pstmt = ((Conexion)iConn).getConnection().prepareStatement(query);
				pstmt.setInt(1, idNivel);
				pstmt.setString(2, alias);
				pstmt.setInt(3, 1);				
				pstmt.executeUpdate();	
				query = this.consultas.findPartidas();
				pstmt.setString(1, alias);
				ResultSet rs = pstmt.executeQuery();
				int idPartida = 0;
				if (rs.next())
				{
					idPartida = rs.getInt("id");
				}
				rs.close();
				pstmt.close();	
				return idPartida;
			}
			else {
				throw new IngresarPartidasException("La partida ya existe");
			}
		}
		catch(ExistePartidasException | SQLException e) {
			throw new IngresarPartidasException();
		}	
	}
	
	public boolean member(IConexion iConn, String alias) throws ExistePartidasException{
		try {
			String query = this.consultas.findPartidas();
			PreparedStatement pstmt = ((Conexion)iConn).getConnection().prepareStatement(query);
			pstmt.setString(1, alias);		
			boolean existe = false;		
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
			{
				existe = true;
			}
			rs.close();
			pstmt.close();
			return existe;	
		}		
		catch(SQLException eSql) {
			throw new ExistePartidasException();
		}
		
	}
	
	public void delete(IConexion iConn, String alias) throws BorrarPartidasException{
		try	{
			if(member(iConn, alias)) {
				String query = this.consultas.deletePartidas();
				PreparedStatement pstmt = ((Conexion)iConn).getConnection().prepareStatement(query);
				pstmt.setString(1, alias);				
				pstmt.executeUpdate();	
				pstmt.close();
			}
			else {
				throw new BorrarPartidasException("La partida no existe");
			}
		}
		catch(SQLException | ExistePartidasException e) {
			throw new BorrarPartidasException();
		}		
	}

	public VOPartidas find(IConexion iConn, String alias)throws BuscarPartidasException {
		try {
			if(member(iConn, alias)) {	
				String query = this.consultas.findPartidas();
				PreparedStatement pstmt = ((Conexion)iConn).getConnection().prepareStatement(query);
				pstmt.setString(1, alias);		
				ResultSet rs = pstmt.executeQuery();
				VOPartidas voPartida = new VOPartidas();
				if (rs.next())
				{
					int idPartida = rs.getInt("id");
					String nombre = rs.getString("alias");
					//no tengo claro como invocar el tipo de datos MAPA y EstadoPartida 
					//por eso puse string
					String mapa = rs.getString(mapa);
					String estado = rs.getString("estado");					
					voPartida.setNombre(nombre);
					voPartida.setIdPartida(idPartida);			
					voPartida.setMapa(mapa);
					voPartida.setEstado(estado);
				}
				rs.close();
				pstmt.close();
				return voPartida;						
			}			
			else{
				throw new BuscarPartidasException("No existe la partida que se busca");
			}
				
		}		
		catch(ExistePartidasException | SQLException e) {
			throw new BuscarPartidasException();
		}	
	}

		
}



