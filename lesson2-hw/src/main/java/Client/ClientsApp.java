package Client;

import Client.ClientApplication.Messenger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ClientsApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ClientConfig.class);
        Messenger messenger = context.getBean("messenger", Messenger.class);
        messenger.run();
        context.close();
    }
}

