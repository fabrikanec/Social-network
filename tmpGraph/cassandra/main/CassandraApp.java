package org.baeldung.spring.data.cassandra.main;

import com.datastax.driver.core.Session;
import org.baeldung.spring.data.cassandra.model.Comment;
import org.baeldung.spring.data.cassandra.model.User;
import org.baeldung.spring.data.cassandra.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.Resource;
import java.util.Arrays;

@ComponentScan(basePackages = {"org.baeldung.spring.data.cassandra.config"})
public class CassandraApp  {
    private static final Logger LOG = LoggerFactory.getLogger(CassandraApp.class);

    public CassandraApp(UserRepository userRepository, Session session) {
        this.userRepository = userRepository;
        this.session = session;
    }

    public CassandraApp() {
    }

    @Resource
    @Autowired
    private static UserRepository userRepository;

    @Autowired
    private Session session;

    public static void main(String[] args) throws ClassNotFoundException {
        User user = userRepository.save(new User().
                            setUserComments(Arrays.asList(new Comment("kek"), new Comment("lol"))));
        System.out.println(user.getUserComments());


    }
}


