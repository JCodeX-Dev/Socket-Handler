package dev.jcodex.sockethandler.service;

import dev.jcodex.sockethandler.model.Packet;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.OutputStream;

@Data
@AllArgsConstructor
final class SocketWriterService {

    private OutputStream stream;
    private Connection connection;

    void write(Packet packet) {

    }

}
