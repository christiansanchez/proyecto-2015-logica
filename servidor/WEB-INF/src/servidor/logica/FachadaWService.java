package servidor.logica;

import java.util.List;

import servidor.excepciones.ActualizarEstadoPartidaException;
import servidor.excepciones.BuscarPartidasException;
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
		String resultado = "false";
		String nombrePartida = "";
		String estado = "";
		String[] parts = dataJuego.split(",");
		for(String palabra2: parts){
			String[] parts2 = palabra2.split(":");
			if(parts2[0].equals("nombrePartida")){
				nombrePartida = parts2[1];
			}
			if(parts2[0].equals("estado")){
				estado = parts2[1];
			}
		}
		if(estado.isEmpty() || estado.isEmpty()){
			return resultado;
		}
		IConexion iConn = null;
		try {
			FachadaService instanciaWS = FachadaService.getInstancia();				
			if(estado.equals("") && instanciaWS.iPartidas.hasPartidaEnCurso(iConn, nombrePartida)){
				//si la partida se encuentra en curso en bd, se debe volver a estado creada,
				//para que pueda volverse a jugar desde el ultimo punto de guardado			
				instanciaWS.iPartidas.updatePartidaEnCursoToCreada(iConn, nombrePartida);
				resultado = "true";					
			}
		} 
		catch (ActualizarEstadoPartidaException | ExistePartidaEnCursoException | FachadaException e) {
			resultado = "false";
		}
		return resultado;
	}

	public String getUnirsePartida(String dataJuego) throws FachadaException{
		String resultado = "";
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
