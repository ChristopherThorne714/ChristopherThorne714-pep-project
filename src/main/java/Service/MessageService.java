package Service;

import DAO.MessageDAO;
import Model.Message;
import java.util.List;

/**
 * service layer for interacting with MessageDAO instances
 * to be called by endpoint handlers in SocialMediaController
 */
public class MessageService {
    private MessageDAO messageDAO;
    
    public MessageService() {
        messageDAO = new MessageDAO();
    }

    /**
     * for testing purposes
     * @param messageDAO
     */
    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    /**
     * gets a list of all messages
     * @return List<Messages> as JSON
     */
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    /**
     * gets a message using the provided id
     * @param message_id with which to retrieve a message
     * @return retrieved message
     */
    public Message getMessageById(int message_id) {
        return messageDAO.getMessageById(message_id);
    }
    /**
     * inserts a message into message table
     * @param message to be added
     * @return message added as JSON
     */
     public Message addMessage(Message message) {
        return messageDAO.insertMessage(message);
     }

     /**
      * 
      * @param message_id: the id of the message to be updated
      * @param message: the new text to update the message with
      * @return the message that's been updated or null if there was no update
      */
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
        Message message = getMessageById(message_id);
        messageDAO.deleteMessage(message_id);
        return message;
     }

}
