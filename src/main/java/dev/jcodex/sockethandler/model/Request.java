package dev.jcodex.sockethandler.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Request {

    String requestID;

    String message;

}
