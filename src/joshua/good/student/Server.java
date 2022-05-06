package joshua.good.student;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.net.*;

public class Server {

    ServerSocket sSocket;
    Socket socket;
    BufferedReader inDalClient;
    DataOutputStream outVersoClient;
    String messaggioDaInviare;
    String messaggioRicevuto;

    public Server(){
        ServerSocket server = null;

        try {

            // server is listening on port 1234
            server = new ServerSocket(1234);
            server.setReuseAddress(true);

            // running infinite loop for getting
            // client request
            while (true) {

                // socket object to receive incoming client
                // requests
                Socket client = server.accept();

                // Displaying that new client is connected
                // to server
                System.out.println("New client connected: "
                        + client.getInetAddress()
                        .getHostAddress());

                // create a new thread object
                ClientHandler clientSock
                        = new ClientHandler(client);

                // This thread will handle the client
                // separately
                new Thread(clientSock).start();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (server != null) {
                try {
                    server.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void comunica(){
        try {
            //inizializzazione degli oggetti per la comunicazione
            inDalClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outVersoClient = new DataOutputStream(socket.getOutputStream());

            //invio e recezione di messaggi con il server
            messaggioDaInviare = "Prova!\n";
            outVersoClient.writeBytes(messaggioDaInviare+"\n");
            outVersoClient.flush();
            messaggioRicevuto = inDalClient.readLine();
            if (messaggioRicevuto.equals("data")) {
                messaggioDaInviare = Long.toString(System.currentTimeMillis());
            } else {
                messaggioDaInviare = "1";
            }
            outVersoClient.writeBytes(messaggioDaInviare+"\n");
            outVersoClient.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void chiudi(){
        try{
            //chiusura della connessione
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
