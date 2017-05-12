package relation.messageSystem;

import relation.dbService.DBServiceInterface;

public class MessageAuthentificate extends MessageToDB {
    private String name;
    private String password;
    private long sessionId;

    public MessageAuthentificate(Address from, Address to, String name, String password, long sessionId) {
        super(from, to);
        this.name = name;
        this.password = password;
        this.sessionId = sessionId;
    }
    //write meth's to DBService
    public void exec(DBServiceInterface dbService) {
        /*
        Account result = relation.dbService.auth(name, password);
        Message back = new MessageAuthentificate(getTo(), getFrom(), sessionId, result);
        relation.dbService.getMessageSystem().sendMessage(back);
        */
    }

}
