package servidor.persistencia.daos;


import java.util.List;

import servidor.excepciones.ActualizarEstadoPartidaException;
import servidor.excepciones.AgregarPartidaException;
import servidor.excepciones.BuscarPartidasException;
import servidor.excepciones.EliminarPartidaException;
import servidor.excepciones.ExistePartidaEnCursoException;
import servidor.excepciones.ListarPartidasCreadasException;
import servidor.persistencia.poolConexiones.IConexion;
import servidor.valueObjects.VOPartida;

public interface IDAOPartidas {
	
	public List<VOPartida> listarPartidasCreadas(IConexion iConn) throws ListarPartidasCreadasException;

	public VOPartida find(IConexion iConn, String nombrePartida) throws BuscarPartidasException;
	
	public void updatePartidaCredaToEnCurso(IConexion iConn, String nombrePartida, int idPartida) throws ActualizarEstadoPartidaException;
	
	public boolean hasPartidaEnCurso(IConexion iConn, String nombrePartida) throws ExistePartidaEnCursoException;
	
	public void updatePartidaEnCursoToCreada(IConexion iConn, String nombrePartida) throws ActualizarEstadoPartidaException;
	
	public void updatePartidaEnCursoToTerminada(IConexion iConn, String nombrePartida) throws ActualizarEstadoPartidaException;
	
	public void eliminarPartida(IConexion iConn, int idPartida) throws EliminarPartidaException;
	
	public boolean hasPartidaCreada(IConexion iConn, String nombrePartida) throws ExistePartidaEnCursoException;
	
	public VOPartida findPartidaEnCurso(IConexion iConn, String nombrePartida) throws BuscarPartidasException;
	
	public VOPartida findPartidaCreada(IConexion iConn, String nombrePartida) throws BuscarPartidasException;
	
	public void agregarPartidaEnCurso(IConexion iConn, VOPartida voPartida) throws AgregarPartidaException;
}

