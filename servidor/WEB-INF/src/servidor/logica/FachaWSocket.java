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

@ServerEndpoint("/wsocketjuego")
public class FachaWSocket extends Fachada{
	
	private static Set<Session> clients = 
		    Collections.synchronizedSet(new HashSet<Session>());
	
	public FachaWSocket() throws FachadaException {
		Fachada.getInstancia();
	}
	
	@OnMessage
	public void onMessage(String message, Session session) throws FachadaException{	    
	    synchronized(clients){
	      for(Session client : clients){
	    	  if (!client.equals(session)){
	    		  try {
	    			  client.getBasicRemote().sendText(message);
	    		  }
	    		  catch (IOException e) {
	    			  throw new FachadaException(); 
	    		  }
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
	
//	public boolean setUnirsePartida(String nombrePartida, String rolPartida) throws FachadaException{
//		nombrePartida = nombrePartida.trim();
//		boolean resultado = false;
//		Fachada instanciaWS = Fachada.getInstancia();
//		try{
//			instanciaWS.monitorJuego.comenzarEscritura();
//			if(instanciaWS.partidas.member(nombrePartida)) {
//				Partida partidaCreada = instanciaWS.partidas.find(nombrePartida);
//				if (partidaCreada.getEstadoPartida() == EstadoPartida.CREADA){
//					Jugador jugador2 = null;
//					Jugador jugadorPartida = null;
//					if(rolPartida.equals("BARCOCARGUERO")){											
//						jugadorPartida = partidaCreada.getBarcoCarguero();
//						if(jugadorPartida == null){
//							Barco barco = new Barco();
//							barco.barcoNuevo();
//							BarcoCarguero barcoCarguero = new BarcoCarguero(barco);
//							jugador2 = new Jugador(barcoCarguero);
//							partidaCreada.setBarcoCarguero(jugador2);
//						}
//					}
//					else{						
//						jugadorPartida = partidaCreada.getLanchaPirata();
//						if(jugadorPartida == null){
//							Pirata pirata = new Pirata();
//							pirata.pirataNuevo();
//							jugador2 = new Jugador(pirata);
//							partidaCreada.setLanchaPirata(jugador2);							
//						}
//					}
//					EstadoPartida estadoPartida = EstadoPartida.ENCURSO;
//					partidaCreada.setEstadoPartida(estadoPartida);
//					resultado = true;
//				}
//			}			
//		}
//		catch(MonitorException e){
//			resultado = false;
//		}
//		finally{
//			instanciaWS.monitorJuego.terminarEscritura();
//		}
//		return resultado;
//	}
}
