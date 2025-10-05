package es.unaizugaza.BBDD;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConexionBBDD {

    private static Connection conexion;

    public static Connection getConexion() {
        if (conexion == null) {
            try {
                Properties props = new Properties();
                InputStream input = ConexionBBDD.class.getClassLoader()
                        .getResourceAsStream("configuration.properties");

                if (input == null) {
                    throw new RuntimeException("No se encontró el archivo configuration.properties en los recursos.");
                }

                props.load(input);

                String url = props.getProperty("db.url");
                String user = props.getProperty("db.user");
                String password = props.getProperty("db.password");

                conexion = DriverManager.getConnection(url, user, password);

            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Error al establecer la conexión con la base de datos: " + e.getMessage());
            }
        }
        return conexion;
    }

    public static void main(String[] args) {
        getConexion();
    }
}