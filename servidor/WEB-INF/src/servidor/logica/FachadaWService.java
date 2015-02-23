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
public class FachadaWService extends Fachada{
	
	public FachadaWService() throws FachadaException {
		Fachada.getInstancia();
	}
	
	public boolean hasPartida(String nombrePartida) throws FachadaException{		
		return (Fachada.getInstancia()).partidas.member(nombrePartida);
	}
	
	public boolean setPartida(String nombrePartida, String rolPartida, String tipoMapa) throws FachadaException{
		boolean resultado = false;
		Fachada instanciaWS = Fachada.getInstancia();
		try{			
			instanciaWS.monitorJuego.comenzarEscritura();
			if(!instanciaWS.partidas.member(nombrePartida)) {
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
				instanciaWS.partidas.insert(partidaNueva);
				resultado = true;
			}			
		}
		catch(MonitorException e){
			resultado = false;
		}
		finally {	
			instanciaWS.monitorJuego.terminarEscritura();
        } 
		return resultado;		
	}
	
	public String getUnirsePartida() throws FachadaException{
		String resultado = "";
		try{
			Fachada instanciaWS = Fachada.getInstancia();
			instanciaWS.monitorJuego.comenzarLectura();
			resultado = instanciaWS.partidas.getPartidasDisponibles();
		}
		catch(MonitorException  e){
			resultado = "";
		}
		finally{
			Fachada instanciaWS = Fachada.getInstancia();
			instanciaWS.monitorJuego.terminarLectura();
		}
		return resultado;
	}
	
	public String getCargarPartida() throws FachadaException{
		String resultado = "";
		IConexion iConn = null;
		Fachada instanciaWS = Fachada.getInstancia();
		try{			
			instanciaWS.monitorJuego.comenzarLectura();
			iConn = instanciaWS.ipool.obtenerConexion(false);			
			List<VOPartida> partidasCreadas = instanciaWS.iPartidas.listarPartidasCreadas(iConn);
			String partidaStr = "";
			for (VOPartida voPartida : partidasCreadas) {
				partidaStr += "nombrePartida:\"" + voPartida.getNombre() + "\"," + 
						  	  "tipoRolDisponible:\"\"," +
						      "tipoMapa:\"" + voPartida.getTipoMapaStr() + "\";";				 
			}
			resultado += partidaStr;
			if (!resultado.isEmpty()){
				resultado = resultado.substring(0, resultado.length()-1);
			}	
		}
		catch(MonitorException | PersistenciaException | ListarPartidasCreadasException e){
			resultado = "";
			try{
				instanciaWS.ipool.liberarConexion(iConn, false);
                iConn = null;
                instanciaWS.monitorJuego.terminarLectura();                           
	        }
	        catch(PersistenciaException e1) {
	        	instanciaWS.monitorJuego.terminarLectura();
	        }		
		}
		finally{			
			try{
				if(iConn != null) {
					instanciaWS.ipool.liberarConexion(iConn, true);
					instanciaWS.monitorJuego.terminarLectura();					
				}				
			}
			catch(PersistenciaException e){
				instanciaWS.monitorJuego.terminarLectura();
				resultado = "";
			}
		}
		return resultado;
	}
	
	public boolean setCargarPartida(String nombrePartida, String rolPartida){
		return false;
	}
	
	public boolean setUnirsePartida(String nombrePartida, String rolPartida) throws FachadaException{
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
//					instanciaWS.partidas.insert(partidaNueva);
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
