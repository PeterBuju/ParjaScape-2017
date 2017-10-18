/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model.players.packets;

import java.util.concurrent.TimeUnit;
import server.Config;
import server.content.HouseBackup;
import server.content.HouseManager;
import server.event.CycleEvent;
import server.event.CycleEventContainer;
import server.event.CycleEventHandler;
import server.model.perks.Perk;
import server.model.perks.PerkHandler;
import server.model.players.Client;
import server.model.players.Stats;
import static server.model.players.packets.Commands.SendSyntaxError;
import server.model.quests.RadiantQuest;
import server.model.quests.RadiantQuestManager;
import server.util.Misc;

/**
 *
 * @author Alex Macocian
 */
public class PlayerCommands {

    public static void ParseCommand(Client c, String command) {
        String[] args = command.split(" ");
        if (args[0].equalsIgnoreCase("help")) {
            if (args.length == 1) {
                c.sendMessage("Use ::player.commands for a list of commands");
                c.sendMessage("Use ::player.help <command> for help regarding a command");
            } else if (args.length == 2) {
                if (args[1].equalsIgnoreCase("addperk")) {
                    c.sendMessage("Usage: ::player.addperk <id>");
                    c.sendMessage("Adds a perk to the player");
                } else if (args[1].equalsIgnoreCase("showperks")) {
                    c.sendMessage("Usage: ::player.showperks");
                    c.sendMessage("Shows a list of all the perks on the current player");
                } else if (args[1].equalsIgnoreCase("changestat")) {
                    c.sendMessage("Usage: ::player.changestat <id> <val>");
                    c.sendMessage("Finds the stat by id and sets the value with val");
                } else if (args[1].equalsIgnoreCase("showstat")) {
                    c.sendMessage("Usage: ::player.showstat [<id>]");
                    c.sendMessage("Shows the value of the stat specified by id or all the stats of the");
                    c.sendMessage("current player");
                    c.sendMessage("");
                    c.sendMessage("Usage: ::player.showstat all");
                    c.sendMessage("Shows all stats of the current player");
                } else if (args[1].equalsIgnoreCase("emote")) {
                    c.sendMessage("Usage: ::player.emote <id> [<duration>]");
                    c.sendMessage("Forces the player to do the specified emote. If a duration is specified,");
                    c.sendMessage("it will do the emote after the duration expires");
                } else if (args[1].equalsIgnoreCase("tele")) {
                    c.sendMessage("Usage: ::tele <x> <y> [<height>]");
                    c.sendMessage("Teleports the user to the provided coordinates");
                    c.sendMessage("");
                    c.sendMessage("Usage: ::tele <direction> [<distance>]");
                    c.sendMessage("Teleports the user in the specified direction over the specified distance");
                    c.sendMessage("Valid directions are: 'N', 'S', 'E', 'W', 'U', 'D'");
                    c.sendMessage("");
                    c.sendMessage("Usage: ::tele <location>");
                    c.sendMessage("Teleports the player to the location specified");
                    c.sendMessage("For a list of available locations type ::player.tele locations");
                } else if (args[1].equalsIgnoreCase("coords")) {
                    c.sendMessage("Usage: ::player.coords");
                    c.sendMessage("Returns the current coordinates");
                } else if (args[1].equalsIgnoreCase("additem")) {
                    c.sendMessage("Usage: ::player.additem <id> [<amount>]");
                    c.sendMessage("Adds an item with the specified amount");
                } else if (args[1].equalsIgnoreCase("addquest")) {
                    c.sendMessage("Usage: ::player.addquest <id>");
                    c.sendMessage("Adds the quest to the player");
                } else if (args[1].equalsIgnoreCase("showquests")) {
                    c.sendMessage("Usage: ::player.showquests");
                    c.sendMessage("Shows a list of quests that the player has");
                } else {
                    SendSyntaxError(c, "player.help <command>");
                }
            }
        } else if (args[0].equalsIgnoreCase("commands")) {
            c.sendMessage("Player commands: ");
            c.sendMessage(" help");
            c.sendMessage(" coords");
            c.sendMessage(" tele");
            c.sendMessage(" emote");
            c.sendMessage(" showstat");
            c.sendMessage(" changestat");
            c.sendMessage(" showperks");
            c.sendMessage(" addperk");
            c.sendMessage(" additem");
            c.sendMessage(" inMulti");
            c.sendMessage(" addQuest");
            c.sendMessage(" showquests");
        } else if (args[0].equalsIgnoreCase("addperk")) {
            try {
                if (args.length != 2) {
                    SendSyntaxError(c, "::player.addperk <id>");
                } else {
                    int id = Integer.parseInt(args[1]);
                    if (!c.perks.contains(PerkHandler.GetPerk(id))) {
                        c.perks.add(PerkHandler.GetPerk(id));
                    } else {
                        c.sendMessage("Player already has the '" + PerkHandler.GetPerk(id).getName() + "' perk");
                    }
                }
            } catch (Exception e) {
                SendSyntaxError(c, "::player.addperk <id>");
            }
        } else if (args[0].equalsIgnoreCase("showperks")) {
            for (Perk perk : c.perks) {
                c.sendMessage(perk.getId() + " : " + perk.getName());
            }
        } else if (args[0].equalsIgnoreCase("additem")) {
            try {
                if (args.length < 2 || args.length > 3) {
                    SendSyntaxError(c, "::player.additem <id> [<amount>]");
                } else {
                    int amount = 1;
                    int id = Integer.parseInt(args[1]);
                    if (args.length == 3) {
                        amount = Integer.parseInt(args[2]);
                    }
                    c.getItems().addItem(id, amount);
                }
            } catch (Exception e) {
                SendSyntaxError(c, "::player.additem <id> [<amount>]");
            }
        } else if (args[0].equalsIgnoreCase("changestat")) {
            if (args.length == 3) {
                try {
                    c.stats[Integer.parseInt(args[1])] = Integer.parseInt(args[2]);
                    c.sendMessage("Changed " + Stats.GetStatName(Integer.parseInt(args[1])) + " to " + c.stats[Integer.parseInt(args[1])]);
                    c.showStats();
                } catch (Exception e) {
                    SendSyntaxError(c, "::player.changestat <id> <val>");
                }
            } else {
                SendSyntaxError(c, "::player.changestat <id> <val>");
            }
        } else if (args[0].equalsIgnoreCase("showstat")) {
            if (args.length == 1) {
            } else if (args.length == 2) {
                if (args[1].equalsIgnoreCase("all")) {
                    for (int i = 0; i < 7; i++) {
                        c.sendMessage(Stats.GetStatName(i) + ": " + c.stats[i]);
                    }
                } else {
                    try {
                        c.sendMessage(Stats.GetStatName(Integer.parseInt(args[1])) + ": " + c.stats[Integer.parseInt(args[1])]);
                    } catch (Exception e) {
                        SendSyntaxError(c, "::player.showstat <id>");
                    }
                }
            } else {
                SendSyntaxError(c, "::player.showstat <id>");
            }
        } else if (args[0].equalsIgnoreCase("addquest")) {
            if (args.length == 2) {
                try {
                    c.radiantQuests.add(RadiantQuestManager.radiantQuests.get(Integer.parseInt(args[1])));
                } catch (Exception e) {
                    SendSyntaxError(c, "::player.addquest <id>");
                }
            } else {
                SendSyntaxError(c, "::player.addquest <id>");
            }
        } else if (args[0].equalsIgnoreCase("showquests")) {
            if (args.length == 1) {
                try {
                    for(RadiantQuest quest : c.radiantQuests){
                        c.sendMessage(quest.name);
                    }
                } catch (Exception e) {
                    SendSyntaxError(c, "::player.showquests");
                }
            } else {
                SendSyntaxError(c, "::player.showquests");
            }
        } else if (args[0].equalsIgnoreCase("emote")) {
            if (args.length == 3) {
                c.startAnimation(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
            } else if (args.length == 2) {
                c.startAnimation(Integer.parseInt(args[1]));
            } else {
                SendSyntaxError(c, "::player.emote <id> [<duration>]");
            }
        } else if (args[0].equalsIgnoreCase("tele")) {
            if (args.length == 2) {
                if (args[1].equalsIgnoreCase("help")) {
                    c.sendMessage("Tele teleports the player to a specified location");
                    c.sendMessage("Use '::tele <location>' to teleport to a certain location");
                    c.sendMessage("Use '::tele <direction> [<distance>] to teleport in a direction over a distance");
                    c.sendMessage("Use '::tele <x> <y> [<z>] to teleport to a set of coodinates");
                    c.sendMessage("Use '::tele locations' for a list of locations");
                } else if (args[1].equalsIgnoreCase("locations")) {
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
                } else if (args[1].equalsIgnoreCase("startlocation")) {
                    c.getPA().movePlayer(Config.START_LOCATION_X, Config.START_LOCATION_Y, 1);
                } else if (args[1].equalsIgnoreCase("edgeville")) {
                    c.getPA().movePlayer(Config.EDGEVILLE_X, Config.EDGEVILLE_Y, 0);
                } else if (args[1].equalsIgnoreCase("lumbridge")) {
                    c.getPA().movePlayer(Config.LUMBY_X, Config.LUMBY_Y, 0);
                } else if (args[1].equalsIgnoreCase("fallador")) {
                    c.getPA().movePlayer(Config.FALADOR_X, Config.FALADOR_Y, 0);
                } else if (args[1].equalsIgnoreCase("ardougne")) {
                    c.getPA().movePlayer(Config.ARDOUGNE_X, Config.ARDOUGNE_Y, 0);
                } else if (args[1].equalsIgnoreCase("alkharid")) {
                    c.getPA().movePlayer(Config.AL_KHARID_X, Config.AL_KHARID_Y, 0);
                } else if (args[1].equalsIgnoreCase("camelot")) {
                    c.getPA().movePlayer(Config.CAMELOT_X, Config.CAMELOT_Y, 0);
                } else if (args[1].equalsIgnoreCase("annakarl")) {
                    c.getPA().movePlayer(Config.ANNAKARL_X, Config.ANNAKARL_Y, 0);
                } else if (args[1].equalsIgnoreCase("carrallangar")) {
                    c.getPA().movePlayer(Config.CARRALLANGAR_X, Config.CARRALLANGAR_Y, 0);
                } else if (args[1].equalsIgnoreCase("dareeyak")) {
                    c.getPA().movePlayer(Config.DAREEYAK_X, Config.DAREEYAK_Y, 0);
                } else if (args[1].equalsIgnoreCase("ghorrock")) {
                    c.getPA().movePlayer(Config.GHORROCK_X, Config.GHORROCK_Y, 0);
                } else if (args[1].equalsIgnoreCase("karamja")) {
                    c.getPA().movePlayer(Config.KARAMJA_X, Config.KARAMJA_Y, 0);
                } else if (args[1].equalsIgnoreCase("kharyrll")) {
                    c.getPA().movePlayer(Config.KHARYRLL_X, Config.KHARYRLL_Y, 0);
                } else if (args[1].equalsIgnoreCase("lassar")) {
                    c.getPA().movePlayer(Config.LASSAR_X, Config.LASSAR_Y, 0);
                } else if (args[1].equalsIgnoreCase("paddewwa")) {
                    c.getPA().movePlayer(Config.PADDEWWA_X, Config.PADDEWWA_Y, 0);
                } else if (args[1].equalsIgnoreCase("senntisten")) {
                    c.getPA().movePlayer(Config.SENNTISTEN_X, Config.SENNTISTEN_Y, 0);
                } else if (args[1].equalsIgnoreCase("trollheim")) {
                    c.getPA().movePlayer(Config.TROLLHEIM_X, Config.TROLLHEIM_Y, 0);
                } else if (args[1].equalsIgnoreCase("varrock")) {
                    c.getPA().movePlayer(Config.VARROCK_X, Config.VARROCK_Y, 0);
                } else if (args[1].equalsIgnoreCase("watchtower")) {
                    c.getPA().movePlayer(Config.WATCHTOWER_X, Config.WATCHTOWER_Y, 0);
                } else if (args[1].equalsIgnoreCase("U")) {
                    c.getPA().movePlayer(c.absX, c.absY, c.heightLevel + 1);
                } else if (args[1].equalsIgnoreCase("D")) {
                    c.getPA().movePlayer(c.absX, c.absY, c.heightLevel - 1);
                } else if (args[1].equalsIgnoreCase("N")) {
                    c.getPA().movePlayer(c.absX, c.absY + 1, c.heightLevel);
                } else if (args[1].equalsIgnoreCase("S")) {
                    c.getPA().movePlayer(c.absX, c.absY - 1, c.heightLevel);
                } else if (args[1].equalsIgnoreCase("E")) {
                    c.getPA().movePlayer(c.absX + 1, c.absY, c.heightLevel);
                } else if (args[1].equalsIgnoreCase("W")) {
                    c.getPA().movePlayer(c.absX - 1, c.absY, c.heightLevel);
                } else {
                    SendSyntaxError(c, "tele <location>");
                }
            } else if (args.length == 3) {
                if (args[1].equalsIgnoreCase("U") || args[1].equalsIgnoreCase("D") || args[1].equalsIgnoreCase("N")
                        || args[1].equalsIgnoreCase("S") || args[1].equalsIgnoreCase("E") || args[1].equalsIgnoreCase("W")) {
                    try {
                        if (args[1].equalsIgnoreCase("U")) {
                            c.getPA().movePlayer(c.absX, c.absY, c.heightLevel + Integer.parseInt(args[2]));
                        } else if (args[1].equalsIgnoreCase("D")) {
                            c.getPA().movePlayer(c.absX, c.absY, c.heightLevel - Integer.parseInt(args[2]));
                        } else if (args[1].equalsIgnoreCase("N")) {
                            c.getPA().movePlayer(c.absX, c.absY + Integer.parseInt(args[2]), c.heightLevel);
                        } else if (args[1].equalsIgnoreCase("S")) {
                            c.getPA().movePlayer(c.absX, c.absY - Integer.parseInt(args[2]), c.heightLevel);
                        } else if (args[1].equalsIgnoreCase("E")) {
                            c.getPA().movePlayer(c.absX + Integer.parseInt(args[2]), c.absY, c.heightLevel);
                        } else if (args[1].equalsIgnoreCase("W")) {
                            c.getPA().movePlayer(c.absX - Integer.parseInt(args[2]), c.absY, c.heightLevel);
                        }
                    } catch (Exception e) {
                        SendSyntaxError(c, "tele <direction> [<distance>]");
                    }
                } else {
                    try {
                        c.getPA().movePlayer(Integer.parseInt(args[1]), Integer.parseInt(args[2]), c.heightLevel);
                    } catch (Exception e) {
                        SendSyntaxError(c, "tele <x> <y> [<z>]");
                    }
                }
            } else if (args.length > 3) {
                try {
                    c.getPA().movePlayer(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
                } catch (Exception e) {
                    SendSyntaxError(c, "tele <x> <y> [<z>]");
                }
            }
        } else if (args[0].equalsIgnoreCase("coords")) {
            c.sendMessage("X: " + c.absX + " Y: " + c.absY + " Z: " + c.heightLevel);
        } else if (args[0].equalsIgnoreCase("inMulti")) {
            c.sendMessage("Multi: " + c.inMulti());
        } else {
            SendSyntaxError(c, "player.help");
        }
    }
}
