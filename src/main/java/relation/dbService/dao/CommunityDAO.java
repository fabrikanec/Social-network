package main.java.relation.dbService.dao;

import main.java.relation.dbService.dataSets.CommunityDataSet;
import main.java.relation.dbService.dataSets.UserDataSet;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class CommunityDAO {
    private Session session;

    public CommunityDAO(Session session) {
        this.session = session;
    }

    public Set<CommunityDataSet> getUsers(String community_name) throws HibernateException {
        Criteria criteria = session.createCriteria(CommunityDataSet.class);
        Set<CommunityDataSet> res = new HashSet<>();
        res.add((CommunityDataSet) criteria.add(Restrictions.eq("community_name", community_name)).list().get(0));
        res.add((CommunityDataSet) criteria.add(Restrictions.eq("community_name", community_name)).list().get(1));
        return res;
    }

    public Long addUser(UserDataSet user, String communityName) throws HibernateException {
        return (Long) session.save(new CommunityDataSet(user, communityName));
    }

    public Long insertCommunity(String name) throws HibernateException {
        return (Long) session.save(new CommunityDataSet(name));
    }
}