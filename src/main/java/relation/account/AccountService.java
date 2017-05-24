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
    public Long addArticle(Long id, char secure, String text, Date date) throws DBException {
        return  dbService.addArticle(id,secure, text, date);
    }

    public String getArticleText(Long article_id) throws DBException {
        ArticleDataSet dataSet = dbService.getArticle(article_id);
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

    public String getEventText(Long event_id) throws DBException {
        EventDataSet dataSet = dbService.getEvent(event_id);
        if (dataSet != null) {
            return dataSet.getText();
        } else {
            return null;
        }
    }

    /** FriendDataSet Logic */
    public Long addFriend(UserDataSet user, UserDataSet friend) throws DBException {
        return dbService.addFriend(user, friend);
    }

    public Long getFriend(Long friend_id) throws DBException {
        FriendDataSet dataSet = dbService.getFriend(friend_id);
        if (dataSet != null) {
            return dataSet.getFriend();
        } else {
            return null;
        }
    }

    /** CommentDataSet Logic */
    public Long addComment(Long id, Long article_id, Long event_id, String text) throws DBException {
        return  dbService.addComment(id, article_id, event_id, text);
    }

    public String getCommentText(Long comment_id) throws DBException {
        CommentDataSet dataSet = dbService.getComment(comment_id);
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

    public String getMessageText(Long message_id) throws DBException {
        MessageDataSet dataSet = dbService.getMessage(message_id);
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

    public Long addUser(UserDataSet user, String community_name) throws DBException {
        return dbService.addUser(user, community_name);
    }

    public List<CommunityDataSet> getUsers(String com_name) throws DBException {
        return dbService.getUsers(com_name);

    }

    public Long count_comm() throws DBException {
        return dbService.count_comm();
    }
}