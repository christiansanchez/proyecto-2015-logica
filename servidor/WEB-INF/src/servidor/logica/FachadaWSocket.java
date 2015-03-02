package servidor.logica;


import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import javax.xml.soap.SOAPException;

import servidor.excepciones.MonitorException;


/*
 * Clase encargada de definir la logica y los metodos utilizados
 * desde el web socket.
 */
@ServerEndpoint("/wsocketjuego")
public class FachadaWSocket{
	
	private static Set<Session> clients = Collections.synchronizedSet(new HashSet<Session>());
	
	public FachadaWSocket(){
		FachadaSocket.getInstancia();
	}
	
	@OnOpen
	public void onOpen (Session session) {
		clients.add(session);
	}

	@OnClose
	public void onClose (Session session) {
	    clients.remove(session);
	}
	
	@OnError
    public void onError(Throwable t) {
        t.printStackTrace();
    }
	
	private void sendMessage(Session session, String resultado, String aMiMismo){
		//metodo encargado de enviar mensajes a los clientes conectados
		for(Session client : clients){
			boolean enviar = true;
			if(!aMiMismo.equals("null")) {
				if (!client.equals(session)){
					 if (!aMiMismo.equals("false")){
						 enviar = false;
					 }				
				}
				else if(client.equals(session)){
					if (!aMiMismo.equals("true")){
						enviar = false;	
					}
				}			
			}
			if (enviar){
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
		//metodo encargado de recibir los mensajes desde la capa de presentacion
	    synchronized(clients){	    	
	    	String[] parts = message.split(";");
	    	if(parts.length > 1){
	    		String[] parts2 = parts[0].split(":");
		    	if (parts2[0].equals("requestAction")){
		    		String resultado = "";
		    		if(!parts[1].isEmpty()){
			    		String dataJuego = parts[1];	    		
				    	if(parts2[1].equals("unirse")){	    		
				    		//cuando el segundo jugador se une a una partida creada o cargada
			    			String result = this.unirsePartida(dataJuego);
			    			String estado = this.estadoPartida(dataJuego);
			    			resultado += "responseAction:unirse;\"result\":" + result + ",\"status\":" + estado + "," + dataJuego;
			    			String enviarAMi = "null";
			    			this.sendMessage(session, resultado, enviarAMi);
				    	}
				    	else if(parts2[1].equals("guardar")){
				    		//cuando alguno de los jugadores guarda la partida
				    		boolean result = this.guardarPartida(dataJuego);
				    		String estado = this.estadoPartida(dataJuego);
				    		resultado += "responseAction:guardar;\"result\":" + result + ",\"status\":" + estado + "," + dataJuego;
				    		String enviarAMi = "false";
				    		this.sendMessage(session, resultado, enviarAMi);
				    	}
				    	else if(parts2[1].equals("abandonar")){	
				    		//cuando alguno de los jugadores abandona la partida
				    		boolean result = this.abandonarPartida(dataJuego);
				    		String estado = this.estadoPartida(dataJuego);
				    		resultado += "responseAction:abandonar;\"result\":" + result + ",\"status\":" + estado + "," + dataJuego;
				    		String enviarAMi = "false";
				    		this.sendMessage(session, resultado, enviarAMi);
				    	}		    	
				    	else if(parts2[1].equals("impactoLancha")){	
				    		//cuando se produce un impacto en una lancha
				    		boolean result = this.impactoLancha(dataJuego, session);
				    		String estado = this.estadoPartida(dataJuego);
				    		resultado += "responseAction:impactoLancha;\"result\":" + result + ",\"status\":" + estado + "," + dataJuego;
				    		String enviarAMi = "false";
				    		this.sendMessage(session, resultado, enviarAMi);
				    	}
				    	else if(parts2[1].equals("impactoBarco")){
				    		//cuando se produce un impacto en una manguera del barco
				    		boolean result = this.impactoBarco(dataJuego);
				    		String estado = this.estadoPartida(dataJuego);
				    		resultado += "responseAction:impactoBarco;\"result\":" + result + ",\"status\":" + estado + "," + dataJuego;
				    		String enviarAMi = "false";
				    		this.sendMessage(session, resultado, enviarAMi);
				    	}
				    	else if(parts2[1].equals("lanchaDestruida")){
				    		//cuando barco choca lancha	y la hunde    		
				    		boolean result = this.lanchaDestruida(dataJuego);
				    		String estado = this.estadoPartida(dataJuego);
				    		resultado += "responseAction:lanchaDestruida;\"result\":" + result + ",\"status\":" + estado + "," + dataJuego;
				    		String enviarAMi = "false";
				    		this.sendMessage(session, resultado, enviarAMi);
				    	}
				    	else if(parts2[1].equals("dibujar")){	
				    		//utilizado para la sincronizacion		
				    		String estado = this.estadoPartida(dataJuego);
				    		resultado += "responseAction:dibujar;\"status\":" + estado + "," + dataJuego;
				    		String enviarAMi = "false";
				    		this.sendMessage(session, resultado, enviarAMi);
				    	}
				    	else if(parts2[1].equals("hasPartida")){	  
				    		//consulta si la partida esta creada en memoria
				    		boolean result = this.hasPartida(dataJuego);
				    		String estado = this.estadoPartida(dataJuego);
				    		resultado += "responseAction:hasPartida;\"result\":" + result + ",\"status\":" + estado + "," + dataJuego;
				    		String enviarAMi = "true";
				    		this.sendMessage(session, resultado, enviarAMi);
				    	}
				    	else if(parts2[1].equals("setPartida")){	  
				    		//crear una nueva partida ingresando el primer jugador
				    		boolean result = this.setPartida(dataJuego);
				    		String estado = this.estadoPartida(dataJuego);
				    		resultado += "responseAction:setPartida;\"result\":" + result + ",\"status\":" + estado + "," + dataJuego;
				    		String enviarAMi = "true";
				    		this.sendMessage(session, resultado, enviarAMi);
				    	}
				    	else if(parts2[1].equals("getUnirsePartida")){	 
				    		//listado de partidas para poder unirse
				    		String result = this.getUnirsePartida();
				    		resultado += "responseAction:getUnirsePartida;\"result\":" + result  + "," + dataJuego;
				    		String enviarAMi = "true";
				    		this.sendMessage(session, resultado, enviarAMi);
				    	}
				    	else if(parts2[1].equals("getCargarPartida")){	 
				    		//listado cargado de db para cargar una partida
				    		String result = this.getCargarPartida();
				    		resultado += "responseAction:getCargarPartida;\"result\":" + result + "," + dataJuego;
				    		String enviarAMi = "true";
				    		this.sendMessage(session, resultado, enviarAMi);
				    	}
				    	else if(parts2[1].equals("setCargarPartida")){	
				    		//empezar una partida que estaba guardada
				    		String result = this.setCargarPartida(dataJuego);
				    		String estado = this.estadoPartida(dataJuego);
				    		resultado += "responseAction:setCargarPartida;\"result\":" + result + ",\"status\":" + estado + "," + dataJuego;
				    		String enviarAMi = "true";
				    		this.sendMessage(session, resultado, enviarAMi);
				    	}
		    		}	
		    	}
	    	}	    	
	    }
	}
	
	private String estadoPartida(String dataJuego){
		dataJuego = dataJuego.replace("\"", "");
		String nombrePartida = "";
		String estado = "";
		String[] parts = dataJuego.split(",");
		for(String palabra2: parts){
			String[] parts2 = palabra2.split(":");
			if(parts2[0].equals("nombrePartida")){
				nombrePartida = parts2[1];
			}			
		}		
		nombrePartida = nombrePartida.trim();
		if(!nombrePartida.isEmpty()){						
			FachadaSocket instancia = FachadaSocket.getInstancia();
			try {
				instancia.monitorJuego.comenzarLectura();
				if(instancia.partidas.member(nombrePartida)){
					estado = instancia.partidas.find(nombrePartida).getEstadoPartidaStr();
					estado = "\"" + estado + "\"";
				}
			}
			catch (MonitorException e) {	
				System.out.println("ERRROR MONITOR");
				estado = "false,\"error\":\"Error con monitor.\"";		
			}
			finally{
				instancia.monitorJuego.terminarLectura();
				estado = estado.replace(" ", "%20");
			}
		}
		return estado;
	}
	
	private String setCargarPartida(String dataJuego){
		dataJuego = dataJuego.replace("\"", "");
		String resultado = "";
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
		rolPartida = rolPartida.trim();	
		tipoMapa = tipoMapa.trim();
		if(nombrePartida.isEmpty() || rolPartida.isEmpty() || tipoMapa.isEmpty()){
			resultado = "false,\"error\":\"Partida sin nombre, sin rol o sin tipo mapa.\"";
		}
		else{
			FachadaSocket instancia = FachadaSocket.getInstancia();
			try {
				instancia.monitorJuego.comenzarEscritura();
				resultado = instancia.webservice.setCargarPartida(dataJuego);
				if(!resultado.isEmpty()){									
					TipoMapa mapaElegido;
					if (tipoMapa.equals("MARABIERTO")){
						mapaElegido = TipoMapa.MARABIERTO;
					}
					else{
						mapaElegido = TipoMapa.ISLAS;
					}
					Mapa mapa = new Mapa(mapaElegido);
					Partida partidaNueva = null;
					Barco barco = new Barco();
					BarcoCarguero barcoCarguero = new BarcoCarguero(barco);				
					Pirata pirata = new Pirata();
					Lancha lancha1 = new Lancha();
					Lancha lancha2 = new Lancha();
					Lancha lancha3 = new Lancha();
					String[] partsResultado = resultado.split(";");
					for(String lineaResultado: partsResultado){
						String[] lineaValores = lineaResultado.split(",");
						if (lineaValores.length > 0){
							for(String lineaResultado2: lineaValores){
								String[] lineaValores2 = lineaResultado2.split(":");
								if(lineaValores2[0].equals("posicionXBarco")){
									(barcoCarguero.getBarco()).setPosicionX(Float.parseFloat(lineaValores2[1]));
								}
								else if(lineaValores2[0].equals("posicionYBarco")){
									(barcoCarguero.getBarco()).setPosicionY(Float.parseFloat(lineaValores2[1]));
								}
								else if(lineaValores2[0].equals("anguloBarco")){
									(barcoCarguero.getBarco()).setAngulo(Integer.parseInt(lineaValores2[1]));
								}
								else if(lineaValores2[0].equals("posicionXLancha1")){
									lancha1.setPosicionX(Float.parseFloat(lineaValores2[1]));
								}
								else if(lineaValores2[0].equals("posicionYLancha1")){
									lancha1.setPosicionY(Float.parseFloat(lineaValores2[1]));
								}						
								else if(lineaValores2[0].equals("energiaLancha1")){
									lancha1.setImpactosPermitidos(Integer.parseInt(lineaValores2[1]));
								}
								else if(lineaValores2[0].equals("anguloLancha1")){
									lancha1.setAngulo(Integer.parseInt(lineaValores2[1]));
								}												
								else if(lineaValores2[0].equals("posicionXLancha2")){
									lancha2.setPosicionX(Float.parseFloat(lineaValores2[1]));
								}
								else if(lineaValores2[0].equals("posicionYLancha2")){
									lancha2.setPosicionY(Float.parseFloat(lineaValores2[1]));
								}						
								else if(lineaValores2[0].equals("energiaLancha2")){
									lancha2.setImpactosPermitidos(Integer.parseInt(lineaValores2[1]));
								}
								else if(lineaValores2[0].equals("anguloLancha2")){
									lancha2.setAngulo(Integer.parseInt(lineaValores2[1]));
								}																	
								else if(lineaValores2[0].equals("posicionXLancha3")){
									lancha3.setPosicionX(Float.parseFloat(lineaValores2[1]));
								}
								else if(lineaValores2[0].equals("posicionYLancha3")){
									lancha3.setPosicionY(Float.parseFloat(lineaValores2[1]));
								}						
								else if(lineaValores2[0].equals("energiaLancha3")){
									lancha3.setImpactosPermitidos(Integer.parseInt(lineaValores2[1]));
								}
								else if(lineaValores2[0].equals("anguloLancha1")){
									lancha1.setAngulo(Integer.parseInt(lineaValores2[1]));
								}						
							}
						}
						else{
							String[] lineaValores2 = lineaResultado.split(":");
							if(lineaValores2[0].equals("manguera1")){
								(barcoCarguero.getBarco()).setMangueras(1, Boolean.getBoolean(lineaValores2[1]));
							}
							else if(lineaValores2[0].equals("manguera2")){
								(barcoCarguero.getBarco()).setMangueras(2, Boolean.getBoolean(lineaValores2[1]));
							}
							else if(lineaValores2[0].equals("manguera3")){
								(barcoCarguero.getBarco()).setMangueras(3, Boolean.getBoolean(lineaValores2[1]));						
							}
							else if(lineaValores2[0].equals("manguera4")){
								(barcoCarguero.getBarco()).setMangueras(4, Boolean.getBoolean(lineaValores2[1]));
							}
							else if(lineaValores2[0].equals("manguera5")){
								(barcoCarguero.getBarco()).setMangueras(5, Boolean.getBoolean(lineaValores2[1]));	
							}
							else if(lineaValores2[0].equals("manguera6")){
								(barcoCarguero.getBarco()).setMangueras(6, Boolean.getBoolean(lineaValores2[1]));
							}
							else if(lineaValores2[0].equals("manguera7")){
								(barcoCarguero.getBarco()).setMangueras(7, Boolean.getBoolean(lineaValores2[1]));
							}
							else if(lineaValores2[0].equals("manguera8")){
								(barcoCarguero.getBarco()).setMangueras(8, Boolean.getBoolean(lineaValores2[1]));
							}					
						}				
					}
					pirata.setLancha(1, lancha1);
					pirata.setLancha(2, lancha2);
					pirata.setLancha(3, lancha3);
					Jugador jugador1 = new Jugador(barcoCarguero);
					Jugador jugador2 = new Jugador(pirata);	
					partidaNueva = new Partida(nombrePartida, jugador1, jugador2, mapa);
					EstadoPartida estado = EstadoPartida.CREADA;				
					partidaNueva.setEstadoPartida(estado);
					instancia.partidas.insert(partidaNueva);
				}	
			}
			catch (SOAPException | IOException e) {
				System.out.println("ERROR SOAP WEBSERVICE");
				resultado = "false,\"error\":\"Error con webservice.\"";
			}
			catch (MonitorException e) {	
				System.out.println("ERRROR MONITOR");
				resultado = "false,\"error\":\"Error con monitor.\"";			
			}	
			finally{
				instancia.monitorJuego.terminarEscritura();
				resultado.replace(" ", "%20");
			}
		}		
		return resultado;
	}
	
	private String getCargarPartida(){		
		FachadaSocket instancia = FachadaSocket.getInstancia();
		String resultado = "false";
		try{
			instancia.monitorJuego.comenzarLectura();			
			resultado = instancia.webservice.getCargarPartida();
		}
		catch(MonitorException  e){
			System.out.println("ERROR MONITOR");
			resultado = "false,\"error\":\"Error con monitor.\"";
		} catch (SOAPException | IOException e) {
			System.out.println("ERROR WEB SERVICE");
			resultado = "false,\"error\":\"Error con webservice.\"";
		}		
		finally{
			instancia.monitorJuego.terminarLectura();
		}
		return resultado;
	}
	
	private boolean guardarPartida(String dataJuego){
		dataJuego = dataJuego.replace("\"", "");
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
		FachadaSocket instancia =  null;
		if(!nombrePartida.isEmpty()){
			try {
				instancia = FachadaSocket.getInstancia();			
				instancia.monitorJuego.comenzarEscritura();						
				Partida partida = instancia.partidas.find(nombrePartida);
				EstadoPartida estadoTerminada = EstadoPartida.TERMINADA;
				EstadoPartida estadoEnCurso = EstadoPartida.ENCURSO;
				String dataJuegoAux = "";
				if(partida.getEstadoPartida() == estadoTerminada){
					dataJuegoAux = "nombrePartida:" + nombrePartida + ",estado:terminar";
					//instancia.partidas.delete(nombrePartida);
				}
				else if (partida.getEstadoPartida() == estadoEnCurso){					
					dataJuegoAux = "nombrePartida:" + nombrePartida + ",estado:guardar," + dataJuego;
				}
				String resultado2 = instancia.webservice.setGuardarPartida(dataJuegoAux);			
				resultado = Boolean.parseBoolean(resultado2);
			}
			catch (MonitorException e) {
				System.out.println("ERROR MONITOR");
				resultado = false;
			}
			catch (SOAPException | IOException e) {
				System.out.println("ERROR WEB SERVICE");
				resultado = false;
			}
			finally{
				instancia.monitorJuego.terminarEscritura();			
			}
		}
		return resultado;
	}
	
	private String getUnirsePartida(){	
		String resultado = "false";
		FachadaSocket instancia = FachadaSocket.getInstancia();
		try{
			instancia.monitorJuego.comenzarLectura();
			resultado = instancia.partidas.getPartidasDisponibles();
			resultado = resultado.replace(" ", "%20");
		}
		catch(MonitorException  e){
			System.out.println("ERROR MONITOR");
			resultado = "false,\"error\":\"Error con monitor.\"";
		}
		finally{
			instancia.monitorJuego.terminarLectura();
		}
		return resultado;	
	}
	
	private boolean hasPartida(String dataJuego){
		dataJuego = dataJuego.replace("\"", "");
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
		FachadaSocket instancia = null;		
		try{
			instancia = FachadaSocket.getInstancia();
			instancia.monitorJuego.comenzarLectura();			
			resultado = instancia.partidas.member(nombrePartida);
		}
		catch(MonitorException  e){
			System.out.println("ERROR MONITOR");
			resultado = false;
		} 
		finally{
			instancia.monitorJuego.terminarLectura();
		}
		
		return resultado;
	}

	private boolean setPartida(String dataJuego){
		dataJuego = dataJuego.replace("\"", "");
		FachadaSocket isntancia = null;
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
			resultado = false;
		}		
		else{
			isntancia = FachadaSocket.getInstancia();
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
				isntancia.monitorJuego.terminarEscritura();
	        } 
		}				
		return resultado;
	}
	
