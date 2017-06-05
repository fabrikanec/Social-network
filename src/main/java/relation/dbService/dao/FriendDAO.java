package main.java.relation.dbService.dao;

import main.java.relation.dbService.dataSets.FriendDataSet;
import main.java.relation.dbService.dataSets.UserDataSet;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.UUID;

public class FriendDAO {
    private Session session;

    public FriendDAO(Session session) {
        this.session = session;
    }

    public Long getFriend(Long userId) throws HibernateException {
        Criteria criteria = session.createCriteria(FriendDataSet.class);
        return ((Long) criteria.add(Restrictions.eq("userId", userId)).uniqueResult());
    }

    public Long addFriend(Long user, Long friend) throws HibernateException {
        return (Long) session.save(new FriendDataSet(user, friend));
    }
}