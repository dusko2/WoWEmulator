/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.world.handler;

import wowemulator.world.protocol.WorldOpcodeHandler;
import wowemulator.networking.packet.Packet;
import wowemulator.world.protocol.WorldOpcode;
import wowemulator.world.packet.WorldPacket;
import wowemulator.world.WorldSession;

/**
 *
 * @author Dusko
 */
public class RealmSplitHandler implements WorldOpcodeHandler {

    private enum RealmSplitState {
        
        Normal      (0x00),
        Split       (0x01),
        SplitPending(0x02);
        
        public final int rawValue;

        private RealmSplitState(int rawValue) {
            this.rawValue = rawValue;
        }
    }
    
    @Override
    public void handle(WorldSession session, Packet packet) {
        int unknown = packet.getInt();
        String splitDate = "01/01/01";
        
        WorldPacket response = new WorldPacket(WorldOpcode.SmsgRealmSplit, 4 + 4 + splitDate.length() + 1);
        response.putInt(unknown);
        response.putInt(RealmSplitState.Normal.rawValue);
        response.putString(splitDate);
        session.send(response);
    }
}
