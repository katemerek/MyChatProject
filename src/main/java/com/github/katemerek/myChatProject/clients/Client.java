package com.github.katemerek.myChatProject.clients;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class Client implements Runnable {
    private String clientName = null;
    private Socket socket = null;
    private Scanner inputStream = null;
    private PrintWriter outputStream = null;
    private BufferedReader response = null;

    public Client(String hostName, int portNumber, String clientName) {

        this.clientName = clientName;

        while (true) { //retries for wrong port number

            try {
                socket = new Socket(hostName, portNumber);
                System.out.println(clientName + ", you're now connected!");
                inputStream = new Scanner(System.in);
                outputStream = new PrintWriter(socket.getOutputStream());
                break;


            } catch (IOException e) {
                inputStream = new Scanner(System.in);
                System.err.println("Connection error. Pls enter a valid port number of the server.");
                System.err.println("you can try the default server port number '8888' or type 'exit' to stop");
                String pNumber = inputStream.nextLine();

                if (pNumber.equalsIgnoreCase("exit") || pNumber.isBlank()) {
                    throw new RuntimeException("Exiting...");
                }

                try {
                    portNumber = Integer.parseInt(pNumber);
                } catch (Exception er) {
                    throw new RuntimeException(er.getMessage());
                }
            }
        }
    }

    @Override
    public void run() {

        try { //Get the response
            response = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            inputStream.close();
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public static void main(String[] args) {

        String userName = null;
        System.out.print("Pls, enter your name here: ");
        Scanner in = new Scanner(System.in);

        while (true) {
            userName = in.nextLine();
            if (!userName.isBlank()) break;
            System.err.println("Name cannot be blank. Please enter your name");
        }

        System.out.print("Pls, enter the server port number here or press ENTER to use the default '8888': ");

        String portNumber = in.nextLine();
        System.out.println(portNumber);
        in.reset();
        int port = 8888;
        try {
            port = Integer.parseInt(portNumber.isBlank() ? "8888" : portNumber);
        } catch (Exception ignore) {
        }

        try {
            Thread cl = new Thread(new Client("localhost", port, userName));
            cl.start();
            String line = "";
            while (!line.equals("exit")) {
                line = in.readline
                out.println(line);
                System.out.println(in.readLine());
            }
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }



    }
}