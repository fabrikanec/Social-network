package relation.dbService.dataSets;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "users")
public class UsersDataSet implements Serializable {
    private static final Long serialVersionUID = -8706689714326132798L;

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = 0L;

    @Column(name = "login", unique = true, updatable = false)
    private String login;

    @Column(name = "password", updatable = false)
    private String password;

    @Column (name = "name")
    private String name;

    @Column (name = "surname")
    private String surname;


    @SuppressWarnings("UnusedDeclaration")
    public UsersDataSet() {
    }

    public UsersDataSet(Long id, String login, String password, String name, String surname) {
        //this.setId(id);
        this.setLogin(login);
        this.setPassword(password);
        this.setName(name);
        this.setSurname(surname);
    }

    @SuppressWarnings("UnusedDeclaration")
    public UsersDataSet(Long id, String login, String password) {
        //this.setId(id);
        this.setLogin(login);
        this.setPassword(password);
        this.setName(login);
        this.setSurname("");
    }

    public UsersDataSet(String login, String password) {
        //this.setId(++this.id);
        this.setLogin(login);
        this.setPassword(password);
        this.setName(login);
        this.setSurname("");
    }

    @SuppressWarnings("UnusedDeclaration")

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) { this.surname = surname; }

    @Override
    public String toString() {
        return "UserDataSet{" +
                "id=" + id +
                ", login='" + login + '\'' +
                '}';
    }
}