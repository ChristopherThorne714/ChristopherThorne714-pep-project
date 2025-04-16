package Service;

import DAO.MessageDAO;
import Model.Message;
import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;
    
    public MessageService() {
        messageDAO = new MessageDAO();
    }

    /**
     * 
     * @param messageDAO
     */
    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    /**
     * 
     * @return List<Messages>
     */
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    /* */
    public Message getMessageById(int message_id) {
        return messageDAO.getMessageById(message_id);
    }
    /**
     * 
     * @param message to be added
     * @return message added
     */
     public Message addMessage(Message message) {
        return messageDAO.insertMessage(message);
     }
}
