package dev.jcodex.sockethandler.service;

import dev.jcodex.sockethandler.model.Packet;
import lombok.AllArgsConstructor;

import java.io.InputStream;

@AllArgsConstructor
final class SocketReaderService implements Runnable {

    private InputStream stream;
    private Connection connection;

    @Override
    public void run() {

        while (true) {
            //read data logic
            String data = "";


            //if data is read
            //pass it to Connection service

            Packet packet = convertStringToPacket(data);
            connection.readData(packet);
        }

    }

    private Packet convertStringToPacket(String data) {

        return null;
    }


}
