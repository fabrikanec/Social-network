package main.java.relation.dbService.dao;

import main.java.relation.dbService.dataSets.ArticleDataSet;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.Date;
import java.util.UUID;

public class ArticleDAO {
    private Session session;

    public ArticleDAO(Session session) {
        this.session = session;
    }

    public ArticleDataSet getArticle(Long article_id) throws HibernateException {
        Criteria criteria = session.createCriteria(ArticleDataSet.class);
        return ((ArticleDataSet) criteria.add(Restrictions.eq("article_id", article_id)).uniqueResult());
    }

    public Long insertArticle(Long id, String publisher, String title, String text) throws HibernateException {
        return (Long) session.save(new ArticleDataSet(id, publisher, title, text));
    }
}
