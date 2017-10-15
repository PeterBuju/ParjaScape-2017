/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model.players.packets;

import server.Server;
import server.content.House;
import server.content.HouseManager;
import server.event.CycleEvent;
import server.event.CycleEventContainer;
import server.event.CycleEventHandler;
import server.model.npcs.DBNPC;
import server.model.npcs.NPC;
import server.model.npcs.NPCGroup;
import server.model.npcs.NPCGroupHandler;
import server.model.npcs.NPCHandler;
import server.model.npcs.NPCDataBase;
import server.model.objects.Fire;
import server.model.objects.FishBank;
import server.model.objects.Objects;
import server.model.objects.Rock;
import server.model.objects.Tree;
import server.model.players.Client;
import server.model.players.Palette;
import server.model.players.Player;
import server.model.players.PlayerHandler;
import server.model.players.PlayerSave;
import server.model.players.Recipe;
import server.model.players.RecipeManager;
import static server.model.players.packets.Commands.SendSyntaxError;
import server.world.FireManager;
import server.world.FishBankManager;
import server.world.RockManager;
import server.world.Tile;
import server.world.TreeManager;

/**
 *
 * @author Alex Macocian
 */
public class DebuggingCommands {

    public static void ParseCommand(Client c, String command) {
        String[] args = command.split(" ");
        if (args[0].equalsIgnoreCase("help")) {
            if (args.length == 1) {
                c.sendMessage("Use ::debug.commands for a list of commands");
                c.sendMessage("Use ::debug.help <command> for help regarding a command");
            } else if (args.length == 2) {
                if (args[1].equalsIgnoreCase("restart")) {
                    c.sendMessage("Usage: ::debug.restart");
                    c.sendMessage("Restarts the server");
                } else if (args[1].equalsIgnoreCase("rebuild")) {
                    c.sendMessage("Usage: ::debug.rebuild");
                    c.sendMessage("Rebuilds and restarts the server");
                } else if (args[1].equalsIgnoreCase("enable")) {
                    c.sendMessage("Usage: ::debug.enable");
                    c.sendMessage("Turns on debugging mode");
                } else if (args[1].equalsIgnoreCase("disable")) {
                    c.sendMessage("Usage: ::debug.disable");
                    c.sendMessage("Turns off debugging mode");
                } else if (args[1].equalsIgnoreCase("showInterface")) {
                    c.sendMessage("Usage: ::debug.showInterface <id>");
                    c.sendMessage("Displays the interface with the certain id");
                } else if (args[1].equalsIgnoreCase("bank")) {
                    c.sendMessage("Usage: ::debug.bank");
                    c.sendMessage("Opens the bank interface");
                } else if (args[1].equalsIgnoreCase("playSound")) {
                    c.sendMessage("Usage: ::debug.playSound <id> [<volume>] [<delay>]");
                    c.sendMessage("Plays the sound with the specified id");
                } else if (args[1].equalsIgnoreCase("playMusic")) {
                    c.sendMessage("Usage: ::debug.playMusic <id>");
                    c.sendMessage("Plays the music with the specified id");
                } else if (args[1].equalsIgnoreCase("perklist")) {
                    c.sendMessage("Usage: ::debug.perklist");
                    c.sendMessage("Shows a list of all available perks");
                } else if (args[1].equalsIgnoreCase("list")) {
                    c.sendMessage("Usage: ::debug.list <argument> [<id>]");
                    c.sendMessage("Lists all objects of argument type and of a certain id if specified");
                    c.sendMessage("Use ::debug.list help for a list of possible arguments");
                } else if (args[1].equalsIgnoreCase("players")) {
                    c.sendMessage("Usage: ::debug.players");
                    c.sendMessage("Returns the number of active players");
                } else if (args[1].equalsIgnoreCase("openShop")) {
                    c.sendMessage("Usage: ::debug.openShop <id>");
                    c.sendMessage("Opens the shop with the specified id");
                } else if (args[1].equalsIgnoreCase("showRecipe")) {
                    c.sendMessage("Usage: ::debug.showRecipe [<id>]");
                    c.sendMessage("Shows a list of recipes or the recipe with the specified id");
                } else if (args[1].equalsIgnoreCase("addObject")) {
                    c.sendMessage("Usage: ::debug.addObject <id>");
                    c.sendMessage("Spawns an object of id at the current location");
                } else if (args[1].equalsIgnoreCase("showRocks")) {
                    c.sendMessage("Usage: ::debug.showRocks");
                    c.sendMessage("Shows a list of currently managed rocks");
                } else if (args[1].equalsIgnoreCase("showTrees")) {
                    c.sendMessage("Usage: ::debug.showTrees");
                    c.sendMessage("Shows a list of currently managed trees");
                } else if (args[1].equalsIgnoreCase("showWalkableInterface")) {
                    c.sendMessage("Usage: ::debug.showWalkableInterface <id>");
                    c.sendMessage("Shows the specified interface and enables walking");
                } else if (args[1].equalsIgnoreCase("showNpcGroups")) {
                    c.sendMessage("Usage: ::debug.showNpcGroups");
                    c.sendMessage("Shows a list with the loaded groups of NPCs");
                } else if (args[1].equalsIgnoreCase("showDBNpcs")){
                    c.sendMessage("Usage: ::debug.showDBNpcs");
                    c.sendMessage("Shows a list of all NPCS in the database");
                } else {
                    SendSyntaxError(c, "debug.help <command>");
                }
            }
        } else if (args[0].equalsIgnoreCase("commands")) {
            c.sendMessage("Debugging commands: ");
            c.sendMessage(" help");
            c.sendMessage(" disable");
            c.sendMessage(" enable");
            c.sendMessage(" restart");
            c.sendMessage(" rebuild");
            c.sendMessage(" showInterface");
            c.sendMessage(" showWalkableInterface");
            c.sendMessage(" bank");
            c.sendMessage(" list");
            c.sendMessage(" players");
            c.sendMessage(" playSound");
            c.sendMessage(" playMusic");
            c.sendMessage(" perklist");
            c.sendMessage(" openShop");
            c.sendMessage(" showRecipe");
            c.sendMessage(" addObject");
            c.sendMessage(" showTrees");
            c.sendMessage(" showRocks");
            c.sendMessage(" showNpcGroups");
            c.sendMessage(" showDBNpcs");
        } else if (args[0].equalsIgnoreCase("playSound")) {
            try {
                int delay = 0;
                int volume = 100;
                int id = 0;
                if (args.length == 4) {
                    id = Integer.parseInt(args[1]);
                    delay = Integer.parseInt(args[2]);
                    volume = Integer.parseInt(args[3]);
                    c.getPA().sendSound(id, volume, delay);
                } else if (args.length == 3) {
                    id = Integer.parseInt(args[1]);
                    delay = Integer.parseInt(args[2]);
                    c.getPA().sendSound(id, volume, delay);
                } else if (args.length == 2) {
                    id = Integer.parseInt(args[1]);
                    c.getPA().sendSound(id, volume, delay);
                } else {
                    SendSyntaxError(c, "debug.playSound <id> [<volume>] [<delay>]");
                }
            } catch (Exception e) {
                SendSyntaxError(c, "debug.playSound <id> [<delay>] [<delay>]");
            }
        } else if (args[0].equalsIgnoreCase("playMusic")) {
            try {
                int id = 0;
                if (args.length == 2) {
                    c.playMusic(args[1]);
                    c.sendMessage("Playing song " + id);
                } else {
                    SendSyntaxError(c, "debug.playMusic <id>");
                }
            } catch (Exception e) {
                SendSyntaxError(c, "debug.playMusic <id>");
            }
        } else if (args[0].equalsIgnoreCase("showInterface")) {
            if (args.length != 2) {
                SendSyntaxError(c, "::debug.showInterface <id>");
            } else {
                int id = Integer.parseInt(args[1]);
                c.customInterface(id);
            }
        } else if (args[0].equalsIgnoreCase("list")) {
            if (args.length <= 1 || args.length > 3) {
                SendSyntaxError(c, "::debug.list <argument> [<id>]");
            } else if (args[1].equalsIgnoreCase("help")) {
                c.sendMessage("Possible arguments: ");
                c.sendMessage("     npcs");
                c.sendMessage("     players");
            } else if (args[1].equalsIgnoreCase("npcs")) {
                if (args.length == 3) {
                    int id = Integer.parseInt(args[2]);
                    for (NPC npc : NPCHandler.npcs) {
                        if (npc != null) {
                            if (npc.npcType == id) {
                                c.sendMessage("id: " + npc.npcType + " x: " + npc.absX + " y: " + npc.absY + " desc: " + npc.description);
                            }
                        }
                    }
                } else {
                    for (NPC npc : NPCHandler.npcs) {
                        if (npc != null) {
                            c.sendMessage("id: " + npc.npcType + " x: " + npc.spawnX + " y: " + npc.spawnY + " desc: " + npc.description);
                        }
                    }
                }
            } else if (args[1].equalsIgnoreCase("players")) {
                if (args.length == 3) {
                    int id = Integer.parseInt(args[2]);
                    for (int i = 0; i <= PlayerHandler.playerCount; i++) {
                        Player player = PlayerHandler.players[i];
                        if (player != null) {
                            if (i == id) {
                                c.sendMessage("id: " + i + " name: " + player.playerName + " x: " + player.absX + " y: " + player.absY);
                            }
                        }
                    }
                } else {
                    for (int i = 0; i <= PlayerHandler.playerCount; i++) {
                        Player player = PlayerHandler.players[i];
                        if (player != null) {
                            c.sendMessage("id: " + i + " name: " + player.playerName + " x: " + player.absX + " y: " + player.absY);
                        }
                    }
                }
            }
        } else if (args[0].equalsIgnoreCase("players")) {
            c.sendMessage("There are currently " + PlayerHandler.getPlayerCount() + " players online.");
        } else if (args[0].equalsIgnoreCase("openShop")) {
            try {
                c.getShops().openShop(Integer.parseInt(args[1]));
            } catch (Exception e) {
                SendSyntaxError(c, "debug.openShop <id>");
            }
        } else if (args[0].equalsIgnoreCase("restart")) {
            for (Player p : PlayerHandler.players) {
                if (p == null) {
                    continue;
                }
                Client cl = (Client) p;
                cl.getPA().movePlayer(cl.oldLocation[0], cl.oldLocation[1], cl.oldLocation[2]);
                PlayerSave.saveGame((Client) p);
            }
            HouseManager.SaveHouses();
            System.exit(0);
        } else if (args[0].equalsIgnoreCase("rebuild")) {
            for (Player p : PlayerHandler.players) {
                if (p == null) {
                    continue;
                }
                Client cl = (Client) p;
                cl.getPA().movePlayer(cl.oldLocation[0], cl.oldLocation[1], cl.oldLocation[2]);
                PlayerSave.saveGame((Client) p);
            }
            HouseManager.SaveHouses();
            System.exit(1);
        } else if (args[0].equalsIgnoreCase("bank")) {
            c.getPA().openUpBank();
        } else if (args[0].equalsIgnoreCase("playMusic2")) {
            c.getPA().playMusic2(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        } else if (args[0].equalsIgnoreCase("showRecipe")) {
            if (args.length == 2) {
                try {
                    Recipe recipe = RecipeManager.GetRecipe(Integer.parseInt(args[1]));
                    c.sendMessage("Obj: " + recipe.preCookedId + " Cook: " + recipe.succesfulCookedId + " Burn: " + recipe.failedCookedId + " Lvl: " + recipe.requiredLevel + " Exp: " + recipe.gainExperience + " stopBurnLvl: " + recipe.stopBurnLevel);
                } catch (Exception e) {
                    SendSyntaxError(c, "debug.showRecipe [<id>]");
                }
            } else {
                for (Recipe recipe : RecipeManager.Recipes) {
                    c.sendMessage("Obj: " + recipe.preCookedId + " Cook: " + recipe.succesfulCookedId + " Burn: " + recipe.failedCookedId + " Lvl: " + recipe.requiredLevel + " Exp: " + recipe.gainExperience + " stopBurnLvl: " + recipe.stopBurnLevel);
                }
            }
        } else if (args[0].equalsIgnoreCase("addObject")) {
            try {
                c.getPA().checkObjectSpawn(Integer.parseInt(args[1]), c.absX, c.absY, 1, 10);
                c.sendMessage("Added object: " + args[1]);
            } catch (Exception e) {
                SendSyntaxError(c, "debug.addObject <id>");
            }
        } else if (args[0].equalsIgnoreCase("showRocks")) {
            for (Rock rock : RockManager.rocks) {
                c.sendMessage("InitId: " + rock.GetInitObjId() + " PosX: " + rock.GetPosX() + " PosY: " + rock.GetPosY() + " Charges: " + rock.GetCharges());
            }
        } else if (args[0].equalsIgnoreCase("showTrees")) {
            for (Tree tree : TreeManager.trees) {
                c.sendMessage("InitId: " + tree.GetInitObjId() + " PosX: " + tree.GetPosX() + " PosY: " + tree.GetPosY() + " Charges: " + tree.GetCharges());
            }
        } else if (args[0].equalsIgnoreCase("construct")) {
            try {
                c.getPA().movePlayer(50, 50, 0);
                Palette palette = new Palette();
                Tile tile = new Tile(Integer.parseInt(args[1]), Integer.parseInt(args[2]), 0);

                Palette.PaletteTile paletteTile = new Palette.PaletteTile(tile.getTileX(), tile.getTileY(), 0);
                palette.setTile(6, 6, 0, paletteTile);

                for (int j = -3; j < 3; j++) {
                    for (int i = -3; i < 3; i++) {
                        Palette.PaletteTile palTile = new Palette.PaletteTile(tile.getTileX() + (8 * j), tile.getTileY() + (8 * i), 0);
                        palette.setTile(6 + j, 6 + i, 0, palTile);
                    }
                }
                c.getPA().sendConstructMapRegion(palette);
            } catch (Exception e) {
                SendSyntaxError(c, "::debug.construct [x] [y]");
            }
        } else if (args[0].equalsIgnoreCase("showWalkableInterface")) {
            if (args.length == 2) {
                try {
                    c.getPA().walkableInterface(Integer.parseInt(args[1]));
                } catch (Exception e) {
                    SendSyntaxError(c, "debug.showWalkableInterface <id>");
                }
            } else {
                SendSyntaxError(c, "debug.showWalkableInterface <id>");
            }
        } else if (args[0].equalsIgnoreCase("showNpcGroups")) {
            for (NPCGroup group : NPCGroupHandler.Groups) {
                for (server.model.npcs.Attacker attacker : group.attackers) {
                    c.sendMessage(group.getName() + " -> " + attacker.id);
                }
            }
        } else if(args[0].equalsIgnoreCase("showDBNpcs")){
            for (DBNPC npc : NPCDataBase.NPCDatabase){
                c.sendMessage(npc.id + " - " + npc.name);
            }
        } else {
            SendSyntaxError(c, "debug.help");
        }
    }
}
