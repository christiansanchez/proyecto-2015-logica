package servidor.valueObjects;

import java.io.Serializable;

public class VOUnirse_a_Partida implements Serializable{

    private static final long serialVersionUID = 7321693932102103092L;
    private VOJugador jugador2;
    private String alias;
    
    
    public VOUnirse_a_Partida(VOJugador jugador2) {
        super();
        this.jugador2 = jugador2;
        
    }
    
    public VOJugador getJugador2() {
        return jugador2;
    }

    public void setJugador2(VOJugador jugador2) {
        this.jugador2 = jugador2;
    }
    
    
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
