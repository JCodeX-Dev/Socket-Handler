package dev.jcodex.sockethandler.service;

import dev.jcodex.sockethandler.exception.ClientSocketConnectionException;
import dev.jcodex.sockethandler.exception.IncomingClientConnectionException;
import dev.jcodex.sockethandler.exception.ServerSocketConnectionException;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public final class ServerConnectionService {

    @Getter
    public List<Connection> connections;

    public void init(int port){
        connections = new ArrayList<>();

        startServer(port);
    }

    private void startServer(int port) {
        Runnable server = () -> {
            while (true){
                ServerSocket serverSocket = null;
                try {
                    serverSocket = new ServerSocket(port);
                } catch (IOException e) {
                    throw new ServerSocketConnectionException("Server Socket failed to start on the listening port");
                }
                Socket clientSocket = null;
                try {
                    clientSocket = serverSocket.accept();
                } catch (IOException e) {
                    throw new IncomingClientConnectionException("Failed to accept incoming client connection");
                }

                try {
                    Connection connection = Connection.init(clientSocket);
                    connections.add(connection);
                } catch (IOException e) {
                    throw new ClientSocketConnectionException("Socket connection failed connecting to client");
                }
            }
        };

        server.run();
    }
}
