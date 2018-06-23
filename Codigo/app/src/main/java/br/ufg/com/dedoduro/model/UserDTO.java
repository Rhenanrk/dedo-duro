package br.ufg.com.dedoduro.model;

public class UserDTO {
    String email;
    String idUser;

    public UserDTO() {

    }

    public UserDTO(String email, String idUser) {
        this.email = email;
        this.idUser = idUser;
    }
}
