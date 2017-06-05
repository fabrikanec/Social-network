package main.java.relation.account;

import main.java.relation.dbService.DBException;
import main.java.relation.dbService.DBServiceInterface;
import main.java.relation.dbService.dataSets.*;

import java.sql.Blob;
import java.util.*;


public class AccountService {
    //private final Map<String, UserProfile> loginToProfile;
    private final Map<String, UserProfile> sessionIdToProfile;
    private DBServiceInterface dbService;

    public AccountService(DBServiceInterface dbService) {
       // loginToProfile = new HashMap<>();
        sessionIdToProfile = new HashMap<>();
        this.dbService = dbService;
    }

    /** Session Logic */
    public void addSession(String sessionId, UserProfile userProfile) {
        sessionIdToProfile.put(sessionId, userProfile);
    }

    public void deleteSession(String sessionId) {
        sessionIdToProfile.remove(sessionId);
    }


    /** UserDataSet Logic */
    public Long addNewUser(UserProfile userProfile) throws DBException {
        //loginToProfile.put(userProfile.getLogin(), userProfile);
        return dbService.addUser(userProfile.getLogin(), userProfile.getPass());
    }

    public UserProfile getUserProfileByLogin(String login) throws DBException {
        UserDataSet dataSet = dbService.getUser(login);
        if (dataSet != null) {
            return new UserProfile(dataSet.getLogin(), dataSet.getPassword());
        } else {
            return null;
        }
    }
    public UserDataSet getUserByLogin(String login) throws DBException {
        UserDataSet dataSet = dbService.getUser(login);
        if (dataSet != null) {
            return dataSet;
        } else {
            return null;
        }
    }

    public UserProfile getUserBySessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

    /** ArticleDataSet Logic */
    public Long addArticle(String publisher, String title, String text) throws DBException {
        return  dbService.addArticle(publisher, title, text);
    }

    public String getArticleText(Long articleId) throws DBException {
        ArticleDataSet dataSet = dbService.getArticle(articleId);
        if (dataSet != null) {
            return dataSet.getText();
        } else {
            return null;
        }
    }

    /** EventDataSet Logic */
    public Long addEvent(Long id, String name, String text, String subj) throws DBException {
        return  dbService.addEvent(id, name, text, subj);
    }

    public String getEventText(Long eventId) throws DBException {
        EventDataSet dataSet = dbService.getEvent(eventId);
        if (dataSet != null) {
            return dataSet.getText();
        } else {
            return null;
        }
    }

    /** FriendDataSet Logic */
    public Long addFriend(Long user, Long friend) throws DBException {
        return dbService.addFriend(user, friend);
    }

    public Long getFriend(Long friendId) throws DBException {
        return dbService.getFriend(friendId);
    }

    /** CommentDataSet Logic */
    public Long addComment(Long userId, Long articleId, Long eventId, String text) throws DBException {
        return  dbService.addComment(userId, articleId, eventId, text);
    }

    public String getCommentText(Long commentId) throws DBException {
        CommentDataSet dataSet = dbService.getComment(commentId);
        if (dataSet != null) {
            return dataSet.getText();
        } else {
            return null;
        }
    }

    /** MessageDataSet Logic */
    public Long addMessage(Long id, Boolean receaverMsgDeletedFlag, Boolean posterMsgDeletedFlag, String text, Date date) throws DBException {
        return  dbService.addMessage(id, receaverMsgDeletedFlag, posterMsgDeletedFlag, text, date);
    }

    public String getMessageText(Long messageId) throws DBException {
        MessageDataSet dataSet = dbService.getMessage(messageId);
        if (dataSet != null) {
            return dataSet.getText();
        } else {
            return null;
        }
    }

    public Long count_msg() throws DBException {
        return dbService.count_msg();
    }

    /** CommunityDataSet Logic */
    public Long addNewCommunity(String name) throws DBException {
        return dbService.addCommunity(name);
    }

    public void addUser(Long user, String communityName) throws DBException {
        dbService.addUser(user, communityName);
    }

    public Set<Long> getUsers(String comName) throws DBException {
        return dbService.getUsers(comName);

    }

    public Long count_comm() throws DBException {
        return dbService.count_comm();
    }
}