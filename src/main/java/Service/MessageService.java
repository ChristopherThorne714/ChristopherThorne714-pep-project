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
     * @return List<Messages> as JSON
     */
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    /**
     * 
     * @param message_id with which to retrieve a message
     * @return retrieved message
     */
    public Message getMessageById(int message_id) {
        return messageDAO.getMessageById(message_id);
    }
    /**
     * 
     * @param message to be added
     * @return message added as JSON
     */
     public Message addMessage(Message message) {
        return messageDAO.insertMessage(message);
     }

     /* */
     public Message updateMessage(int message_id, Message message) {
        if ((messageDAO.updateMessage(message_id, message)) == true) {
            return messageDAO.getMessageById(message_id);
        }
        return null;
     }

     /**
      * 
      * @param message_id with which to retrieve a message
      * @return the deleted message as JSON
      */
     public Message removeMessage(int message_id) {
        if ((messageDAO.deleteMessage(message_id)) == true) {
            return messageDAO.getMessageById(message_id);
        }
        return null;
     }

}
