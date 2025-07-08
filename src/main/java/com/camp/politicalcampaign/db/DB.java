package com.camp.politicalcampaign.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase utilitaria para gestionar la conexión a la base de datos.
 * Implementa un singleton para reutilizar la conexión.
 */
public class DB {

    // Instancia única de la conexión
    private static Connection connection;

    // Parámetros de conexión (ajusta según tu entorno)
    private static final String DRIVER   = "com.mysql.cj.jdbc.Driver";
    private static final String URL      = "jdbc:mysql://localhost:3306/voluntariado"
                                        + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USER     = "root";
    private static final String PASSWORD = "YourStrongPassword";

    // Constructor privado para evitar instanciación
    private DB() { }

    /**
     * Obtiene la conexión a la base de datos.
     * Si no existe o está cerrada, la crea.
     */
    public static synchronized Connection getConexion() throws SQLException, ClassNotFoundException {
        if (connection == null || connection.isClosed()) {
            // Carga el driver de MySQL
            Class.forName(DRIVER);
            // Crea la conexión
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }

    /**
     * Cierra la conexión si está abierta.
     */
    public static synchronized void close() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace(); // o usa tu logger preferido
            }
        }
    }
}