package es.unaizugaza.modelos;

import java.time.LocalDate;

public class Persona {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private int dni;

    // Constructor vacío
    public Persona() {}

    // Constructor con todos los campos
    public Persona(int dni, String firstName, String lastName, LocalDate birthDate) {
        this.dni = dni;
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
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser nula");
        }
        if (birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser futura");
        }
        this.birthDate = birthDate;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    // toString
    @Override
    public String toString() {
        return firstName + " " + lastName + " fecha de nacimiento: "
                + birthDate + " id: " + dni;
    }
}
