package servidor.persistencia.daos;


import java.util.List;

import servidor.persistencia.poolConexiones.IConexion;
import servidor.excepciones.BorrarPartidasException;
import servidor.excepciones.BuscarPartidasException;
import servidor.excepciones.ExistePartidasException;
import servidor.excepciones.IngresarPartidasException;
import servidor.excepciones.ListarPartidasCreadasException;
import servidor.valueObjects.VOPartida;
import servidor.valueObjects.VOPartidas;

public interface IDAOPartidas {
	
	public List<VOPartida> listarPartidasCreadas(IConexion iConn) throws ListarPartidasCreadasException;
	
	//public List<VOPartidas> listarPartidas(IConexion iConn) throws ListarPartidasException;
	
//	public int insert(IConexion iConn, String alias, int idNivel) throws IngresarPartidasException;
//	
//	public boolean member(IConexion iConn, String alias) throws ExistePartidasException;
//	
//	public void delete(IConexion iConn, String alias) throws BorrarPartidasException;
//		
//	public VOPartidas find(IConexion iConn, String alias)throws BuscarPartidasException;
}
