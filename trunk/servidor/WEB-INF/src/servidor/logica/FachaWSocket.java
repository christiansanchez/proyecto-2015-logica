package servidor.logica;

import servidor.excepciones.FachadaException;

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
	
	
}
