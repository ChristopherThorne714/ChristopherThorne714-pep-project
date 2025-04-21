package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {

    /**
     * should create a list with all messages from message table 
     * @return a list of all message objects in db
     */
    public List<Message> getAllMessages() {
        Connection con = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();

        try {
            String sql = "SELECT * FROM message;";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        return messages;
    }

    /**
     * should select a message using the id and return the updated message
     * @param message_id id to use in the sql query
     * @return the updated message if successful or null otherwise
     */
    public Message getMessageById(int message_id) {
        Connection con = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM message WHERE message_id = ?;";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, message_id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return new Message(message_id, rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    /**
     * should query for all messages associated with a given account_id
     * @param account_id id given with which to search for messages
     * @return a list of message objects associated with account_id
     */
    public List<Message> getAccountMessages(int account_id) {
        Connection con = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();

        try {
            String sql = "SELECT * FROM message WHERE posted_by = ?;";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, account_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
            return messages;
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    /**
     * should insert the given message into the db and return the newly created object 
     * @param message to be inserted to db
     * @return  message inserted
    */
    public Message insertMessage(Message message) {
        Connection con = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) SELECT ?, ?, ? WHERE EXISTS (SELECT 1 FROM account WHERE account.account_id = ?)";
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());
            ps.setInt(4, message.getPosted_by());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int generated_message_key = (int) rs.getLong(1);
                return new Message(generated_message_key, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    /**
     * should update a message in db and return true or false based on success
     * @param message_id id of the message to be updated
     * @param message message text to update with
     * @return true or false based on success 
     */
    public Boolean updateMessage(int message_id, Message message) {
        Connection con = ConnectionUtil.getConnection();

        try {
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?;";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, message.getMessage_text());
            ps.setInt(2, message_id);

            int result = ps.executeUpdate();
            if (result != 0) {
                return true;
            }
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    /**
     *  should delete a message and return nothing
     * @param message_id id of the message to be deleted
     */
    public void deleteMessage(int message_id) {
        Connection con = ConnectionUtil.getConnection();

        try {
            String sql = "DELETE FROM message WHERE message_id = ?;";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, message_id);

            ps.executeUpdate();
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
