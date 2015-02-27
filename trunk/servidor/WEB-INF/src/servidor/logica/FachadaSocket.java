package servidor.logica;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.xml.soap.SOAPException;

import servidor.logica.monitor.MonitorLecturaEscritura;
import servidor.logica.ws.SOAPManager;

/*
 * Clase fachada para web socket.
 */
public class FachadaSocket{
		
	private static FachadaSocket instancia = null;
	protected MonitorLecturaEscritura monitorJuego;
	protected Partidas partidas;
	protected String urlWebService;
	protected SOAPManager webservice;
 	
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
			this.webservice = new SOAPManager(this.urlWebService); 
		}
		catch (IOException | UnsupportedOperationException | SOAPException e ) {
			System.out.println("Ocurrio error al instanciar fachada");
		}	
	}			
}