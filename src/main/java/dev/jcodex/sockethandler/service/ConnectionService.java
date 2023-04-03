package dev.jcodex.sockethandler.service;

import dev.jcodex.sockethandler.component.AppServiceComponent;
import dev.jcodex.sockethandler.exception.InvalidDataReceivedException;
import dev.jcodex.sockethandler.exception.RequestTimedOutException;
import dev.jcodex.sockethandler.model.Request;
import dev.jcodex.sockethandler.model.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Optional;


public class ConnectionService {


    RequestRespnseHandler handler;

    SocketWriterService writerService;
    SocketReaderService readerService;

    AppServiceComponent appServiceComponent;

    public ConnectionService(AppServiceComponent appServiceComponent) {
        this.appServiceComponent = appServiceComponent;
    }

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
    public void readData(String data) {
        try {
            Optional<Request> optional = handler.handleData(data);
            optional.ifPresent(request -> {
                //create service to handle incoming request
                appServiceComponent.consumeRequest(request);
            });
        } catch (InvalidDataReceivedException e) {
            throw new RuntimeException(e);
        }
    }


}
