package relation.dbService;

import relation.dbService.dataSets.*;

import java.util.Date;

/**
 * Created by nikitaborodulin on 23/01/16.
 */
public interface DBServiceInterface {
    /** user logic */
    Long addUser(String login, String password) throws DBException;
    UsersDataSet getUser(String login) throws DBException;
    UsersDataSet getUserById(Long id) throws DBException;

    /** comment logic */
    Long addComment(Long id, String text) throws DBException;
    CommentDataSet getComment(Long id) throws DBException;
    Long count_comm() throws DBException;

    /** message logic */
    Long addMessage(Long id, Boolean receaverMsgDeletedFlag, Boolean posterMsgDeletedFlag, String text, Date date) throws DBException;
    MessageDataSet getMessage(Long message_id) throws DBException;
    Long count_msg() throws DBException;



}