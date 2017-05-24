package main.java.relation.dbService.dao;

import main.java.relation.dbService.dataSets.FriendDataSet;
import main.java.relation.dbService.dataSets.UserDataSet;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class FriendDAO {
    private Session session;

    public FriendDAO(Session session) {
        this.session = session;
    }

    public FriendDataSet getFriend(Long id) throws HibernateException {
        Criteria criteria = session.createCriteria(FriendDataSet.class);
        return ((FriendDataSet) criteria.add(Restrictions.eq("id", id)).uniqueResult());
    }

    public Long addFriend(UserDataSet user, UserDataSet friend) throws HibernateException {
        return (Long) session.save(new FriendDataSet(user, friend));
    }
}