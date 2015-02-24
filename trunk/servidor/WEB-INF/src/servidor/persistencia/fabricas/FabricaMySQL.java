package servidor.persistencia.fabricas;

import servidor.persistencia.daos.DAOFigurasPartidas;
import servidor.persistencia.daos.DAOPartidas;
import servidor.persistencia.daos.IDAOFigurasPartidas;
import servidor.persistencia.daos.IDAOPartidas;

public class FabricaMySQL implements FabricaAbstracta{
		
	public IDAOPartidas crearDAOPartidas(){
		return ((IDAOPartidas) new DAOPartidas());
	}
	
	public IDAOFigurasPartidas crearDAOFigurasPartidas(){
		return ((IDAOFigurasPartidas) new DAOFigurasPartidas());
	}
	
}
