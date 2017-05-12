package relation.messageSystem;

import relation.dbService.DBServiceInterface;

public abstract class MessageToDB extends Message {
    public MessageToDB(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Abonent abonent) throws WrongTargetMessageException {
        if(abonent instanceof DBServiceInterface) {
            exec((DBServiceInterface) abonent);
        } else {
            throw new WrongTargetMessageException(new Exception());
        }
    }

    public abstract void exec(DBServiceInterface dbservice);
}
