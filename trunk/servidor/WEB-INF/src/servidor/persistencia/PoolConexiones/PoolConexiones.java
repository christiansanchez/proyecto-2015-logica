package servidor.persistencia.PoolConexiones;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import servidor.excepciones.PersistenciaException;

public class PoolConexiones implements IPoolConexiones{
	
	private String driver;
	private String url;
	private String user;
	private String password;
	private int nivelTransaccionalidad;
	private int tamanio;
	private int creadas;
	private int tope;
	private Conexion[] conexiones;
	
	public PoolConexiones() throws PersistenciaException
	{
		//leo archivo de configuracion
		Properties prop = new Properties();
		String nombreArchivo = "src/configFiles/config.properties";
		try 
		{			
			//leo archivo de configuracion			
			prop.load( new FileInputStream(nombreArchivo));			
			//cargo driver
			this.driver = prop.getProperty("driver");			
			Class.forName(this.driver);			
			//datos para conexion
			this.url = prop.getProperty("url");				
			this.user = prop.getProperty("user");	
			this.password = prop.getProperty("pass");		
			this.tamanio = Integer.parseInt(prop.getProperty("tamanioPoolConexiones"));
			this.nivelTransaccionalidad = Integer.parseInt(prop.getProperty("nivelTransaccionalidad"));
			this.creadas = 0;
			this.tope = 0;
			this.conexiones = new Conexion[this.tamanio];			
		} 
		catch (IOException | ClassNotFoundException e) 
		{			
			throw new PersistenciaException();
		} 				
	}

	public synchronized void liberarConexion(IConexion conection, boolean ok) throws PersistenciaException
	{				
		try 
		{
			if(ok)
			{								
				((Conexion)conection).getConnection().commit();				 
			}
			else
			{
				((Conexion)conection).getConnection().rollback();
			}					
		}
		catch (SQLException e) 
		{			
			throw new PersistenciaException();
		}
		finally
		{
			this.tope++;
			this.conexiones[this.tope - 1] = ((Conexion)conection);
			notify();
		}
	}

	public synchronized IConexion obtenerConexion(boolean modifica) throws PersistenciaException
	{
		try 
		{
			if((this.creadas < this.tamanio) && (this.tope == 0))
			{
				this.creadas++;
				Conexion conn = new Conexion(DriverManager.getConnection(this.url, this.user, this.password));
				conn.getConnection().setTransactionIsolation(this.nivelTransaccionalidad);
				conn.getConnection().setAutoCommit(false);				
				return ((IConexion)conn);				 
			}
			else
			{					
				while(this.tope == 0)
				{
					wait();
				}
				this.tope--;
				Conexion auxCon = this.conexiones[this.tope]; 
				this.conexiones[this.tope] = null;
				return (IConexion)auxCon;									
			}
		}	
		catch (InterruptedException e) 
		{
			throw new PersistenciaException();
		}	
		catch (SQLException e) 
		{
			throw new PersistenciaException();
		}
	}		
}