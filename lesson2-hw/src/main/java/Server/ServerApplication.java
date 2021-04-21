package Server;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ServerApplication {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ServerConfig.class);
        Server server = context.getBean("server", Server.class);
        server.start();
        context.close();
    }
}
