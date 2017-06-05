package main.java.relation.dbService.dao;

import main.java.relation.dbService.dataSets.ArticleDataSet;
import main.java.relation.dbService.dataSets.CommentDataSet;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.Date;
import java.util.UUID;

//PASSED
public class CommentDAO {
    private Session session;

    public CommentDAO(Session session) {
        this.session = session;
    }

    public CommentDataSet getComment(Long id) throws HibernateException {
        Criteria criteria = session.createCriteria(CommentDataSet.class);
        return ((CommentDataSet) criteria.add(Restrictions.eq("commentId", id)).uniqueResult());
    }

    public Long insertComment(Long userId, Long articleId, Long eventId, String text) throws HibernateException {
        return (Long) session.save(new CommentDataSet(userId, articleId, eventId, text));
    }
}