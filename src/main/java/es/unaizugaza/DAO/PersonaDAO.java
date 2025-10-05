package es.unaizugaza.DAO;
import es.unaizugaza.modelos.Persona;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.Properties;

public class PersonaDAO {

    private String URL;

    private String USER;

    private String PASS;

    public PersonaDAO() {
        loadConfig();
    }

    private void loadConfig() {
        Properties props = new Properties();
        try (InputStream input = getClass().getResourceAsStream("/configuration.properties")) {
            if (input == null) {
                System.err.println("No se encontró configuration.properties");
                return;
            }
            props.load(input);
            URL = props.getProperty("db.url");
            USER = props.getProperty("db.user");
            PASS = props.getProperty("db.password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Persona> getTodasPersonas() {
        ObservableList<Persona> lista = FXCollections.observableArrayList();
        String sql = "SELECT dni, first_name, last_name, birth_date FROM Persona";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int dni = rs.getInt("dni");
                String nombre = rs.getString("first_name");
                String apellido = rs.getString("last_name");
                Date fechaSQL = rs.getDate("birth_date");
                LocalDate fecha = fechaSQL != null ? fechaSQL.toLocalDate() : null;

                Persona Persona = new Persona(dni, nombre, apellido, fecha);
                lista.add(Persona);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public Persona obtenerPersona(int dni) {
        String sql = "SELECT dni, first_name, last_name, birth_date FROM Persona WHERE dni = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, dni);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String nombre = rs.getString("first_name");
                String apellido = rs.getString("last_name");
                Date fechaSQL = rs.getDate("birth_date");
                LocalDate fecha = fechaSQL != null ? fechaSQL.toLocalDate() : null;

                return new Persona(dni, nombre, apellido, fecha);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean insertarPersona(Persona p) {
        String sql = "INSERT INTO Persona(first_name, last_name, birth_date) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, p.getFirstName());
            ps.setString(2, p.getLastName());
            ps.setDate(3, p.getBirthDate() != null ? Date.valueOf(p.getBirthDate()) : null);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                ResultSet keys = ps.getGeneratedKeys();
                if (keys.next()) {
                    p.setDni(keys.getInt(1));
                }
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean borrarPersona(int dni) {
        String sql = "DELETE FROM Persona WHERE dni = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, dni);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean borrarTodasPersonas() {
        String sql = "DELETE FROM Persona";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sql);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}