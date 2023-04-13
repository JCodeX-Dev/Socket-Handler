package dev.jcodex.sockethandler.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Packet implements Serializable {
    Message type;
    String reqID;
    String body;
}
