/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.world.packet;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
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
            byte[] encodedHeader = new byte[6];
            inputStream.read(encodedHeader);

            PacketHeader header = decodeHeader(encodedHeader);
            if (header.packetSize < 0) {
                return null;
            }

            byte[] bodyBytes = new byte[header.packetSize];
            inputStream.read(bodyBytes);

            Packet packet = new Packet(header, bodyBytes);
            packet.body.order(ByteOrder.LITTLE_ENDIAN);

            return packet;
        } catch (IOException ex) {
            return null;
        }
    }

    @Override public void sendPacket(Packet packet) {
        try {
            byte[] header = encode(packet.size, packet.rawOpcode);
            packet.body.position(0);

            ByteBuffer buffer = ByteBuffer.allocate(header.length + packet.body.capacity());
            buffer.put(header);
            buffer.put(packet.body);

//            for (int i = 0; i < buffer.array().length; i++) {
//                System.out.print((char)buffer.array()[i]);
//            }
//            System.out.println("");

            outputStream.write(buffer.array());
        } catch (IOException ex) {
            Logger.getLogger(WorldSessionPacketIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public final void initCrypt(byte[] sessionKey) {
        crypt.init(sessionKey);
    }

    private byte[] encode(int size, int opcode){
//        int index = 0;
//        int newSize = size + 2;
//        byte[] header = new byte[4];
//        if (newSize > 0x7FFF) {
//            header[index++] = (byte) (0x80 | (0xFF & (newSize >> 16)));
//        }
//
//        header[index++] = (byte)(0xFF & (newSize >> 8));
//        header[index++] = (byte)(0xFF & (newSize));
//        header[index++] = (byte)(0xFF & opcode);
//        header[index] = (byte)(0xFF & (opcode >> 8));
//
//        return crypt.encrypt(header);

        if (crypt.isEnabled()) {
            int maxOpcode = 0x1FFF;

            int data = (size << 13) | (opcode & maxOpcode);
            System.out.println(String.format("DATA = %d, OPCODE = %d, SIZE = %d", data, opcode, size));

            ByteBuffer buffer = ByteBuffer.allocate(4);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            buffer.putInt(data);

            return crypt.encrypt(buffer.array());
        }

        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.putShort((short)(size + 2));
        buffer.putShort((short)opcode);
        return buffer.array();
    }

    private PacketHeader decodeHeader(byte[] encoded){
//    	  byte[] header = crypt.decrypt(encoded);
//
//        ByteBuffer buffer = ByteBuffer.allocate(6);
//        buffer.order(ByteOrder.LITTLE_ENDIAN);
//        buffer.put(header);
//        buffer.position(0);
//
//        short size = (short)(buffer.get() << 8);
//        size |= buffer.get() & 0xFF;
//        size -= 4;
//
//        short opcode = (short)buffer.getInt();
//        return new PacketHeader(opcode, size);

        byte[] header = crypt.decrypt(encoded);

        ByteBuffer buffer = ByteBuffer.allocate(6);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.put(header);
        buffer.position(0);

        short size = (short)(buffer.getShort() - 4);
        int opcode = (short)buffer.getInt();
//        System.out.println(">> [decodeHeader] Opcode: " + Integer.toHexString(opcode).toUpperCase());

        return new PacketHeader((short)opcode, size);
    }
}

//>> [Server Packet] MSG_VERIFY_CONNECTIVITY
//>> [Client Packet] MSG_VERIFY_CONNECTIVITY
//000000000000000000000000000000000017915324211
//>> [Server Packet] SMSG_AUTH_CHALLENGE
//>> [Client Packet] CMSG_AUTH_SESSION
//0, 0, 0, 0, 0, 0, 0, 0, 171, 147, 18, 209, 55, 44, 0, 0, 0, 4, 30, 60, 177, 236, 24, 1, 0, 95, 74, 181, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 104, 200, 21, 95, 238, 71, 151, 77, 245, 100, 2, 190, 114, 1, 0, 0, 48, 5, 0, 0, 120, 156, 117, 147, 97, 110, 131, 48, 12, 133, 217, 61, 118, 132, 93, 162, 237, 86, 212, 169, 72, 172, 208, 254, 157, 76, 226, 130, 69, 136, 145, 9, 108, 237, 229, 183, 160, 73, 211, 38, 57, 252, 228, 123, 118, 226, 151, 231, 167, 44, 203, 182, 142, 238, 119, 16, 251, 190, 49, 29, 225, 130, 3, 250, 112, 62, 100, 15, 195, 199, 227, 49, 251, 199, 197, 116, 128, 236, 184, 189, 37, 56, 122, 208, 201, 108, 2, 177, 87, 217, 22, 164, 65, 153, 58, 30, 19, 56, 4, 135, 87, 66, 103, 11, 242, 52, 192, 168, 137, 200, 91, 242, 173, 222, 192, 129, 233, 11, 144, 30, 245, 169, 118, 224, 208, 91, 16, 13, 117, 224, 34, 108, 113, 210, 43, 29, 69, 171, 42, 88, 208, 94, 64, 8, 26, 135, 147, 38, 227, 161, 129, 112, 228, 54, 201, 106, 252, 12, 58, 28, 193, 132, 19, 144, 221, 11, 12, 122, 243, 243, 190, 20, 190, 146, 126, 244, 51, 54, 115, 91, 51, 59, 13, 190, 120, 195, 179, 15, 40, 175, 60, 139, 7, 167, 72, 114, 119, 27, 59, 117, 246, 188, 136, 230, 232, 134, 230, 69, 53, 203, 130, 122, 70, 242, 153, 156, 221, 130, 239, 211, 116, 199, 62, 8, 187, 180, 64, 37, 7, 63, 141, 104, 244, 27, 29, 2, 14, 27, 23, 39, 133, 100, 10, 87, 73, 197, 38, 134, 36, 149, 163, 85, 113, 30, 91, 1, 139, 42, 63, 50, 247, 177, 118, 207, 146, 190, 100, 1, 70, 88, 39, 188, 96, 9, 86, 33, 37, 134, 159, 29, 80, 235, 34, 77, 63, 223, 154, 28, 181, 234, 132, 87, 150, 54, 53, 105, 189, 110, 132, 238, 100, 77, 3, 22, 224, 161, 69, 109, 95, 106, 238, 81, 183, 183, 94, 109, 171, 122, 114, 250, 179, 70, 76, 30, 69, 101, 23, 38, 91, 5, 150, 120, 166, 238, 192, 165, 84, 255, 191, 205, 56, 133, 93, 199, 100, 80, 161, 107, 71, 189, 223, 102, 14, 221, 239, 226, 255, 85, 124, 197, 239, 27, 122, 235, 150, 196, 0, 80, 68, 85, 83, 75, 79,
//>> [Server Packet] SMSG_AUTH_RESPONSE
//DATA = 977594, OPCODE = 2746, SIZE = 119
//>> [Server Packet] SMSG_ADDON_INFO
//DATA = 2348554, OPCODE = 5642, SIZE = 286
//>> [Server Packet] SMSG_CLIENTCACHE_VERSION
//DATA = 32810, OPCODE = 42, SIZE = 4
//>> [Server Packet] SMSG_TUTORIAL_FLAGS
//DATA = 269200, OPCODE = 7056, SIZE = 32
//>> [Server Packet] SMSG_SET_TIME_ZONE_INFORMATION
//DATA = 137665, OPCODE = 6593, SIZE = 16
//No defined handler for opcode [CMSG_REALM_SPLIT 0x18B2 (6322)] sent by [Player: <none> (Guid: 0, Account: 1)]
//>> [Client Packet] CMSG_READY_FOR_ACCOUNT_DATA_TIMES
//>> [Server Packet] SMSG_ACCOUNT_DATA_TIMES
//DATA = 341547, OPCODE = 5675, SIZE = 41
//>> [Client Packet] CMSG_ENUM_CHARACTERS
//>> [Server Packet] SMSG_ENUM_CHARACTERS_RESULT
//DATA = 2331075, OPCODE = 4547, SIZE = 284
