package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {

    public List<Message> getAllMessages() {
        Connection con = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();

        try {
            String sql = "SELECT * FROM message;";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Message message = new Message(rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        return messages;
    }

    /* */
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
     * 
     * @param message to be inserted to db
     * @return  message inserted
    */
    public Message insertMessage(Message message) {
        Connection con = ConnectionUtil.getConnection();

        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES ?, ?, ?;";
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());

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
}
