package servidor.logica;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import servidor.excepciones.FachadaException;

/*
 * Clase encargada de contener las variables, metodos y funciones
 * utilizadas por las clases que la heredan. 
 */
public class Fachada {
	/*Atributos*/	
	private static Fachada instancia = null;		
	
	public static Fachada getInstancia() throws FachadaException{
		if(instancia == null) {
			instancia = new Fachada();
		}
		return instancia;
	}
	
	protected Fachada() throws FachadaException{
		Properties prop = new Properties();		
		String nombreArchivo = "config.properties";		
		
		try {
			prop.load(new FileInputStream(nombreArchivo));
			String pool =  prop.getProperty("nameClassPool");
			//TODO: descomentar esto y agregarlo cuando este desarrollada el pool de conexiones
			//this.ipool = (IPoolConexiones)Class.forName(pool).newInstance();
												
			String fabrica =  prop.getProperty("nameClassFactory");
			//TODO: descomentar esto y agregarlo cuando este desarrollada la persistencia
			//FabricaAbstracta iFabrica = ((FabricaAbstracta)Class.forName(fabrica).newInstance());			
		} catch (IOException e) {
			throw new FachadaException();			
		}			
	}

}
