package dev.jcodex.sockethandler.service;

import lombok.AllArgsConstructor;

import java.io.InputStream;

@AllArgsConstructor
public class SocketReaderService implements Runnable {

    InputStream stream;
    ConnectionService connectionService;

    @Override
    public void run() {

        while (true){
            //read data logic
            String data = "";



            //if data is read
            //pass it to Connection service
            connectionService.readData(data);
        }

    }


}
