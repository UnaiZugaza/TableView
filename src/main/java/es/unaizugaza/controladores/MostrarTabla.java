package es.unaizugaza.controladores;

import es.unaizugaza.BBDD.ConexionBBDD;
import es.unaizugaza.DAO.PersonaDAO;
import es.unaizugaza.modelos.Persona;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;
import java.time.LocalDate;

public class MostrarTabla {


    @FXML
    private Button btAdd;

    @FXML
    private Button btDeleteRow;

    @FXML
    private Button btRestoreRows;


    @FXML
    private TableColumn<Persona, Integer> tableDni;

    @FXML
    private TableColumn<Persona, String> tableFirstName;

    @FXML
    private TableColumn<Persona, String> tableLastName;

    @FXML
    private TableColumn<Persona, LocalDate> tableBirthDate;

    @FXML
    private DatePicker dateBirth;

    @FXML
    private TableView<Persona> tableMain;

    @FXML
    private TextArea txtFirstName;

    @FXML
    private TextArea txtLastName;

    @FXML
    void anyadirPersona(ActionEvent event) {
        if (!(txtFirstName.getText().equals("") || txtLastName.getText().equals("")
                || dateBirth.getValue() == null)) {
            Persona persona = new Persona((tableMain.getItems().size()+1), txtFirstName.getText(), txtLastName.getText(), dateBirth.getValue());
            tableMain.getItems().add(persona);
            insertarEnBBDD(persona);
        }
    }

    @FXML
    void borrarFilas(ActionEvent event) {
        ObservableList<Persona> selected = tableMain.getSelectionModel().getSelectedItems();

        if (selected == null || selected.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Selecciona al menos una fila");
            alert.showAndWait();
            return;
        }

        ObservableList<Persona> toRemove = FXCollections.observableArrayList(selected);

        for (Persona p : toRemove) {
            if (dao.borrarPersona(p.getDni())) { // elimina de la BBDD
                listaOriginal.remove(p);        // elimina de la lista observable
            }
        }
    }

    @FXML
    void restaurar(ActionEvent event) {
        tableMain.setItems(FXCollections.observableArrayList(listaOriginal));
    }


    private PersonaDAO dao = new PersonaDAO();

    private ObservableList<Persona> listaOriginal = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        tableDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        tableFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        tableLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tableBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));

        listaOriginal = FXCollections.observableArrayList();
        cargarPersonas(); // llenará listaOriginal

        tableMain.setItems(listaOriginal); // ahora apunta a la lista que sí tiene datos
        tableMain.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }


    private void insertarEnBBDD(Persona persona) {
        String sql = "INSERT INTO Persona (dni, first_Name, last_Name, birth_Date) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionBBDD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, persona.getDni());
            stmt.setString(2, persona.getFirstName());
            stmt.setString(3, persona.getLastName());
            stmt.setDate(4, java.sql.Date.valueOf(persona.getBirthDate()));

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void cargarPersonas() {
        String sql = "SELECT * FROM Persona";

        try (Connection conn = ConexionBBDD.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Persona persona = new Persona(
                        rs.getInt("dni"),
                        rs.getString("first_Name"),
                        rs.getString("last_Name"),
                        rs.getDate("birth_Date").toLocalDate()
                );
                listaOriginal.add(persona);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Establecemos la lista original en la tabla
        tableMain.setItems(FXCollections.observableArrayList(listaOriginal));
    }
}