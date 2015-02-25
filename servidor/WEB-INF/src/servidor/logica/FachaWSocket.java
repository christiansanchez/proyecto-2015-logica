package servidor.logica;

import servidor.excepciones.FachadaException;
import servidor.excepciones.MonitorException;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
/*
 * Clase encargada de definir la logica y los metodos utilizados
 * desde el web socket.
 */

@ServerEndpoint("/fachadawsocket")
public class FachaWSocket extends Fachada{

	public FachaWSocket() throws FachadaException {
		super();
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
