package servidor.persistencia.fabricas;

import servidor.persistencia.daos.DAOPartidas;
import servidor.persistencia.daos.IDAOPartidas;

public class FabricaMySQL implements FabricaAbstracta{
		
	public IDAOPartidas crearDAOPartidas(){
		return ((IDAOPartidas) new DAOPartidas());
	}
	
}
