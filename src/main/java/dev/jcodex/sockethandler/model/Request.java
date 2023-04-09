package dev.jcodex.sockethandler.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Request {

    String requestID;

    String message;

}
