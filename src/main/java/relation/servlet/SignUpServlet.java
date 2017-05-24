package main.java.relation.servlet;

import main.java.relation.account.AccountService;
import main.java.relation.account.UserProfile;
import main.java.relation.dbService.DBException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class SignUpServlet extends HttpServlet {
    private final AccountService accountService;

    public SignUpServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    //sign up
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        //check if login is available
        UserProfile profile = null;
        try {
            profile = accountService.getUserProfileByLogin(login);
        } catch (DBException e) {
            e.printStackTrace();
        }
        if (profile != null) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("This login is already taken, please choose another");
            return;
        }

        UserProfile newProfile = new UserProfile(login, password);
        try {
            accountService.addNewUser(newProfile);
        } catch (DBException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("Successful registration!");

    }
}