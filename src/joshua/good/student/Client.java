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
import java.io.*;
import java.net.*;
import java.util.*;

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
        // establish a connection by providing host and port
        // number
        try (Socket socket = new Socket("localhost", 1234)) {

            // writing to server
            PrintWriter out = new PrintWriter(
                    socket.getOutputStream(), true);

            // reading from server
            BufferedReader in
                    = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));

            // object of scanner class
            Scanner sc = new Scanner(System.in);
            String line = null;

            while (!"exit".equalsIgnoreCase(line)) {

                // reading from user
                line = sc.nextLine();

                // sending the user input to server
                out.println(line);
                out.flush();

                // displaying server reply
                System.out.println("Server replied "
                        + in.readLine());
            }

            // closing the scanner object
            sc.close();
        }
        catch (IOException e) {
            e.printStackTrace();
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