	private boolean abandonarPartida(String dataJuego){
		dataJuego = dataJuego.replace("\"", "");
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
		if(!nombrePartida.isEmpty()){
			FachadaSocket instancia = FachadaSocket.getInstancia();
			try{
				instancia.monitorJuego.comenzarEscritura();					
				Partida partida = instancia.partidas.find(nombrePartida);
				EstadoPartida estadoTerminada = EstadoPartida.TERMINADA;				
				if(partida.getEstadoPartida() == estadoTerminada){
					String dataJuegoAux = "nombrePartida:" + nombrePartida + ",estado:abandonar";
					String resultado2 = instancia.webservice.setGuardarPartida(dataJuegoAux);
					instancia.partidas.delete(nombrePartida);
					resultado = Boolean.parseBoolean(resultado2);
				}
				else{
					partida.setEstadoPartida(estadoTerminada);
					resultado = true;
				}
			}
			catch(MonitorException e){
				System.out.println("ERROR MONITOR");
				resultado = false;
			}
			catch(SOAPException | IOException e){
				System.out.println("ERROR MONITOR");
				resultado = false;
			}
			finally{
				instancia.monitorJuego.terminarEscritura();
			}				
		}
		return resultado;
	}
	
	private boolean lanchaDestruida(String dataJuego){
		dataJuego = dataJuego.replace("\"", "");
		boolean resultado = false;
		String nombrePartida = "";
		int numeroLancha = 0;
		String[] parts = dataJuego.split(",");	    		
		for(String palabra2: parts){
			String[] parts2 = palabra2.split(":");
			if(parts2[0].equals("numeroLancha")){
				numeroLancha = Integer.parseInt(parts2[1]);
			}
			else if(parts2[0].equals("nombrePartida")){
				nombrePartida = parts2[1];
			}
		}
		nombrePartida = nombrePartida.trim();
		if(!nombrePartida.isEmpty() && numeroLancha > 0 && numeroLancha < 4){
			FachadaSocket instancia = FachadaSocket.getInstancia();
			try{
				instancia.monitorJuego.comenzarEscritura();
				Partida partida = instancia.partidas.find(nombrePartida);
				Jugador jugador = partida.getLanchaPirata();
				Pirata pirataRol = (Pirata)jugador.getRol();
				pirataRol.destruirLancha(numeroLancha);
			}
			catch(MonitorException e){
				resultado = false;
			}
			finally{
				instancia.monitorJuego.terminarEscritura();
			}
		}		
		return resultado;
	}
	
