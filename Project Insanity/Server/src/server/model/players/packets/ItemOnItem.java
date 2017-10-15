package server.model.players.packets;

/**
 * @author Ryan / Lmctruck30
 */

import server.model.items.UseItem;
import server.model.players.Client;
import server.model.players.PacketType;
import server.model.players.skills.Firemaking;

public class ItemOnItem implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int usedWithSlot = c.getInStream().readUnsignedWord();
		int itemUsedSlot = c.getInStream().readUnsignedWordA();
		int useWith = c.playerItems[usedWithSlot] - 1;
		int itemUsed = c.playerItems[itemUsedSlot] - 1;
                if (Firemaking.isLog(itemUsed) && Firemaking.isTinderbox(useWith)) {
				c.getFiremaking().lightFire(c, itemUsed, itemUsedSlot);
			}
                else if (Firemaking.isLog(useWith) && Firemaking.isTinderbox(itemUsed)) {
				c.getFiremaking().lightFire(c, useWith, c.getItems().getItemSlot(useWith));
			}
                else
                    UseItem.ItemonItem(c, itemUsed, useWith);
	}

}
