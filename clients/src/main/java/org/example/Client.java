package org.example;


import org.example.model.Account;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Serializable {
    public static void main( String[] args ) throws IOException {
        Scanner scanner = new Scanner(System.in);
            try (Socket socketClient = new Socket("localhost", 1111)) {
                try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(socketClient.getOutputStream())) {
                    Account account = new Account( "name");
                    objectOutputStream.writeObject(account);
                    while (true) {
                        objectOutputStream.writeObject(scanner.nextLine());
                        objectOutputStream.flush();
                    }
                }
            }
        }
    }
