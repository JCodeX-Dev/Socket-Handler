package dev.jcodex.sockethandler.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvalidDataReceivedException extends Exception{
    String error;
}
