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
	
	private void sendMessage(Session session, String resultado){
		for(Session client : clients){
			//if (!client.equals(session)){
				try {
	    			  client.getBasicRemote().sendText(resultado);
				}
				catch (IOException e) {
	    			  System.out.println("Ocurrio un error al enviar mensaje"); 
				}
			//}
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
				    		//cuando el segundo jugador se une a una partida creada o cargada
			    			boolean result = this.unirsePartida(dataJuego);
			    			resultado += "responseAction:unirse;\"result\":" + result + "," + dataJuego;
			    			this.sendMessage(session, resultado);
				    	}
				    	else if(parts2[1].equals("guardar")){
				    		//cuando alguno de los jugadores guarda la partida
				    		boolean result = this.guardarPartida(dataJuego);
				    		resultado += "responseAction:guardar;\"result\":" + result + "," + dataJuego;
				    		this.sendMessage(session, resultado);
				    	}
				    	else if(parts2[1].equals("abandonar")){	
				    		//cuando alguno de los jugadores abandona la partida
				    		boolean result = this.abandonarPartida(dataJuego);
				    		resultado += "responseAction:abandonar;\"result\":" + result + "," + dataJuego;
				    		this.sendMessage(session, resultado);
				    	} 		    	
				    	else if(parts2[1].equals("impactoLancha")){	
				    		//cuando se produce un impacto en una lancha
				    		boolean result = this.impactoLancha(dataJuego, session);
				    		resultado += "responseAction:impactoLancha;\"result\":" + result + "," + dataJuego;
				    		this.sendMessage(session, resultado);
				    	}
				    	else if(parts2[1].equals("impactoBarco")){
				    		//cuando se produce un impacto en una manguera del barco
				    		boolean result = this.impactoBarco(dataJuego);
				    		resultado += "responseAction:impactoBarco;\"result\":" + result + "," + dataJuego;
				    		this.sendMessage(session, resultado);
				    	}
				    	else if(parts2[1].equals("lanchaDestruida")){
				    		//cuando barco choca lancha	y la hunde    		
				    		boolean result = this.lanchaDestruida(dataJuego);
				    		resultado += "responseAction:lanchaDestruida;\"result\":" + result + "," + dataJuego;
				    		this.sendMessage(session, resultado);
				    	}
				    	else if(parts2[1].equals("dibujar")){	
				    		//utilizado para la sincronizacion
				    		resultado += "responseAction:dibujar;" + dataJuego;
				    		this.sendMessage(session, resultado);
				    	}
				    	else if(parts2[1].equals("hasPartida")){	  
				    		//consulta si la partida esta creada en memoria
				    		boolean result = this.hasPartida(dataJuego);
				    		resultado += "responseAction:hasPartida;\"result\":" + result + "," + dataJuego;
				    		this.sendMessage(session, resultado);
				    	}
				    	else if(parts2[1].equals("setPartida")){	  
				    		//crear una nueva partida ingresando el primer jugador
				    		boolean result = this.setPartida(dataJuego);
				    		resultado += "responseAction:setPartida;\"result\":" + result + "," + dataJuego;
				    		this.sendMessage(session, resultado);
				    	}
				    	else if(parts2[1].equals("getUnirsePartida")){	 
				    		//listado de partidas para poder unirse
				    		String result = this.getUnirsePartida();
				    		resultado += "responseAction:getUnirsePartida;\"result\":" + result  + "," + dataJuego;
				    		this.sendMessage(session, resultado);
				    	}
				    	else if(parts2[1].equals("getCargarPartida")){	 
				    		//listado cargado de db para cargar una partida
				    		String result = this.getCargarPartida();
				    		resultado += "responseAction:getCargarPartida;\"result\":" + result + "," + dataJuego;
				    		this.sendMessage(session, resultado);
				    	}
				    	else if(parts2[1].equals("setCargarPartida")){	
				    		//empezar una partida que estaba guardada
				    		String result = this.setCargarPartida(dataJuego);
				    		resultado += "responseAction:setCargarPartida;\"result\":" + result + "," + dataJuego;
				    		this.sendMessage(session, resultado);
				    	}
		    		}	
		    	}
	    	}	    	
	    }
	}
	
	private String setCargarPartida(String dataJuego){
		dataJuego = dataJuego.replace("\"", "");
		String resultado = "false";
//		String nombrePartida = "";
//		String rolPartida = "";
//		String[] parts = dataJuego.split(",");	    		
//		for(String palabra2: parts){
//			String[] parts2 = palabra2.split(":");
//			if(parts2[0].equals("nombrePartida")){
//				nombrePartida = parts2[1];
//			}
//			else if(parts2[0].equals("rolPartida")){
//				rolPartida = parts2[1];
//			}
//		}
//		nombrePartida = nombrePartida.trim();
//		rolPartida = rolPartida.trim();		
//		
//		FachadaSocket isntancia = FachadaSocket.getInstancia();
//		try {
//			isntancia.monitorJuego.comenzarEscritura();			
//			WservicejuegoStub clienteWS;
//			clienteWS = new WservicejuegoStub(isntancia.urlWebService);
//			SetCargarPartida reqSetCargarPartida = new SetCargarPartida();
//			reqSetCargarPartida.setNombrePartida(nombrePartida);
//			reqSetCargarPartida.setRolPartida(rolPartida);
//			SetCargarPartidaResponse respSetCargarPartida;			
//			respSetCargarPartida = clienteWS.setCargarPartida(reqSetCargarPartida);
//			resultado = respSetCargarPartida.get_return();
//			//TODO: pasar lo de web service para aca			
//		} 
//		catch (AxisFault e1) {
//			resultado = "ERROR AXIS";
//		}			
//		catch (RemoteException | FachadaExceptionException0 | MonitorException e) {				
//			resultado = "ERRROR";					
//		}	
//		finally{
//			isntancia.monitorJuego.terminarEscritura();
//		}
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
			resultado = "ERRROR";
		} catch (SOAPException | IOException e) {
			System.out.println("ERROR WEB SERVICE");
			resultado = "ERRROR";
		}		
		finally{
			instancia.monitorJuego.terminarLectura();
		}
		return resultado;
	}
	
	private boolean guardarPartida(String dataJuego){
		dataJuego = dataJuego.replace("\"", "");
		boolean resultado = false;
//		String nombrePartida = "";
//		String[] parts = dataJuego.split(",");	    		
//		for(String palabra2: parts){
//			String[] parts2 = palabra2.split(":");
//			if(parts2[0].equals("nombrePartida")){
//				nombrePartida = parts2[1];
//			}
//		}
//		nombrePartida = nombrePartida.trim();
//		if(!nombrePartida.isEmpty()){			
//			FachadaSocket instancia = FachadaSocket.getInstancia();
//			try{
//				instancia.monitorJuego.comenzarLectura();//TODO: aca ver si es lectura o escritura				
//				Partida partida = instancia.partidas.find(nombrePartida);
//				EstadoPartida estadoTerminada = EstadoPartida.TERMINADA;
//				EstadoPartida estadoEnCurso = EstadoPartida.ENCURSO;
//				if(partida.getEstadoPartida() == estadoTerminada){
//					String dataAux = "nombrePartida:" + nombrePartida + ",estado:terminar";
//					//instancia.iPartidas.updatePartidaEnCursoToTerminada(iConn, nombrePartida);
//				}
//				else if (partida.getEstadoPartida() == estadoEnCurso){
//					String dataAux = "nombrePartida:" + nombrePartida + ",estado:ingresarNueva";
//					//if(!instanciaWS.iPartidas.hasPartidaEnCurso(iConn, nombrePartida)){
//						//como la partida no existe, la inserto
//						VOPartida voPartidaNueva = new VOPartida(0, nombrePartida);
//						EstadoPartida estadoNueva = partida.getEstadoPartida();
//						voPartidaNueva.setEstado(estadoNueva);
//						Mapa mapa = partida.getMapa();
//						voPartidaNueva.setMapa(mapa);						
//						//instanciaWS.iPartidas.agregarPartidaEnCurso(iConn, voPartidaNueva);
//					//}
//					//Hay que guardar las figuras de la partida porque estan jugando y
//					//uno de los jugadores dicidio guardar
//					VOPartida voPartida = instanciaWS.iPartidas.findPartidaEnCurso(iConn, nombrePartida);					
//					List<VOFigurasPartidas> listaFiguras = new LinkedList<VOFigurasPartidas>();
//					VOFigurasPartidas voManguera1 = new VOFigurasPartidas();
//					VOFigurasPartidas voManguera2 = new VOFigurasPartidas();
//					VOFigurasPartidas voManguera3 = new VOFigurasPartidas();
//					VOFigurasPartidas voManguera4 = new VOFigurasPartidas();
//					VOFigurasPartidas voManguera5 = new VOFigurasPartidas();
//					VOFigurasPartidas voManguera6 = new VOFigurasPartidas();
//					VOFigurasPartidas voManguera7 = new VOFigurasPartidas();
//					VOFigurasPartidas voManguera8 = new VOFigurasPartidas();					
//					VOFigurasPartidas voBarco = new VOFigurasPartidas();
//					VOFigurasPartidas voLancha1 = new VOFigurasPartidas();
//					VOFigurasPartidas voLancha2 = new VOFigurasPartidas();
//					VOFigurasPartidas voLancha3 = new VOFigurasPartidas();
//					for(String palabra3: parts){
//						String[] parts2 = palabra3.split(":");
//						if(parts2[0].equals("manguera1")){
//							voManguera1.setId_figura(1); 
//							voManguera1.setMangueras(Boolean.parseBoolean(parts2[1]));
//							voManguera1.setId_partida(voPartida.getIdPartida());
//							voManguera1.setImpactosPermitidos(0);
//							voManguera1.setPosicionX(0);
//							voManguera1.setPosicionY(0);
//							voManguera1.setAngulo(0);
//						}
//						else if(parts2[0].equals("manguera2")){
//							voManguera2.setId_figura(1); 
//							voManguera2.setMangueras(Boolean.parseBoolean(parts2[1]));
//							voManguera2.setId_partida(voPartida.getIdPartida());
//							voManguera2.setImpactosPermitidos(0);
//							voManguera2.setPosicionX(0);
//							voManguera2.setPosicionY(0);
//							voManguera2.setAngulo(0);
//						}
//						else if(parts2[0].equals("manguera3")){
//							voManguera3.setId_figura(1); 
//							voManguera3.setMangueras(Boolean.parseBoolean(parts2[1]));
//							voManguera3.setId_partida(voPartida.getIdPartida());
//							voManguera3.setImpactosPermitidos(0);
//							voManguera3.setPosicionX(0);
//							voManguera3.setPosicionY(0);
//							voManguera3.setAngulo(0);
//						}
//						else if(parts2[0].equals("manguera4")){
//							voManguera4.setId_figura(1); 
//							voManguera4.setMangueras(Boolean.parseBoolean(parts2[1]));
//							voManguera4.setId_partida(voPartida.getIdPartida());
//							voManguera4.setImpactosPermitidos(0);
//							voManguera4.setPosicionX(0);
//							voManguera4.setPosicionY(0);
//							voManguera4.setAngulo(0);
//						}
//						else if(parts2[0].equals("manguera5")){
//							voManguera5.setId_figura(1); 
//							voManguera5.setMangueras(Boolean.parseBoolean(parts2[1]));
//							voManguera5.setId_partida(voPartida.getIdPartida());
//							voManguera5.setImpactosPermitidos(0);
//							voManguera5.setPosicionX(0);
//							voManguera5.setPosicionY(0);
//							voManguera5.setAngulo(0);
//						}
//						else if(parts2[0].equals("manguera8")){
//							voManguera8.setId_figura(1); 
//							voManguera8.setMangueras(Boolean.parseBoolean(parts2[1]));
//							voManguera8.setId_partida(voPartida.getIdPartida());
//							voManguera8.setImpactosPermitidos(0);
//							voManguera8.setPosicionX(0);
//							voManguera8.setPosicionY(0);
//							voManguera8.setAngulo(0);
//						}
//						else if(parts2[0].equals("manguera6")){
//							voManguera6.setId_figura(1); 
//							voManguera6.setMangueras(Boolean.parseBoolean(parts2[1]));
//							voManguera6.setId_partida(voPartida.getIdPartida());
//							voManguera6.setImpactosPermitidos(0);
//							voManguera6.setPosicionX(0);
//							voManguera6.setPosicionY(0);
//							voManguera6.setAngulo(0);
//						}
//						else if(parts2[0].equals("manguera7")){
//							voManguera7.setId_figura(1); 
//							voManguera7.setMangueras(Boolean.parseBoolean(parts2[1]));
//							voManguera7.setId_partida(voPartida.getIdPartida());
//							voManguera7.setImpactosPermitidos(0);
//							voManguera7.setPosicionX(0);
//							voManguera7.setPosicionY(0);
//							voManguera7.setAngulo(0);
//						}
//						else if(parts2[0].equals("posicionXLancha3")){
//							voLancha3.setId_figura(12); 
//							voLancha3.setMangueras(false);
//							voLancha3.setId_partida(voPartida.getIdPartida());
//							voLancha3.setImpactosPermitidos(0);							
//							voLancha3.setPosicionX(Float.parseFloat(parts2[1]));
//						}
//						else if(parts2[0].equals("anguloLancha3")){
//							voLancha3.setAngulo(Integer.parseInt(parts2[1]));
//						}		
//						else if(parts2[0].equals("posicionYLancha3")){
//							voLancha3.setPosicionY(Float.parseFloat(parts2[1]));
//						}
//						else if(parts2[0].equals("energiaLancha3")){
//							voLancha3.setImpactosPermitidos(Integer.parseInt(parts2[1]));
//						}
//						else if(parts2[0].equals("posicionYLancha2")){
//							voLancha2.setPosicionY(Float.parseFloat(parts2[1]));
//						}						
//						else if(parts2[0].equals("anguloLancha2")){
//							voLancha2.setAngulo(Integer.parseInt(parts2[1]));
//						}
//						else if(parts2[0].equals("energiaLancha2")){
//							voLancha2.setImpactosPermitidos(Integer.parseInt(parts2[1]));
//						}					
//						else if(parts2[0].equals("posicionXLancha2")){
//							voLancha2.setId_figura(11); 
//							voLancha2.setMangueras(false);
//							voLancha2.setId_partida(voPartida.getIdPartida());
//							voLancha2.setImpactosPermitidos(0);							
//							voLancha2.setPosicionX(Float.parseFloat(parts2[1]));
//						}
//						else if(parts2[0].equals("anguloLancha1")){
//							voLancha1.setAngulo(Integer.parseInt(parts2[1]));
//						}						
//						else if(parts2[0].equals("energiaLancha1")){
//							voLancha1.setImpactosPermitidos(Integer.parseInt(parts2[1]));
//						}
//						else if(parts2[0].equals("posicionYLancha1")){
//							voLancha1.setPosicionY(Float.parseFloat(parts2[1]));
//						}
//						else if(parts2[0].equals("posicionXLancha1")){
//							voLancha1.setId_figura(10); 
//							voLancha1.setMangueras(false);
//							voLancha1.setId_partida(voPartida.getIdPartida());
//							voLancha1.setImpactosPermitidos(0);							
//							voLancha1.setPosicionX(Float.parseFloat(parts2[1]));	
//						}
//						else if(parts2[0].equals("anguloBarco")){
//							voBarco.setAngulo(Integer.parseInt(parts2[1]));
//						}
//						else if(parts2[0].equals("posicionYBarco")){
//							 voBarco.setPosicionY(Float.parseFloat(parts2[1]));							 
//						}
//						else if(parts2[0].equals("posicionXBarco")){
//							voBarco.setId_figura(9); 
//							voBarco.setMangueras(false);
//							voBarco.setId_partida(voPartida.getIdPartida());
//							voBarco.setImpactosPermitidos(0);							
//							voBarco.setPosicionX(Float.parseFloat(parts2[1]));							
//						}						
//					}
//					listaFiguras.add(voManguera1);
//					listaFiguras.add(voManguera2);
//					listaFiguras.add(voManguera3);
//					listaFiguras.add(voManguera4);
//					listaFiguras.add(voManguera5);
//					listaFiguras.add(voManguera6);
//					listaFiguras.add(voManguera7);
//					listaFiguras.add(voManguera8);
//					listaFiguras.add(voBarco);
//					listaFiguras.add(voLancha1);
//					listaFiguras.add(voLancha2);
//					listaFiguras.add(voLancha3);
//					instanciaWS.iFigurasPartidas.agregarFigurasPartidas(iConn, listaFiguras);	
//				}
//				else{
//					partida.setEstadoPartida(estadoTerminada);
//				}
//			}
//			catch(MonitorException | PersistenciaException | ActualizarEstadoPartidaException | 
//				BuscarPartidasException | ExistePartidaEnCursoException | AgregarPartidaException | 
//				AgregarFigurasPartidasException e){
//					resultado = false;;
//					try {                             
//						instanciaWS.ipool.liberarConexion(iConn, false);
//		                iConn = null;
//		                instanciaWS.monitorJuego.terminarEscritura();                 	                  
//			        }
//			        catch(PersistenciaException e1) {
//			        	instanciaWS.monitorJuego.terminarEscritura();    
//			        	resultado = false;;
//			        }
//				}
//				finally{
//					instanciaWS.monitorJuego.terminarEscritura();
//					try {
//		                if(iConn != null) {                
//		                	instanciaWS.ipool.liberarConexion(iConn, true);
//		                	instanciaWS.monitorJuego.terminarEscritura();
//		                } 	                   
//		            }
//		            catch(PersistenciaException e) {
//		            	instanciaWS.monitorJuego.terminarEscritura();    
//		            	resultado = false;
//		            } 	
//				}
//		}		
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
			resultado = "false";
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
		FachadaSocket isntancia = FachadaSocket.getInstancia();
		resultado = isntancia.partidas.member(nombrePartida);
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
			return false;
		}		
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
			//IConexion iConn = null;
			FachadaSocket instancia = FachadaSocket.getInstancia();
			try{
				instancia.monitorJuego.comenzarEscritura();
				//iConn = instanciaWS.ipool.obtenerConexion(true);						
				Partida partida = instancia.partidas.find(nombrePartida);
				EstadoPartida estadoTerminada = EstadoPartida.TERMINADA;				
				if(partida.getEstadoPartida() == estadoTerminada){
					String dataJuegoAux = "nombrePartida:" + nombrePartida + ",estado:abandonar";
					String resultado2 = instancia.webservice.setGuardarPartida(dataJuegoAux);
					resultado = true;
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
		
	private boolean unirsePartida(String dataJuego){
		dataJuego = dataJuego.replace("\"", "");
		boolean resultado = false;
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
			return resultado;
		}
		
		FachadaSocket instancia = FachadaSocket.getInstancia();
		try {
			instancia.monitorJuego.comenzarEscritura();
			if(!instancia.partidas.member(nombrePartida)) {
				return resultado;
			}
			Partida partidaCreada = instancia.partidas.find(nombrePartida);
			if (partidaCreada.getEstadoPartida() != EstadoPartida.CREADA){
				return resultado;
			}
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
		} catch (MonitorException e) {
			System.out.println("ERROR MONITOR");			 
		}
		finally{
			instancia.monitorJuego.terminarEscritura();
		}
		return resultado;
	}
}