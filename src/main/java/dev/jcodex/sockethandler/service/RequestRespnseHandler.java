package dev.jcodex.sockethandler.service;

import dev.jcodex.sockethandler.exception.InvalidDataReceivedException;
import dev.jcodex.sockethandler.exception.RequestTimedOutException;
import dev.jcodex.sockethandler.model.Request;
import dev.jcodex.sockethandler.model.Response;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class RequestRespnseHandler {
    HashMap<String, Response> responseMap = new HashMap<>();

    boolean found;


    private String generateRequestID(){
        return UUID.randomUUID().toString();
    }

    public Request generateRequest(String message){
        String requestID = generateRequestID();
        return new Request(requestID,message);
    }


    public Response readResponse(Request request) throws RequestTimedOutException {

        String requestID = request.getRequestID();
        responseMap.put(requestID,null);

        if (requestTimeout(requestID))
            return responseMap.get(requestID);
        else {
            throw new RequestTimedOutException("Request timed out");
        }


    }

    private boolean requestTimeout(String requestID){
        found = false;
        Thread thread = new Thread(() -> {
            long startTime = System.currentTimeMillis();
            long timeout = 60 * 1000; // 60 seconds

            while (!found && (System.currentTimeMillis() - startTime) < timeout) {
                try {
                    Thread.sleep(5); // wait 5ms between checks
                } catch (InterruptedException e) {
                    // thread interrupted, exit loop
                    break;
                }

                if (Optional.of(responseMap.get(requestID)).isPresent()) {
                    found = true;
                }
            }
        });
        return found;
    }

    public Optional<Request> handleData(String data) throws InvalidDataReceivedException {
        Object obj = parseData();
        if (obj instanceof Response){
            Response response = (Response)obj;
            responseMap.put(response.getRequestID(),response);
            return Optional.of(null);
        } else if (obj instanceof Request) {
            return Optional.of((Request) obj);
        }
        else {
            throw new InvalidDataReceivedException("Invalid Data received at socket input stream");
        }
    }

    private Object parseData() {
        //data parsing logic
        //scan the data to identify request or response
        //data to be deserialized


        //return type Request or Response
        //if neither return null
    }
}
