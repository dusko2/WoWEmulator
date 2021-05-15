/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.archivcore.logon.auth;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import io.archivcore.logon.LogonSession;
import io.archivcore.networking.client.PacketIO;
import io.archivcore.networking.client.TCPConnection;
import io.archivcore.networking.packet.Packet;
import io.archivcore.networking.packet.PacketHeader;

/**
 *
 * @author Dusko
 */
public class AuthPacketIO extends PacketIO {

    public AuthPacketIO(TCPConnection connection) {
        super(connection);
    }

    @Override public Packet readPacket() {
        try {
            short opcodeValue = (short)inputStream.read();

            AuthOpcode opcode = AuthOpcode.getOpcode(opcodeValue);
            if (opcode == null) {
                return null;
            }

            short size = opcode.getSize();

            // ClientLogonChallenge packet has dynamic size
            if (opcode == AuthOpcode.ClientLogonChallenge) {
                inputStream.read(); // unknown
                size = (short)inputStream.read();
                inputStream.read(); // unknown
            }

            PacketHeader header = new PacketHeader(opcodeValue, size);

            byte[] bodyBytes = new byte[size];
            inputStream.read(bodyBytes);

            return new Packet(header, bodyBytes);
        } catch (IOException ex) {
            return null;
        }
    }

    @Override public void sendPacket(Packet packet) {
        byte[] packetBytes = packet.body.array();
        try {
            outputStream.write(packetBytes);
        } catch (IOException ex) {
            Logger.getLogger(LogonSession.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
