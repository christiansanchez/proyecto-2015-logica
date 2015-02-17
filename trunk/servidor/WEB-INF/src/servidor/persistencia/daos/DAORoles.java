package servidor.persistencia.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import servidor.excepciones.ListarRolesException;
import servidor.persistencia.consultas.ConsultaRoles;
import servidor.persistencia.poolConexiones.Conexion;
import servidor.persistencia.poolConexiones.IConexion;
import servidor.valueObjects.VORoles;

public abstract class DAORoles implements IDAORoles{
	
	private ConsultaRoles consultas;
	
	public DAORoles(){		
		this.consultas = new ConsultaRoles();			
	}
	
	public List<VORoles> listarRoles(IConexion iConn) throws ListarRolesException 
	{
		try
		{
			String query = this.consultas.allRoles();
			PreparedStatement pstmt = ((Conexion)iConn).getConnection().prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			List<VORoles> list = new LinkedList<VORoles>();			
			while(rs.next()){
				int idRol = rs.getInt("id");
				String nombre = rs.getString("nombre");				
				VORoles voRoles = new VORoles(idRol, nombre);
				list.add(voRoles);
			}
			rs.close();
			pstmt.close();
			return list;
		}
		catch(SQLException eSql)
		{
			throw new ListarRolesException();
		}
	}	
}
