package dev.jcodex.sockethandler;

import dev.jcodex.sockethandler.model.Packet;
import dev.jcodex.sockethandler.service.IOHandler;

public abstract class ServiceController {

    public IOHandler handler;

    final void init(IOHandler handler) {
        this.handler = handler;
        handler.registerServiceController(this);
    }

    public abstract void requestHandler(Packet request);

}
