package servidor.persistencia.daos;
	

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import servidor.excepciones.ActualizarEstadoPartidaException;
import servidor.excepciones.BuscarPartidasException;
import servidor.excepciones.ListarPartidasCreadasException;
import servidor.valueObjects.VOPartida;
import servidor.logica.EstadoPartida;
import servidor.logica.Mapa;
import servidor.logica.TipoMapa;
import servidor.persistencia.consultas.ConsultaPartidas;
import servidor.persistencia.poolConexiones.Conexion;
import servidor.persistencia.poolConexiones.IConexion;

public class DAOPartidas implements IDAOPartidas{

	private ConsultaPartidas consultas;

	public DAOPartidas(){		
		this.consultas = new ConsultaPartidas();			
	}

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
	
	public void updatePartidaCredaToEnCurso(IConexion iConn, String nombrePartida, int idPartida) throws ActualizarEstadoPartidaException{
		try {			
			String query = this.consultas.updatePartidaCreadaToEncurso();
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
}



