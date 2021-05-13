/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.world.packet;

import io.archivcore.networking.DataBuffer;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import wowemulator.crypt.Crypt;
import wowemulator.networking.client.PacketIO;
import wowemulator.networking.client.TCPConnection;
import wowemulator.networking.packet.Packet;
import wowemulator.networking.packet.PacketHeader;

/**
 *
 * @author Dusko
 */
public class WorldSessionPacketIO extends PacketIO {

    private final Crypt crypt = new Crypt();

    public WorldSessionPacketIO(TCPConnection connection) {
        super(connection);
    }

    @Override public Packet readPacket() {
        try {
            int headerSize = crypt.isEnabled() ? 4 : 6;
            byte[] encodedHeader = new byte[headerSize];
            inputStream.read(encodedHeader);

            PacketHeader header = decodeHeader(encodedHeader);
            if (header.packetSize < 0) {
                return null;
            }

            byte[] bodyBytes = new byte[header.packetSize];
            inputStream.read(bodyBytes);

            Packet packet = new Packet(header, bodyBytes);
            return packet;
        } catch (IOException ex) {
            return null;
        }
    }

    @Override public void sendPacket(Packet packet) {
        try {
            byte[] header = encode(packet.size, packet.rawOpcode);
            byte[] body = packet.body.array();

            DataBuffer buffer = new DataBuffer(header.length + body.length);
            buffer.putBytes(header);
            buffer.putBytes(body);

            outputStream.write(buffer.array());
        } catch (IOException ex) {
            Logger.getLogger(WorldSessionPacketIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public final void initCrypt(byte[] sessionKey) {
        crypt.init(sessionKey);
    }

    private byte[] encode(int size, int opcode) {
        DataBuffer buffer = new DataBuffer(4);

        if (crypt.isEnabled()) {
            int maxOpcode = 0x1FFF;

            int data = (size << 13) | (opcode & maxOpcode);
            buffer.putInt(data);

            return crypt.encrypt(buffer.array());
        }

        buffer.putShort((short)(size + 2));
        buffer.putShort((short)opcode);
        return buffer.array();
    }

    private PacketHeader decodeHeader(byte[] encoded) {
        int bufferSize = crypt.isEnabled() ? 4 : 6;
        DataBuffer buffer = new DataBuffer(bufferSize);

        if (crypt.isEnabled()) {
            byte[] decrypted = crypt.decrypt(encoded);

            buffer.putBytes(decrypted);
            buffer.position(0);

            int headerValue = buffer.getInt();
            short size = (short)((headerValue & ~0x1FFF) >> 13);
            short opcode = (short)(headerValue & 0x1FFF);

            return new PacketHeader(opcode, size);
        }

        buffer.putBytes(encoded);
        buffer.position(0);

        short size = (short)(buffer.getShort() - 4);
        int opcode = (short)buffer.getInt();

        return new PacketHeader((short)opcode, size);
    }
}
