package dev.jcodex.sockethandler.service;

import dev.jcodex.sockethandler.model.Packet;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

@AllArgsConstructor
final class SocketReaderService implements Runnable {

    private ObjectInputStream stream;
    private Connection connection;

    public SocketReaderService(InputStream stream, Connection connection) throws IOException {
        this.stream = new ObjectInputStream(stream);
        this.connection = connection;
    }

    @Override
    public void run() {

        while (true) {
            //read data logic
            Packet packet;
            try {
                packet = (Packet) stream.readObject();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }


            //if data is read
            //pass it to Connection service
            connection.readData(packet);
        }

    }


}
