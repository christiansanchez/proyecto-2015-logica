package servidor.logica;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import servidor.excepciones.FachadaException;
import servidor.logica.monitor.MonitorLecturaEscritura;
import servidor.persistencia.fabricas.FabricaAbstracta;
import servidor.persistencia.poolConexiones.IPoolConexiones;

/*
 * Clase encargada de contener las variables, metodos y funciones
 * utilizadas por las clases que la heredan. 
 */
public class Fachada {
	/*Atributos*/	
	private static Fachada instancia = null;
	protected Partidas partidas;
	protected IPoolConexiones ipool;
	protected MonitorLecturaEscritura monitorJuego;
	
	public static Fachada getInstancia() throws FachadaException{
		if(instancia == null) {
			instancia = new Fachada();
		}
		return instancia;
	}
	
	protected Fachada() throws FachadaException{
		this.partidas = new Partidas();
		Properties prop = new Properties();	
		String nombreArchivo = "config.properties";
		try {
			prop.load(new FileInputStream(nombreArchivo));
			String pool =  prop.getProperty("nameClassPool");
			//TODO: descomentar esto y agregarlo cuando este desarrollada el pool de conexiones
			this.ipool = (IPoolConexiones)Class.forName(pool).newInstance();

			String fabrica =  prop.getProperty("nameClassFactory");
			//TODO: descomentar esto y agregarlo cuando este desarrollada la persistencia
			FabricaAbstracta iFabrica = ((FabricaAbstracta)Class.forName(fabrica).newInstance());

		} catch (IOException | InstantiationException | IllegalAccessException
				| ClassNotFoundException e ) {
			throw new FachadaException();			
		}			
	}

}
