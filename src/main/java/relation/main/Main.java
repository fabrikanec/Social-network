package main.java.relation.main;

import main.java.persistance.chat.WebSocketChatServlet;
import main.java.persistance.sockets.SocketService;
import main.java.relation.accountServer.AccountServer;
import main.java.relation.accountServer.AccountServerController;
import main.java.relation.accountServer.AccountServerControllerMBean;
import main.java.relation.accountServer.AccountServerI;
import main.java.relation.account.UserProfile;
import main.java.relation.dbService.DBException;
import main.java.relation.dbService.dataSets.MessageDataSet;
import main.java.relation.servlet.AdminRequestServlet;
import main.java.relation.servlet.ResourceRequestServlet;
import main.java.relation.servlet.SessionsServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import main.java.relation.resourceServer.ResourceServer;
import main.java.relation.resourceServer.ResourceServerController;
import main.java.relation.resourceServer.ResourceServerControllerMBean;
import main.java.relation.resourceServer.ResourceServerI;
import main.java.relation.resource.DBParametersResource;
import main.java.relation.sax.ReadXMLFileSAX;
import main.java.relation.servlet.*;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.util.Properties;

import main.java.relation.account.AccountService;
import main.java.relation.dbService.DBService;

public class Main {
    private static final Logger logger = LogManager.getLogger(main.java.relation.main.Main.class.getName());

    public static void main(String[] args) throws Exception {
        /*Properties properties = new Properties();
        try (InputStream input = new FileInputStream("config.properties")) {
            properties.load(input);


            System.out.println(properties.getProperty("database"));
            System.out.println(properties.getProperty("dbuser"));
            System.out.println(properties.getProperty("dbpassword"));

        } catch (IOException ex) {
            ex.printStackTrace();
        }


         RandomAccessFile aFile = new RandomAccessFile("data/data.txt", "rw");
        FileChannel inChannel = aFile.getChannel();

        ByteBuffer buf = ByteBuffer.allocate(64);

        int bytesRead = inChannel.read(buf);
        while (bytesRead != -1) {

            System.out.println("Read " + bytesRead);
            buf.flip();

            while (buf.hasRemaining()) {
                System.out.print((char) buf.get());
            }
            System.out.print("\n");
            buf.clear();
            bytesRead = inChannel.read(buf);
        }
        aFile.close();

        int count;

        try (SeekableByteChannel = fileChannel = Files.newByteChannel(Paths.get("data/nio-data.txt"))) {

            ByteBuffer buf = ByteBuffer.allocate(BUFFER_SIZE);

            do {
            // читаем файл и т.п.
            } while (count != -1);
        } catch (...) {...}
        */

        if (args.length != 1) {
            logger.error("Use port as the first argument");
            System.exit(1);
        }
        String portString = args[0];
        final int port = Integer.valueOf(portString);

        logger.info("Starting at http://127.0.0.1:" + portString);

        AccountServerI accountServer = new AccountServer(10);
        ResourceServerI resourceServer = new ResourceServer();

        AccountServerControllerMBean serverStatistics = new AccountServerController(accountServer);
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("Admin:type=AccountServerController");
        mbs.registerMBean(serverStatistics, name);

        ResourceServerControllerMBean resourceServerBean = new ResourceServerController(resourceServer);
        mbs = ManagementFactory.getPlatformMBeanServer();
        name = new ObjectName("Admin:type=ResourceServerController");
        mbs.registerMBean(resourceServerBean, name);

        Server server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        DBService dbService = new DBService();
        AccountService accountService = new AccountService(dbService);
        dbService.printConnectInfo();

        context.addServlet(new ServletHolder(new ResourceRequestServlet(resourceServer)), ResourceRequestServlet.PAGE_URL);
        context.addServlet(new ServletHolder(new AdminRequestServlet(accountServer)), AdminRequestServlet.PAGE_URL);
        context.addServlet(new ServletHolder(new HomePageServlet(accountServer)), HomePageServlet.PAGE_URL);
        context.addServlet(new ServletHolder(new SignUpServlet(accountService)), "/signup");
        context.addServlet(new ServletHolder(new SignInServlet(accountService)), "/signin");
        context.addServlet(new ServletHolder(new SessionsServlet(accountService)), "/api/v1/sessions");
        context.addServlet(new ServletHolder(new WebSocketChatServlet()), "/src/chat");
        context.addServlet(new ServletHolder(new main.java.relation.servlet.AllRequestsServlet()), "/*");

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setResourceBase("static");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});
        server.setHandler(handlers);

        server.start();
        //sockets

        Runnable socketServer = () -> {
            try {
                new SocketService("localhost", 5050).startServer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        new Thread(socketServer);
        //
        logger.info("Server started");
        //testDB(dbService, accountService);
        server.join();
    }

    public static void testDB(DBService dbService, AccountService accountService) throws DBException{
        //insert to db
        Long id = accountService.addNewUser(new UserProfile("todd", "Valio"));
        System.out.println(accountService.getUserProfileByLogin("todd").getPass());
        Long id1 = accountService.addNewUser(new UserProfile("Valio", "Todd"));

        Long id_article = accountService.addArticle(id, '0', "text", new java.util.Date());
        Long id_event =  accountService.addEvent(id, "name", "TOO MUCH TEXT EVENT", "some subj");
        System.out.println(accountService.getArticleText(id_article));

        Long id_article1 = accountService.addArticle(id, '0', "anothertext", new java.util.Date());
        System.out.println(accountService.getArticleText(id_article));
        System.out.println(accountService.getEventText(id_event));

        Long _id_event = accountService.addEvent(id, "name", "TOO", "some subj");
        System.out.println(accountService.getArticleText(id_article1));
        System.out.println(accountService.getEventText(_id_event));
        System.out.println(accountService.getEventText(id_event));
        System.out.println(accountService.getArticleText(id_article));

        Long id_comment = accountService.addComment(id, id_article, id_event, "kekich");
        Long id_comment1 = accountService.addComment(id, id_article, id_event, "lolich");
        System.out.println(accountService.getCommentText(id_comment1));
        System.out.println(accountService.getCommentText(id_comment));
        System.out.println(accountService.getCommentText(id_comment));
        System.out.println(accountService.getCommentText(id_comment1));

        MessageDataSet.BoolType boolType = new MessageDataSet.BoolType();
        Long id_msg = accountService.addMessage(id, false, false, "msg", new java.util.Date());
        Long id_msg1 = accountService.addMessage(id, false, false, "msg666", new java.util.Date());
        accountService.addMessage(id, false, false, "msg13", new java.util.Date());

        System.out.println(accountService.getMessageText(id_msg));
        System.out.println(accountService.getMessageText(id_msg1));

        accountService.addUser(accountService.getUserByLogin("todd"), "Community");
        accountService.addUser(accountService.getUserByLogin("Valio"), "Community");
        Long fromComUser = accountService.getUsers("Community").get(0).getId();
        Long fromComUser1 = accountService.getUsers("Community").get(1).getId();
        System.out.println((fromComUser.equals(id)));
        System.out.println(fromComUser1.equals(id1));

        Long id_friend = accountService.addNewUser(new UserProfile("Volly", "Valio"));
        accountService.addFriend(accountService.getUserByLogin("Volly"), accountService.getUserByLogin("todd"));
        System.out.println(accountService.getFriend(id_friend).equals(id));
        //
        System.out.println(accountService.count_comm());
        System.out.println(accountService.count_msg());
        //
    }
}