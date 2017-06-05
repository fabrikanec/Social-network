package main.java.relation.dbService.dao;

import main.java.relation.dbService.dataSets.ArticleDataSet;
import main.java.relation.dbService.dataSets.EventDataSet;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.Date;
import java.util.UUID;

//PASSED
public class EventDAO {
    private Session session;

    public EventDAO(Session session) {
        this.session = session;
    }

    public EventDataSet getEvent(Long id) throws HibernateException {
        Criteria criteria = session.createCriteria(EventDataSet.class);
        return ((EventDataSet) criteria.add(Restrictions.eq("eventId", id)).uniqueResult());
    }

    public Long insertEvent(Long userId, String name, String text, String subj) throws HibernateException {
        return (Long) session.save(new EventDataSet(userId, name, text, subj));
    }
}
