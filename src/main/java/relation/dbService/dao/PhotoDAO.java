package relation.dbService.dao;

import relation.dbService.dataSets.PhotoDataSet;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.sql.Blob;

//neo4
public class PhotoDAO {
    private Session session;

    public PhotoDAO(Session session) {
        this.session = session;
    }

    public PhotoDataSet getPhoto(Long photo_id) throws HibernateException {
        Criteria criteria = session.createCriteria(PhotoDataSet.class);
        return ((PhotoDataSet) criteria.add(Restrictions.eq("photo_id", photo_id)).uniqueResult());
    }

    public Long insertPhoto(Long id, Blob photo) throws HibernateException {
        return (Long) session.save(new PhotoDataSet(id, photo));
    }
}