	private boolean impactoBarco(String dataJuego){
		dataJuego = dataJuego.replace("\"", "");
		boolean resultado = false;
		String nombrePartida = "";
		int numeroManguera = 0;
		String[] parts = dataJuego.split(",");	    		
		for(String palabra2: parts){
			String[] parts2 = palabra2.split(":");
			if(parts2[0].equals("numeroManguera")){
				numeroManguera = Integer.parseInt(parts2[1]);
			}
			else if(parts2[0].equals("nombrePartida")){
				nombrePartida = parts2[1];
			}
		}
		nombrePartida = nombrePartida.trim();
		if(!nombrePartida.isEmpty() && numeroManguera > 0 && numeroManguera < 9){
			FachadaSocket instancia = FachadaSocket.getInstancia();
			try{
				instancia.monitorJuego.comenzarEscritura();
				Partida partida = instancia.partidas.find(nombrePartida);
				Jugador jugador = partida.getBarcoCarguero();
				BarcoCarguero barcoCarguero = (BarcoCarguero)jugador.getRol();				
				barcoCarguero.getBarco().mangueraDestruida(numeroManguera);
				resultado = true;
			}
			catch(MonitorException e){
				resultado = false;
			}
			finally{
				instancia.monitorJuego.terminarEscritura();
			}
		}		
		return resultado;
	}
	
