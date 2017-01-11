package server.model.players.packets;

import java.io.BufferedWriter;
import java.io.FileWriter;
import server.Config;
import server.Connection;
import server.Server;
import server.model.npcs.NPC;
import server.model.npcs.NPCGroup;
import server.model.npcs.NPCGroupHandler;
import server.model.npcs.NPCHandler;
import server.model.perks.Perk;
import server.model.perks.PerkHandler;
import server.model.players.Client;
import server.model.players.PacketType;
import server.model.players.Player;
import server.model.players.PlayerHandler;
import server.model.players.PlayerSave;
import server.model.players.Stats;
import server.util.Misc;
import server.world.WorldMap;


/**
 * Commands
 **/
public class Commands implements PacketType {

        public void SendSyntaxError(Client c, String message){
            c.sendMessage("Invalid syntax. Use the following syntax:");
            c.sendMessage(message);
        }
	
	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
	String playerCommand = c.getInStream().readString();
	if(Config.SERVER_DEBUG)
		Misc.println(c.playerName+" playerCommand: "+playerCommand);
		if (playerCommand.startsWith("/") && playerCommand.length() > 1) {
			if (c.clanId >= 0) {
				System.out.println(playerCommand);
				playerCommand = playerCommand.substring(1);
				Server.clanChat.playerMessageToClan(c.playerId, playerCommand, c.clanId);
			} else {
				if (c.clanId != -1)
					c.clanId = -1;
				c.sendMessage("You are not in a clan.");
			}
			return;
		}
		if(c.playerRights >= 0) {
                        if (playerCommand.equalsIgnoreCase("commands")){
                            c.sendMessage("List of commands:");
                            c.sendMessage("     help");
                            c.sendMessage("     sNpc");
                            c.sendMessage("     players");
                            c.sendMessage("     changepassword");
                            c.sendMessage("     ioi");
                            c.sendMessage("     coords");
                            c.sendMessage("     tele");
                            c.sendMessage("     teleS");
                            c.sendMessage("     teleN");
                            c.sendMessage("     teleE");
                            c.sendMessage("     teleW");
                            c.sendMessage("     teleD");
                            c.sendMessage("     teleU");
                            c.sendMessage("     emote");
                            c.sendMessage("     teleTo");
                            c.sendMessage("     restart");
                            c.sendMessage("     debugging");
                            c.sendMessage("     npcs");
                            c.sendMessage("     list");
                            c.sendMessage("     showinterface");
                            c.sendMessage("     stats");
                            c.sendMessage("     changestat");
                            c.sendMessage("     additem");
                            c.sendMessage("     perks");
                            c.sendMessage("     addperk");
                            c.sendMessage("     perklist");
                            c.sendMessage("     npcGroupList");
                        }
                        else if (playerCommand.toLowerCase().startsWith("help")){
                            String[] args = playerCommand.split(" ");
                            if (args.length == 1){
                                c.sendMessage("Use ::commands for a list of commands");
                                c.sendMessage("Use ::help <command name> for help regarding one command");
                            }
                            else if (args.length == 2){
                                if (args[1].equalsIgnoreCase("players")){
                                    c.sendMessage("Usage: ::players");
                                    c.sendMessage("Returns the number of active players");
                                }
                                else if (args[1].equalsIgnoreCase("changepassword")){
                                    c.sendMessage("Usage: ::changepassword <password>");
                                    c.sendMessage("Changes the current password with the one provided");
                                }
                                else if (args[1].equalsIgnoreCase("ioi")){
                                    c.sendMessage("Usage: ::ioi <id> <amount>");
                                    c.sendMessage("Gets the item of the provided id and amount");
                                }
                                else if (args[1].equalsIgnoreCase("coords")){
                                    c.sendMessage("Usage: ::coords");
                                    c.sendMessage("Returns the current coordinates");
                                }
                                else if (args[1].equalsIgnoreCase("tele")){
                                    c.sendMessage("Usage: ::tele <x> <y> [<height>]");
                                    c.sendMessage("Teleports the user to the provided coordinates");
                                }
                                else if (args[1].equalsIgnoreCase("teleN")){
                                    c.sendMessage("Usage: ::teleN [<distance>]");
                                    c.sendMessage("Teleports the player to the north. If no distance is specified, ");
                                    c.sendMessage("it will only teleport the player one step");
                                }
                                else if (args[1].equalsIgnoreCase("teleS")){
                                    c.sendMessage("Usage: ::teleS [<distance>]");
                                    c.sendMessage("Teleports the player to the south. If no distance is specified, ");
                                    c.sendMessage("it will only teleport the player one step");
                                }
                                else if (args[1].equalsIgnoreCase("teleE")){
                                    c.sendMessage("Usage: ::teleE [<distance>]");
                                    c.sendMessage("Teleports the player to the east. If no distance is specified, ");
                                    c.sendMessage("it will only teleport the player one step");
                                }
                                else if (args[1].equalsIgnoreCase("teleW")){
                                    c.sendMessage("Usage: ::teleW [<distance>]");
                                    c.sendMessage("Teleports the player to the west. If no distance is specified, ");
                                    c.sendMessage("it will only teleport the player one step");
                                }
                                else if (args[1].equalsIgnoreCase("teleU")){
                                    c.sendMessage("Usage: ::teleU [<distance>]");
                                    c.sendMessage("Teleports the player upwards. If no distance is specified, ");
                                    c.sendMessage("it will only teleport the player one step");
                                }
                                else if (args[1].equalsIgnoreCase("teleD")){
                                    c.sendMessage("Usage: ::teleD [<distance>]");
                                    c.sendMessage("Teleports the player downwards. If no distance is specified, ");
                                    c.sendMessage("it will only teleport the player one step");
                                }
                                else if (args[1].equalsIgnoreCase("emote")){
                                    c.sendMessage("Usage: ::emote <id> [<duration>]");
                                    c.sendMessage("Forces the player to do the specified emote. If a duration is specified,");
                                    c.sendMessage("it will do the emote after the duration expires");
                                }
                                else if (args[1].equalsIgnoreCase("teleTo")){
                                    c.sendMessage("Usage: ::teleTo <location>");
                                    c.sendMessage("Teleports the player to the location specified");
                                    c.sendMessage("For a list of available locations type ::teleTo locations");
                                }
                                else if (args[1].equalsIgnoreCase("restart")){
                                    c.sendMessage("Usage: ::restart");
                                    c.sendMessage("Restarts the server");
                                }
                                else if (args[1].equalsIgnoreCase("debugging")){
                                    c.sendMessage("Usage: ::debugging");
                                    c.sendMessage("Turns on/off debugging mode");
                                }
                                else if (args[1].equalsIgnoreCase("snpc")){
                                    c.sendMessage("Usage: ::sNpc <id> [-args]");
                                    c.sendMessage("Spawns an npc with the specified id.");
                                    c.sendMessage("Arguments can be used to further customize the npc");
                                    c.sendMessage("Use ::sNpc help for a help menu");
                                }
                                else if (args[1].equalsIgnoreCase("npcs")){
                                    c.sendMessage("Usage: ::npcs [<id>]");
                                    c.sendMessage("Lists the number of npcs currently spawned with a certain id if specified");
                                }
                                else if (args[1].equalsIgnoreCase("list")){
                                    c.sendMessage("Usage: ::list <argument> [<id>]");
                                    c.sendMessage("Lists all objects of argument type and of a certain id if specified");
                                    c.sendMessage("Use ::list help for a list of possible arguments");
                                }
                                else if (args[1].equalsIgnoreCase("showInterface")){
                                    c.sendMessage("Usage: ::showInterface <id>");
                                    c.sendMessage("Displays the interface with the certain id");
                                }
                                else if (args[1].equalsIgnoreCase("stats")){
                                    c.sendMessage("Usage: ::stats [<id>]");
                                    c.sendMessage("Shows the value of the stat specified by id or all the stats of the");
                                    c.sendMessage("current player");
                                }
                                else if (args[1].equalsIgnoreCase("changestat")){
                                    c.sendMessage("Usage: ::changestat <id> <val>");
                                    c.sendMessage("Finds the stat by id and sets the value with val");
                                }
                                else if (args[1].equalsIgnoreCase("additem")){
                                    c.sendMessage("Usage: ::additem <id> [<amount>]");
                                    c.sendMessage("Adds an item with the specified amount");
                                }
                                else if (args[1].equalsIgnoreCase("perks")){
                                    c.sendMessage("Usage: ::perks");
                                    c.sendMessage("Shows a list of all the perks on the current player");
                                }
                                else if (args[1].equalsIgnoreCase("perklist")){
                                    c.sendMessage("Usage: ::perklist");
                                    c.sendMessage("Shows a list of all available perks");
                                }
                                else if (args[1].equalsIgnoreCase("addperk")){
                                    c.sendMessage("Usage: ::addperk <id>");
                                    c.sendMessage("Adds a perk to the player");
                                }
                                else if (args[1].equalsIgnoreCase("npcgrouplist")){
                                    c.sendMessage("Usage: ::npcGroupList");
                                    c.sendMessage("Shows a list of groups of npcs");
                                }
                            }
                            else{
                                SendSyntaxError(c, "::help [<command name>]");
                            }
                        }
                        else if (playerCommand.equalsIgnoreCase("npcgrouplist")){
                            if (NPCGroupHandler.Groups.size() == 0){
                                c.sendMessage("No groups");
                            }
                            for (int i = 0; i <= NPCGroupHandler.Groups.size(); i++){
                                c.sendMessage(i + " : " + NPCGroupHandler.Groups.get(i).getName() + " - " + NPCGroupHandler.Groups.get(i).getAttackerId());
                            }                         
                        }
                        else if (playerCommand.split(" ")[0].equalsIgnoreCase("addperk")){
                            String[] args = playerCommand.split(" ");
                            try{
                            if (args.length != 2){
                                SendSyntaxError(c, "::addperk <id>");
                            }
                            else{
                                int id = Integer.parseInt(args[1]);
                                if (!c.perks.contains(PerkHandler.GetPerk(id)))
                                    c.perks.add(PerkHandler.GetPerk(id));
                                else
                                    c.sendMessage("Player already has the '" + PerkHandler.GetPerk(id).getName() + "' perk");
                            }
                            }
                            catch(Exception e){
                                SendSyntaxError(c, "::addperk <id>");
                            }
                            
                        }
                        else if (playerCommand.split(" ")[0].equalsIgnoreCase("perks")){
                            for (Perk perk : c.perks){
                                c.sendMessage(perk.getId() + " : " + perk.getName());
                            }
                        }
                        else if (playerCommand.split(" ")[0].equalsIgnoreCase("perklist")){
                            for (Perk perk : PerkHandler.perkList){
                                c.sendMessage(perk.getId() + " : " + perk.getName());
                            }
                        }
                        else if (playerCommand.split(" ")[0].equalsIgnoreCase("additem")){
                            String[] args = playerCommand.split(" ");
                            try{
                            if (args.length < 2 || args.length > 3){
                                SendSyntaxError(c, "::additem <id> [<amount>]");
                            }
                            else{
                                int amount = 1;
                                int id = Integer.parseInt(args[1]);
                                if (args.length == 3){
                                    amount = Integer.parseInt(args[2]);
                                }
                                c.getItems().addItem(id, amount);
                            }
                            }
                            catch(Exception e){
                                SendSyntaxError(c, "::additem <id> [<amount>]");
                            }
                            
                        }
                        else if (playerCommand.split(" ")[0].equalsIgnoreCase("stats")){
                            String[] args = playerCommand.split(" ");
                            if (args.length == 1)
                                for (int i = 0; i <= 6; i++){
                                    c.sendMessage(Stats.GetStatName(i) + ": " + c.stats[i]);
                                }
                            else{
                                try{
                                    c.sendMessage(Stats.GetStatName(Integer.parseInt(args[1])) + ": " + c.stats[Integer.parseInt(args[1])]);
                                }
                                catch (Exception e){
                                    SendSyntaxError(c, "::stats [<id>]");
                                }
                            }
                        }
                        else if (playerCommand.split(" ")[0].equalsIgnoreCase("changestat")){
                            String[] args = playerCommand.split(" ");
                            if (args.length == 3){
                                try{
                                    c.stats[Integer.parseInt(args[1])] = Integer.parseInt(args[2]);
                                    c.sendMessage("Changed " + Stats.GetStatName(Integer.parseInt(args[1])) + " to " + c.stats[Integer.parseInt(args[1])]);
                                    c.showStats();
                                }
                                catch(Exception e){
                                    SendSyntaxError(c, "::changestat <id> <val>");
                                }
                            }
                            else {
                                SendSyntaxError(c, "::changestat <id> <val>");
                            }
                        }
                        else if (playerCommand.split(" ")[0].equalsIgnoreCase("showInterface")){
                            String[] args = playerCommand.split(" ");
                            if (args.length != 2){
                                SendSyntaxError(c, "::showInterface <id>");
                            }
                            else{
                                int id = Integer.parseInt(args[1]);
                                c.customInterface(id);
                            }
                        }
                        else if (playerCommand.toLowerCase().startsWith("npcs")){
                            int npcs = 1;
                            while(NPCHandler.npcs[npcs] != null){
                                npcs++;
                            }
                            c.sendMessage(npcs + " npcs currently spawned");
                        }
                        else if (playerCommand.toLowerCase().startsWith("list")){                         
                            String[] args = playerCommand.split(" ");
                            if (args.length <= 1 || args.length > 3){
                                SendSyntaxError(c, "::list <argument> [<id>]");
                            }
                            else if(args[1].equalsIgnoreCase("help")){
                                c.sendMessage("Possible arguments: ");
                                c.sendMessage("     npcs");
                                c.sendMessage("     players");
                            }
                            else if(args[1].equalsIgnoreCase("npcs")){
                                if (args.length == 3){
                                    int id = Integer.parseInt(args[2]);
                                    for (NPC npc : NPCHandler.npcs){
                                        if (npc != null)
                                            if (npc.npcType == id)
                                                c.sendMessage("id: " + npc.npcType + " x: " + npc.absX + " y: " + npc.absY + " desc: " + npc.description);
                                    }
                                }
                                else
                                {
                                    for (NPC npc : NPCHandler.npcs){
                                        if (npc != null)
                                            c.sendMessage("id: " + npc.npcType + " x: " + npc.spawnX + " y: " + npc.spawnY + " desc: " + npc.description);
                                    }
                                }                               
                            }
                            else if(args[1].equalsIgnoreCase("players")){
                                if (args.length == 3){
                                    int id = Integer.parseInt(args[2]);
                                    for (int i = 0; i <= PlayerHandler.playerCount; i++){
                                        Player player = PlayerHandler.players[i];
                                        if (player != null)
                                            if (i == id)
                                                c.sendMessage("id: " + i + " name: " + player.playerName + " x: " + player.absX + " y: " + player.absY);
                                    }
                                }
                                else
                                {
                                    for (int i = 0; i <= PlayerHandler.playerCount; i++){
                                        Player player = PlayerHandler.players[i];
                                        if (player != null)
                                            c.sendMessage("id: " + i + " name: " + player.playerName + " x: " + player.absX + " y: " + player.absY);
                                    }
                                }                               
                            }
                        }
                        else if (playerCommand.toLowerCase().startsWith("snpc")){
                            String[] args = playerCommand.split(" ");
                            int height = c.heightLevel;
                            int x = c.absX;
                            int y = c.absY;
                            int npcType = 0;
                            int walkingType = 0;
                            int HP = 10;
                            int maxHit = 1;
                            int attack = 1;
                            int defense = 1;
                            String description = "";
                            boolean attackplayer = false;
                            boolean headicon = false;
                            boolean persistence = false;
                            boolean validcmd = true;
                            try{
                            if (args.length >= 2){                               
                                if (args[1].equalsIgnoreCase("help")){
                                    if (args.length < 3){//LIST ARGUMENTS
                                        c.sendMessage("List of arguments:");
                                        c.sendMessage("-x");
                                        c.sendMessage("-y");
                                        c.sendMessage("-h");
                                        c.sendMessage("-w");
                                        c.sendMessage("-hp");
                                        c.sendMessage("-mh");
                                        c.sendMessage("-a");
                                        c.sendMessage("-d");
                                        c.sendMessage("-ap");
                                        c.sendMessage("-hi");
                                        c.sendMessage("-p");
                                        c.sendMessage("-desc");
                                        c.sendMessage("Type ::snpc help <argument> for more information about one argument");
                                    }
                                    else{//HELP FOR ONE SPECIFIC ARGUMENT
                                        if (args[2].equalsIgnoreCase("-x")){
                                            c.sendMessage("Usage: -x <val>");
                                            c.sendMessage("-x sets the X coordinate of the npc");
                                            c.sendMessage("Val is an int value. It can take any numerical value");
                                        }
                                        else if (args[2].equalsIgnoreCase("-y")){
                                            c.sendMessage("Usage: -y <val>");
                                            c.sendMessage("-y sets the Y coordinate of the npc");
                                            c.sendMessage("Val is an int value. It can take any numerical value");
                                        }
                                        else if (args[2].equalsIgnoreCase("-h")){
                                            c.sendMessage("Usage: -h <val>");
                                            c.sendMessage("-h sets the HeightLevel of the npc");
                                            c.sendMessage("Val is an int value. It can take values from 0 to 3");
                                        }
                                        else if (args[2].equalsIgnoreCase("-w")){
                                            c.sendMessage("Usage: -w <val>");
                                            c.sendMessage("-w sets the WalkingType of the npc. 1 if the npc is walking and 0 if not");
                                            c.sendMessage("Val is an int value. It can take a value between 0 and 1");
                                        }
                                        else if (args[2].equalsIgnoreCase("-hp")){
                                            c.sendMessage("Usage: -hp <val>");
                                            c.sendMessage("-hp sets the HitPoints of the npc");
                                            c.sendMessage("Val is an int value. It can take any numerical value");
                                        }
                                        else if (args[2].equalsIgnoreCase("-mh")){
                                            c.sendMessage("Usage: -mh <val>");
                                            c.sendMessage("-mh sets the MaxHit value");
                                            c.sendMessage("Val is an int value. It can take any numerical value");
                                        }
                                        else if (args[2].equalsIgnoreCase("-a")){
                                            c.sendMessage("Usage: -a <val>");
                                            c.sendMessage("-a sets the Attack level of the npc");
                                            c.sendMessage("Val is an int value. It can take any numerical value");
                                        }
                                        else if (args[2].equalsIgnoreCase("-d")){
                                            c.sendMessage("Usage: -d <val>");
                                            c.sendMessage("-d sets the Defence level of the npc");
                                            c.sendMessage("Val is an int value. It can take any numerical value");                                        
                                        }
                                        else if (args[2].equalsIgnoreCase("-ap")){
                                            c.sendMessage("Usage: -ap <val>");
                                            c.sendMessage("-ap sets the AttackPlayer value. If true, the npc will attack the player, otherwise not");
                                            c.sendMessage("Val is an boolean value. It can be either true or false(t or f)");
                                        }
                                        else if (args[2].equalsIgnoreCase("-hi")){
                                            c.sendMessage("Usage: -hi <val>");
                                            c.sendMessage("-hi sets the HeadIcon value");
                                            c.sendMessage("Val is an boolean value. It can be either true or false(t or f)");
                                        }
                                        else if (args[2].equalsIgnoreCase("-p")){
                                            c.sendMessage("Usage: -p <val>");
                                            c.sendMessage("-p sets the persistence. If true, the npc will be saved in the config");
                                            c.sendMessage("Val is an boolean value. It can be either true or false(t or f)");
                                        }
                                        else if (args[2].equalsIgnoreCase("-desc")){
                                            c.sendMessage("Usage: -desc <val>");
                                            c.sendMessage("-desc sets the description of the npc. The description of the character is written in the");
                                            c.sendMessage("spawn-config.cfg file");
                                            c.sendMessage("Val is a string value. It can be whatever characters excluding the space character");
                                        }
                                    }
                                }
                                else{//HANDLE ARGUMENTS
                                    npcType = Integer.parseInt(args[1]);
                                    for (int i = 2; i < args.length; i++){
                                        if (Config.SERVER_DEBUG)
                                            c.sendMessage(args[i] + " " + args[i+1]);
                                        if (args[i].equalsIgnoreCase("-x")){
                                            i++;
                                            x = Integer.parseInt(args[i]);
                                        }
                                        else if (args[i].equalsIgnoreCase("-y")){
                                            i++;
                                            y = Integer.parseInt(args[i]);
                                        }
                                        else if (args[i].equalsIgnoreCase("-h")){
                                            i++;
                                            height = Integer.parseInt(args[i]);
                                        }
                                        else if (args[i].equalsIgnoreCase("-w")){
                                            i++;
                                            walkingType = Integer.parseInt(args[i]);
                                        }
                                        else if (args[i].equalsIgnoreCase("-hp")){
                                            i++;
                                            HP = Integer.parseInt(args[i]);
                                        }
                                        else if (args[i].equalsIgnoreCase("-mh")){
                                            i++;
                                            maxHit = Integer.parseInt(args[i]);
                                        }
                                        else if (args[i].equalsIgnoreCase("-a")){
                                            i++;
                                            attack = Integer.parseInt(args[i]);
                                        }
                                        else if (args[i].equalsIgnoreCase("-d")){
                                            i++;
                                            defense = Integer.parseInt(args[i]);
                                        }
                                        else if (args[i].equalsIgnoreCase("-ap")){
                                            i++;
                                            if (args[i].equalsIgnoreCase("true")|| args[i].equalsIgnoreCase("t") || args[i].equalsIgnoreCase("1")){
                                                attackplayer = true;
                                            }
                                            else{
                                                attackplayer = false;
                                            }
                                        }
                                        else if (args[i].equalsIgnoreCase("-hi")){
                                            i++;
                                            if (args[i].equalsIgnoreCase("true")|| args[i].equalsIgnoreCase("t") || args[i].equalsIgnoreCase("1")){
                                                headicon = true;
                                            }
                                            else{
                                                headicon = false;
                                            }
                                        }
                                        else if (args[i].equalsIgnoreCase("-p")){
                                            i++;
                                            if (args[i].equalsIgnoreCase("true")|| args[i].equalsIgnoreCase("t") || args[i].equalsIgnoreCase("1")){
                                                persistence = true;
                                            }
                                            else{
                                                persistence = false;
                                            }
                                        }
                                        else if (args[i].equalsIgnoreCase("-desc")){
                                            if (Config.SERVER_DEBUG)
                                                c.sendMessage("Setting desccription");
                                            i++;
                                            description = args[i];
                                            if (Config.SERVER_DEBUG){
                                                c.sendMessage("Set description");
                                            }
                                        }
                                        else{
                                            validcmd = false;
                                            break;
                                        }
                                    }
                                }
                            }
                            }
                            catch(Exception e){
                                SendSyntaxError(c, "sNpc <id> [-args]");
                                validcmd = false;
                            }
                            if (Config.SERVER_DEBUG){
                                c.sendMessage("validcmd: " + validcmd);
                            }
                            if (npcType != 0 && validcmd){
                                Server.npcHandler.spawnNpc(c, npcType, x, y, height, walkingType, HP, maxHit, attack, defense, attackplayer, headicon);
                                if (persistence){
                                    try{
                                    BufferedWriter bf = new BufferedWriter(new FileWriter("./Data/cfg/spawn-config.cfg", true));
                                    bf.newLine();
                                    bf.write("\nspawn = " + npcType +   "	" + x +"	" + y + "	" + height + "	" + walkingType +"	" + maxHit + "	" + attack + "	" + defense + "	" + description);
                                    bf.close();
                                    }
                                    catch(Exception e){
                                        if (Config.SERVER_DEBUG)    
                                            c.sendMessage("Error writing to file");
                                    }
                                }
                            }
                        }
                        else if (playerCommand.equalsIgnoreCase("players")) {
				c.sendMessage("There are currently "+PlayerHandler.getPlayerCount()+ " players online.");
			}
                        else if (playerCommand.startsWith("shop")) {
				c.getShops().openShop(Integer.parseInt(playerCommand.substring(5)));
			}
                        else if (playerCommand.startsWith("changepassword") && playerCommand.length() > 15) {
				c.playerPass = playerCommand.substring(15);
				c.sendMessage("Your password is now: " + c.playerPass);			
			}
                        
                        else if (playerCommand.equalsIgnoreCase("debugging")){
                            Config.SERVER_DEBUG = !Config.SERVER_DEBUG;
                            c.sendMessage("Debugging is " + Config.SERVER_DEBUG);
                        }
                        
                        else if (playerCommand.equalsIgnoreCase("restart")){
                            for(Player p : PlayerHandler.players) {
                            if(p == null)
                                continue;	
                            PlayerSave.saveGame((Client)p);
                            }
                            System.exit(0);
                        }
                        else if (playerCommand.startsWith("ioi")) {
				String[] args = playerCommand.split(" ");
                                if (args.length == 3)
                                    c.getItems().itemOnInterface(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
                                else
                                    SendSyntaxError(c, "ioi <id> <amount>");
			}
			
                        else if (playerCommand.startsWith("coords")){
                            c.sendMessage("X: " + c.absX + " Y: " + c.absY + "Z: " + c.heightLevel);
                        }
			
			/*if (playerCommand.startsWith("setlevel")) {
				if (c.inWild())
					return;
				for (int j = 0; j < c.playerEquipment.length; j++) {
					if (c.playerEquipment[j] > 0) {
						c.sendMessage("Take off your shit idiot..");
						return;
					}
				}
				try {
				String[] args = playerCommand.split(" ");
				int skill = Integer.parseInt(args[1]);
				int level = Integer.parseInt(args[2]);
				if (level > 99)
					level = 99;
				else if (level < 0)
					level = 1;
				c.playerXP[skill] = c.getPA().getXPForLevel(level)+5;
				c.playerLevel[skill] = c.getPA().getLevelForXP(c.playerXP[skill]);
				c.getPA().refreshSkill(skill);
				} catch (Exception e){}
			}
			if (playerCommand.equals("spec")) {
				if (!c.inWild())
					c.specAmount = 10.0;
			}
			if (playerCommand.startsWith("object")) {
				String[] args = playerCommand.split(" ");				
				c.getPA().object(Integer.parseInt(args[1]), c.absX, c.absY, 0, 10);
			}
			if (playerCommand.equals("gwd")) {
				c.getPA().movePlayer(2905, 3611, 4);			
			}
			if (playerCommand.equals("gwd2")) {
				c.getPA().movePlayer(2905, 3611, 8);			
			}
			if (playerCommand.equals("gwd3")) {
				c.getPA().movePlayer(2905, 3611, 12);			
			}
			*/
                        else if (playerCommand.split(" ")[0].equalsIgnoreCase("tele")) {
				String[] arg = playerCommand.split(" ");
                                if (arg[0].equalsIgnoreCase("tele")){
				if (arg.length > 3)
					c.getPA().movePlayer(Integer.parseInt(arg[1]),Integer.parseInt(arg[2]),Integer.parseInt(arg[3]));
				else if (arg.length == 3)
					c.getPA().movePlayer(Integer.parseInt(arg[1]),Integer.parseInt(arg[2]),c.heightLevel);
                                else{
                                    SendSyntaxError(c, "tele <x> <y> [<z>]");
                                }
                                }
			}
                        else if (playerCommand.split(" ")[0].equalsIgnoreCase("telen")){ //TELEPORTS PLAYER TO THE NORTH
                            String[] arg = playerCommand.split(" ");
                            int steps = 1;
				if (arg.length > 1)
                                {
                                    steps = Integer.parseInt(arg[1]);
                                }
                                c.getPA().movePlayer(c.absX, c.absY + steps, c.heightLevel);
                        }
                        else if (playerCommand.split(" ")[0].equalsIgnoreCase("teles")){ //TELEPORTS PLAYER TO THE NORTH
                            String[] arg = playerCommand.split(" ");
                            int steps = 1;
				if (arg.length > 1)
                                {
                                    steps = Integer.parseInt(arg[1]);
                                }
                                c.getPA().movePlayer(c.absX, c.absY - steps, c.heightLevel);
                        }
                        else if (playerCommand.split(" ")[0].equalsIgnoreCase("telee")){ //TELEPORTS PLAYER TO THE NORTH
                            String[] arg = playerCommand.split(" ");
                            int steps = 1;
				if (arg.length > 1)
                                {
                                    steps = Integer.parseInt(arg[1]);
                                }
                                c.getPA().movePlayer(c.absX + steps, c.absY, c.heightLevel);
                        }
                        else if (playerCommand.split(" ")[0].equalsIgnoreCase("telew")){ //TELEPORTS PLAYER TO THE NORTH
                            String[] arg = playerCommand.split(" ");
                            int steps = 1;
				if (arg.length > 1)
                                {
                                    steps = Integer.parseInt(arg[1]);
                                }
                                c.getPA().movePlayer(c.absX - steps, c.absY, c.heightLevel);
                        }
                        else if (playerCommand.split(" ")[0].equalsIgnoreCase("teled")){ //TELEPORTS PLAYER TO THE NORTH
                            String[] arg = playerCommand.split(" ");
                            int steps = 1;
				if (arg.length > 1)
                                {
                                    steps = Integer.parseInt(arg[1]);
                                }
                                c.getPA().movePlayer(c.absX, c.absY, c.heightLevel - steps);
                        }
                        else if (playerCommand.split(" ")[0].equalsIgnoreCase("teleu")){ //TELEPORTS PLAYER TO THE NORTH
                            String[] arg = playerCommand.split(" ");
                            int steps = 1;
				if (arg.length > 1)
                                {
                                    steps = Integer.parseInt(arg[1]);
                                }
                                c.getPA().movePlayer(c.absX, c.absY, c.heightLevel + steps);
                        }
                        else if (playerCommand.startsWith("emote")){
                            String[] arg = playerCommand.split(" ");
                            if (arg.length > 2){
                                c.startAnimation(Integer.parseInt(arg[1]), Integer.parseInt(arg[2]));
                            }else if(arg.length == 2)
                            {
                                c.startAnimation(Integer.parseInt(arg[1]));
                            }
                            else
                            {
                                SendSyntaxError(c, "emote <id> [<duration>]");
                            }
                        }
                        else if (playerCommand.split(" ")[0].equalsIgnoreCase("teleto")){
                            String[] arg = playerCommand.split(" ");
                            if (arg.length >= 2){
                                if (arg[1].equalsIgnoreCase("locations")){
                                    c.sendMessage("List of available locations:");
                                    c.sendMessage("     StartLocation");
                                    c.sendMessage("     EdgeVille");
                                    c.sendMessage("     Lumbridge");
                                    c.sendMessage("     Fallador");
                                    c.sendMessage("     Ardougne");
                                    c.sendMessage("     AlKharid");
                                    c.sendMessage("     Camelot");
                                    c.sendMessage("     Annakarl");
                                    c.sendMessage("     Carrallangar");
                                    c.sendMessage("     Dareeyak");
                                    c.sendMessage("     Ghorrock");
                                    c.sendMessage("     Karamja");
                                    c.sendMessage("     Kharyrll");
                                    c.sendMessage("     Lassar");
                                    c.sendMessage("     Paddewwa");
                                    c.sendMessage("     Senntisten");
                                    c.sendMessage("     Trollheim");
                                    c.sendMessage("     Varrock");
                                    c.sendMessage("     WatchTower");
                                }
                                else if (arg[1].equalsIgnoreCase("startlocation")){
                                    c.getPA().movePlayer(Config.START_LOCATION_X, Config.START_LOCATION_Y, 1);
                                }
                                else if (arg[1].equalsIgnoreCase("edgeville")){
                                    c.getPA().movePlayer(Config.EDGEVILLE_X, Config.EDGEVILLE_Y, 0);
                                }
                                else if (arg[1].equalsIgnoreCase("lumbridge")){
                                    c.getPA().movePlayer(Config.LUMBY_X, Config.LUMBY_Y, 0);
                                }
                                else if (arg[1].equalsIgnoreCase("fallador")){
                                    c.getPA().movePlayer(Config.FALADOR_X, Config.FALADOR_Y, 0);
                                }
                                else if (arg[1].equalsIgnoreCase("ardougne")){
                                    c.getPA().movePlayer(Config.ARDOUGNE_X, Config.ARDOUGNE_Y, 0);
                                }
                                else if (arg[1].equalsIgnoreCase("alkharid")){
                                    c.getPA().movePlayer(Config.AL_KHARID_X, Config.AL_KHARID_Y, 0);
                                }
                                else if (arg[1].equalsIgnoreCase("camelot")){
                                    c.getPA().movePlayer(Config.CAMELOT_X, Config.CAMELOT_Y, 0);
                                }
                                else if (arg[1].equalsIgnoreCase("annakarl")){
                                    c.getPA().movePlayer(Config.ANNAKARL_X, Config.ANNAKARL_Y, 0);
                                }
                                else if (arg[1].equalsIgnoreCase("carrallangar")){
                                    c.getPA().movePlayer(Config.CARRALLANGAR_X, Config.CARRALLANGAR_Y, 0);
                                }
                                else if (arg[1].equalsIgnoreCase("dareeyak")){
                                    c.getPA().movePlayer(Config.DAREEYAK_X, Config.DAREEYAK_Y, 0);
                                }
                                else if (arg[1].equalsIgnoreCase("ghorrock")){
                                    c.getPA().movePlayer(Config.GHORROCK_X, Config.GHORROCK_Y, 0);
                                }
                                else if (arg[1].equalsIgnoreCase("karamja")){
                                    c.getPA().movePlayer(Config.KARAMJA_X, Config.KARAMJA_Y, 0);
                                }
                                else if (arg[1].equalsIgnoreCase("kharyrll")){
                                    c.getPA().movePlayer(Config.KHARYRLL_X, Config.KHARYRLL_Y, 0);
                                }
                                else if (arg[1].equalsIgnoreCase("lassar")){
                                    c.getPA().movePlayer(Config.LASSAR_X, Config.LASSAR_Y, 0);
                                }
                                else if (arg[1].equalsIgnoreCase("paddewwa")){
                                    c.getPA().movePlayer(Config.PADDEWWA_X, Config.PADDEWWA_Y, 0);
                                }
                                else if (arg[1].equalsIgnoreCase("senntisten")){
                                    c.getPA().movePlayer(Config.SENNTISTEN_X, Config.SENNTISTEN_Y, 0);
                                }
                                else if (arg[1].equalsIgnoreCase("trollheim")){
                                    c.getPA().movePlayer(Config.TROLLHEIM_X, Config.TROLLHEIM_Y, 0);
                                }
                                else if (arg[1].equalsIgnoreCase("varrock")){
                                    c.getPA().movePlayer(Config.VARROCK_X, Config.VARROCK_Y, 0);
                                }
                                else if (arg[1].equalsIgnoreCase("watchtower")){
                                    c.getPA().movePlayer(Config.WATCHTOWER_X, Config.WATCHTOWER_Y, 0);
                                }
                            }
                            else{
                                SendSyntaxError(c, "teleTo <Location>");
                            }
                        }
			/*
			if (playerCommand.equalsIgnoreCase("mypos")) {
				c.sendMessage("X: "+c.absX);
				c.sendMessage("Y: "+c.absY);
			}
			if (playerCommand.startsWith("item")) {
				if (c.inWild())
					return;
				try {
				String[] args = playerCommand.split(" ");
				if (args.length == 3) {
					int newItemID = Integer.parseInt(args[1]);
					int newItemAmount = Integer.parseInt(args[2]);
					if ((newItemID <= 20000) && (newItemID >= 0)) {
						c.getItems().addItem(newItemID, newItemAmount);
						System.out.println("Spawned: " + newItemID + " by: " + c.playerName);
					} else {
						c.sendMessage("No such item.");
					}
				} else {
					c.sendMessage("Use as ::item 995 200");
				}
				} catch (Exception e) {
				
				}*/
			}
			
		
		
		
		if(c.playerRights >= 3) {
			
			/*if (playerCommand.startsWith("task")) {
				c.taskAmount = -1;
				c.slayerTask = 0;
			}
			
			if (playerCommand.startsWith("starter")) {
				c.getDH().sendDialogues(100, 945);			
			}*/
			if (playerCommand.equalsIgnoreCase("mypos")) {
				c.sendMessage("X: "+c.absX);
				c.sendMessage("Y: "+c.absY);
			}
			if (playerCommand.startsWith("reloaddrops")) {
				Server.npcDrops = null;
				Server.npcDrops = new server.model.npcs.NPCDrops();
				/*for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client)Server.playerHandler.players[j];
						c2.sendMessage("[" + c.playerName + "] " + "NPC Drops have been reloaded.");
					}
				}*/
			}
			
			if (playerCommand.startsWith("sanity")) {
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client)Server.playerHandler.players[j];
						c2.sendMessage("[" + c.playerName + "]: " + playerCommand.substring(7));
					}
				}
			}
			if (playerCommand.startsWith("reloadshops")) {
				Server.shopHandler = new server.world.ShopHandler();
			}
			
			if (playerCommand.startsWith("fakels")) {
				int item = Integer.parseInt(playerCommand.split(" ")[1]);
				Server.clanChat.handleLootShare(c, item, 1);
			}
			
			if (playerCommand.startsWith("interface")) {
				String[] args = playerCommand.split(" ");
				c.getPA().showInterface(Integer.parseInt(args[1]));
			}
			if (playerCommand.startsWith("gfx")) {
				String[] args = playerCommand.split(" ");
				c.gfx0(Integer.parseInt(args[1]));
			}
			if (playerCommand.startsWith("update")) {
				String[] args = playerCommand.split(" ");
				int a = Integer.parseInt(args[1]);
				PlayerHandler.updateSeconds = a;
				PlayerHandler.updateAnnounced = false;
				PlayerHandler.updateRunning = true;
				PlayerHandler.updateStartTime = System.currentTimeMillis();
			}
			
			/*if (playerCommand.startsWith("item") && c.playerName.equalsIgnoreCase("Sanity")) {
				try {
					String[] args = playerCommand.split(" ");
					if (args.length == 3) {
						int newItemID = Integer.parseInt(args[1]);
						int newItemAmount = Integer.parseInt(args[2]);
						if ((newItemID <= 20000) && (newItemID >= 0)) {
							c.getItems().addItem(newItemID, newItemAmount);		
						} else {
							c.sendMessage("No such item.");
						}
					} else {
						c.sendMessage("Use as ::pickup 995 200");
					}
				} catch(Exception e) {
					
				}
			}*/
			
			if (playerCommand.equals("Vote")) {
				for (int j = 0; j < Server.playerHandler.players.length; j++)
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client)Server.playerHandler.players[j];
						c2.getPA().sendFrame126("www.google.ca", 12000);
					}
			}


			if (playerCommand.equalsIgnoreCase("debug")) {
				Server.playerExecuted = true;
			}
			
			if (playerCommand.startsWith("interface")) {
				try {	
					String[] args = playerCommand.split(" ");
					int a = Integer.parseInt(args[1]);
					c.getPA().showInterface(a);
				} catch(Exception e) {
					c.sendMessage("::interface ####"); 
				}
			}
			
			if(playerCommand.startsWith("www")) {
				c.getPA().sendFrame126(playerCommand,0);			
			}
			

		
			
			
			
			if (playerCommand.startsWith("xteleto")) {
				String name = playerCommand.substring(8);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (Server.playerHandler.players[i] != null) {
						if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(name)) {
							c.getPA().movePlayer(Server.playerHandler.players[i].getX(), Server.playerHandler.players[i].getY(), Server.playerHandler.players[i].heightLevel);
						}
					}
				}			
			}
			

			
			if(playerCommand.startsWith("npc") && c.playerName.equalsIgnoreCase("Sanity")) {
				try {
					int newNPC = Integer.parseInt(playerCommand.substring(4));
					if(newNPC > 0) {
						Server.npcHandler.spawnNpc(c, newNPC, c.absX, c.absY, 0, 0, 120, 7, 70, 70, false, false);
						c.sendMessage("You spawn a Npc.");
					} else {
						c.sendMessage("No such NPC.");
					}
				} catch(Exception e) {
					
				}			
			}
			
			
			if (playerCommand.startsWith("ipban")) { // use as ::ipban name
				try {
					String playerToBan = playerCommand.substring(6);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
								Connection.addIpToBanList(Server.playerHandler.players[i].connectedFrom);
								Connection.addIpToFile(Server.playerHandler.players[i].connectedFrom);
								c.sendMessage("You have IP banned the user: "+Server.playerHandler.players[i].playerName+" with the host: "+Server.playerHandler.players[i].connectedFrom);
								Server.playerHandler.players[i].disconnected = true;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			
			if (playerCommand.startsWith("ban") && playerCommand.charAt(3) == ' ') { // use as ::ban name
				try {	
					String playerToBan = playerCommand.substring(4);
					Connection.addNameToBanList(playerToBan);
					Connection.addNameToFile(playerToBan);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
								Server.playerHandler.players[i].disconnected = true;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			
			if (playerCommand.startsWith("unban")) {
				try {	
					String playerToBan = playerCommand.substring(6);
					Connection.removeNameFromBanList(playerToBan);
					c.sendMessage(playerToBan + " has been unbanned.");
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.startsWith("anim")) {
				String[] args = playerCommand.split(" ");
				c.startAnimation(Integer.parseInt(args[1]));
				c.getPA().requestUpdates();
			}
			
			if (playerCommand.startsWith("mute")) {
				try {	
					String playerToBan = playerCommand.substring(5);
					Connection.addNameToMuteList(playerToBan);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
								Client c2 = (Client)Server.playerHandler.players[i];
								c2.sendMessage("You have been muted by: " + c.playerName);
								break;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}			
			}
			if (playerCommand.startsWith("ipmute")) {
				try {	
					String playerToBan = playerCommand.substring(7);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
								Connection.addIpToMuteList(Server.playerHandler.players[i].connectedFrom);
								c.sendMessage("You have IP Muted the user: "+Server.playerHandler.players[i].playerName);
								Client c2 = (Client)Server.playerHandler.players[i];
								c2.sendMessage("You have been muted by: " + c.playerName);
								break;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}			
			}
			if (playerCommand.startsWith("unipmute")) {
				try {	
					String playerToBan = playerCommand.substring(9);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
								Connection.unIPMuteUser(Server.playerHandler.players[i].connectedFrom);
								c.sendMessage("You have Un Ip-Muted the user: "+Server.playerHandler.players[i].playerName);
								break;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}			
			}
			if (playerCommand.startsWith("unmute")) {
				try {	
					String playerToBan = playerCommand.substring(7);
					Connection.unMuteUser(playerToBan);
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}			
			}

		}
	}
}
		
		
		
		
		
		
		

