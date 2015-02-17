package servidor.daos;


import java.util.List;

import servidor.excepciones.ListarRolesException;
import servidor.persistencia.PoolConexiones.IConexion;
import servidor.valueObjects.VORoles;

public interface IDAORoles {

	public List<VORoles> listarRoles(IConexion iConn) throws ListarRolesException;
}
