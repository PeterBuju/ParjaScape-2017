/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model.players.packets;
import java.io.BufferedWriter;
import java.io.FileWriter;
import server.Config;
import server.Server;
import server.model.npcs.NPCGroupHandler;
import server.model.npcs.NPCHandler;
import server.model.players.Client;
import static server.model.players.packets.Commands.SendSyntaxError;
/**
 *
 * @author Alex Macocian
 */
public class NpcCommands {
    public static void ParseCommand(Client c, String command){
        String[] args = command.split(" ");
        if (args[0].equalsIgnoreCase("help")) {
            if (args.length == 1) {
                c.sendMessage("Use ::npc.commands for a list of commands");
                c.sendMessage("Use ::npc.help <command> for help regarding a command");
            } else if (args.length == 2) {
                if (args[1].equalsIgnoreCase("spawn")) {
                    c.sendMessage("Usage: ::npc.spawn <id> [-args]");
                    c.sendMessage("Spawns an npc with the specified id.");
                    c.sendMessage("Arguments can be used to further customize the npc");
                    c.sendMessage("Use ::npc.spawn help for a help menu");
                } else if (args[1].equalsIgnoreCase("npcs")) {
                    c.sendMessage("Usage: ::npc.npcs [<id>]");
                    c.sendMessage("Lists the number of npcs currently spawned with a certain id if specified");
                } else if (args[1].equalsIgnoreCase("showgroup")) {
                    c.sendMessage("Usage: ::npc.showGroup");
                    c.sendMessage("Shows a list of groups of npcs");
                } else {
                    SendSyntaxError(c, "npc.help <command>");
                }
            }
        } else if (args[0].equalsIgnoreCase("commands")) {
            c.sendMessage("Npc commands: ");
            c.sendMessage(" help");
            c.sendMessage(" npcs");
            c.sendMessage(" spawn");
            c.sendMessage(" showGroup");
        } else if (args[0].equalsIgnoreCase("showGroup")) {
            if (NPCGroupHandler.Groups.size() == 0) {
                c.sendMessage("No groups");
            }
            for (int i = 0; i <= NPCGroupHandler.Groups.size(); i++) {
                c.sendMessage(i + " : " + NPCGroupHandler.Groups.get(i).getName() + " - " + NPCGroupHandler.Groups.get(i).getAttackerId());
            }
        } else if (args[0].equalsIgnoreCase("npcs")) {
            int npcs = 1;
            while (NPCHandler.npcs[npcs] != null) {
                npcs++;
            }
            c.sendMessage(npcs + " npcs currently spawned");
        } else if (args[0].equalsIgnoreCase("spawn")) {
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
            try {
                if (args.length >= 2) {
                    if (args[1].equalsIgnoreCase("help")) {
                        if (args.length < 3) {//LIST ARGUMENTS
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
                            c.sendMessage("Type ::npc.spawn help <argument> for more information about one argument");
                        } else {//HELP FOR ONE SPECIFIC ARGUMENT
                            if (args[2].equalsIgnoreCase("-x")) {
                                c.sendMessage("Usage: -x <val>");
                                c.sendMessage("-x sets the X coordinate of the npc");
                                c.sendMessage("Val is an int value. It can take any numerical value");
                            } else if (args[2].equalsIgnoreCase("-y")) {
                                c.sendMessage("Usage: -y <val>");
                                c.sendMessage("-y sets the Y coordinate of the npc");
                                c.sendMessage("Val is an int value. It can take any numerical value");
                            } else if (args[2].equalsIgnoreCase("-h")) {
                                c.sendMessage("Usage: -h <val>");
                                c.sendMessage("-h sets the HeightLevel of the npc");
                                c.sendMessage("Val is an int value. It can take values from 0 to 3");
                            } else if (args[2].equalsIgnoreCase("-w")) {
                                c.sendMessage("Usage: -w <val>");
                                c.sendMessage("-w sets the WalkingType of the npc. 1 if the npc is walking and 0 if not");
                                c.sendMessage("Val is an int value. It can take a value between 0 and 1");
                            } else if (args[2].equalsIgnoreCase("-hp")) {
                                c.sendMessage("Usage: -hp <val>");
                                c.sendMessage("-hp sets the HitPoints of the npc");
                                c.sendMessage("Val is an int value. It can take any numerical value");
                            } else if (args[2].equalsIgnoreCase("-mh")) {
                                c.sendMessage("Usage: -mh <val>");
                                c.sendMessage("-mh sets the MaxHit value");
                                c.sendMessage("Val is an int value. It can take any numerical value");
                            } else if (args[2].equalsIgnoreCase("-a")) {
                                c.sendMessage("Usage: -a <val>");
                                c.sendMessage("-a sets the Attack level of the npc");
                                c.sendMessage("Val is an int value. It can take any numerical value");
                            } else if (args[2].equalsIgnoreCase("-d")) {
                                c.sendMessage("Usage: -d <val>");
                                c.sendMessage("-d sets the Defence level of the npc");
                                c.sendMessage("Val is an int value. It can take any numerical value");
                            } else if (args[2].equalsIgnoreCase("-ap")) {
                                c.sendMessage("Usage: -ap <val>");
                                c.sendMessage("-ap sets the AttackPlayer value. If true, the npc will attack the player, otherwise not");
                                c.sendMessage("Val is an boolean value. It can be either true or false(t or f)");
                            } else if (args[2].equalsIgnoreCase("-hi")) {
                                c.sendMessage("Usage: -hi <val>");
                                c.sendMessage("-hi sets the HeadIcon value");
                                c.sendMessage("Val is an boolean value. It can be either true or false(t or f)");
                            } else if (args[2].equalsIgnoreCase("-p")) {
                                c.sendMessage("Usage: -p <val>");
                                c.sendMessage("-p sets the persistence. If true, the npc will be saved in the config");
                                c.sendMessage("Val is an boolean value. It can be either true or false(t or f)");
                            } else if (args[2].equalsIgnoreCase("-desc")) {
                                c.sendMessage("Usage: -desc <val>");
                                c.sendMessage("-desc sets the description of the npc. The description of the character is written in the");
                                c.sendMessage("spawn-config.cfg file");
                                c.sendMessage("Val is a string value. It can be whatever characters excluding the space character");
                            }
                        }
                    } else {//HANDLE ARGUMENTS
                        npcType = Integer.parseInt(args[1]);
                        for (int i = 2; i < args.length; i++) {
                            if (Config.SERVER_DEBUG) {
                                c.sendMessage(args[i] + " " + args[i + 1]);
                            }
                            if (args[i].equalsIgnoreCase("-x")) {
                                i++;
                                x = Integer.parseInt(args[i]);
                            } else if (args[i].equalsIgnoreCase("-y")) {
                                i++;
                                y = Integer.parseInt(args[i]);
                            } else if (args[i].equalsIgnoreCase("-h")) {
                                i++;
                                height = Integer.parseInt(args[i]);
                            } else if (args[i].equalsIgnoreCase("-w")) {
                                i++;
                                walkingType = Integer.parseInt(args[i]);
                            } else if (args[i].equalsIgnoreCase("-hp")) {
                                i++;
                                HP = Integer.parseInt(args[i]);
                            } else if (args[i].equalsIgnoreCase("-mh")) {
                                i++;
                                maxHit = Integer.parseInt(args[i]);
                            } else if (args[i].equalsIgnoreCase("-a")) {
                                i++;
                                attack = Integer.parseInt(args[i]);
                            } else if (args[i].equalsIgnoreCase("-d")) {
                                i++;
                                defense = Integer.parseInt(args[i]);
                            } else if (args[i].equalsIgnoreCase("-ap")) {
                                i++;
                                if (args[i].equalsIgnoreCase("true") || args[i].equalsIgnoreCase("t") || args[i].equalsIgnoreCase("1")) {
                                    attackplayer = true;
                                } else {
                                    attackplayer = false;
                                }
                            } else if (args[i].equalsIgnoreCase("-hi")) {
                                i++;
                                if (args[i].equalsIgnoreCase("true") || args[i].equalsIgnoreCase("t") || args[i].equalsIgnoreCase("1")) {
                                    headicon = true;
                                } else {
                                    headicon = false;
                                }
                            } else if (args[i].equalsIgnoreCase("-p")) {
                                i++;
                                if (args[i].equalsIgnoreCase("true") || args[i].equalsIgnoreCase("t") || args[i].equalsIgnoreCase("1")) {
                                    persistence = true;
                                } else {
                                    persistence = false;
                                }
                            } else if (args[i].equalsIgnoreCase("-desc")) {
                                if (Config.SERVER_DEBUG) {
                                    c.sendMessage("Setting desccription");
                                }
                                i++;
                                description = args[i];
                                if (Config.SERVER_DEBUG) {
                                    c.sendMessage("Set description");
                                }
                            } else {
                                validcmd = false;
                                break;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                SendSyntaxError(c, "npc.spawn <id> [-args]");
                validcmd = false;
            }
            if (Config.SERVER_DEBUG) {
                c.sendMessage("validcmd: " + validcmd);
            }
            if (npcType != 0 && validcmd) {
                Server.npcHandler.spawnNpc(c, npcType, x, y, height, walkingType, HP, maxHit, attack, defense, attackplayer, headicon);
                if (persistence) {
                    try {
                        BufferedWriter bf = new BufferedWriter(new FileWriter("./Data/cfg/spawn-config.cfg", true));
                        bf.newLine();
                        bf.write("\nspawn = " + npcType + "	" + x + "	" + y + "	" + height + "	" + walkingType + "	" + maxHit + "	" + attack + "	" + defense + "	" + description);
                        bf.close();
                    } catch (Exception e) {
                        if (Config.SERVER_DEBUG) {
                            c.sendMessage("Error writing to file");
                        }
                    }
                }
            }
        }
        else {
            SendSyntaxError(c, "npc.help");
        }
    }
}
