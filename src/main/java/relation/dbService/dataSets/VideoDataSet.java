package relation.dbService.dataSets;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Blob;

@Entity
@Table(name = "user_video")
public class VideoDataSet implements Serializable { // Serializable Important to Hibernate!
    private static final Long serialVersionUID = -8706689714326132798L;

    @Column(name = "user_id")
    private Long id;

    @Id
    @Column(name = "video_id", unique = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long video_id;

    @Column(name = "video")
    private Blob video;

    //Important to Hibernate!
    @SuppressWarnings("UnusedDeclaration")
    public VideoDataSet() {
    }

    public VideoDataSet(Long id, Blob video) {
        this.setId(id);
        //this.setVideoId(video_id);
        this.setVideo(video);
    }


    @SuppressWarnings("UnusedDeclaration")

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVideoId() { return video_id; }

    public void setVideoId(Long video_id) { this.video_id = video_id; }

    public Blob getVideo() { return video; }

    public void setVideo(Blob video) { this.video = video; }

    @Override
    public String toString() {
        return "VideoDataSet{" +
                "id=" + id +
                ", video_id='" + video_id + '\'' +
                '}';
    }
}