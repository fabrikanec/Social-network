package main.java.relation.account;


public class UserProfile {
    private final String login;
    private final String password;
    private final String email;
    private final String name;
    private final String surname;

    public UserProfile(String login, String password, String email, String name, String surname) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.name = name;
        this.surname = surname;
    }

    public UserProfile(String login, String password) {
        this.login = login;
        this.password = password;
        this.email = "Unknown";
        this.name = login;
        this.surname = "";
    }

    public UserProfile(String login) {
        this.login = login;
        this.password = login;
        this.email = "Unknown";
        this.name = login;
        this.surname = "";
    }

    public String getLogin() {
        return login;
    }

    public String getPass() { return password; }

    public String getEmail() {
        return email;
    }

    public String getName() { return name; }

    public String getSurname() { return surname; }
}
