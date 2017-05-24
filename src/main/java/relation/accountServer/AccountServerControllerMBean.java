package main.java.relation.accountServer;

@SuppressWarnings("UnusedDeclaration")
public interface AccountServerControllerMBean {
    public int getUsers();

    public int getUsersLimit();

    public void setUsersLimit(int usersLimit);
}
