package Inicio;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;



public class ConexionMySQL {
	
	//variables base de datos 
	private static final String Driver = "com.mysql.cj.jdbc.Driver";
	private static final String URL="jdbc:mysql://localhost:3306/derrap";
	private static final String Usuario="root";
	private static final String psw="Medac123";
	
	Connection con=null;
	PreparedStatement pstmt = null;
	ResultSet rs=null;
		
	
	
    //metodo para conectar a la base de datos 
    public void conectar() throws SQLException, ClassNotFoundException {
            Class.forName(Driver);
            con = DriverManager.getConnection(URL,  Usuario, psw);   
    }
	
	//Metodo para cerrar la conexion 
    public void cerrarConexion() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    
    //Metodo para hacer el login 
    
    public int logIn(String user, String contra) throws SQLException {
        int rol = 0;

        // Convertir el usuario a un ID numérico solo si es un número
        int id;
        try {
            id = Integer.parseInt(user);
        } catch (NumberFormatException e) {
            return 3; // Usuario no encontrado, ya que el ID no es un número
        }

        String consulta = "SELECT * FROM Usuarios WHERE ID_Usuario = ?";
        
        try (PreparedStatement pstmt = con.prepareStatement(consulta)) {
            pstmt.setInt(1, id);  
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String storedPassword = rs.getString("Contraseña");
                    if (storedPassword.equalsIgnoreCase(contra)) {
                        rol = rs.getInt("Rol"); 
                    } else {
                        rol = 4; // Contraseña incorrecta
                    }
                } else {
                    rol = 3; // Usuario no encontrado
                }
            }
        }
        return rol;
    }

    
    //metodo para insertar clientes
    public int insetarClientes(String dni,String nombre, String apellido, String telefono) throws SQLException {
        
        String consulta = "INSERT INTO "+ "Cliente" +"(DNI, Nombre, Apellido, Tlf) VALUES (?, ?, ?, ?)";
        
         PreparedStatement pstmt = con.prepareStatement(consulta);
        //le paso los parametros a la consulta sustituyendo los ?
           pstmt.setString(1, dni);
           pstmt.setString(2, nombre);
           pstmt.setString(3, apellido);
           pstmt.setString(4, telefono);
           //ejecuto el insert
            int insert = pstmt.executeUpdate();
            //devuelvo las columnas que han sido afectadas con el insert
           return  insert;
    	
    }
    
  //metodo para insertar Mecanico
    public int insetarMecanico(String dni,String nombre, String apellido, String telefono) throws SQLException {
        
        String consulta = "INSERT INTO "+ "Mecanico" +"(DNI, Nombre, Apellido, Tlf, Estado) VALUES (?, ?, ?, ?, ?)";
        
         PreparedStatement pstmt = con.prepareStatement(consulta);
        //le paso los parametros a la consulta sustituyendo los ?
           pstmt.setString(1, dni);
           pstmt.setString(2, nombre);
           pstmt.setString(3, apellido);
           pstmt.setString(4, telefono);
           pstmt.setString(5, "Disponible");
           //ejecuto el insert
            int insert = pstmt.executeUpdate();
            //devuelvo las columnas que han sido afectadas con el insert
           return  insert;
    	
    }
    
    //metodo que pasandole la consulta devuelve el cunrsor con los datos 
    public ResultSet ejecutarSelect( String Consulta) throws SQLException {
       Statement stmt =  con.createStatement();
       ResultSet rset = stmt.executeQuery(Consulta);
       return rset;
   }
    
    public int ejecutarUpdateCliente(String dni,String nombre, String apellido, String telefono) throws SQLException{
        String consulta = "UPDATE Cliente SET Nombre = ?, Apellido = ?, Tlf = ? WHERE dni = ?;";
        PreparedStatement pstmt = con.prepareStatement(consulta);
        pstmt.setString(4, dni);
        pstmt.setString(1, nombre);
        pstmt.setString(2, apellido);
        pstmt.setString(3, telefono);
         int update = pstmt.executeUpdate();
        return update;
     } 
    public int ejecutarUpdateMecanico(String n_empleado,String nombre, String apellido, String telefono, String dni) throws SQLException{
        String consulta = "UPDATE Mecanico SET Nombre = ?, Apellido = ?, Tlf = ?, dni = ? WHERE N_empleado = ?;";
        PreparedStatement pstmt = con.prepareStatement(consulta);
        pstmt.setString(5, n_empleado);
        pstmt.setString(1, nombre);
        pstmt.setString(2, apellido);
        pstmt.setString(3, telefono);
        pstmt.setString(4, dni);
         int update = pstmt.executeUpdate();
        return update;
     }
    public int ejecutarDeleteMecanico(String n_empleado) throws SQLException{
        String consulta = "DELETE from Mecanico WHERE N_Empleado = ?;";
        PreparedStatement pstmt = con.prepareStatement(consulta);
        pstmt.setString(1, n_empleado);
         int update = pstmt.executeUpdate();
        return update;
     }
    


    //metodo para insertar Vehiculo
    public int insertarVehiculo(String matricula,String marca, String modelo, String color, String Fecha_Entrada, String fecha_salida,String clienteDNI,String ReparacionCodigoReparacion) throws SQLException {
        
        String consulta = "INSERT INTO "+ "Vehiculo" +"(Matricula, marca, modelo, color, Fecha_Entrada, Fecha_Salida, cliente_DNI, Reparacion_Codigo_Reparacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
         PreparedStatement pstmt = con.prepareStatement(consulta);
        //le paso los parametros a la consulta sustituyendo los ?
           pstmt.setString(1, matricula);
           pstmt.setString(2, marca);
           pstmt.setString(3, modelo);
           pstmt.setString(4, color);
           pstmt.setString(5, Fecha_Entrada);
           pstmt.setString(6, fecha_salida);
           pstmt.setString(7, clienteDNI);
           pstmt.setString(8, ReparacionCodigoReparacion);
           //ejecuto el insert
            int insert = pstmt.executeUpdate();
            //devuelvo las columnas que han sido afectadas con el insert
           return  insert;
    	
    }
    
    
public int ejecutarUpdateVehiculo(String dNI_Cliente, String matricula, String marca, String modelo, String color, String fecha_Entrada, String fecha_Salida, String reparacion) throws SQLException {
    String consulta = "UPDATE Vehiculo SET Marca = ?, Modelo = ?, Color = ?, Fecha_Entrada = ?, Fecha_Salida = ?, Reparacion_Codigo_Reparacion = ?, cliente_DNI = ? WHERE Matricula = ?;";
    PreparedStatement pstmt = con.prepareStatement(consulta);
    pstmt.setString(1, marca);
    pstmt.setString(2, modelo);
    pstmt.setString(3, color);
    pstmt.setString(4, fecha_Entrada);
    pstmt.setString(5, fecha_Salida);
    pstmt.setString(6, reparacion);
    pstmt.setString(8, matricula);
    pstmt.setString(7, dNI_Cliente);
    int update = pstmt.executeUpdate();
    return update;
}





    }

    


