package dev.jcodex.sockethandler.service;

import dev.jcodex.sockethandler.exception.InvalidDataReceivedException;
import dev.jcodex.sockethandler.exception.RequestTimedOutException;
import dev.jcodex.sockethandler.model.Request;
import dev.jcodex.sockethandler.model.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

@Service
public class ConnectionService {


    RequestRespnseHandler handler;

    SocketWriterService writerService;
    SocketReaderService readerService;

    public void init(String host, int port) throws IOException {
        Socket client = new Socket(host,port);

        InputStream reader = client.getInputStream();
        readerService = new SocketReaderService(reader,this);
        OutputStream writer = client.getOutputStream();
        writerService = new SocketWriterService(writer,this);

        handler = new RequestRespnseHandler();

        startListening();
    }

    public void init(Socket client) throws IOException {
        InputStream reader = client.getInputStream();
        readerService = new SocketReaderService(reader,this);
        OutputStream writer = client.getOutputStream();
        writerService = new SocketWriterService(writer,this);

        handler = new RequestRespnseHandler();

        startListening();
    }

    private void startListening() {
       readerService.run();
    }


    //outgoing request handler
    public String sendData(String message) throws RequestTimedOutException {
        Request request = handler.generateRequest(message);
        writerService.write(request);
        Response response = handler.readResponse(request);
        return response.getMessage();
    }


    //incoming request handler
    public String readData(String data) throws InvalidDataReceivedException {
        handler.handleData(data);
    }


}
