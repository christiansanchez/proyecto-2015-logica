package servidor.persistencia.daos;
	

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import servidor.excepciones.ActualizarEstadoPartidaException;
import servidor.excepciones.AgregarPartidaException;
import servidor.excepciones.BuscarPartidasException;
import servidor.excepciones.EliminarPartidaException;
import servidor.excepciones.ExistePartidaEnCursoException;
import servidor.excepciones.ListarPartidasCreadasException;
import servidor.logica.EstadoPartida;
import servidor.logica.Mapa;
import servidor.logica.TipoMapa;
import servidor.persistencia.consultas.ConsultaPartidas;
import servidor.persistencia.poolConexiones.Conexion;
import servidor.persistencia.poolConexiones.IConexion;
import servidor.valueObjects.VOPartida;

public class DAOPartidas implements IDAOPartidas{

	private ConsultaPartidas consultas;

	public DAOPartidas(){		
		this.consultas = new ConsultaPartidas();			
	}

	@Override
	public List<VOPartida> listarPartidasCreadas(IConexion iConn) throws ListarPartidasCreadasException{
		try{
			String query = this.consultas.findPartidasCreadas();
			PreparedStatement pstmt = ((Conexion)iConn).getConnection().prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			List<VOPartida> list = new LinkedList<VOPartida>();	
			while(rs.next()){
				int idPartida = rs.getInt("id_partida");
				String nombrePartida = rs.getString("nombre");				
				VOPartida voPartida = new VOPartida(idPartida, nombrePartida);
				String tipoMapaStr = rs.getString("tipo_mapa");
				TipoMapa tipoMapa;
				if (tipoMapaStr.equals("Islas")){
					tipoMapa = TipoMapa.ISLAS;
				}
				else{
					tipoMapa = TipoMapa.MARABIERTO;
				}
				Mapa mapa = new Mapa(tipoMapa);
				voPartida.setMapa(mapa);
				EstadoPartida estado = EstadoPartida.CREADA;
				voPartida.setEstado(estado);
				list.add(voPartida);
			}
			return list;
		}
		catch(SQLException eSql) {
			throw new ListarPartidasCreadasException();
		}
	}

	@Override
	public VOPartida find(IConexion iConn, String nombrePartida)throws BuscarPartidasException{
		try {			
			String query = this.consultas.findPartidasNombre();
			PreparedStatement pstmt = ((Conexion)iConn).getConnection().prepareStatement(query);
			pstmt.setString(1, nombrePartida);		
			ResultSet rs = pstmt.executeQuery();			
			VOPartida voPartida = null;
			if (rs.next())
			{
				int idPartida = rs.getInt("id_partida");
				String nombre = rs.getString("nombre");				
				voPartida = new VOPartida(idPartida, nombre);
				String tipoMapaStr = rs.getString("tipo_mapa");
				TipoMapa tipoMapa;
				if (tipoMapaStr.equals("Islas")){
					tipoMapa = TipoMapa.ISLAS;
				}
				else{
					tipoMapa = TipoMapa.MARABIERTO;
				}
				Mapa mapa = new Mapa(tipoMapa);
				voPartida.setMapa(mapa);
			}
			rs.close();
			pstmt.close();
			return voPartida;				
		}	
		catch(SQLException e){
			throw new BuscarPartidasException();
		}	
	}
	
	@Override
	public void updatePartidaCredaToEnCurso(IConexion iConn, String nombrePartida, int idPartida) throws ActualizarEstadoPartidaException{
		try {			
			String query = this.consultas.updatePartidaCreadaToEnCurso();
			PreparedStatement pstmt = ((Conexion)iConn).getConnection().prepareStatement(query);
			pstmt.setString(2, nombrePartida);
			pstmt.setInt(1, idPartida);
			pstmt.executeUpdate();			
			pstmt.close();				
		}	
		catch(SQLException e){
			throw new ActualizarEstadoPartidaException();
		}
	}
	
