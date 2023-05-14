package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    public Message insertMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "INSERT INTO message (message_text, posted_by, time_posted_epoch) VALUES (?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, message.getMessage_text());
            preparedStatement.setInt(2, message.getPosted_by());
            preparedStatement.setLong(3, message.getTime_posted_epoch());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int generatedMessageId = (int) resultSet.getLong(1);
                return new Message(generatedMessageId, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public List<Message> selectAllMessages(int id) {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();

        try {
            String sql = "SELECT * FROM message WHERE posted_by = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Message message = new Message(
                        resultSet.getInt("message_id"),
                        resultSet.getInt("posted_by"),
                        resultSet.getString("message_text"),
                        resultSet.getLong("time_posted_epoch")
                );
                messages.add(message);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }
}
