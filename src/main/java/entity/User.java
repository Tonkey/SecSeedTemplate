package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import security.IUser;

@Entity
public class User implements IUser, Serializable {

    private String password;  //Pleeeeease dont store me in plain text

    @Id
    private String userName;

    @ElementCollection
    List<String> roles = new ArrayList();

    public User() {
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        System.out.println(password);
    }

    public User(String userName, String password, List<String> roles) {
        this.userName = userName;
        this.password = password;
        this.roles = roles;
    }

    public void addRole(String role) {
        roles.add(role);
    }

    @Override
    public List<String> getRolesAsStrings() {
        return roles;
    }

    public String getRoles() {
        return roles.get(0);
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
