package relation.accounts;


import relation.dbService.DBException;
import relation.dbService.DBServiceInterface;
import relation.dbService.dataSets.CommentDataSet;
import relation.dbService.dataSets.MessageDataSet;
import relation.dbService.dataSets.UsersDataSet;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


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


    /** UsersDataSet Logic */
    public Long addNewUser(UserProfile userProfile) throws DBException {
        //loginToProfile.put(userProfile.getLogin(), userProfile);
        return dbService.addUser(userProfile.getLogin(), userProfile.getPass());
    }

    public UserProfile getUserProfileByLogin(String login) throws DBException {
        UsersDataSet dataSet = dbService.getUser(login);
        if (dataSet != null) {
            return new UserProfile(dataSet.getLogin(), dataSet.getPassword());
        } else {
            return null;
        }
    }
    public UsersDataSet getUserByLogin(String login) throws DBException {
        UsersDataSet dataSet = dbService.getUser(login);
        if (dataSet != null) {
            return dataSet;
        } else {
            return null;
        }
    }

    public UserProfile getUserBySessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }


    /** CommentDataSet Logic */
    public Long addComment(Long id, String text) throws DBException {
        return  dbService.addComment(id, text);
    }

    public String getCommentText(Long comment_id) throws DBException {
        CommentDataSet dataSet = dbService.getComment(comment_id);
        if (dataSet != null) {
            return dataSet.getText();
        } else {
            return null;
        }
    }

    public Long count_comm() throws DBException {
        return dbService.count_comm();
    }


    /** MessageDataSet Logic */
    public Long addMessage(Long id, Boolean receaverMsgDeletedFlag, Boolean posterMsgDeletedFlag, String text, Date date) throws DBException {
        return  dbService.addMessage(id, receaverMsgDeletedFlag, posterMsgDeletedFlag, text, date);
    }

    public String getMessageText(Long message_id) throws DBException {
        MessageDataSet dataSet = dbService.getMessage(message_id);
        return dataSet != null ? dataSet.getText() : null;
    }

    public Long count_msg() throws DBException {
        return dbService.count_msg();
    }
}