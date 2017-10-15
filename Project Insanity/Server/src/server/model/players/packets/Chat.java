package server.model.players.packets;

import server.model.players.Client;
import server.model.players.PacketType;
import server.Connection;

/**
 * Chat
 *
 */
public class Chat implements PacketType {

    @Override
    public void processPacket(Client c, int packetType, int packetSize) {
        if (c.tutorial <= 12) {
		c.sendMessage("Your message do not appear for other players in tutorial island.");
		return;
	}
        c.setChatTextEffects(c.getInStream().readUnsignedByteS());
        c.setChatTextColor(c.getInStream().readUnsignedByteS());
        c.setChatTextSize((byte) (c.packetSize - 2));
        c.inStream.readBytes_reverseA(c.getChatText(), c.getChatTextSize(), 0);
        if (!Connection.isMuted(c)) {
            c.setChatTextUpdateRequired(true);
        }
    }
}
