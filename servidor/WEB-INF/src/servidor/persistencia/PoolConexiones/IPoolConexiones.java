package servidor.persistencia.PoolConexiones;

import servidor.excepciones.PersistenciaException;

public interface IPoolConexiones {
	
	public IConexion obtenerConexion (boolean modifica) throws PersistenciaException, PersistenciaException;
	
	public void liberarConexion (IConexion conection, boolean ok) throws PersistenciaException;
	
}