	@Override
	public boolean hasPartidaEnCurso(IConexion iConn, String nombrePartida) throws ExistePartidaEnCursoException{
		try {							
			boolean resultado = false;
			String query = this.consultas.existePartidaEnCurso();
			PreparedStatement pstmt = ((Conexion)iConn).getConnection().prepareStatement(query);
			pstmt.setString(1, nombrePartida);			
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
			{
				resultado = true;
			}			
			pstmt.close();
			return resultado;
			
		}	
		catch(SQLException e){
			throw new ExistePartidaEnCursoException();
		}
	}
	
	@Override
	public void updatePartidaEnCursoToCreada(IConexion iConn, String nombrePartida) throws ActualizarEstadoPartidaException{
		try {			
			String query = this.consultas.updatePartidaEnCursoToCreada();
			PreparedStatement pstmt = ((Conexion)iConn).getConnection().prepareStatement(query);
			pstmt.setString(1, nombrePartida);
			pstmt.executeUpdate();			
			pstmt.close();				
		}	
		catch(SQLException e){
			throw new ActualizarEstadoPartidaException();
		}
	}
	
	@Override
	public void updatePartidaEnCursoToTerminada(IConexion iConn, String nombrePartida) throws ActualizarEstadoPartidaException{
		try {			
			String query = this.consultas.updatePartidaEnCursoToTerminada();
			PreparedStatement pstmt = ((Conexion)iConn).getConnection().prepareStatement(query);
			pstmt.setString(1, nombrePartida);
			pstmt.executeUpdate();			
			pstmt.close();				
		}	
		catch(SQLException e){
			throw new ActualizarEstadoPartidaException();
		}
	}
	
	@Override
	public VOPartida findPartidaEnCurso(IConexion iConn, String nombrePartida) throws BuscarPartidasException{
		try {			
			String query = this.consultas.findPartidasEnCurso();
			PreparedStatement pstmt = ((Conexion)iConn).getConnection().prepareStatement(query);
			pstmt.setString(1, nombrePartida);		
			ResultSet rs = pstmt.executeQuery();			
			VOPartida voPartida = null;
			if (rs.next())
			{
				int idPartida = rs.getInt("id_partida");
				String nombre = rs.getString("nombre");				
				voPartida = new VOPartida(idPartida, nombre);
				String tipoMapaStr = rs.getString("tipo_mapa");
				TipoMapa tipoMapa;
				if (tipoMapaStr.equals("Islas")){
					tipoMapa = TipoMapa.ISLAS;
				}
				else{
					tipoMapa = TipoMapa.MARABIERTO;
				}
				Mapa mapa = new Mapa(tipoMapa);
				voPartida.setMapa(mapa);
			}
			rs.close();
			pstmt.close();
			return voPartida;				
		}	
		catch(SQLException e){
			throw new BuscarPartidasException();
		}	
	}
	
	@Override
	public void agregarPartidaEnCurso(IConexion iConn, VOPartida voPartida) throws AgregarPartidaException{
		try	{				
			String query = this.consultas.agregarPartidaEnCurso();
			PreparedStatement pstmt = ((Conexion)iConn).getConnection().prepareStatement(query);
			String nombrePartida = voPartida.getNombre();
			String tipoMapa = voPartida.getTipoMapaStrDB();
			String estadoPartida = "Creada";
			pstmt.setString(1, nombrePartida);
			pstmt.setString(2, tipoMapa);
			pstmt.setString(3, estadoPartida);				
			pstmt.executeUpdate();				
			pstmt.close();			
		}
		catch(SQLException e) {
			throw new AgregarPartidaException();
		}	
	}
	
	public void eliminarPartida(IConexion iConn, int idPartida) throws EliminarPartidaException{
		try	{				
			String query = this.consultas.eliminarPartidaId();
			PreparedStatement pstmt = ((Conexion)iConn).getConnection().prepareStatement(query);			
			pstmt.setInt(1, idPartida);						
			pstmt.executeUpdate();				
			pstmt.close();			
		}
		catch(SQLException e) {
			throw new EliminarPartidaException();
		}	
	}
	
}



