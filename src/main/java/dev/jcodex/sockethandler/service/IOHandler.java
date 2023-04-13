package dev.jcodex.sockethandler.service;

import dev.jcodex.sockethandler.ServiceController;
import dev.jcodex.sockethandler.exception.InvalidDataReceivedException;
import dev.jcodex.sockethandler.exception.RequestTimedOutException;
import dev.jcodex.sockethandler.model.Message;
import dev.jcodex.sockethandler.model.Packet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public final class IOHandler {

    private HashMap<String, Packet> requestMap;

//    HashMap<String, Request> responseMap;

    private Connection connection;

    private ServiceController controller;

    private IOHandler() {
    }

    public static IOHandler init(Connection connection) {
        Optional<IOHandler> optional = Optional.of(connection.getHandler());

        IOHandler handler = optional.orElse(new IOHandler());

        handler.connection = connection;
        connection.registerIOHandler(handler);
        return handler;
    }

    public void registerServiceController(ServiceController controller) {
        this.controller = controller;
    }


    //outgoing request
    public Packet sendRequest(String body) throws RequestTimedOutException, IOException {
        Packet packet = createPacket(body, Message.REQUEST);
        return sendRequest(packet);
    }

    public Packet sendRequest(Packet packet) throws RequestTimedOutException, IOException {
        String requestID = packet.getReqID();

        connection.sendData(packet);

        requestMap.put(requestID, null);

        if (requestTimeout(requestID))
            return requestMap.get(requestID);
        else {
            throw new RequestTimedOutException("Request timed out");
        }

    }


    // outgoing response
    public void sendResponse(String body) {
        //handle outgoing response
    }

    public void sendResponse(Packet packet) {
        //handle outgoing response
    }

    //request timeout function waiting for response
    private boolean requestTimeout(String requestID) {
        var context = new Object() {
            boolean found = false;
        };
        Thread thread = new Thread(() -> {
            long startTime = System.currentTimeMillis();
            long timeout = 60 * 1000; // 60 seconds

            while (!context.found && (System.currentTimeMillis() - startTime) < timeout) {
                try {
                    Thread.sleep(5); // wait 5ms between checks
                } catch (InterruptedException e) {
                    // thread interrupted, exit loop
                    break;
                }

                if (Optional.of(requestMap.get(requestID)).isPresent()) {
                    context.found = true;
                }
            }
        });
        return context.found;
    }


    //incoming request/response
    void receiveData(Packet packet) {
        Message type = packet.getType();
        if (type.equals(Message.RESPONSE)) {
            requestMap.put(packet.getReqID(), packet);  //incoming response
        } else if (type.equals(Message.REQUEST)) {
            controller.requestHandler(packet);          //incoming request
        } else {
            try {
                throw new InvalidDataReceivedException("Invalid Data received at socket input stream");
            } catch (InvalidDataReceivedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String generateRequestID() {
        return UUID.randomUUID().toString();
    }

    public Packet createPacket(String body, Message type) {
        String reqID = generateRequestID();
        Packet packet = new Packet(type, reqID, body);
        return packet;
    }
}
