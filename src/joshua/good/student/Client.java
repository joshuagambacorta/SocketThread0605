package joshua.good.student;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Timestamp;

public class Client {

    InetAddress indirizzoServer;
    int portaServer;
    Socket socket;
    BufferedReader tastiera;
    BufferedReader inDalServer;
    DataOutputStream outVersoServer;
    String messaggioDaInviare;
    String messaggioRicevuto;

    public Client(){
        //Porta del server maggiore di 1024
        int port = 2000;
        try{
            socket = new Socket("127.0.0.1", port);
            System.out.println("client connesso con il client: "+socket.getRemoteSocketAddress());
            System.out.println("client connesso con la porta: "+socket.getLocalPort());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (socket!=null)
                    socket.close();
            } catch (ConnectException e) {
                System.err.println("Errore in chiusura: "+e);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void comunica(){
        try {
            //inizializzazione degli oggetti per la comunicazione
            inDalServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outVersoServer = new DataOutputStream(socket.getOutputStream());
            tastiera = new BufferedReader(new InputStreamReader(System.in));

            //invio e recezione messaggi con server
            messaggioRicevuto = inDalServer.readLine();
            System.out.println("il server dice: "+messaggioRicevuto);
            System.out.println("la tua risposta la server");
            messaggioDaInviare = tastiera.readLine();
            if(!messaggioRicevuto.equals("1")){
                Timestamp serverDate = new Timestamp(Long.parseLong(messaggioRicevuto));
                System.out.println("Data del server: " + serverDate);
            } else {
                System.out.println("Richiesta errata, chiedere \"data\" per ottenere dal server la data");
            }
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
