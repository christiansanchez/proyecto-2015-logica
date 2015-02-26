package servidor.persistencia.fabricas;

import servidor.persistencia.daos.DAOFigurasPartidas;
import servidor.persistencia.daos.DAOPartidas;
import servidor.persistencia.daos.IDAOFigurasPartidas;
import servidor.persistencia.daos.IDAOPartidas;

public class FabricaMySQL implements FabricaAbstracta{
		
	@Override
	public IDAOPartidas crearDAOPartidas(){
		return (new DAOPartidas());
	}
	
	@Override
	public IDAOFigurasPartidas crearDAOFigurasPartidas(){
		return (new DAOFigurasPartidas());
	}
	
}
