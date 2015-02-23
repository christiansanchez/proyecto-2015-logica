package servidor.logica;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import servidor.excepciones.FachadaException;
import servidor.logica.monitor.MonitorLecturaEscritura;
import servidor.persistencia.daos.IDAOPartidas;
import servidor.persistencia.fabricas.FabricaAbstracta;
import servidor.persistencia.poolConexiones.IPoolConexiones;

/*
 * Clase encargada de contener las variables, metodos y funciones
 * utilizadas por las clases que la heredan. 
 */
public class Fachada {
	/*Atributos*/	
	protected static Fachada instancia = null;
	protected Partidas partidas;
	protected IPoolConexiones ipool;
	protected MonitorLecturaEscritura monitorJuego;
	protected IDAOPartidas iPartidas;
	
	public static Fachada getInstancia() throws FachadaException{
		if(instancia == null) {
			instancia = new Fachada();
		}
		return instancia;
	}
	
	protected Fachada() throws FachadaException{
		this.monitorJuego = MonitorLecturaEscritura.getInstancia();	
		this.partidas = new Partidas();
		Properties prop = new Properties();	
		String nombreArchivo = "C:\\Documents and Settings\\christian\\Escritorio\\Proyecto 1\\workspace\\servidor\\configFiles\\config.properties";		
		try {
			prop.load(new FileInputStream(nombreArchivo));
			String pool =  prop.getProperty("nameClassPool");
			this.ipool = (IPoolConexiones)Class.forName(pool).newInstance();

			String fabrica =  prop.getProperty("nameClassFactory");
			FabricaAbstracta iFabrica = ((FabricaAbstracta)Class.forName(fabrica).newInstance());
			this.iPartidas = iFabrica.crearDAOPartidas();

		} catch (IOException | InstantiationException | IllegalAccessException
				| ClassNotFoundException e ) {
			throw new FachadaException();			
		}			
	}

}
