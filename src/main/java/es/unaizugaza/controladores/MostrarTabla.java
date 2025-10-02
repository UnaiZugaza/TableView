package es.unaizugaza.controladores;

import es.unaizugaza.modelos.Persona;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
<<<<<<< HEAD
import java.util.Stack;
=======
>>>>>>> 5d15ec248a0fe7027e9e34ecc086e3446edc2afc

import java.time.LocalDate;

public class MostrarTabla {

<<<<<<< HEAD
    private final Stack<Persona> deletedStack = new Stack<>();
=======
>>>>>>> 5d15ec248a0fe7027e9e34ecc086e3446edc2afc

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
<<<<<<< HEAD
        Persona selected = tableMain.getSelectionModel().getSelectedItem();
        if(selected != null) {
            tableMain.getItems().remove(selected);
            deletedStack.push(selected);
        }
=======

>>>>>>> 5d15ec248a0fe7027e9e34ecc086e3446edc2afc
    }

    @FXML
    void restaurar(ActionEvent event) {
<<<<<<< HEAD
        if(!deletedStack.isEmpty()) {
            Persona restored = deletedStack.pop();
            tableMain.getItems().add(restored);

        }
=======

>>>>>>> 5d15ec248a0fe7027e9e34ecc086e3446edc2afc
    }

    @FXML
    public void initialize() {
        tableId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        tableLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tableBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
    }
}