package server.model.players.skills;

import java.util.ArrayList;
import server.Config;
import server.model.players.Client;
import server.model.objects.Objects;
import server.Server;
import server.clip.region.Region;
import server.model.objects.Fire;
import static server.model.players.packets.Commands.SendSyntaxError;
import server.world.FireManager;
/**
 * Firemaking.java
 *
 * @author Sanity
 *
 **/ 
public class Firemaking {
	
	private Client c;
	
	private static int[] logs = {1511,1521,1519,1517,1515,1513};
	private static int[] exp = {40,60,90,135,203,304};
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

                        Firemake(int logID, int xp, int levelReq, int obj) {
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
					Objects fire = new Objects(2728,c.getX(),c.getY(), 0, -1, 10, 3);
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
                                            if (FireManager.AddFire(c, f.obj)){
						c.lastLight = System.currentTimeMillis();
						c.getItems().deleteItem(f.logID, fromSlot, 1);
						c.getPA().addSkillXP(f.xp * Config.FIREMAKING_EXPERIENCE, c.playerFiremaking);                                             
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
                                                c.startAnimation(733,0);
						/* Credits to Faris */
                                            }
                                            else{
                                                c.sendMessage("You have reached the maximum number of fires you can make!");
                                                c.sendMessage("Wait for one of your fires to disappear before creating another one!");
                                            }
					}
				} else {
					c.sendMessage("You need a firemaking level of at least "+f.levelReq+" to burn this log.");
				}
			}
		}
	}
        
        public static void ParseCommand(Client c, String command) {
        String[] args = command.split(" ");
        if (args[0].equalsIgnoreCase("help")) {
            if (args.length == 1) {
                c.sendMessage("Use ::firemaking.commands for a list of commands");
                c.sendMessage("Use ::firemaking.help <command> for help regarding a command");
            } else if (args.length == 2) {
                if (args[1].equalsIgnoreCase("showFire")) {
                    c.sendMessage("Usage: ::firemaking.showFire [<id>]");
                    c.sendMessage("Shows a list of current fires. If an id is specified, it shows the fires made by them");
                } else if (args[1].equalsIgnoreCase("extinguishFires")) {
                    c.sendMessage("Usage: ::firemaking.extinguishFires [<id>]");
                    c.sendMessage("Extinguish current fires. If an id is specified, it extinguishes the fires made by that player");
                } else if (args[1].equalsIgnoreCase("destroyFires")) {
                    c.sendMessage("Usage: ::firemaking.destroyFires [<id>]");
                    c.sendMessage("Destroys current fires. If an id is specified, it destroys the fires made by that player");
                } else if (args[1].equalsIgnoreCase("dieTime")) {
                    c.sendMessage("Usage: ::firemaking.dieTime [<seconds>]");
                    c.sendMessage("If no value is specified, it shows the current time before the fire dies.");
                    c.sendMessage("If a value is specified, it sets the time before a fire dies, in seconds");
                } else if (args[1].equalsIgnoreCase("disappearTime")) {
                    c.sendMessage("Usage: ::firemaking.disappearTime [<seconds>]");
                    c.sendMessage("If no value is specified, it shows the current time before the fire disappears.");
                    c.sendMessage("If a value is specified, it sets the time before a fire disappears, in seconds");
                } else if (args[1].equalsIgnoreCase("maximumFires")) {
                    c.sendMessage("Usage: ::firemaking.maximumFires [<value>]");
                    c.sendMessage("If no value is specified, it shows the maximum number of fires one player can have.");
                    c.sendMessage("If a value is specified, it sets the maximum number of fires one player can have");
                } else if (args[1].equalsIgnoreCase("allowReplaceFire")) {
                    c.sendMessage("Usage: ::firemaking.allowReplaceFire [<value>]");
                    c.sendMessage("If no value is specified, it says if replacing fires is allowed.");
                    c.sendMessage("If a value is specified, it sets if replacing fire is allowed");
                    c.sendMessage("Value must be a boolean value (True or t | False or f)");
                } else {
                    SendSyntaxError(c, "firemaking.help <command>");
                }
            } else {
                SendSyntaxError(c, "firemaking.help <command>");
            }
        } else if (args[0].equalsIgnoreCase("commands")) {
            c.sendMessage("Firemaking commands: ");
            c.sendMessage(" help");
            c.sendMessage(" showFire");
            c.sendMessage(" extinguishFires");
            c.sendMessage(" destroyFires");
            c.sendMessage(" dieTime");
            c.sendMessage(" disappearTime");
            c.sendMessage(" maximumFires");
            c.sendMessage(" allowReplaceFire");
        }else if (args[0].equalsIgnoreCase("showFire")) {
            if (args.length > 1) {
                try {
                    for (Fire fire : FireManager.fires) {
                        if (fire.GetPlayerId() == Integer.parseInt(args[1])){
                            c.sendMessage("Player ID: " + fire.GetPlayerId() + " X: " + fire.GetPosX() + " Y: " + fire.GetPosY() + " BurningTime: " + fire.GetBurningTime());
                        }
                    }
                } catch (Exception e) {
                    SendSyntaxError(c, "firemaking.showFire [<id>]");
                }
            } else {
                try {
                    for (Fire fire : FireManager.fires) {
                        c.sendMessage("Player ID: " + fire.GetPlayerId() + " X: " + fire.GetPosX() + " Y: " + fire.GetPosY() + " BurningTime: " + fire.GetBurningTime());
                    }
                } catch (Exception e) {
                    SendSyntaxError(c, "firemaking.showFire [<id>]");
                }
            }
        } else if (args[0].equalsIgnoreCase("extinguishFires")) {
            if (args.length > 1) {
                try {
                    for (Fire fire : FireManager.fires) {
                        if (fire.GetPlayerId() == Integer.parseInt(args[1])) {
                            if (fire.GetFireStatus()) {
                                FireManager.ExtinguishFire(fire);
                            }
                        }
                    }
                } catch (Exception e) {
                    SendSyntaxError(c, "firemaking.extinguishFires [<id>]");
                }
            } else {
                try {
                    for (Fire fire : FireManager.fires) {
                        if (fire.GetFireStatus()) {
                            FireManager.ExtinguishFire(fire);
                        }
                    }
                } catch (Exception e) {
                    SendSyntaxError(c, "firemaking.extinguishFires [<id>]");
                }
            }
        } else if (args[0].equalsIgnoreCase("destroyFires")) {
            ArrayList<Fire> toDelete = new ArrayList<>();
            if (args.length > 1) {
                try {
                    for (Fire fire : FireManager.fires) {
                        if (fire.GetPlayerId() == Integer.parseInt(args[1])) {
                            toDelete.add(fire);
                        }
                    }
                } catch (Exception e) {
                    SendSyntaxError(c, "firemaking.destroyFires [<id>]");
                }
            } else {
                try {
                    for (Fire fire : FireManager.fires) {
                        toDelete.add(fire);
                    }
                } catch (Exception e) {
                    SendSyntaxError(c, "firemaking.destroyFires [<id>]");
                }
            }
            for (Fire fire : toDelete) {
                FireManager.RemoveFire(fire);
            }
        } else if (args[0].equalsIgnoreCase("dieTime")) {
            if (args.length == 1) {
                c.sendMessage("dieTime: " + FireManager.timeToDie);
            } else if (args.length == 2) {
                try {
                    FireManager.timeToDie = Integer.parseInt(args[1]);
                    c.sendMessage("dieTime: " + FireManager.timeToDie);
                } catch (Exception e) {
                    SendSyntaxError(c, "firemaking.dieTime [<seconds>]");
                }
            } else {
                SendSyntaxError(c, "firemaking.dieTime [<seconds>]");
            }
        } else if (args[0].equalsIgnoreCase("disappearTime")) {
            if (args.length == 1) {
                c.sendMessage("disappearTime: " + FireManager.timeToDisappear);
            } else if (args.length == 2) {
                try {
                    FireManager.timeToDisappear = Integer.parseInt(args[1]);
                    c.sendMessage("disappearTime: " + FireManager.timeToDisappear);
                } catch (Exception e) {
                    SendSyntaxError(c, "firemaking.disappearTime [<seconds>]");
                }
            } else {
                SendSyntaxError(c, "firemaking.disappearTime [<seconds>]");
            }
        } else if (args[0].equalsIgnoreCase("maximumFires")) {
            if (args.length == 1) {
                c.sendMessage("maximumFires: " + FireManager.maxFires);
            } else if (args.length == 2) {
                try {
                    FireManager.maxFires = Integer.parseInt(args[1]);
                    c.sendMessage("maxFires: " + FireManager.maxFires);
                } catch (Exception e) {
                    SendSyntaxError(c, "firemaking.maxFires [<value>]");
                }
            } else {
                SendSyntaxError(c, "firemaking.maxFires [<value>]");
            }
        } else if (args[0].equalsIgnoreCase("allowReplaceFire")) {
            if (args.length == 1) {
                c.sendMessage("allowReplaceFire: " + FireManager.allowFireRemoval);
            } else if (args.length == 2) {
                try {
                    if (args[1].equalsIgnoreCase("t") || args[1].equalsIgnoreCase("true")){
                        FireManager.allowFireRemoval = true;
                    }
                    else if (args[1].equalsIgnoreCase("f") || args[1].equalsIgnoreCase("false")){
                        FireManager.allowFireRemoval = false;
                    }
                    c.sendMessage("allowReplaceFire: " + FireManager.allowFireRemoval);
                } catch (Exception e) {
                    SendSyntaxError(c, "firemaking.allowReplaceFire [<value>]");
                }
            } else {
                SendSyntaxError(c, "firemaking.allowReplaceFire [<value>]");
            }
        } else {
            SendSyntaxError(c, "firemaking.help");
        }
    }
}
