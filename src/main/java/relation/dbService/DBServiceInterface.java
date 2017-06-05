package main.java.relation.dbService;

import main.java.relation.dbService.dataSets.*;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

/**
 * Created by nikitaborodulin on 23/01/16.
 */
public interface DBServiceInterface {
    /** user logic */
    Long addUser(String login, String password) throws DBException;
    UserDataSet getUser(String login) throws DBException;
    UserDataSet getUser(Long id) throws DBException;

    /** article logic */
    Long addArticle(String publisher, String title, String text) throws DBException;
    ArticleDataSet getArticle(Long articleId) throws DBException;

    /** friend logic */
    Long addFriend(Long user, Long friend) throws DBException;
    Long getFriend(Long id) throws DBException;

    /** event logic */
    Long addEvent(Long id, String name, String text, String subj) throws DBException;
    EventDataSet getEvent(Long id) throws DBException;

    /** comment logic */
    Long addComment(Long id, Long articleId, Long eventId, String text) throws DBException;
    CommentDataSet getComment(Long id) throws DBException;

    /** message logic */
    Long addMessage(Long id, Boolean receaverMsgDeletedFlag, Boolean posterMsgDeletedFlag, String text, Date date) throws DBException;
    MessageDataSet getMessage(Long messageId) throws DBException;
    Long count_msg() throws DBException;

    /** community logic */
    Long addCommunity(String name) throws DBException;
    void addUser(Long user, String name) throws DBException;
    Set<Long> getUsers(String comName) throws DBException;
    Long count_comm() throws DBException;
}