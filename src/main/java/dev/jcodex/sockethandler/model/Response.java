package dev.jcodex.sockethandler.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {
    String requestID;
    String message;
}
