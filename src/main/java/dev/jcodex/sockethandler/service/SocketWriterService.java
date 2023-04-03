package dev.jcodex.sockethandler.service;

import dev.jcodex.sockethandler.model.Request;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.OutputStream;
import java.security.cert.CertPath;

@Data
@AllArgsConstructor
public class SocketWriterService {

    OutputStream stream;
    ConnectionService connectionService;

    public void write(Request request){

    }
}
