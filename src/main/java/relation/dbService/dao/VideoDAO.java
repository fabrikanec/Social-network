package relation.dbService.dao;

import relation.dbService.dataSets.PhotoDataSet;
import relation.dbService.dataSets.VideoDataSet;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.sql.Blob;

//neo4
public class VideoDAO {
    private Session session;

    public VideoDAO(Session session) {
        this.session = session;
    }

    public VideoDataSet getVideo(Long video_id) throws HibernateException {
        Criteria criteria = session.createCriteria(PhotoDataSet.class);
        return ((VideoDataSet) criteria.add(Restrictions.eq("video_id", video_id)).uniqueResult());
    }

    public Long insertVideo(Long id, Blob video) throws HibernateException {
        return (Long) session.save(new VideoDataSet(id, video));
    }
}