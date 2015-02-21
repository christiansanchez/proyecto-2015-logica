package servidor.logica;

import servidor.excepciones.FachadaException;
import servidor.excepciones.MonitorException;
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
				EstadoPartida estadoPartida = EstadoPartida.CREADA;
				Partida partidaNueva = null;
				Jugador jugador1 = null;
				Jugador jugador2 = null;
				if(rolPartida.equals("BARCOCARGUERO")){					
					Barco barco = new Barco();
					barco.barcoNuevo();
					BarcoCarguero barcoCarguero = new BarcoCarguero(barco);
					jugador1 = new Jugador(barcoCarguero);
					partidaNueva = new Partida(nombrePartida, jugador1, jugador2, mapa, estadoPartida);
				}
				else{
					Pirata pirata = new Pirata();
					pirata.pirataNuevo();
					jugador1 = new Jugador(pirata);
					partidaNueva = new Partida(nombrePartida, jugador2, jugador1, mapa, estadoPartida);
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
		return null;
	}
	
	public String getCargarPartida(){
		return null;
	}
	
	public boolean setCargarPartida(String nombrePartida, String rolPartida){
		return false;
	}
	
	public boolean setUnirsePartida(String nombrePartida, String rolPartida){
		return false;
	}
}
