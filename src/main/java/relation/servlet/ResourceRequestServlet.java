package main.java.relation.servlet;

import main.java.relation.resource.TestResource;
import main.java.relation.resourceServer.ResourceServerI;
import main.java.relation.sax.ReadXMLFileSAX;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResourceRequestServlet extends HttpServlet {
    static final Logger logger = LogManager.getLogger(ResourceRequestServlet.class.getName());
    public static final String PAGE_URL = "/resources";
    private final ResourceServerI resource;

    public ResourceRequestServlet(ResourceServerI resource) {
        this.resource = resource;
    }

    public void doPost(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=utf-8");
        String stringPath = request.getParameter("path");
        TestResource testResource = (TestResource) ReadXMLFileSAX.readXML(stringPath);
        resource.setResource(testResource);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
