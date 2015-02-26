package servidor.logica;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import servidor.excepciones.ActualizarEstadoPartidaException;
import servidor.excepciones.BuscarPartidasException;
import servidor.excepciones.FachadaException;
import servidor.excepciones.FigurasPartidasIdPartidaException;
import servidor.excepciones.ListarPartidasCreadasException;
import servidor.excepciones.MonitorException;
import servidor.excepciones.PersistenciaException;
import servidor.persistencia.daos.IDAOFigurasPartidas;
import servidor.persistencia.daos.IDAOPartidas;
import servidor.persistencia.fabricas.FabricaAbstracta;
import servidor.persistencia.poolConexiones.IConexion;
import servidor.persistencia.poolConexiones.IPoolConexiones;
import servidor.valueObjects.VOFigurasPartidas;
import servidor.valueObjects.VOPartida;
/*
 * Clase encargada de definir la logica y los metodos utilizados
 * desde el web service.
 */
public class FachadaWService {
	
	private static FachadaWService instancia = null;
	private IPoolConexiones ipool;
	private IDAOPartidas iPartidas;
	private IDAOFigurasPartidas iFigurasPartidas;
	
	public static FachadaWService getInstancia() throws FachadaException{
		if(instancia == null) {
			instancia = new FachadaWService();
		}
		return instancia;
	}
	
	private FachadaWService() throws FachadaException{
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
	
	public String getUnirsePartida() throws FachadaException{
		String resultado = "";
		return resultado;		
	}
	
	public String getCargarPartida() throws FachadaException{
		String resultado = "";
		return resultado;	
	}
	
	public String setCargarPartida(String nombrePartida, String rolPartida) throws FachadaException{
		String resultado = "";
		return resultado;	
	}
	
}
