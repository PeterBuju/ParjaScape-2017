package server.model.players.packets;

import server.model.players.Client;
import server.model.players.PacketType;
import server.Server;
import server.model.players.util.RegionMusic;

public class ChangeRegion implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		c.getPA().removeObjects();
                new RegionMusic().playMusic(c);
		//Server.objectManager.loadObjects(c);
                Server.objectHandler.updateObjects(c);
	}

}
