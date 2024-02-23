package minnnisu.personalnote.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class UserInfo {
    public String username;
    public String name;
    public String email;

    public UserInfo(String username, String name, String email){
        this.username = username;
        this.name = name;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}