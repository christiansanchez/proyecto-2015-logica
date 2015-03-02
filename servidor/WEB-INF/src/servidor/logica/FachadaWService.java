package servidor.logica;

import java.util.LinkedList;
import java.util.List;

import servidor.excepciones.ActualizarEstadoPartidaException;
import servidor.excepciones.AgregarFigurasPartidasException;
import servidor.excepciones.AgregarPartidaException;
import servidor.excepciones.BuscarPartidasException;
import servidor.excepciones.EliminarPartidaException;
import servidor.excepciones.ExistePartidaEnCursoException;
import servidor.excepciones.FachadaException;
import servidor.excepciones.FigurasPartidasIdPartidaException;
import servidor.excepciones.ListarPartidasCreadasException;
import servidor.excepciones.PersistenciaException;
import servidor.persistencia.poolConexiones.IConexion;
import servidor.valueObjects.VOFigurasPartidas;
import servidor.valueObjects.VOPartida;
/*
 * Clase encargada de definir la logica y los metodos utilizados
 * desde el web service.
 */
public class FachadaWService {
	
	public FachadaWService(){
		try {
			FachadaService.getInstancia();
		} 
		catch (FachadaException e) {
			System.out.println("ERROR CONSTRUCTOR");
		}		
	}
	
	public String setGuardarPartida(String dataJuego){
		dataJuego = dataJuego.replace("\"", "");
		String resultado = "false";
		String nombrePartida = "";
		String estado = "";
		String status = "";
		String[] parts = dataJuego.split(",");
		String tipoMapa = "";
		String posicionBarcoX = "";
		String posicionBarcoY = "";
		String anguloBarco = "";
		String manguera1 = "";
		String manguera2 = "";
		String manguera3 = "";
		String manguera4 = "";
		String manguera5 = "";
		String manguera6 = "";
		String manguera7 = "";
		String manguera8 = "";
		String posicionXLancha1 = "";
		String posicionYLancha1 = "";
		String energiaLancha1 = "";
		String anguloLancha1 = "";
		String posicionXLancha2 = "";
		String posicionYLancha2 = "";
		String energiaLancha2 = "";
		String anguloLancha2 = "";
		String posicionXLancha3 = "";
		String posicionYLancha3 = "";
		String energiaLancha3 = "";
		String anguloLancha3 = "";
		for(String palabra2: parts){
			String[] parts2 = palabra2.split(":");
			if(parts2[0].equals("nombrePartida")){
				nombrePartida = parts2[1];
			}
			else if(parts2[0].equals("estado")){
				estado = parts2[1];
			}
			else if(parts2[0].equals("status")){
				status = parts2[1];
			}			
			else if(parts2[0].equals("tipoMapa")){
				tipoMapa = parts2[1];
			}
			else if(parts2[0].equals("posicionBarcoX")){
				posicionBarcoX = parts2[1];
			}
			else if(parts2[0].equals("posicionBarcoY")){
				posicionBarcoY = parts2[1];
			}
			else if(parts2[0].equals("anguloBarco")){
				anguloBarco = parts2[1];
			}
			else if(parts2[0].equals("manguera1")){
				manguera1 = parts2[1];
			}
			else if(parts2[0].equals("manguera2")){
				manguera2 = parts2[1];
			}
			else if(parts2[0].equals("manguera3")){
				manguera3 = parts2[1];
			}
			else if(parts2[0].equals("manguera4")){
				manguera4 = parts2[1];
			}
			else if(parts2[0].equals("manguera5")){
				manguera5 = parts2[1];
			}
			else if(parts2[0].equals("manguera6")){
				manguera6 = parts2[1];
			}
			else if(parts2[0].equals("manguera7")){
				manguera7 = parts2[1];
			}
			else if(parts2[0].equals("manguera8")){
				manguera8 = parts2[1];
			}
			else if(parts2[0].equals("posicionXLancha1")){
				posicionXLancha1 = parts2[1];
			}
			else if(parts2[0].equals("posicionYLancha1")){
				posicionYLancha1 = parts2[1];
			}
			else if(parts2[0].equals("energiaLancha1")){
				energiaLancha1 = parts2[1];
			}
			else if(parts2[0].equals("anguloLancha1")){
				anguloLancha1 = parts2[1];
			}
			else if(parts2[0].equals("posicionXLancha2")){
				posicionXLancha2 = parts2[1];
			}
			else if(parts2[0].equals("posicionYLancha2")){
				posicionYLancha2 = parts2[1];
			}
			else if(parts2[0].equals("energiaLancha2")){
				energiaLancha2 = parts2[1];
			}
			else if(parts2[0].equals("anguloLancha2")){
				anguloLancha2 = parts2[1];
			}
			else if(parts2[0].equals("posicionXLancha3")){
				posicionXLancha3 = parts2[1];
			}
			else if(parts2[0].equals("posicionYLancha3")){
				posicionYLancha3 = parts2[1];
			}
			else if(parts2[0].equals("energiaLancha3")){
				energiaLancha3 = parts2[1];
			}
			else if(parts2[0].equals("anguloLancha3")){
				anguloLancha3 = parts2[1];
			}
		}
		if(estado.isEmpty() || estado.isEmpty()){
			return resultado;
		}
		IConexion iConn = null;
		FachadaService instanciaWS = null;
		try {			
			instanciaWS = FachadaService.getInstancia();
			iConn = instanciaWS.ipool.obtenerConexion(true);
			if(estado.equals("abandonar") && instanciaWS.iPartidas.hasPartidaEnCurso(iConn, nombrePartida)){
				//si la partida se encuentra en curso en bd, se debe volver a estado creada,
				//para que pueda volverse a jugar desde el ultimo punto de guardado			
				instanciaWS.iPartidas.updatePartidaEnCursoToCreada(iConn, nombrePartida);
				resultado = "true";					
			}
			else if(estado.equals("terminar") && instanciaWS.iPartidas.hasPartidaEnCurso(iConn, nombrePartida)){
				//si la partida se encuentra en curso en bd, se debe pasar a estado tTerminada,
				//para que pueda volverse a jugar desde el ultimo punto de guardado			
				instanciaWS.iPartidas.updatePartidaEnCursoToTerminada(iConn, nombrePartida);
				resultado = "true";					
			}
			else if (estado.equals("guardar")){
				if(status.equals("ENCURSO")){
					if(instanciaWS.iPartidas.hasPartidaEnCurso(iConn, nombrePartida)){
						//si existe partida en curso la borro
						VOPartida voPartida = instanciaWS.iPartidas.findPartidaEnCurso(iConn, nombrePartida);
						instanciaWS.iPartidas.eliminarPartida(iConn, voPartida.getIdPartida());	
						instanciaWS.ipool.liberarConexion(iConn, true);
						iConn = instanciaWS.ipool.obtenerConexion(true);
					}					
					if(!instanciaWS.iPartidas.hasPartidaEnCurso(iConn, nombrePartida)){
						//partida no existe en base de datos y se agrega
						VOPartida voPartidaNueva = new VOPartida(0, nombrePartida);						
						TipoMapa mapaElegido;
						if (tipoMapa.equals("MARABIERTO")){
							mapaElegido = TipoMapa.MARABIERTO;
						}
						else{
							mapaElegido = TipoMapa.ISLAS;
						}
						Mapa mapa = new Mapa(mapaElegido);							
						EstadoPartida estadoNueva = EstadoPartida.ENCURSO;												
						voPartidaNueva.setEstado(estadoNueva);						
						voPartidaNueva.setMapa(mapa);						
						instanciaWS.iPartidas.agregarPartidaEnCurso(iConn, voPartidaNueva);
						instanciaWS.ipool.liberarConexion(iConn, true);
						iConn = instanciaWS.ipool.obtenerConexion(true);
						
					}					
					//Hay que guardar las figuras de la partida porque estan jugando y
					//uno de los jugadores dicidio guardar
					VOPartida voPartida = instanciaWS.iPartidas.findPartidaEnCurso(iConn, nombrePartida);					
					List<VOFigurasPartidas> listaFiguras = new LinkedList<VOFigurasPartidas>();
					VOFigurasPartidas voManguera1 = new VOFigurasPartidas();
					VOFigurasPartidas voManguera2 = new VOFigurasPartidas();
					VOFigurasPartidas voManguera3 = new VOFigurasPartidas();
					VOFigurasPartidas voManguera4 = new VOFigurasPartidas();
					VOFigurasPartidas voManguera5 = new VOFigurasPartidas();
					VOFigurasPartidas voManguera6 = new VOFigurasPartidas();
					VOFigurasPartidas voManguera7 = new VOFigurasPartidas();
					VOFigurasPartidas voManguera8 = new VOFigurasPartidas();					
					VOFigurasPartidas voBarco = new VOFigurasPartidas();
					VOFigurasPartidas voLancha1 = new VOFigurasPartidas();
					VOFigurasPartidas voLancha2 = new VOFigurasPartidas();
					VOFigurasPartidas voLancha3 = new VOFigurasPartidas();
				
					voManguera1.setId_figura(1); 
					voManguera1.setMangueras(Boolean.parseBoolean(manguera1));
					voManguera1.setId_partida(voPartida.getIdPartida());
					voManguera1.setImpactosPermitidos(0);
					voManguera1.setPosicionX(0);
					voManguera1.setPosicionY(0);
					voManguera1.setAngulo(0);

					voManguera2.setId_figura(1); 
					voManguera2.setMangueras(Boolean.parseBoolean(manguera2));
					voManguera2.setId_partida(voPartida.getIdPartida());
					voManguera2.setImpactosPermitidos(0);
					voManguera2.setPosicionX(0);
					voManguera2.setPosicionY(0);
					voManguera2.setAngulo(0);

					voManguera3.setId_figura(1); 
					voManguera3.setMangueras(Boolean.parseBoolean(manguera3));
					voManguera3.setId_partida(voPartida.getIdPartida());
					voManguera3.setImpactosPermitidos(0);
					voManguera3.setPosicionX(0);
					voManguera3.setPosicionY(0);
					voManguera3.setAngulo(0);

					voManguera4.setId_figura(1); 
					voManguera4.setMangueras(Boolean.parseBoolean(manguera4));
					voManguera4.setId_partida(voPartida.getIdPartida());
					voManguera4.setImpactosPermitidos(0);
					voManguera4.setPosicionX(0);
					voManguera4.setPosicionY(0);
					voManguera4.setAngulo(0);

					voManguera5.setId_figura(1); 
					voManguera5.setMangueras(Boolean.parseBoolean(manguera5));
					voManguera5.setId_partida(voPartida.getIdPartida());
					voManguera5.setImpactosPermitidos(0);
					voManguera5.setPosicionX(0);
					voManguera5.setPosicionY(0);
					voManguera5.setAngulo(0);

					voManguera8.setId_figura(1); 
					voManguera8.setMangueras(Boolean.parseBoolean(manguera8));
					voManguera8.setId_partida(voPartida.getIdPartida());
					voManguera8.setImpactosPermitidos(0);
					voManguera8.setPosicionX(0);
					voManguera8.setPosicionY(0);
					voManguera8.setAngulo(0);

					voManguera6.setId_figura(1); 
					voManguera6.setMangueras(Boolean.parseBoolean(manguera6));
					voManguera6.setId_partida(voPartida.getIdPartida());
					voManguera6.setImpactosPermitidos(0);
					voManguera6.setPosicionX(0);
					voManguera6.setPosicionY(0);
					voManguera6.setAngulo(0);

					voManguera7.setId_figura(1); 
					voManguera7.setMangueras(Boolean.parseBoolean(manguera7));
					voManguera7.setId_partida(voPartida.getIdPartida());
					voManguera7.setImpactosPermitidos(0);
					voManguera7.setPosicionX(0);
					voManguera7.setPosicionY(0);
					voManguera7.setAngulo(0);
						
					voLancha3.setId_figura(12); 
					voLancha3.setMangueras(false);
					voLancha3.setId_partida(voPartida.getIdPartida());
					voLancha3.setImpactosPermitidos(0);							
					voLancha3.setPosicionX(Float.parseFloat(posicionXLancha3));
					voLancha3.setAngulo(Integer.parseInt(anguloLancha3));
					voLancha3.setPosicionY(Float.parseFloat(posicionYLancha3));
					voLancha3.setImpactosPermitidos(Integer.parseInt(energiaLancha3));
				
					voLancha2.setPosicionY(Float.parseFloat(posicionYLancha2));
					voLancha2.setAngulo(Integer.parseInt(anguloLancha2));
					voLancha2.setImpactosPermitidos(Integer.parseInt(energiaLancha2));
					voLancha2.setId_figura(11); 
					voLancha2.setMangueras(false);
					voLancha2.setId_partida(voPartida.getIdPartida());
					voLancha2.setImpactosPermitidos(Integer.parseInt(energiaLancha2));							
					voLancha2.setPosicionX(Float.parseFloat(posicionXLancha2));
		
					voLancha1.setAngulo(Integer.parseInt(anguloLancha1));
					voLancha1.setImpactosPermitidos(Integer.parseInt(energiaLancha1));
					voLancha1.setPosicionY(Float.parseFloat(posicionYLancha1));
					voLancha1.setId_figura(10); 
					voLancha1.setMangueras(false);
					voLancha1.setId_partida(voPartida.getIdPartida());
					voLancha1.setImpactosPermitidos(Integer.parseInt(energiaLancha1));							
					voLancha1.setPosicionX(Float.parseFloat(posicionXLancha1));	
			
					voBarco.setAngulo(Integer.parseInt(anguloBarco));
					voBarco.setPosicionY(Float.parseFloat(posicionBarcoY));							 				
					voBarco.setId_figura(9); 
					voBarco.setMangueras(false);
					voBarco.setId_partida(voPartida.getIdPartida());
					voBarco.setImpactosPermitidos(0);							
					voBarco.setPosicionX(Float.parseFloat(posicionBarcoX));							
				
					listaFiguras.add(voManguera1);
					listaFiguras.add(voManguera2);
					listaFiguras.add(voManguera3);
					listaFiguras.add(voManguera4);
					listaFiguras.add(voManguera5);
					listaFiguras.add(voManguera6);
					listaFiguras.add(voManguera7);
					listaFiguras.add(voManguera8);
					listaFiguras.add(voBarco);
					listaFiguras.add(voLancha1);
					listaFiguras.add(voLancha2);
					listaFiguras.add(voLancha3);					
					instanciaWS.iFigurasPartidas.agregarFigurasPartidas(iConn, listaFiguras);	
					resultado = "true";
				}
			}			
		} 
		catch (ActualizarEstadoPartidaException | ExistePartidaEnCursoException | 
				FachadaException | AgregarPartidaException | BuscarPartidasException |
				AgregarFigurasPartidasException | PersistenciaException | EliminarPartidaException e) {
			resultado = "false";			
			try{
				instanciaWS.ipool.liberarConexion(iConn, false);
                iConn = null;	                                           
	        }
	        catch(PersistenciaException e1) {
	        	System.out.println("ERROR CERRAR CONEXION DB");
	        }
		}
		finally{
			try{
				if(iConn != null) {
					instanciaWS.ipool.liberarConexion(iConn, true);	
				}				
			}
			catch(PersistenciaException e1){	
				System.out.println("ERROR CERRAR CONEXION DB FINALLY");
			}	
		}
		return resultado;
	}