	private boolean impactoLancha(String dataJuego, Session session){
		dataJuego = dataJuego.replace("\"", "");
		boolean resultado = false;
		String nombrePartida = "";
		int numeroLancha = 0;
		String[] parts = dataJuego.split(",");	    		
		for(String palabra2: parts){
			String[] parts2 = palabra2.split(":");
			if(parts2[0].equals("numeroLancha")){				
				numeroLancha = Integer.parseInt(parts2[1].trim());
			}
			else if(parts2[0].equals("nombrePartida")){
				nombrePartida = parts2[1];
			}
		}
		nombrePartida = nombrePartida.trim();
		if(!nombrePartida.isEmpty() && numeroLancha > 0 && numeroLancha < 4){
			FachadaSocket instancia = FachadaSocket.getInstancia();
			try{
				instancia.monitorJuego.comenzarEscritura();
				Partida partida = instancia.partidas.find(nombrePartida);
				Jugador jugador = partida.getLanchaPirata();
				Pirata pirataRol = (Pirata)jugador.getRol();
				pirataRol.impactoLancha(numeroLancha);
				resultado = true;
			}
			catch(MonitorException e){
				resultado = false;
			}
			finally{
				instancia.monitorJuego.terminarEscritura();
			}
		}	
		return resultado;
	}
		
