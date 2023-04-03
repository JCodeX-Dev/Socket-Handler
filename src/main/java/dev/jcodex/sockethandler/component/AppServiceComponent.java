package dev.jcodex.sockethandler.component;

import dev.jcodex.sockethandler.model.Packet;
import dev.jcodex.sockethandler.model.Request;
import dev.jcodex.sockethandler.model.Response;
import dev.jcodex.sockethandler.service.ConnectionService;
import dev.jcodex.sockethandler.service.ServerConnectionService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.Socket;


@Component
public interface AppServiceComponent {

    default ConnectionService connectToServer(String host, int port) throws IOException {
        ConnectionService service = new ConnectionService(this);
        service.init(host, port);
        return service;
    }

    default ConnectionService assignAppService(Socket clientSocket) throws IOException {
        ConnectionService service = new ConnectionService(this);
        service.init(clientSocket);
        return service;
    }

    default ServerConnectionService startServer(int port) {
        ServerConnectionService service = new ServerConnectionService(this);
        service.init(port);
        return service;
    }

//    default Queue<Request> requestConsumer(){
//
//    }

    Packet createPacket();

    String encryptData();

    void consumeRequest(Request request);

    Request produceRequest();

    void consumeResponse(Response response);

    Response produceResponse();

    ConnectionService retrieveClientConnection(ServerConnectionService service);


}
