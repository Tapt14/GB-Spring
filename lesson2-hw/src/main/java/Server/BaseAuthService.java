package Server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseAuthService implements AuthService {

    @Override
    public void start() {
        System.out.println("Server.Server Auth 2.0 is starting...");
    }

    @Override
    public void stop() {
        System.out.println("Server.Server Auth 2.0 is showdown...");
    }

    @Override
    public Users getNickByLoginAndPass(String login, String password) {

        Connection connection = ConfigMySqlDb.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE login = ? AND password = ?");
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return new Users(
                        resultSet.getInt("id"),
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getString("nickname")
                );

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
    }finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
        return null;
    }
}
