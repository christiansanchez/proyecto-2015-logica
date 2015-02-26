package servidor.logica;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import servidor.logica.monitor.MonitorLecturaEscritura;

/*
 * Clase fachada para web socket.
 */
public class FachadaSocket{
		
	private static FachadaSocket instancia = null;
	protected MonitorLecturaEscritura monitorJuego;
	protected Partidas partidas;
	protected String urlWebService;
 	
	public static FachadaSocket getInstancia(){
		if(instancia == null) {
			instancia = new FachadaSocket();
		}
		return instancia;
	}
	
	protected FachadaSocket(){
		this.monitorJuego = MonitorLecturaEscritura.getInstancia();
		this.partidas = new Partidas();
		Properties prop = new Properties();	
		String nombreArchivo = "C:\\Documents and Settings\\christian\\Escritorio\\Proyecto 1\\workspace\\servidor\\configFiles\\config.properties";
		try{
			prop.load(new FileInputStream(nombreArchivo));			
			this.urlWebService =  prop.getProperty("urlWebService");
		}
		catch (IOException e ) {
			System.out.println("Ocurrio error al instanciar fachada");
		}	
	}			
}