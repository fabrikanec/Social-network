package relation.dbService.dao;

import relation.dbService.dataSets.EventDataSet;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

//mongo
public class EventDAO {
    private Session session;

    public EventDAO(Session session) {
        this.session = session;
    }

    public EventDataSet getEvent(Long event_id) throws HibernateException {
        Criteria criteria = session.createCriteria(EventDataSet.class);
        return ((EventDataSet) criteria.add(Restrictions.eq("event_id", event_id)).uniqueResult());
    }

    public Long insertEvent(Long id, String name, String text, String subj) throws HibernateException {
        return (Long) session.save(new EventDataSet(id, name, text, subj));
    }
}
