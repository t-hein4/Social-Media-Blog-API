package Service;

import DAO.MessageDAO;
import Model.Account;
import Model.Message;

public class MessageService {
    private final MessageDAO messageDAO;

    public MessageService() {
        this.messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public Message createMessage(Message message) {
        String messageText = message.getMessage_text();
        if (messageText.length() > 0 && messageText.length() < 255) {
            return messageDAO.insertMessage(message);
        }
        return null;
    }
}
