package joshua.good.student;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    ServerSocket sSocket;
    Socket socket;
    BufferedReader inDalClient;
    DataOutputStream outVersoClient;
    String messaggioDaInviare;
    String messaggioRicevuto;

    public Server(){
        //Porta del server maggiore di 1024
        int port = 2000;
        //tempo in millisecondi in attesa
        int time = 2000;
        //oggetto sServerSocket necessario per accettare la richiesta del client
        ServerSocket sSocket = null;
        //oggetto da usare per fare la connessione TCP
        Socket socket = null;
        try {
            //apro una porta nel server socket (appena istanziato)
            sSocket = new ServerSocket(port);
            System.out.println("In attesa di connessione...");

            //accetto la connessione
            socket = sSocket.accept();
            System.out.println("Accettata la connessione: "+socket.getRemoteSocketAddress());

            //Scambio di dati
            //socket.getInputStream();
        } catch (
                IOException e) {
            System.err.println("Errore di I/O nell'istanza del server: "+e);
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
