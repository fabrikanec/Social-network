package relation.messageSystem;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageSystem {
    private static MessageSystem messageSystem;
    private final Map<Address, ConcurrentLinkedQueue<Message>> addressBook;

    private MessageSystem() {
        addressBook = new HashMap<Address, ConcurrentLinkedQueue<Message>>();
    }

    public static MessageSystem getInstance() {
        if(messageSystem == null) {
            messageSystem = new MessageSystem();
        }
        return messageSystem;
    }

    public void sendMessage(Message message) {
        Queue<Message> messageQueue = addressBook.get(message.getTo());
        messageQueue.add(message);
    }

    public void execForAbonent(Abonent abonent) throws WrongTargetMessageException {
        Queue<Message> messageQueue = addressBook.get(abonent.getAdress());
        while(!messageQueue.isEmpty()) {
            Message msg = messageQueue.poll();
            msg.exec(abonent);
        }
    }
}
