package servidor.persistencia.daos;


import java.util.List;

import servidor.excepciones.AgregarFigurasPartidasException;
import servidor.excepciones.FigurasPartidasIdPartidaException;
import servidor.persistencia.poolConexiones.IConexion;
import servidor.valueObjects.VOFigurasPartidas;

public interface IDAOFigurasPartidas {

	public void agregarFigurasPartidas(IConexion iConn, List<VOFigurasPartidas> listaFiguras) throws AgregarFigurasPartidasException;
	
	public List<VOFigurasPartidas> listarFigurasPartidasIdPartida(IConexion iConn, int idPartida) throws FigurasPartidasIdPartidaException;

}
