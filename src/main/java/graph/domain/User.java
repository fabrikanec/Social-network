package graph.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@JsonIdentityInfo(generator = JSOGGenerator.class)
@NodeEntity
public class User {
    @GraphId
    Long id;

    private String name;
    private int born;

    @Relationship(type = "ACTED_IN")
    private List<Video> videos;

    @Relationship(type = "ACTED_IN")
    private List<Photo> photos;

    public User() {
    }

    public String getName() {
        return name;
    }

    public int getBorn() {
        return born;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBorn(int born) {
        this.born = born;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
}