	public String getCargarPartida() throws FachadaException{
		String resultado = "";
		IConexion iConn = null;
		FachadaService instancia = FachadaService.getInstancia();
		try{
			iConn = instancia.ipool.obtenerConexion(false);
			List<VOPartida> partidasCreadas = instancia.iPartidas.listarPartidasCreadas(iConn);
			String partidaStr = "";
			for (VOPartida voPartida : partidasCreadas) {
				partidaStr += "\"nombrePartida\":\"" + voPartida.getNombre() + "\"," +						  
						      "\"tipoMapa\":\"" + voPartida.getTipoMapaStr() + "\";";				 
			}
			resultado += partidaStr;
			if (!resultado.isEmpty()){
				resultado = resultado.substring(0, resultado.length()-1);
			}
			resultado = resultado.replace(" ", "%20");
		}
		catch(PersistenciaException | ListarPartidasCreadasException e){
				resultado = "";
				try{
					instancia.ipool.liberarConexion(iConn, false);
	                iConn = null;	                                           
		        }
		        catch(PersistenciaException e1) {
		        	System.out.println("ERROR CERRAR CONEXION DB");
		        }		
		}			
		finally{			
			try{
				if(iConn != null) {
					instancia.ipool.liberarConexion(iConn, true);	
				}				
			}
			catch(PersistenciaException e1){	
				System.out.println("ERROR CERRAR CONEXION DB FINALLY");
			}			
		}	
		return resultado;	
	}
	
