package dev.jcodex.sockethandler.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Packet {
    Message type;
    String reqID;
    String body;
}
