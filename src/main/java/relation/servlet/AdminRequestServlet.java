package main.java.relation.servlet;

import main.java.relation.accountServer.AccountServerI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AdminRequestServlet extends HttpServlet {
    static final Logger logger = LogManager.getLogger(AdminRequestServlet.class.getName());
    public static final String PAGE_URL = "/admin";
    private final AccountServerI accountServer;

    public AdminRequestServlet(AccountServerI accountServer) {
        this.accountServer = accountServer;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=utf-8");
        int limit = accountServer.getUsersLimit();
        response.getWriter().println(String.valueOf(limit));
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
