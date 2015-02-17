package servidor.persistencia.daos;

import java.util.List;

import servidor.excepciones.BuscarEstadoPartidaException;
import servidor.excepciones.ListarEstadoPartidaException;
import servidor.persistencia.poolConexiones.IConexion;
import servidor.valueObjects.VOEstadoPartida;

public interface IDAOEstadoPartida {

	public VOEstadoPartida find(IConexion iConn, String nomb)throws BuscarEstadoPartidaException;
	
	public List<VOEstadoPartida> listarEstadosPartidas(IConexion iConn) throws ListarEstadoPartidaException;
}
