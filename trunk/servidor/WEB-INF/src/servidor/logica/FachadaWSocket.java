package servidor.logica;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
/*
 * Clase encargada de definir la logica y los metodos utilizados
 * desde el web socket.
 */


import servidor.excepciones.FachadaException;
import servidor.excepciones.MonitorException;

@ServerEndpoint("/wsocketjuego")
public class FachadaWSocket{
	
	private static Set<Session> clients = Collections.synchronizedSet(new HashSet<Session>());
	
	@OnMessage
	public void onMessage(String message, Session session) throws FachadaException{	    
	    synchronized(clients){	    	
	    	String[] parts = message.split(";");
	    	String[] parts2 = parts[0].split(":");
	    	if (parts2[0].equals("requestAction")){
	    		String resultado = "";
	    		String dataJuego = parts[1];
		    	if(parts2[1].equals("unirse")){	    		
	    			boolean result = this.unirsePartida(parts[1]);
	    			resultado += "responseAction:unirse;result:" + result + "," + dataJuego;
		    	}
		    	else if(parts2[1].equals("guardar")){	  		    		
		    		boolean result = this.guardarPartida(dataJuego);
		    		resultado += "responseAction:guardar;result:" + result + "," + dataJuego;		    		
		    	}
		    	else if(parts2[1].equals("abandonar")){	  
		    		boolean result = this.abandonarPartida(dataJuego);
		    		resultado += "responseAction:abandonar;result:" + result + "," + dataJuego;		    		
		    	}  	
		    	else if(parts2[1].equals("barcoPuntoLlegada")){	  
		    		boolean result = this.abandonarPartida(dataJuego);
		    		resultado += "responseAction:abandonar;result:" + result + "," + dataJuego;			    		
		    	}
		    	else if(parts2[1].equals("impactoLancha")){	  
		    		boolean result = this.barcoPuntoLlegada(dataJuego);
		    		resultado += "responseAction:abandonar;result:" + result + "," + dataJuego;			    	
		    	}
		    	else if(parts2[1].equals("impactoBarco")){	  
		    		boolean result = this.abandonarPartida(dataJuego);
		    		resultado += "responseAction:abandonar;result:" + result + "," + dataJuego;	
		    	}
		    	else if(parts2[1].equals("lanchaDestruida")){		    				    		
		    		boolean result = this.abandonarPartida(dataJuego);
		    		resultado += "responseAction:abandonar;result:" + result + "," + dataJuego;		    		
		    	}
		    	else if(parts2[1].equals("dibujar")){	  
		    		resultado += "responseAction:dibujar;" + dataJuego;
		    		this.sendMessage(session, resultado);
		    	}		    	
	    	}	
	    }
	 }
	
	private void sendMessage(Session session, String resultado) throws FachadaException{
		for(Session client : clients){
			if (!client.equals(session)){
				try {
	    			  client.getBasicRemote().sendText(resultado);
				}
				catch (IOException e) {
	    			  throw new FachadaException(); 
				}
			}
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
	
	private boolean guardarPartida(String dataJuego){
		boolean resultado = false;
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
	
	private boolean impactoLancha(String dataJuego){
		boolean resultado = false;
		return resultado;
	}
	
	private boolean barcoPuntoLlegada(String dataJuego){
		boolean resultado = false;
		return resultado;
	}
	
	private boolean unirsePartida(String dataJuego) throws FachadaException{							
		String nombrePartida = "";
		String rolPartida = "";
		String[] parts = dataJuego.split(",");	    		
		for(String palabra2: parts){
			String[] parts2 = palabra2.split(":");
			if(parts2[0].equals("rolPartida")){
				rolPartida = parts2[1];
			}
			else if(parts2[0].equals("nombrePartida")){
				nombrePartida = parts2[1];
			}
		}
		nombrePartida = nombrePartida.trim();
		boolean resultado = false;
		Fachada instanciaWS = Fachada.getInstancia();
		try{
			instanciaWS.monitorJuego.comenzarEscritura();
			if(instanciaWS.partidas.member(nombrePartida)) {
				Partida partidaCreada = instanciaWS.partidas.find(nombrePartida);
				if (partidaCreada.getEstadoPartida() == EstadoPartida.CREADA){
					Jugador jugador2 = null;
					Jugador jugadorPartida = null;
					if(rolPartida.equals("BARCOCARGUERO")){											
						jugadorPartida = partidaCreada.getBarcoCarguero();
						if(jugadorPartida == null){
							Barco barco = new Barco();
							barco.barcoNuevo();
							BarcoCarguero barcoCarguero = new BarcoCarguero(barco);
							jugador2 = new Jugador(barcoCarguero);
							partidaCreada.setBarcoCarguero(jugador2);
						}
					}
					else{						
						jugadorPartida = partidaCreada.getLanchaPirata();
						if(jugadorPartida == null){
							Pirata pirata = new Pirata();
							pirata.pirataNuevo();
							jugador2 = new Jugador(pirata);
							partidaCreada.setLanchaPirata(jugador2);							
						}
					}
					EstadoPartida estadoPartida = EstadoPartida.ENCURSO;
					partidaCreada.setEstadoPartida(estadoPartida);
					resultado = true;
				}
			}			
		}
		catch(MonitorException e){
			resultado = false;
		}
		finally{
			instanciaWS.monitorJuego.terminarEscritura();
		}
		return resultado;
	}
}
