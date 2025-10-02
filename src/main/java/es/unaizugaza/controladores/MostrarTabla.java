package es.unaizugaza.controladores;

import es.unaizugaza.modelos.Persona;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.Stack;

import java.time.LocalDate;

public class MostrarTabla {

    private final Stack<Persona> deletedStack = new Stack<>();

    @FXML
    private Button btAdd;

    @FXML
    private Button btDeleteRow;

    @FXML
    private Button btRestoreRows;


    @FXML
    private TableColumn<Persona, Integer> tableId;

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
        }
    }

    @FXML
    void borrarFilas(ActionEvent event) {
        Persona selected = tableMain.getSelectionModel().getSelectedItem();
        if(selected != null) {
            tableMain.getItems().remove(selected);
            deletedStack.push(selected);
        }
    }

    @FXML
    void restaurar(ActionEvent event) {
        if(!deletedStack.isEmpty()) {
            Persona restored = deletedStack.pop();
            tableMain.getItems().add(restored);

        }
    }

    @FXML
    public void initialize() {
        tableId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        tableLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tableBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
    }
}