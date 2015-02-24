package servidor.persistencia.fabricas;

import servidor.persistencia.daos.IDAOFigurasPartidas;
import servidor.persistencia.daos.IDAOPartidas;

public interface FabricaAbstracta {

	public IDAOPartidas crearDAOPartidas();
	
	public IDAOFigurasPartidas crearDAOFigurasPartidas();
	
}
