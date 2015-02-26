package servidor.logica;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import servidor.excepciones.FachadaException;
import servidor.persistencia.daos.IDAOFigurasPartidas;
import servidor.persistencia.daos.IDAOPartidas;
import servidor.persistencia.fabricas.FabricaAbstracta;
import servidor.persistencia.poolConexiones.IPoolConexiones;

public class FachadaService {

	protected static FachadaService instancia = null;
	protected IPoolConexiones ipool;
	protected IDAOPartidas iPartidas;
	protected IDAOFigurasPartidas iFigurasPartidas;
	
	public static FachadaService getInstancia() throws FachadaException{
		if(instancia == null) {
			instancia = new FachadaService();
		}
		return instancia;
	}
	
	private FachadaService() throws FachadaException{
		Properties prop = new Properties();	
		String nombreArchivo = "C:\\Documents and Settings\\christian\\Escritorio\\Proyecto 1\\workspace\\servidor\\configFiles\\config.properties";	
		try{
			prop.load(new FileInputStream(nombreArchivo));			
			String pool =  prop.getProperty("nameClassPool");
			this.ipool = (IPoolConexiones)Class.forName(pool).newInstance();	
			String fabrica =  prop.getProperty("nameClassFactory");
			FabricaAbstracta iFabrica = ((FabricaAbstracta)Class.forName(fabrica).newInstance());
			this.iPartidas = iFabrica.crearDAOPartidas();
			this.iFigurasPartidas = iFabrica.crearDAOFigurasPartidas();
		}
		catch (IOException | InstantiationException | IllegalAccessException | ClassNotFoundException e ) {
			throw new FachadaException();
		}				
	}	
}
