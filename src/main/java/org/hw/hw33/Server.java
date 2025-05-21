package org.hw.hw33;

import org.apache.log4j.Logger;
import org.example.model.Account;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private static final Map<String, ClientInfo> activeConnections = new ConcurrentHashMap<>();
    private static int clientCounter = 0;
    private final static Logger LOGGER = Logger.getLogger(Server.class);

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(1111)) {
            LOGGER.info("Server started on port 1111");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                String clientId = "client-" + (++clientCounter);
                LocalDateTime connectionTime = LocalDateTime.now();
                ClientInfo clientInfo = new ClientInfo(clientId, connectionTime, clientSocket);
                activeConnections.put(clientId, clientInfo);

//                System.out.println("New client connected: " + clientId);
                LOGGER.info(clientId + " connected successfully.");

                new Thread(() -> handleClient(clientInfo)).start();
            }
        }
    }

    private static void handleClient(ClientInfo clientInfo) {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(clientInfo.socket.getInputStream())) {
            while (true) {
                try {
                    Object receivedObject = objectInputStream.readObject();
                    if (receivedObject instanceof Account) {
                        Account account = (Account) receivedObject;
                    } else if (receivedObject instanceof String) {
                        String phrase = (String) receivedObject;
                        LOGGER.info(clientInfo.id + " input: " + phrase);

                        if (phrase.equalsIgnoreCase("exit")) {
                            LOGGER.info(clientInfo.id + " disconnected successfully.");
                            activeConnections.remove(clientInfo.id);
                            break;
                        }
                    } else {
                        LOGGER.warn("Unknown object received from " + clientInfo.id);
                    }
                } catch (EOFException e) {
                    LOGGER.warn(clientInfo.id + " disconnected unexpectedly.");
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            LOGGER.error(e);
        }
    }

    private static class ClientInfo {
        private String id;
        private LocalDateTime connectionTime;
        private Socket socket;

        ClientInfo(String id, LocalDateTime connectionTime, Socket socket) {
            this.id = id;
            this.connectionTime = connectionTime;
            this.socket = socket;
        }
    }
}
