package Client;

import Client.ClientApplication.Messenger;

public class ClientsApp {
    public static void main(String[] args) {
        new Thread(Messenger::new).start();

    }
}

