package relation.dbService.dataSets;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Blob;

@Entity
@Table(name = "user_photo")
public class PhotoDataSet implements Serializable { // Serializable Important to Hibernate!
    private static final Long serialVersionUID = -8706689714326132798L;

    @Column(name = "user_id")
    private Long id;

    @Id
    @Column(name = "photo_id", unique = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long photo_id;

    @Column(name = "photo")
    private Blob photo;

    //Important to Hibernate!
    @SuppressWarnings("UnusedDeclaration")
    public PhotoDataSet() {
    }

    public PhotoDataSet(Long id, Blob photo) {
        this.setId(id);
        //this.setPhotoId(photo_id);
        this.setPhoto(photo);
    }


    @SuppressWarnings("UnusedDeclaration")

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPhotoId() { return photo_id; }

    public void setPhotoId(Long photo_id) { this.photo_id = photo_id; }

    public Blob getPhoto() { return photo; }

    public void setPhoto(Blob photo) { this.photo = photo; }

    @Override
    public String toString() {
        return "PhotoDataSet{" +
                "id=" + id +
                ", photo_id='" + photo_id + '\'' +
                '}';
    }
}