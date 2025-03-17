package com.miServidor.miServidor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;



public class MostrarPersonas {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/telemedicina";
        String usuario = "root";
        String contraseña = "patatagaming2323@#";

        try {
            // Establecer la conexión
            Connection conexion = DriverManager.getConnection(url, usuario, contraseña);

            // Crear una sentencia
            Statement sentencia = conexion.createStatement();

            // Ejecutar la consulta
            ResultSet resultado = sentencia.executeQuery("SELECT * FROM doctores");

            // Mostrar los resultados
            while (resultado.next()) {
                int id = resultado.getInt("id");
                String nombre = resultado.getString("nombre");
                String especialidad = resultado.getString("especialidad");
                System.out.println("ID: " + id + ", Nombre: " + nombre + ", Edad: " + especialidad);
            }

            // Cerrar conexiones
            resultado.close();
            sentencia.close();
            conexion.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
