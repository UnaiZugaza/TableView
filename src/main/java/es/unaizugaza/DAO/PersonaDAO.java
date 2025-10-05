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
        String sql = "SELECT id, first_name, last_name, birth_date FROM persona";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("first_name");
                String apellido = rs.getString("last_name");
                Date fechaSQL = rs.getDate("birth_date");
                LocalDate fecha = fechaSQL != null ? fechaSQL.toLocalDate() : null;

                Persona persona = new Persona(id, nombre, apellido, fecha);
                lista.add(persona);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public Persona obtenerPersona(int id) {
        String sql = "SELECT id, first_name, last_name, birth_date FROM persona WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String nombre = rs.getString("first_name");
                String apellido = rs.getString("last_name");
                Date fechaSQL = rs.getDate("birth_date");
                LocalDate fecha = fechaSQL != null ? fechaSQL.toLocalDate() : null;

                return new Persona(id, nombre, apellido, fecha);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean insertarPersona(Persona p) {
        String sql = "INSERT INTO persona(first_name, last_name, birth_date) VALUES (?, ?, ?)";
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

    /**
     * Elimina una persona de la base de datos según su ID.
     *
     * @param id identificador único de la persona a eliminar.
     * @return {@code true} si la eliminación fue exitosa, {@code false} en caso contrario.
     */
    public boolean borrarPersona(int id) {
        String sql = "DELETE FROM persona WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina todos los registros de la tabla <b>persona</b>.
     *
     * <p>Se debe usar con precaución, ya que esta operación no puede revertirse.</p>
     *
     * @return {@code true} si la operación fue exitosa, {@code false} si ocurrió un error.
     */
    public boolean borrarTodasPersonas() {
        String sql = "DELETE FROM persona";
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