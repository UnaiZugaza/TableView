package es.unaizugaza.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Propiedades {


    public static Properties cargar(String nombreArchivo) {
        Properties props = new Properties();
        try (InputStream input = Propiedades.class.getClassLoader().getResourceAsStream(nombreArchivo)) {
            if (input == null) {
                throw new IOException("Archivo de propiedades no encontrado: " + nombreArchivo);
            }
            props.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }
}