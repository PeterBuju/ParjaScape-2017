package server.model.players.skills;

import server.Config;
import server.model.players.Client;
import server.model.objects.Objects;
import server.Server;
import server.clip.region.Region;
/**
 * Firemaking.java
 *
 * @author Sanity
 *
 **/ 
public class Firemaking {
	
	private Client c;
	
	private int[] logs = {1511,1521,1519,1517,1515,1513};
	private int[] exp = {40,60,90,135,203,304};
	private int[] level = {1,15,30,45,60,75};	
	private int DELAY = 1250;
        private long lastLight;
	public boolean resetAnim = false;
        
        public enum Firemake {
		NORMAL(1511, 40, 1, 4266),
		OAK(1521, 60, 15, 4266),
		WILLOW(1519, 90, 30, 4266),
		MAPLE(1517, 135, 45, 4266),
		YEW(1515, 203, 60, 4266),
		MAGIC(1513, 304, 75, 4266);
		//RED(7404, 50, 1, x),
		//GREEN(7405, 50, 1, x),
		//BLUE(7406, 50, 1, x);

		int logID, xp, levelReq, obj;

		private Firemake(int logID, int xp, int levelReq, int obj) {
			this.logID = logID;
			this.xp = xp;
			this.levelReq = levelReq;
			this.obj = obj;
		}
	}
	
	public Firemaking(Client c) {
		this.c = c;
	}
	
	public void checkLogType(int logType, int otherItem) {
		for (int j = 0; j < logs.length;j++) {
			if (logs[j] == logType || logs[j] == otherItem) {
				lightFire(j);
				return;
			}
		}	
	}
        public static Firemake forLog(int id) {
		for (Firemake f : Firemake.values()) {
			if (f.logID == id) {
				return f;
			}
		}
		return null;
	}
	
	public static boolean isLog(int id) {
		return forLog(id) != null;
	}
        
        public static boolean isTinderbox(int id){
		if (id == 590 || id == 2946 || id == 7156 || id == 10497){ //All tinderbox Id's 2946 is a golden tinderbox :D
			return true;
		} else {
			return false;
		}
	}
	
	public void lightFire(int slot) {
		if (c.duelStatus >= 5) {
			c.sendMessage("Why am I trying to light a fire in the duel arena?");
			return;
		}	
		if (c.playerLevel[c.playerFiremaking] >= level[slot]) {
			if (c.getItems().playerHasItem(590) && c.getItems().playerHasItem(logs[slot])) {
				if (System.currentTimeMillis() - lastLight > DELAY) {
					c.startAnimation(733,0);
					c.getItems().deleteItem(logs[slot], c.getItems().getItemSlot(logs[slot]), 1);
					c.getPA().addSkillXP(logs[slot] * Config.FIREMAKING_EXPERIENCE, c.playerFiremaking);
					Objects fire = new Objects(2732,c.getX(),c.getY(), 0, -1, 10, 3);
					Objects fire2 = new Objects(-1,c.getX(),c.getY(), 0, -1, 10, 60);
					Server.objectHandler.addObject(fire);
					Server.objectHandler.addObject(fire2);
					c.sendMessage("You light the fire.");
					c.getPA().walkTo(-1,0);
					c.turnPlayerTo(c.getX() + 1, c.getY());
					this.lastLight = System.currentTimeMillis();
					//c.getPA().frame1();
					resetAnim = true;
				}
			}
		}	
	}
        
        public static void lightFire(Client c, int logID, int fromSlot) {
		Firemake f = forLog(logID);
		if (f != null) {
			if (System.currentTimeMillis() - c.lastLight > 2000) {
				if (c.playerLevel[c.playerFiremaking] >= f.levelReq) {
					if (c.getItems().playerHasItem(590) && c.getItems().playerHasItem(f.logID)) {
						c.lastLight = System.currentTimeMillis();
						c.getItems().deleteItem(f.logID, fromSlot, 1);
						c.getPA().addSkillXP(f.xp, c.playerFarming);
						c.getPA().object(f.obj, c.getX(), c.getY(), 0, 10);
						c.turnPlayerTo(c.getX() + 1, c.getY());
						/* Credits to Faris */
                                                
						if (Region.getClipping(c.getX() - 1, c.getY(), c.heightLevel, -1, 0)) {
							c.getPA().walkTo(-1, 0);
						} else if (Region.getClipping(c.getX() + 1, c.getY(), c.heightLevel, 1, 0)) {
							c.getPA().walkTo(1, 0);
						} else if (Region.getClipping(c.getX(), c.getY() - 1, c.heightLevel, 0, -1)) {
							c.getPA().walkTo(0, -1);
						} else if (Region.getClipping(c.getX(), c.getY() + 1, c.heightLevel, 0, 1)) {
							c.getPA().walkTo(0, 1);
						}
						/* Credits to Faris */
					}
				} else {
					c.sendMessage("You need a firemaking level of at least "+f.levelReq+" to burn this log.");
				}
			}
		}
	}
	
}