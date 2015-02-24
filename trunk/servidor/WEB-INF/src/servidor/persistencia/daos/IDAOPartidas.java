package servidor.persistencia.daos;


import java.util.List;

import servidor.persistencia.poolConexiones.IConexion;
import servidor.excepciones.ActualizarEstadoPartidaException;
import servidor.excepciones.BuscarPartidasException;
import servidor.excepciones.ListarPartidasCreadasException;
import servidor.valueObjects.VOPartida;

public interface IDAOPartidas {
	
	public List<VOPartida> listarPartidasCreadas(IConexion iConn) throws ListarPartidasCreadasException;

	public VOPartida find(IConexion iConn, String nombrePartida) throws BuscarPartidasException;
	
	public void updatePartidaCredaToEnCurso(IConexion iConn, String nombrePartida, int idPartida) throws ActualizarEstadoPartidaException;
}

