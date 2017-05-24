package main.java.relation.dbService.dao;

import main.java.relation.dbService.dataSets.UserDataSet;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

//PASSED
public class UserDAO {

    private Session session;

    public UserDAO(Session session) {
        this.session = session;
    }

    public UserDataSet get(Long id) throws HibernateException {
        return (UserDataSet) session.get(UserDataSet.class, id);
    }

    public UserDataSet get(String login) throws HibernateException{
        Criteria criteria = session.createCriteria(UserDataSet.class);
        return (UserDataSet) criteria.add(Restrictions.eq("login", login)).uniqueResult();
    }

    public Long getUserId(String login) throws HibernateException {
        Criteria criteria = session.createCriteria(UserDataSet.class);
        return ((UserDataSet) criteria.add(Restrictions.eq("login", login)).uniqueResult()).getId();
    }

    public Long insertUser(String login, String password) throws HibernateException {
        return (Long) session.save(new UserDataSet(login, password));
    }
}
