package dev.jcodex.sockethandler.service;

import dev.jcodex.sockethandler.exception.RequestTimedOutException;
import dev.jcodex.sockethandler.model.Packet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


public final class Connection {


    private IOHandler handler;

    private SocketWriterService writerService;
    private SocketReaderService readerService;


    private Connection() {
    }


    public static Connection init(String host, int port) throws IOException {
        Socket client = new Socket(host, port);

        return init(client);
    }

    public static Connection init(Socket client) throws IOException {
        Connection connection = new Connection();

        InputStream reader = client.getInputStream();
        connection.readerService = new SocketReaderService(reader, connection);

        OutputStream writer = client.getOutputStream();
        connection.writerService = new SocketWriterService(writer, connection);


        connection.startListening();

        return connection;
    }

    void registerIOHandler(IOHandler handler) {
        this.handler = handler;
    }

    private void startListening() {
        readerService.run();
    }


    //outgoing request handler
    void sendData(Packet packet) throws RequestTimedOutException {
//        Request request = handler.generateRequest(message);
        writerService.write(packet);
//        Response response = handler.readResponse(request);
//        return response.getMessage();
    }

    //incoming request handler
    void readData(Packet packet) {
        handler.receiveData(packet);
//        try {
//            Optional<Request> optional = handler.receivingData(data);
//            optional.ifPresent(request -> {
//                //create service to handle incoming request
//                appServiceComponent.consumeRequest(request);
//            });
//        } catch (InvalidDataReceivedException e) {
//            throw new RuntimeException(e);
//        }
    }


    IOHandler getHandler() {
        return handler;
    }
}
