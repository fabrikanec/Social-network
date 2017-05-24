package main.java.column.config;

import com.datastax.driver.core.Session;
import org.apache.cassandra.thrift.Cassandra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.CassandraEntityClassScanner;
import org.springframework.data.cassandra.config.CassandraSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.config.java.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.convert.CassandraConverter;
import org.springframework.data.cassandra.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.CassandraAdminOperations;
import org.springframework.data.cassandra.core.CassandraAdminTemplate;
import org.springframework.data.cassandra.mapping.BasicCassandraMappingContext;
import org.springframework.data.cassandra.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.mapping.SimpleUserTypeResolver;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@PropertySource(value = { "classpath:cassandra.properties" })
@ComponentScan(basePackages = {"main.java.column.model", "main.java.column.repository", "main.java.column.service" })
@EnableCassandraRepositories(basePackages = "main.java.column.repository")
public class CassandraConfig extends AbstractCassandraConfiguration {
//    private static final Log LOGGER = LogFactory.getLog(CassandraConfig.class);

    @Autowired
    private Environment environment;

    @Override
    protected String getKeyspaceName() {
        return environment.getProperty("cassandra.keyspace");
    }

    @Override
    @Bean
    public CassandraClusterFactoryBean cluster() {
        final CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
        cluster.setContactPoints(environment.getProperty("cassandra.contactpoints"));
        cluster.setPort(Integer.parseInt(environment.getProperty("cassandra.port")));
//        LOGGER.info("Cluster created with contact points [" + environment.getProperty("cassandra.contactpoints") + "] " + "& port [" + Integer.parseInt(environment.getProperty("cassandra.port")) + "].");
        return cluster;
    }

 /*   @Override
    @Bean
    public CassandraMappingContext cassandraMapping() throws ClassNotFoundException {
        return new BasicCassandraMappingContext();
    }

    @Override
    @Bean
    public CassandraConverter cassandraConverter() throws Exception {
        return super.cassandraConverter();
    }*/


    @Bean(name = "context")
    public CassandraMappingContext mappingContext() throws ClassNotFoundException {
        BasicCassandraMappingContext mappingContext = new BasicCassandraMappingContext();
        mappingContext.setInitialEntitySet(CassandraEntityClassScanner.scan("column.model"));
        mappingContext.setUserTypeResolver(new SimpleUserTypeResolver(cluster().getObject(),getKeyspaceName()));
        return mappingContext;
    }

    @Bean(name = "converter")
    public CassandraConverter converter() throws ClassNotFoundException {
        return new MappingCassandraConverter(mappingContext());
    }

    @Bean(name = "session")
    public CassandraSessionFactoryBean session() throws ClassNotFoundException {
        CassandraSessionFactoryBean session = new CassandraSessionFactoryBean();
        session.setCluster(cluster().getObject());
        session.setKeyspaceName(getKeyspaceName());
        session.setConverter(converter());
        session.setSchemaAction(SchemaAction.CREATE_IF_NOT_EXISTS);
        return session;
    }
}