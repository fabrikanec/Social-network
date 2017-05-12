package relation.dbService;

import relation.dbService.dataSets.*;
import org.eclipse.jetty.server.Authentication;

import java.sql.Blob;
import java.util.Date;
import java.util.List;

/**
 * Created by nikitaborodulin on 23/01/16.
 */
public interface DBServiceInterface {
    /** user logic */
    Long addUser(String login, String password) throws DBException;
    UsersDataSet getUser(String login) throws DBException;
    UsersDataSet getUserById(Long id) throws DBException;
        /** article logic */
    Long addArticle(Long id, char secure, String text, Date date) throws DBException;
    ArticleDataSet getArticle(Long article_id) throws DBException;
    /** friend logic */
    Long addFriend(UsersDataSet user, UsersDataSet friend) throws DBException;
    FriendDataSet getFriend(Long id) throws DBException;
    /** event logic */
    Long addEvent(Long id, String name, String text, String subj) throws DBException;
    EventDataSet getEvent(Long id) throws DBException;
    /** comment logic */
    Long addComment(Long id, Long article_id, Long event_id, String text) throws DBException;
    CommentDataSet getComment(Long id) throws DBException;
    /** message logic */
    Long addMessage(Long id, Boolean receaverMsgDeletedFlag, Boolean posterMsgDeletedFlag, String text, Date date) throws DBException;
    MessageDataSet getMessage(Long message_id) throws DBException;
    Long count_msg() throws DBException;
    /** community logic */
    Long addCommunity(String name) throws DBException;
    Long addUser(UsersDataSet user, String name) throws DBException;
    List<CommunityDataSet> getUsers(String com_name) throws DBException;
    Long count_comm() throws DBException;
    /** photo logic*/
    Long addPhoto(Long id, Blob photo) throws DBException;
    PhotoDataSet getPhoto(Long photo_id) throws DBException;
    /** playList logic*/
    Long addTrack(Long id, Blob track) throws DBException;
    PlayListDataSet getTrack(Long track_id) throws DBException;
    /** video logic*/
    Long addVideo(Long id, Blob video) throws DBException;
    VideoDataSet getVideo(Long video_id) throws DBException;
}