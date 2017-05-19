
package repository;

import com.datastax.driver.core.Session;
import org.baeldung.spring.data.cassandra.model.User;
import org.baeldung.spring.data.cassandra.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.test.integration.support.IntegrationTestConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;
import java.util.stream.Stream;

import static com.datastax.driver.core.querybuilder.QueryBuilder.uuid;
import static org.assertj.core.api.Assertions.*;

/**
 * Integration tests for use with {@link UserRepository}.
 *
 * @author Matthew T. Adams
 * @author Mark Paluch
 * @soundtrack Mary Jane Kelly - Volbeat
 */
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class QueryIntegrationTest extends AbstractSpringDataEmbeddedCassandraIntegrationTest {

    public static class Config extends IntegrationTestConfig {

        @Override
        public String[] getEntityBasePackages() {
            return new String[]{User.class.getPackage().getName()};
        }

        @Override
        public SchemaAction getSchemaAction() {
            return SchemaAction.RECREATE_DROP_UNUSED;
        }
    }

    @Autowired
    private
    UserRepository userRepository;
    @Autowired
    private Session session;

    @Before
    public void before() {
        deleteAllEntities();
    }

    @Test
    public void testListMethodSingleResult() {

        User saved = new User();
        saved.setFirstname(uuid());
        saved.setLastname(uuid());

        saved = userRepository.save(saved);

        List<User> results = userRepository.findFolksWithLastnameAsList(saved.getLastname());

        assertThat(results).isNotNull();
        assertThat(results.size() == 1).isTrue();
        User found = results.iterator().next();
        assertThat(found).isNotNull();
        assertThat(saved.getLastname()).isEqualTo(found.getLastname());
        assertThat(saved.getFirstname()).isEqualTo(found.getFirstname());
    }

    @Test
    public void testListMethodMultipleResults() {

        User saved = new User();
        saved.setFirstname("a");
        saved.setLastname(uuid());

        saved = userRepository.save(saved);

        User saved2 = new User();
        saved2.setFirstname("b");
        saved2.setLastname(saved.getLastname());

        saved2 = userRepository.save(saved2);

        List<User> results = userRepository.findFolksWithLastnameAsList(saved.getLastname());

        assertThat(results).isNotNull();
        assertThat(results.size() == 2).isTrue();
        boolean first = true;
        for (User user : results) {
            assertThat(user).isNotNull();
            assertThat(user.getLastname()).isEqualTo(saved.getLastname());
            assertThat(user.getFirstname()).isEqualTo(first ? saved.getFirstname() : saved2.getFirstname());
            first = false;
        }
    }

    @Test
    public void testListOfMapOfStringToObjectMethodSingleResult() {

        User saved = new User();
        saved.setFirstname(uuid());
        saved.setLastname(uuid());

        saved = userRepository.save(saved);

        List<Map<String, Object>> results = userRepository
                .findFolksWithLastnameAsListOfMapOfStringToObject(saved.getLastname());

        assertThat(results).isNotNull();
        assertThat(results.size() == 1).isTrue();
        Map<String, Object> found = results.iterator().next();
        assertThat(found).isNotNull();
        assertThat(saved.getLastname()).isEqualTo(found.get("lastname"));
        assertThat(saved.getFirstname()).isEqualTo(found.get("firstname"));
    }

    @Test
    public void testEntityMethodResult() {

        User saved = new User();
        saved.setFirstname(uuid());
        saved.setLastname(uuid());

        saved = userRepository.save(saved);

        User found = userRepository.findSingle(saved.getLastname(), saved.getFirstname());

        assertThat(found).isNotNull();
        assertThat(saved.getLastname()).isEqualTo(found.getLastname());
        assertThat(saved.getFirstname()).isEqualTo(found.getFirstname());
    }

    @Test
    public void testListOfMapOfStringToObjectMethodMultipleResults() {

        User saved = new User();
        saved.setFirstname("a");
        saved.setLastname(uuid());

        saved = userRepository.save(saved);

        User saved2 = new User();
        saved2.setFirstname("b");
        saved2.setLastname(saved.getLastname());

        saved2 = userRepository.save(saved2);

        Collection<User> results = userRepository.findFolksWithLastnameAsList(saved.getLastname());

        assertThat(results).isNotNull();
        assertThat(results.size() == 2).isTrue();
        boolean first = true;
        for (User user : results) {
            assertThat(user).isNotNull();
            assertThat(user.getLastname()).isEqualTo(saved.getLastname());
            assertThat(user.getFirstname()).isEqualTo(first ? saved.getFirstname() : saved2.getFirstname());
            first = false;
        }
    }

    @Test
    public void testStringMethodResult() {

        User saved = new User();
        saved.setFirstname(uuid());
        saved.setLastname(uuid());
        saved.setNickname(uuid());

        saved = userRepository.save(saved);

        String nickname = userRepository.findSingleNickname(saved.getLastname(), saved.getFirstname());

        assertThat(nickname).isNotNull();
        assertThat(nickname).isEqualTo(saved.getNickname());
    }

    @Test
    public void testBooleanMethodResult() {

        User saved = new User();
        saved.setFirstname(uuid());
        saved.setLastname(uuid());
        saved.setCool(true);

        saved = userRepository.save(saved);

        boolean value = userRepository.findSingleCool(saved.getLastname(), saved.getFirstname());

        assertThat(value).isEqualTo(saved.isCool());
    }

    @Test
    public void testDateMethodResult() {

        User saved = new User();
        saved.setFirstname(uuid());
        saved.setLastname(uuid());
        saved.setBirthDate(new Date());

        saved = userRepository.save(saved);

        Date value = userRepository.findSingleBirthdate(saved.getLastname(), saved.getFirstname());

        assertThat(value).isEqualTo(saved.getBirthDate());
    }

    @Test
    public void testIntMethodResult() {

        User saved = new User();
        saved.setFirstname(uuid());
        saved.setLastname(uuid());
        saved.setNumberOfChildren(1);

        saved = userRepository.save(saved);

        int value = userRepository.findSingleNumberOfChildren(saved.getLastname(), saved.getFirstname());

        assertThat(value).isEqualTo(saved.getNumberOfChildren());
    }

    @Test
    public void testArrayMethodSingleResult() {

        User saved = new User();
        saved.setFirstname(uuid());
        saved.setLastname(uuid());

        saved = userRepository.save(saved);

        User[] results = userRepository.findFolksWithLastnameAsArray(saved.getLastname());

        assertThat(results).isNotNull();
        assertThat(results.length == 1).isTrue();
        User found = results[0];
        assertThat(found).isNotNull();
        assertThat(saved.getLastname()).isEqualTo(found.getLastname());
        assertThat(saved.getFirstname()).isEqualTo(found.getFirstname());
    }

    @Test
    public void testEscapeSingleQuoteInQueryParameterValue() {

        User saved = new User();
        saved.setFirstname("Bri'an" + uuid());
        String lastname = "O'Brian" + uuid();
        saved.setLastname(lastname);

        saved = userRepository.save(saved);

        List<User> results = userRepository.findFolksWithLastnameAsList(lastname);

        assertThat(results).isNotNull();
        assertThat(results.size() == 1).isTrue();
        for (User user : results) {
            assertThat(user).isNotNull();
            assertThat(user.getLastname()).isEqualTo(saved.getLastname());
            assertThat(user.getFirstname()).isEqualTo(saved.getFirstname());
        }
    }

    @Test
    public void findOptionalShouldReturnTargetType() {

        User userToSave = new User();

        userToSave.setFirstname(uuid());
        userToSave.setLastname(uuid());
        userToSave.setNumberOfChildren(1);

        userToSave = userRepository.save(userToSave);

        Optional<User> savedUser = userRepository.findOptionalWithLastnameAndFirstname(userToSave.getLastname(),
                userToSave.getFirstname());

        assertThat(savedUser.isPresent()).isTrue();
    }

    @Test
    public void findOptionalShouldAbsentOptional() {

        Optional<User> optional = userRepository.findOptionalWithLastnameAndFirstname("not", "existent");

        assertThat(optional.isPresent()).isFalse();
    }

    @Test // DATACASS-297
    public void streamShouldReturnEntities() {

        long before = userRepository.count();

        for (int i = 0; i < 100; i++) {
            User user = new User();

            user.setFirstname(uuid());
            user.setLastname(uuid());
            user.setNumberOfChildren(i);

            userRepository.save(user);
        }

        Stream<User> allPeople = userRepository.findAllPeople();

        long count = allPeople.peek(user -> assertThat(user).isInstanceOf(User.class)).count();

        assertThat(count).isEqualTo(before + 100L);
    }
}