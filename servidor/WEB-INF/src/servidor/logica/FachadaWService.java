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
		super();
	}
	
	public boolean hasPartida(String nombrePartida){
		return this.partidas.member(nombrePartida);
	}
	
	public boolean setPartida(String nombrePartida, String rolPartida, String tipoMapa){
		boolean resultado = false;
		try{
			this.monitorJuego.comenzarEscritura();
			if(!this.partidas.member(nombrePartida)) {
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
				this.partidas.insert(partidaNueva);
				resultado = true;
			}			
		}
		catch(MonitorException e){
			resultado = false;
		}
		finally {	
			this.monitorJuego.terminarEscritura();
        } 
		return resultado;		
	}
	
	public String getUnirsePartida(){
		String resultado = "";
		try{
			this.monitorJuego.comenzarLectura();
			resultado = this.partidas.getPartidasDisponibles();
		}
		catch(MonitorException e){
			resultado = "";
		}
		finally{
			this.monitorJuego.terminarLectura();
		}
		return resultado;
	}
	
	public String getCargarPartida(){
		String resultado = "";
		IConexion iConn = null;
		try{
			this.monitorJuego.comenzarLectura();
			iConn = this.ipool.obtenerConexion(false);			
			List<VOPartida> partidasCreadas = this.iPartidas.listarPartidasCreadas(iConn);
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
                this.ipool.liberarConexion(iConn, false);
                iConn = null;
                this.monitorJuego.terminarLectura();                           
	        }
	        catch(PersistenciaException e1) {
	        	this.monitorJuego.terminarLectura();
	        }		
		}
		finally{			
			try{
				if(iConn != null) {				
					this.ipool.liberarConexion(iConn, true);
					this.monitorJuego.terminarLectura();					
				}				
			}
			catch(PersistenciaException e){
				this.monitorJuego.terminarLectura();
				resultado = "";
			}
		}
		return resultado;
	}
	
	public boolean setCargarPartida(String nombrePartida, String rolPartida){
		return false;
	}
	
	public boolean setUnirsePartida(String nombrePartida, String rolPartida){
		//AUN SIN TERMINAR
		//TODO:los datos se van a cargar cuando se haga set cargar partida
		//voy a tener que controlar que si los jugadores ya estan cargados, no pisarlos.
		//porq si esta cargado esa info fue la que se trajo desde la bd.
		boolean resultado = false;
		try{
			this.monitorJuego.comenzarEscritura();
			if(!this.partidas.member(nombrePartida)) {
				Partida partidaCreada = this.partidas.find(nombrePartida);				
				Jugador jugador2 = null;
				if(rolPartida.equals("BARCOCARGUERO")){					
					Barco barco = new Barco();
					barco.barcoNuevo();
					BarcoCarguero barcoCarguero = new BarcoCarguero(barco);
					jugador2 = new Jugador(barcoCarguero);
//					partidaNueva = new Partida(nombrePartida, jugador1, jugador2, mapa);
				}
				else{
					Pirata pirata = new Pirata();
					pirata.pirataNuevo();
					jugador2 = new Jugador(pirata);
					partidaCreada.setLanchaPirata(jugador2);
//					
				}
//				this.partidas.insert(partidaNueva);
				resultado = true;
			}			
		}
		catch(MonitorException e){
			resultado = false;
		}
		finally{
			this.monitorJuego.terminarEscritura();
		}
		return resultado;
	}
}