	private String unirsePartida(String dataJuego){
		dataJuego = dataJuego.replace("\"", "");
		String resultado = "";
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
		rolPartida = rolPartida.trim();		
		if (nombrePartida.isEmpty() || rolPartida.isEmpty()){
			resultado = "false,\"error\":\"Partida sin nombre o rol asignado.\"";
		}
		else{
			FachadaSocket instancia = FachadaSocket.getInstancia();
			try {
				instancia.monitorJuego.comenzarEscritura();
				if(!instancia.partidas.member(nombrePartida)) {
					resultado = "false,\"error\":\"La partida no existe.\"";
				}
				Partida partidaCreada = instancia.partidas.find(nombrePartida);
				if (partidaCreada.getEstadoPartida() != EstadoPartida.CREADA){
					resultado =  "false,\"error\":\"La partida ya está en curso.\"";
				}
				else{
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
					resultado = "\"nombrePartida\":\"" + partidaCreada.getNombre() + "\"," + 
							  "\"rolPartida\":\"" + rolPartida + "\"," +
							  "\"tipoMapa\":\"" + partidaCreada.getTipoMapa() + "\"," +
							  "\"status\":\"" + partidaCreada.getEstadoPartidaStr() + "\";";
				}									
			} catch (MonitorException e) {
				System.out.println("ERROR MONITOR");			 
			}
			finally{
				instancia.monitorJuego.terminarEscritura();
			}
		}		
		return resultado;
	}
}