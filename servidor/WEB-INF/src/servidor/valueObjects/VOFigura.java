package servidor.valueObjects;

public class VOFigura {

	private static final long serialVersionUID = 3696776826161294598L;
	int idFigura;
	int posX;
	int posY;
	
	public VOFigura(int idFigura, int posX, int posY){
		this.idFigura = idFigura;
		this.posX = posX;
		this.posY = posY;
	}

	public int getIdFigura() {
		return idFigura;
	}

	public void setIdFigura(int idFigura) {
		this.idFigura = idFigura;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	
}
