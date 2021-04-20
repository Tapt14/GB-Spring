package Server;

public interface AuthService {
    void start();
    void stop();
    Users getNickByLoginAndPass(String login, String password);
}
