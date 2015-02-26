package servidor.logica;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.axis2.AxisFault;

import servidor.excepciones.MonitorException;
import servidor.logica.WservicejuegoStub.GetCargarPartidaResponse;
import servidor.logica.WservicejuegoStub.GetUnirsePartidaResponse;
import servidor.logica.WservicejuegoStub.SetCargarPartida;
import servidor.logica.WservicejuegoStub.SetCargarPartidaResponse;
import servidor.logica.WservicejuegoStub.SetPartida;
import servidor.logica.WservicejuegoStub.SetPartidaResponse;
import servidor.logica.monitor.MonitorLecturaEscritura;

import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Properties;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/*
 * Clase encargada de definir la logica y los metodos utilizados
 * desde el web socket.
 */
@ServerEndpoint("/wsocketjuego")
public class FachadaWSocket{
	
	private static Set<Session> clients = Collections.synchronizedSet(new HashSet<Session>());
	private static FachadaWSocket instancia = null;
	private MonitorLecturaEscritura monitorJuego;
	private Partidas partidas;
	private String urlWebService;
	
	public static FachadaWSocket getInstancia(){
		if(instancia == null) {
			instancia = new FachadaWSocket();
		}
		return instancia;
	}
	
	private FachadaWSocket(){
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
	
	@OnOpen
	public void onOpen (Session session) {
		clients.add(session);
	}

	@OnClose
	public void onClose (Session session) {
	    clients.remove(session);
	}
	
	private void sendMessage(Session session, String resultado){
		for(Session client : clients){
			if (!client.equals(session)){
				try {
	    			  client.getBasicRemote().sendText(resultado);
				}
				catch (IOException e) {
	    			  System.out.println("Ocurrio un error al enviar mensaje"); 
				}
			}
		}
	}	
	
	@OnMessage
	public void onMessage(String message, Session session){	    
	    synchronized(clients){	    	
	    	String[] parts = message.split(";");
	    	if(parts.length > 1){
	    		String[] parts2 = parts[0].split(":");
		    	if (parts2[0].equals("requestAction")){
		    		String resultado = "";
		    		if(!parts[1].isEmpty()){
			    		String dataJuego = parts[1];	    		
				    	if(parts2[1].equals("unirse")){	    		
			    			boolean result = this.unirsePartida(dataJuego);
			    			resultado += "responseAction:unirse;result:" + result + "," + dataJuego;
			    			this.sendMessage(session, resultado);
				    	}
				    	else if(parts2[1].equals("guardar")){
				    		//cuando alguno de los jugadores guarda la partida
				    		boolean result = this.guardarPartida(dataJuego);
				    		resultado += "responseAction:guardar;result:" + result + "," + dataJuego;
				    		this.sendMessage(session, resultado);
				    	}
				    	else if(parts2[1].equals("abandonar")){	
				    		//cuando alguno de los jugadores abandona la partida
				    		boolean result = this.abandonarPartida(dataJuego);
				    		resultado += "responseAction:abandonar;result:" + result + "," + dataJuego;
				    		this.sendMessage(session, resultado);
				    	} 		    	
				    	else if(parts2[1].equals("impactoLancha")){	
				    		//cuando se produce un impacto en una lancha
				    		boolean result = this.impactoLancha(dataJuego, session);
				    		resultado += "responseAction:impactoLancha;result:" + result + "," + dataJuego;
				    		this.sendMessage(session, resultado);
				    	}
				    	else if(parts2[1].equals("impactoBarco")){
				    		//cuando se produce un impacto en una manguera del barco
				    		boolean result = this.impactoBarco(dataJuego);
				    		resultado += "responseAction:impactoBarco;result:" + result + "," + dataJuego;
				    		this.sendMessage(session, resultado);
				    	}
				    	else if(parts2[1].equals("lanchaDestruida")){
				    		//cuando barco choca lancha	y la hunde    		
				    		boolean result = this.lanchaDestruida(dataJuego);
				    		resultado += "responseAction:lanchaDestruida;result:" + result + "," + dataJuego;
				    		this.sendMessage(session, resultado);
				    	}
				    	else if(parts2[1].equals("dibujar")){	  
				    		resultado += "responseAction:dibujar;" + dataJuego;
				    		this.sendMessage(session, resultado);
				    	}
				    	else if(parts2[1].equals("hasPartida")){	  
				    		boolean result = this.hasPartida(dataJuego);
				    		resultado += "responseAction:hasPartida;result:" + result + "," + dataJuego;
				    		this.sendMessage(session, resultado);
				    	}
				    	else if(parts2[1].equals("setPartida")){	  
				    		boolean result = this.setPartida(dataJuego);
				    		resultado += "responseAction:setPartida;result:" + result + "," + dataJuego;
				    		this.sendMessage(session, resultado);
				    	}
				    	else if(parts2[1].equals("getUnirsePartida")){	  
				    		String result = this.getUnirsePartida();
				    		resultado += "responseAction:getUnirsePartida;result:" + result;
				    		this.sendMessage(session, resultado);
				    	}
				    	else if(parts2[1].equals("getCargarPartida")){	  
				    		String result = this.getCargarPartida();
				    		resultado += "responseAction:getCargarPartida;result:" + result;
				    		this.sendMessage(session, resultado);
				    	}
				    	else if(parts2[1].equals("setCargarPartida")){	  
				    		String result = this.setCargarPartida(dataJuego);
				    		resultado += "responseAction:setCargarPartida;result:" + result;
				    		this.sendMessage(session, resultado);
				    	}
		    		}	
		    	}
	    	}	    	
	    }
	}
	
	private String setCargarPartida(String dataJuego){
		String resultado = "";
		String nombrePartida = "";
		String rolPartida = "";
		String[] parts = dataJuego.split(",");	    		
		for(String palabra2: parts){
			String[] parts2 = palabra2.split(":");
			if(parts2[0].equals("nombrePartida")){
				nombrePartida = parts2[1];
			}
			else if(parts2[0].equals("rolPartida")){
				rolPartida = parts2[1];
			}
		}
		nombrePartida = nombrePartida.trim();
		rolPartida = rolPartida.trim();		
		
		FachadaWSocket isntancia = FachadaWSocket.getInstancia();
		try {
			isntancia.monitorJuego.comenzarEscritura();			
			WservicejuegoStub clienteWS;
			clienteWS = new WservicejuegoStub(isntancia.urlWebService);
			SetCargarPartida reqSetCargarPartida = new SetCargarPartida();
			reqSetCargarPartida.setNombrePartida(nombrePartida);
			reqSetCargarPartida.setRolPartida(rolPartida);
			SetCargarPartidaResponse respSetCargarPartida;			
			respSetCargarPartida = clienteWS.setCargarPartida(reqSetCargarPartida);
			resultado = respSetCargarPartida.get_return();
			//TODO: pasar lo de web service para aca			
		} 
		catch (AxisFault e1) {
			resultado = "ERROR AXIS";
		}			
		catch (RemoteException | FachadaExceptionException0 | MonitorException e) {				
			resultado = "ERRROR";					
		}	
		finally{
			isntancia.monitorJuego.terminarEscritura();
		}
		return resultado;
	}
	
	private String getCargarPartida(){		
		FachadaWSocket isntancia = FachadaWSocket.getInstancia();
		WservicejuegoStub clienteWS;
		try {
			clienteWS = new WservicejuegoStub(isntancia.urlWebService);
		} catch (AxisFault e) {
			return "ERROR AXIS";
		}
		GetCargarPartidaResponse respGetCargarPartida;
		try {
			respGetCargarPartida = clienteWS.getCargarPartida();
			return respGetCargarPartida.get_return();
		} catch (RemoteException | FachadaExceptionException0 e) {
			return "ERRROR";
		}
	}
	
	private String getUnirsePartida(){		
		FachadaWSocket isntancia = FachadaWSocket.getInstancia();
		WservicejuegoStub clienteWS;
		try {
			clienteWS = new WservicejuegoStub(isntancia.urlWebService);
		} catch (AxisFault e) {
			return "ERROR AXIS";
		}
		GetUnirsePartidaResponse respGetUnirsePartida;
		try {
			respGetUnirsePartida = clienteWS.getUnirsePartida();
			return respGetUnirsePartida.get_return();
		} catch (RemoteException | FachadaExceptionException0 e) {
			return "ERRROR";
		}		
	}
	
	private boolean hasPartida(String dataJuego){
		boolean resultado = false;
		String nombrePartida = "";
		String[] parts = dataJuego.split(",");	    		
		for(String palabra2: parts){
			String[] parts2 = palabra2.split(":");
			if(parts2[0].equals("nombrePartida")){
				nombrePartida = parts2[1];
			}
		}
		nombrePartida = nombrePartida.trim();
		FachadaWSocket isntancia = FachadaWSocket.getInstancia();
		resultado = isntancia.partidas.member(nombrePartida);
		return resultado;
	}

	private boolean setPartida(String dataJuego){
		boolean resultado = false;
		String nombrePartida = "";
		String rolPartida = "";
		String tipoMapa = "";
		String[] parts = dataJuego.split(",");	    		
		for(String palabra2: parts){
			String[] parts2 = palabra2.split(":");
			if(parts2[0].equals("nombrePartida")){
				nombrePartida = parts2[1];
			}
			else if(parts2[0].equals("rolPartida")){
				rolPartida = parts2[1];
			}
			else if(parts2[0].equals("tipoMapa")){
				tipoMapa = parts2[1];
			}
		}
		nombrePartida = nombrePartida.trim();
		tipoMapa = tipoMapa.trim();
		rolPartida = rolPartida.trim();
		if (nombrePartida.isEmpty() || tipoMapa.isEmpty() || rolPartida.isEmpty()){
			return false;
		}
		FachadaWSocket isntancia = FachadaWSocket.getInstancia();
		try{			
			isntancia.monitorJuego.comenzarEscritura();
			if(!isntancia.partidas.member(nombrePartida)) {
				TipoMapa mapaElegido;
				if (tipoMapa.equals("MARABIERTO")){
					mapaElegido = TipoMapa.MARABIERTO;
				}
				else{
					mapaElegido = TipoMapa.ISLAS;
				}
				Mapa mapa = new Mapa(mapaElegido);				
				Partida partidaNueva = null;
				Jugador jugador1 = null;
				Jugador jugador2 = null;
				if(rolPartida.equals("BARCOCARGUERO")){					
					Barco barco = new Barco();
					barco.barcoNuevo();
					BarcoCarguero barcoCarguero = new BarcoCarguero(barco);
					jugador1 = new Jugador(barcoCarguero);
					partidaNueva = new Partida(nombrePartida, jugador1, jugador2, mapa);
				}
				else{
					Pirata pirata = new Pirata();
					pirata.pirataNuevo();
					jugador1 = new Jugador(pirata);
					partidaNueva = new Partida(nombrePartida, jugador2, jugador1, mapa);
				}
				isntancia.partidas.insert(partidaNueva);
				resultado = true;
			}			
		}
		catch(MonitorException e){
			System.out.println("ERROR MONITOR");
			resultado = false;
		}
		finally {	
			instancia.monitorJuego.terminarEscritura();
        } 
		return resultado;
	}
	
	private boolean abandonarPartida(String dataJuego){
		boolean resultado = false;
		return resultado;
	}
	
	private boolean lanchaDestruida(String dataJuego){
		boolean resultado = false;
		return resultado;
	}
	
	private boolean impactoBarco(String dataJuego){
		boolean resultado = false;
		return resultado;
	}
	
	private boolean impactoLancha(String dataJuego, Session session){
		boolean resultado = false;
		return resultado;
	}
		
	private boolean guardarPartida(String dataJuego){
		boolean resultado = false;
		return resultado;
	}
	
	private boolean unirsePartida(String dataJuego){
		boolean resultado = false;
		return resultado;
	}
	
}