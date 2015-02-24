package servidor.persistencia.daos;


import java.util.List;

import servidor.persistencia.poolConexiones.IConexion;
import servidor.logica.Figura;
import servidor.excepciones.FigurasPartidasIdPartidaException;
import servidor.excepciones.GuardarFigurasPartidasException;
import servidor.excepciones.ListarFigurasPartidasException;
import servidor.valueObjects.VOFigurasPartidas;

public interface IDAOFigurasPartidas {

	public List<VOFigurasPartidas> listarFigurasPartidas(IConexion iConn) throws ListarFigurasPartidasException;
	
//	public void insert(IConexion iConn, Figura figura, int idPartida)throws GuardarFigurasPartidasException;
//	
//	public boolean member(IConexion iConn);
	
	public List<VOFigurasPartidas> listarFigurasPartidasIdPartida(IConexion iConn, int idPartida) throws FigurasPartidasIdPartidaException;

}
