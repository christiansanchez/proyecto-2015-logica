package servidor.persistencia.daos;


import java.util.List;

import servidor.excepciones.ListarRolesException;
import servidor.persistencia.poolConexiones.IConexion;
import servidor.valueObjects.VORoles;

public interface IDAORoles {

	public List<VORoles> listarRoles(IConexion iConn) throws ListarRolesException;
}
