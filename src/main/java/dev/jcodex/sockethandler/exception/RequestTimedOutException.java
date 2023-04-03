package dev.jcodex.sockethandler.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RequestTimedOutException extends Exception{
    String error;
}
