package main.java.graph;

import java.util.Collections;

import main.java.graph.entities.Community;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

import main.java.graph.entities.User;

public class Main {

	public static final String NEO4J_URL = "bolt://neo4j:password@localhost";
	public static final String USERNAME = "neo4j";
	public static final String PASSWORD = "admin";

	public static void main(String[] args) {
        Configuration.Builder builder = new Configuration.Builder();

        builder.uri(NEO4J_URL);
        builder.credentials(USERNAME, PASSWORD);
        Configuration conf = builder.build();
		// Create SessionFactory. Pass the package name of the entity classes as
		// the argument.
		SessionFactory sessionFactory = new SessionFactory(conf,"main.java.graph.entities");

		// Create the session
		Session session = sessionFactory.openSession();

		// Create few courses
		/*Community music = new Community();
		music.setName("music comm");

		Community algo = new Community();
		algo.setName("Advanced Algorithm");*/

		Community o4 = new Community();
		o4.setName("o4");

		// Create few students
		User kekich = new User();
		kekich.setName("kekich");

		User lolich = new User();
		lolich.setName("lolich");

		User cheburechich = new User();
		cheburechich.setName("cheburechich");

		kekich.getCommunities().add(o4);
		o4.setUser(kekich);
		lolich.getCommunities().add(o4);
		o4.setUser(lolich);
		cheburechich.getCommunities().add(o4);
		o4.setUser(cheburechich);

		kekich.addFriend(lolich);
		lolich.addFriend(kekich);

		kekich.addFriend(cheburechich);
		cheburechich.addFriend(kekich);

		cheburechich.addFriend(lolich);
		lolich.addFriend(cheburechich);
		// Add the courses
		/*alice.getCommunities().add(music);
		music.setUser(alice);
		alice.getCommunities().add(algo);
		alice.getCommunities().add(db);

		bob.getCommunities().add(music);
		bob.getCommunities().add(algo);

		carol.getCommunities().add(algo);
		algo.setUser(carol);
		carol.getCommunities().add(db);*/

		// Persist the objects. Persisting students persists courses as well.
		session.save(kekich);
		session.save(lolich);
		session.save(cheburechich);
		session.save(o4);

		// Retrieve Students who enrolled for Advanced Algorithm
		Iterable<User> users = session.query(User.class,
				"MATCH (c:Community)<-[:COMMUNITY]-(user) WHERE c.name = 'Advanced Algorithm' RETURN user",
				Collections.emptyMap());

		// Print all the Students


        /*System.out.println("music");
		for (User user : music.getUsers()) {
            System.out.println(user.getName());
        }*/
    }

}