	public String setCargarPartida(String dataJuego) throws FachadaException{
		String resultado = "";
		String nombrePartida = "";String[] parts = dataJuego.split(",");	    		
		for(String palabra2: parts){
			String[] parts2 = palabra2.split(":");
			if(parts2[0].equals("nombrePartida")){
				nombrePartida = parts2[1];
			}
		}	
		nombrePartida = nombrePartida.trim();
		if(nombrePartida.isEmpty()){
			return resultado;
		}
		IConexion iConn = null;
		FachadaService instanciaWS = FachadaService.getInstancia();
		try {
			iConn = instanciaWS.ipool.obtenerConexion(false);
			VOPartida voPartida = instanciaWS.iPartidas.find(iConn, nombrePartida);			
			List<VOFigurasPartidas> figurasPartidas = instanciaWS.iFigurasPartidas.listarFigurasPartidasIdPartida(iConn, voPartida.getIdPartida());				
			String figurasPartidasStr = "";	
			for (VOFigurasPartidas voFigurasPartidas : figurasPartidas){					
				switch(voFigurasPartidas.getId_figura()) {
				 case 1://MANGUERA1 
					 figurasPartidasStr += "\"manguera1\":" + voFigurasPartidas.getMangueras() + ";";
				     break;
				 case 2://MANGUERA2
					 figurasPartidasStr += "\"manguera2\":" + voFigurasPartidas.getMangueras() + ";";
				     break;
				 case 3://MANGUERA3 
					 figurasPartidasStr += "\"manguera3\":" + voFigurasPartidas.getMangueras() + ";";
				     break;
				 case 4://MANGUERA$
					 figurasPartidasStr += "\"manguera4\":" + voFigurasPartidas.getMangueras() + ";";
				     break;
				 case 5://MANGUERA5
					 figurasPartidasStr += "\"manguera5\":" + voFigurasPartidas.getMangueras() + ";";
				     break;
				 case 6://MANGUERA6
					 figurasPartidasStr += "\"manguera6\":" + voFigurasPartidas.getMangueras() + ";";
					 break;
				 case 7://MANGUERA7
					 figurasPartidasStr += "\"manguera7\":" + voFigurasPartidas.getMangueras() + ";";
					 break;
				 case 8://MANGUERA8
					 figurasPartidasStr += "\"manguera8\":" + voFigurasPartidas.getMangueras() + ";";
					 break;
				 case 9://BARCO
					 figurasPartidasStr += "\"posicionXBarco\":" + voFigurasPartidas.getPosicionX() + "," + 
							 			   "\"posicionYBarco\":" + voFigurasPartidas.getPosicionY() + "," + 
							 			   "\"anguloBarco\":" + voFigurasPartidas.getAngulo() + ";";
					 break;
				 case 10://LANCHA1
					 figurasPartidasStr += "\"posicionXLancha1\":" + voFigurasPartidas.getPosicionX() + "," + 
							 			   "\"posicionYLancha1\":" + voFigurasPartidas.getPosicionY() + "," + 
							 			   "\"energiaLancha1\":" +  voFigurasPartidas.getImpactosPermitidos() + "," +
							 			   "\"anguloLancha1\":" + voFigurasPartidas.getAngulo() + ";";
					 break;
				 case 11://LANCHA2
					 figurasPartidasStr += "\"posicionXLancha2\":" + voFigurasPartidas.getPosicionX() + "," + 
							 			   "\"posicionYLancha2\":" + voFigurasPartidas.getPosicionY() + "," + 
							 			   "\"energiaLancha2\":" +  voFigurasPartidas.getImpactosPermitidos() + "," + 
							 			   "\"anguloLancha2\":" + voFigurasPartidas.getAngulo() + ";";
					 break;
				 case 12://LANCHA3
					 figurasPartidasStr += "\"posicionXLancha3\":" + voFigurasPartidas.getPosicionX() + "," +
							 			   "\"posicionYLancha3\":" + voFigurasPartidas.getPosicionY() + "," + 
							 			   "\"energiaLancha3\":" +  voFigurasPartidas.getImpactosPermitidos() + "," +
							 			   "\"anguloLancha3\":" + voFigurasPartidas.getAngulo() + ";";
					 break;
				}
			}
			resultado += figurasPartidasStr;
			if (!resultado.isEmpty()){
				resultado = resultado.substring(0, resultado.length()-1);
			}			
			instanciaWS.iPartidas.updatePartidaCredaToEnCurso(iConn, nombrePartida, voPartida.getIdPartida());
		} 
		catch (PersistenciaException e) {
			System.out.println("ERROR OBTENER CONEXION");
			try {                             
				instanciaWS.ipool.liberarConexion(iConn, false);
                iConn = null;                 	                  
	        }
	        catch(PersistenciaException e1) {
	        	System.out.println("ERROR LIBERAR CONEXION CATCH");
	        	resultado = "";	        	
	        }
		}	
		catch (BuscarPartidasException e) {
			System.out.println("ERROR BUSCAR PARTIDAS");
			resultado = "";
		}	
		catch (FigurasPartidasIdPartidaException e) {
			System.out.println("ERROR OBTENER LAS FIGURAS DE LA PARTIDA");
			resultado = "";
		}	
		catch (ActualizarEstadoPartidaException e) {
			System.out.println("ERROR CAMBIAR ESTADO DE LA PARTIDA");
			resultado = "";
		}
		finally{			
			try {
                if(iConn != null) {                
                	instanciaWS.ipool.liberarConexion(iConn, true);
                } 	                   
            }
            catch(PersistenciaException e) {
            	System.out.println("ERROR LIBERAR CONEXION FINALLY");
            	resultado = "";
            } 	
		}		
		return resultado;	
	}

}
