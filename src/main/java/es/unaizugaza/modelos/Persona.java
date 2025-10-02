package es.unaizugaza.modelos;

import javafx.scene.control.Alert;

import java.time.LocalDate;

public class Persona {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private int id;

    // Constructor vacío
    public Persona() {}

    // Constructor con todos los campos
    public Persona(int id, String firstName, String lastName, LocalDate birthDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        setBirthDate(birthDate); // usa el setter para validar
    }

    // Getters y Setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * Establece la fecha de nacimiento.
     * @param birthDate Fecha de nacimiento no nula y no futura.
     * @throws IllegalArgumentException si la fecha es nula o futura.
     */
    public void setBirthDate(LocalDate birthDate) {
        if (birthDate == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "La fecha de nacimiento no puede ser nula");
        }
        if (birthDate.isAfter(LocalDate.now())) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "La fecha de nacimiento no puede ser futura");
        }
        this.birthDate = birthDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // toString
    @Override
    public String toString() {
        return firstName + " " + lastName + " fecha de nacimiento: "
                + birthDate + " id: " + id;
    }
}
