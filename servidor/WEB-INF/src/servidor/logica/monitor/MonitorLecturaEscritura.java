package servidor.logica.monitor;

import servidor.excepciones.MonitorException;

public class MonitorLecturaEscritura {	
	private int lectores;
	private boolean escribiendo;
	private static MonitorLecturaEscritura instancia;
		
	private MonitorLecturaEscritura(){
		this.lectores = 0;
		this.escribiendo = false;
	}	
	public synchronized static MonitorLecturaEscritura getInstancia ()
	{ 
		if (instancia == null)
		{
			instancia = new MonitorLecturaEscritura();
		}
		return instancia;
	}	
	public synchronized void comenzarLectura() throws MonitorException
	{
		try
		{
			while(this.escribiendo)		
			{
				wait();
			}		
			this.lectores ++;
		}
		catch(InterruptedException e)
		{
			throw new MonitorException("Error en comenzar escritura.");
		}
	}	
	public synchronized void terminarLectura()
	{
		this.lectores --;
		notifyAll();
	}	
	public synchronized void comenzarEscritura()throws MonitorException
	{	
		try
		{
			while(this.lectores > 0 || this.escribiendo)
			{
				wait();
			}
			this.escribiendo = true;
		}
		catch(InterruptedException e)
		{
			throw new MonitorException("Error en comenzar escritura.");
		}
	}	
	public synchronized void terminarEscritura()
	{		
		this.escribiendo = false;	
		notifyAll();
	}
}
