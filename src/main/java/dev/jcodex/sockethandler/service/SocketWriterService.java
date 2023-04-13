package dev.jcodex.sockethandler.service;

import dev.jcodex.sockethandler.model.Packet;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

@Data
@AllArgsConstructor
final class SocketWriterService {

    private ObjectOutputStream stream;
    private Connection connection;

    public SocketWriterService(OutputStream stream, Connection connection) throws IOException {
        this.stream = new ObjectOutputStream(stream);
        this.connection = connection;
    }

    void write(Packet packet) throws IOException {
        stream.writeObject(packet);
    }

}
