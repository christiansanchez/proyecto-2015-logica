package servidor.logica;

import java.util.List;

import servidor.excepciones.FachadaException;
import servidor.excepciones.ListarPartidasCreadasException;
import servidor.excepciones.MonitorException;
import servidor.excepciones.PersistenciaException;
import servidor.persistencia.poolConexiones.IConexion;
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
		String resultado = "";
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
		return resultado;	
	}

}
