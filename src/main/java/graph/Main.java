package main.java.graph;

import java.util.Collections;

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
		algo.setName("Advanced Algorithm");

		Community db = new Community();
		db.setName("Database Internals");

		// Create few students
		User alice = new User();
		alice.setName("Alice");

		User bob = new User();
		bob.setName("Bob");

		User carol = new User();
		carol.setName("Carol");

		// Add the courses
		alice.getCommunities().add(music);
		music.setUser(alice);
		alice.getCommunities().add(algo);
		alice.getCommunities().add(db);

		bob.getCommunities().add(music);
		bob.getCommunities().add(algo);

		carol.getCommunities().add(algo);
		algo.setUser(carol);
		carol.getCommunities().add(db);

		// Persist the objects. Persisting students persists courses as well.
		session.save(alice);
		session.save(bob);
		session.save(carol);*/

		// Retrieve Students who enrolled for Advanced Algorithm
		Iterable<User> users = session.query(User.class,
				"MATCH (c:Community)<-[:COMMUNITY]-(user) WHERE c.name = 'Advanced Algorithm' RETURN user",
				Collections.emptyMap());

		// Print all the Students
		for (User user : users) {
			System.out.println(user.getName());
		}

        /*System.out.println("music");
		for (User user : music.getUsers()) {
            System.out.println(user.getName());
        }*/
    }

}
