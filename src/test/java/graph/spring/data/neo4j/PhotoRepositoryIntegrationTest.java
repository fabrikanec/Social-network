package graph.spring.data.neo4j;

import graph.config.VideoDatabaseNeo4jTestConfiguration;
import graph.domain.Video;
import graph.domain.User;
import graph.domain.Role;
import graph.repostory.VideoRepository;
import graph.repostory.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = VideoDatabaseNeo4jTestConfiguration.class)
@ActiveProfiles(profiles = "test")
public class PhotoRepositoryIntegrationTest {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private UserRepository userRepository;

    public PhotoRepositoryIntegrationTest() {
    }

    @Before
    public void initializeDatabase() {
        System.out.println("seeding embedded database");
        Video italianJob = new Video();
        italianJob.setTitle("The Italian Job");
        italianJob.setReleased(1999);
        videoRepository.save(italianJob);

        User mark = new User();
        mark.setName("Mark Wahlberg");
        userRepository.save(mark);

        Role charlie = new Role();
        charlie.setVideo(italianJob);
        charlie.setUser(mark);
        Collection<String> roleNames = new HashSet();
        roleNames.add("Charlie Croker");
        charlie.setRoles(roleNames);
        List<Role> roles = new ArrayList();
        roles.add(charlie);
        italianJob.setRoles(roles);
        videoRepository.save(italianJob);
    }

    @Test
    @DirtiesContext
    public void testFindByTitle() {
        System.out.println("findByTitle");
        String title = "The Italian Job";
        Video result = videoRepository.findByTitle(title);
        assertNotNull(result);
        assertEquals(1999, result.getReleased());
    }

    @Test
    @DirtiesContext
    public void testCount() {
        System.out.println("count");
        long videoCount = videoRepository.count();
        assertNotNull(videoCount);
        assertEquals(1, videoCount);
    }

    @Test
    @DirtiesContext
    public void testFindAll() {
        System.out.println("findAll");
        Collection<Video> result = (Collection<Video>) videoRepository.findAll();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    @DirtiesContext
    public void testFindByTitleContaining() {
        System.out.println("findByTitleContaining");
        String title = "Italian";
        Collection<Video> result = videoRepository.findByTitleContaining(title);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    @DirtiesContext
    public void testGraph() {
        System.out.println("graph");
        List<Map<String, Object>> graph = videoRepository.graph(5);
        assertEquals(1, graph.size());
        Map<String, Object> map = graph.get(0);
        assertEquals(2, map.size());
        String[] cast = (String[]) map.get("cast");
        String video = (String) map.get("video");
        assertEquals("The Italian Job", video);
        assertEquals("Mark Wahlberg", cast[0]);
    }

    @Test
    @DirtiesContext
    public void testDeleteVideo() {
        System.out.println("deleteVideo");
        videoRepository.delete(videoRepository.findByTitle("The Italian Job"));
        assertNull(videoRepository.findByTitle("The Italian Job"));
    }

    @Test
    @DirtiesContext
    public void testDeleteAll() {
        System.out.println("deleteAll");
        videoRepository.deleteAll();
        Collection<Video> result = (Collection<Video>) videoRepository.findAll();
        assertEquals(0, result.size());
    }
}
