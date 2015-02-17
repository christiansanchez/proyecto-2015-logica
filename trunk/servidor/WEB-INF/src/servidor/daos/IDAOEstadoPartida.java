package servidor.daos;

import java.util.List;

import servidor.excepciones.BuscarEstadoPartidaException;
import servidor.excepciones.ListarEstadoPartidaException;
import servidor.persistencia.PoolConexiones.IConexion;
import servidor.valueObjects.VOEstadoPartida;

public interface IDAOEstadoPartida {

	public VOEstadoPartida find(IConexion iConn, String nomb)throws BuscarEstadoPartidaException;
	
	public List<VOEstadoPartida> listarEstadosPartidas(IConexion iConn) throws ListarEstadoPartidaException;
}
