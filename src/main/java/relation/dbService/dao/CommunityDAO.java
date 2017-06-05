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

    public Set<Long> getUsers(String communityName) throws HibernateException { //TODO comm to long
        Criteria criteria = session.createCriteria(CommunityDataSet.class);
        Set<Long> res = new HashSet<>();
        res.add((Long) criteria.add(Restrictions.eq("communityId", communityName)).list().get(0));
        res.add((Long) criteria.add(Restrictions.eq("communityId", communityName)).list().get(1));
        return res;
    }

    public void addUser(Long user, String communityName) throws HibernateException {
        Criteria criteria = session.createCriteria(CommunityDataSet.class);
        CommunityDataSet community = (CommunityDataSet) criteria.add(Restrictions.eq("communityName", communityName));
        community.setUser(user);
        session.update(community);
    }

    public Long insertCommunity(String name) throws HibernateException {
        return (Long) session.save(new CommunityDataSet(name));
    }
}