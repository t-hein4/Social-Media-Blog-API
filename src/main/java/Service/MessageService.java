package Service;

import DAO.MessageDAO;
import Model.Message;

import java.util.List;

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

    public List<Message> getAllMessagesForAccount(int accountId) {
        return messageDAO.selectAllMessagesByAccountId(accountId);
    }

    public List<Message> getAllMessages() {
        return messageDAO.selectAllMessages();
    }

    public Message getMessageById(int messageId) {
        return messageDAO.selectMessageById(messageId);
    }

    public Message deleteMessage(int messageId) {
        return messageDAO.deleteMessage(messageId);
    }

    public Message updateMessage(int messageId, Message message) {
        String messageText = message.getMessage_text();
        if (messageText.length() > 0 && messageText.length() < 255) {
            return messageDAO.updateMessage(messageId, messageText);
        }
        return null;
    }
}
