/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.world;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
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
    
    @Override
    public Packet readPacket() {
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
    
    @Override
    public void sendPacket(Packet packet) {
        try {
            byte[] header = encode(packet.size, packet.rawOpcode);
            packet.body.position(0);
            
            ByteBuffer buffer = ByteBuffer.allocate(header.length + packet.body.capacity());
            buffer.put(header);
            buffer.put(packet.body);
            
            outputStream.write(buffer.array());
        } catch (IOException ex) {
            Logger.getLogger(WorldSessionPacketIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public final void initCrypt(byte[] sessionKey) {
        crypt.init(sessionKey);
    }
    
    private byte[] encode(int size, int opcode){
        int index = 0;
        int newSize = size + 2;
        byte[] header = new byte[4];
        if (newSize > 0x7FFF) {
            header[index++] = (byte) (0x80 | (0xFF & (newSize >> 16)));
        }
	
        header[index++] = (byte)(0xFF & (newSize >> 8));
        header[index++] = (byte)(0xFF & (newSize >> 0));
        header[index++] = (byte)(0xFF & opcode);
        header[index] = (byte)(0xFF & (opcode >> 8));
        
        System.out.println("Before === " + Arrays.toString(header));
        return crypt.encrypt(header);
    }
    
    private PacketHeader decodeHeader(byte[] encoded){
    	byte[] header = crypt.decrypt(encoded);
    	
        ByteBuffer buffer = ByteBuffer.allocate(6);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.put(header);
        buffer.position(0);
        
        short size = (short)(buffer.get() << 8);
        size |= buffer.get() & 0xFF;
        size -= 4;
        
        short opcode = (short)buffer.getInt();
        return new PacketHeader(opcode, size);
    }
}
