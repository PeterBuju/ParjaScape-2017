package server.model.players;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import server.Server;
import server.content.HouseBackup;
import server.model.items.Item;
import server.model.perks.Perk;
import server.model.perks.PerkHandler;
import server.model.quests.RadiantQuest;
import server.model.quests.RadiantQuestObjective;
import server.util.Misc;

public class PlayerSave {

    /**
     * Loading
	*
     */
    public static int loadGame(Client p, String playerName, String playerPass) {
        String line = "";
        String token = "";
        String token2 = "";
        String[] token3 = new String[3];
        boolean EndOfFile = false;
        int ReadMode = 0;
        BufferedReader characterfile = null;
        boolean File1 = false;

        try {
            characterfile = new BufferedReader(new FileReader("./Data/characters/" + playerName + ".txt"));
            File1 = true;
        } catch (FileNotFoundException fileex1) {
        }

        if (File1) {
            //new File ("./characters/"+playerName+".txt");
        } else {
            Misc.println(playerName + ": character file not found.");
            p.newPlayer = false;
            return 0;
        }
        try {
            line = characterfile.readLine();
        } catch (IOException ioexception) {
            Misc.println(playerName + ": error loading file.");
            return 3;
        }
        while (EndOfFile == false && line != null) {
            line = line.trim();
            int spot = line.indexOf("=");
            if (spot > -1) {
                token = line.substring(0, spot);
                token = token.trim();
                token2 = line.substring(spot + 1);
                token2 = token2.trim();
                token3 = token2.split("\t");
                switch (ReadMode) {
                    case 1:
                        if (token.equals("character-password")) {
                            if (playerPass.equalsIgnoreCase(token2) || Misc.basicEncrypt(playerPass).equals(token2)) {
                                playerPass = Misc.basicEncrypt(playerPass);
                            } else {
                                return 3;
                            }
                        }
                        break;
                    case 2:
                        if (token.equalsIgnoreCase("wisdom")) {
                            p.stats[0] = Integer.parseInt(token2);
                        } else if (token.equalsIgnoreCase("constitution")) {
                            p.stats[1] = Integer.parseInt(token2);
                        } else if (token.equalsIgnoreCase("speed")) {
                            p.stats[2] = Integer.parseInt(token2);
                        } else if (token.equalsIgnoreCase("endurance")) {
                            p.stats[3] = Integer.parseInt(token2);
                        } else if (token.equalsIgnoreCase("intelligence")) {
                            p.stats[4] = Integer.parseInt(token2);
                        } else if (token.equalsIgnoreCase("dexterity")) {
                            p.stats[5] = Integer.parseInt(token2);
                        } else if (token.equalsIgnoreCase("charisma")) {
                            p.stats[6] = Integer.parseInt(token2);
                        }
                        break;
                    case 3:
                        if (token.equalsIgnoreCase("Perk")) {
                            p.perks.add(PerkHandler.GetPerk(Integer.parseInt(token2)));
                        }
                        break;
                    case 4:
                        if (token.equals("character-height")) {
                            p.heightLevel = Integer.parseInt(token2);
                        } else if (token.equals("character-tutorial")) {
                            p.tutorial = Integer.parseInt(token2);
                        } else if (token.equals("run-energy")) {
                            p.runEnergy = Integer.parseInt(token2);
                        } else if (token.equals("character-posx")) {
                            p.teleportToX = (Integer.parseInt(token2) <= 0 ? 3210 : Integer.parseInt(token2));
                        } else if (token.equals("character-posy")) {
                            p.teleportToY = (Integer.parseInt(token2) <= 0 ? 3424 : Integer.parseInt(token2));
                        } else if (token.equals("character-rights")) {
                            p.playerRights = Integer.parseInt(token2);
                        } else if (token.equals("tutorial-progress")) {
                            p.tutorial = Integer.parseInt(token2);
                        } else if (token.equals("crystal-bow-shots")) {
                            p.crystalBowArrowCount = Integer.parseInt(token2);
                        } else if (token.equals("skull-timer")) {
                            p.skullTimer = Integer.parseInt(token2);
                        } else if (token.equals("magic-book")) {
                            p.playerMagicBook = Integer.parseInt(token2);
                        } else if (token.equals("brother-info")) {
                            p.barrowsNpcs[Integer.parseInt(token3[0])][1] = Integer.parseInt(token3[1]);
                        } else if (token.equals("special-amount")) {
                            p.specAmount = Double.parseDouble(token2);
                        } else if (token.equals("selected-coffin")) {
                            p.randomCoffin = Integer.parseInt(token2);
                        } else if (token.equals("barrows-killcount")) {
                            p.pkPoints = Integer.parseInt(token2);
                        } else if (token.equals("teleblock-length")) {
                            p.teleBlockDelay = System.currentTimeMillis();
                            p.teleBlockLength = Integer.parseInt(token2);
                        } else if (token.equals("pc-points")) {
                            p.pcPoints = Integer.parseInt(token2);
                        } else if (token.equals("slayerTask")) {
                            p.slayerTask = Integer.parseInt(token2);
                        } else if (token.equals("taskAmount")) {
                            p.taskAmount = Integer.parseInt(token2);
                        } else if (token.equals("magePoints")) {
                            p.magePoints = Integer.parseInt(token2);
                        } else if (token.equals("autoRet")) {
                            p.autoRet = Integer.parseInt(token2);
                        } else if (token.equals("barrowskillcount")) {
                            p.barrowsKillCount = Integer.parseInt(token2);
                        } else if (token.equals("flagged")) {
                            p.accountFlagged = Boolean.parseBoolean(token2);
                        } else if (token.equals("wave")) {
                            p.waveId = Integer.parseInt(token2);
                        } else if (token.equals("void")) {
                            for (int j = 0; j < token3.length; j++) {
                                p.voidStatus[j] = Integer.parseInt(token3[j]);
                            }
                        } else if (token.equals("gwkc")) {
                            p.killCount = Integer.parseInt(token2);
                        } else if (token.equals("fightMode")) {
                            p.fightMode = Integer.parseInt(token2);
                        }
                        break;
                    case 5:
                        if (token.equals("character-equip")) {
                            p.playerEquipment[Integer.parseInt(token3[0])] = Integer.parseInt(token3[1]);
                            p.playerEquipmentN[Integer.parseInt(token3[0])] = Integer.parseInt(token3[2]);
                        }
                        break;
                    case 6:
                        if (token.equals("character-look")) {
                            p.playerAppearance[Integer.parseInt(token3[0])] = Integer.parseInt(token3[1]);
                        }
                        break;
                    case 7:
                        if (token.equals("character-skill")) {
                            p.playerLevel[Integer.parseInt(token3[0])] = Integer.parseInt(token3[1]);
                            p.playerXP[Integer.parseInt(token3[0])] = Integer.parseInt(token3[2]);
                        }
                        break;
                    case 8:
                        if (token.equals("character-item")) {
                            p.playerItems[Integer.parseInt(token3[0])] = Integer.parseInt(token3[1]);
                            p.playerItemsN[Integer.parseInt(token3[0])] = Integer.parseInt(token3[2]);
                        }
                        break;
                    case 9:
                        if (token.equals("character-bank")) {
                            p.bankItems[Integer.parseInt(token3[0])] = Integer.parseInt(token3[1]);
                            p.bankItemsN[Integer.parseInt(token3[0])] = Integer.parseInt(token3[2]);
                        }
                        break;
                    case 10:
                        if (token.equals("character-friend")) {
                            p.friends[Integer.parseInt(token3[0])] = Long.parseLong(token3[1]);
                        }
                        break;
                    case 11:
                        /* if (token.equals("character-ignore")) {
						ignores[Integer.parseInt(token3[0])] = Long.parseLong(token3[1]);
					} */
                        break;
                }
            } else {
                if (line.equals("[ACCOUNT]")) {
                    ReadMode = 1;
                } else if (line.equals("[STATS]")) {
                    ReadMode = 2;
                } else if (line.equals("[PERKS]")) {
                    ReadMode = 3;
                } else if (line.equals("[CHARACTER]")) {
                    ReadMode = 4;
                } else if (line.equals("[EQUIPMENT]")) {
                    ReadMode = 5;
                } else if (line.equals("[LOOK]")) {
                    ReadMode = 6;
                } else if (line.equals("[SKILLS]")) {
                    ReadMode = 7;
                } else if (line.equals("[ITEMS]")) {
                    ReadMode = 8;
                } else if (line.equals("[BANK]")) {
                    ReadMode = 9;
                } else if (line.equals("[FRIENDS]")) {
                    ReadMode = 10;
                } else if (line.equals("[IGNORES]")) {
                    ReadMode = 11;
                } else if (line.equals("[EOF]")) {
                    try {
                        characterfile.close();
                    } catch (IOException ioexception) {
                    }
                    return 1;
                }
            }
            try {
                line = characterfile.readLine();
            } catch (IOException ioexception1) {
                EndOfFile = true;
            }
        }
        try {
            characterfile.close();
        } catch (IOException ioexception) {
        }
        return 13;
    }

    /**
     * Saving
	*
     */
    public static boolean saveGame(Client p) {
        if (!p.saveFile || p.newPlayer || !p.saveCharacter) {
            //System.out.println("first");
            return false;
        }
        if (p.playerName == null || Server.playerHandler.players[p.playerId] == null) {
            //System.out.println("second");
            return false;
        }
        p.playerName = p.playerName2;
        int tbTime = (int) (p.teleBlockDelay - System.currentTimeMillis() + p.teleBlockLength);
        if (tbTime > 300000 || tbTime < 0) {
            tbTime = 0;
        }
        BufferedWriter characterfile = null;
        try {
            characterfile = new BufferedWriter(new FileWriter("./Data/characters/" + p.playerName + ".txt"));

            /*ACCOUNT*/
            characterfile.write("[ACCOUNT]", 0, 9);
            characterfile.newLine();
            characterfile.write("character-username = ", 0, 21);
            characterfile.write(p.playerName, 0, p.playerName.length());
            characterfile.newLine();
            characterfile.write("character-password = ", 0, 21);
            //p.playerPass = Misc.basicEncrypt(p.playerPass);
            characterfile.write(Misc.basicEncrypt(p.playerPass), 0, Misc.basicEncrypt(p.playerPass).length());
            //characterfile.write(Misc.basicEncrypt(p.playerPass).toString(), 0, Misc.basicEncrypt(p.playerPass).toString().length());
            characterfile.newLine();
            characterfile.newLine();
            /*PERKS*/
            characterfile.write("[PERKS]", 0, 7);
            characterfile.newLine();
            for (Perk perk : p.perks) {
                characterfile.write("Perk = ", 0, 7);
                characterfile.write(Integer.toString(perk.getId()), 0, Integer.toString(perk.getId()).length());
                characterfile.newLine();
            }
            characterfile.newLine();
            /*STATS*/
            characterfile.write("[STATS]", 0, 7);
            characterfile.newLine();
            characterfile.write("wisdom = ", 0, 9);
            characterfile.write(Integer.toString(p.stats[0]), 0, Integer.toString(p.stats[0]).length());
            characterfile.newLine();
            characterfile.write("constitution = ", 0, 15);
            characterfile.write(Integer.toString(p.stats[1]), 0, Integer.toString(p.stats[1]).length());
            characterfile.newLine();
            characterfile.write("speed = ", 0, 8);
            characterfile.write(Integer.toString(p.stats[2]), 0, Integer.toString(p.stats[2]).length());
            characterfile.newLine();
            characterfile.write("endurance = ", 0, 12);
            characterfile.write(Integer.toString(p.stats[3]), 0, Integer.toString(p.stats[3]).length());
            characterfile.newLine();
            characterfile.write("intelligence = ", 0, 15);
            characterfile.write(Integer.toString(p.stats[4]), 0, Integer.toString(p.stats[4]).length());
            characterfile.newLine();
            characterfile.write("dexterity = ", 0, 12);
            characterfile.write(Integer.toString(p.stats[5]), 0, Integer.toString(p.stats[5]).length());
            characterfile.newLine();
            characterfile.write("charisma = ", 0, 11);
            characterfile.write(Integer.toString(p.stats[6]), 0, Integer.toString(p.stats[6]).length());
            characterfile.newLine();
            characterfile.newLine();
            /*CHARACTER*/
            characterfile.write("[CHARACTER]", 0, 11);
            characterfile.newLine();
            characterfile.write("character-tutorial= ", 0, 19);
            characterfile.write(Integer.toString(p.tutorial), 0, Integer.toString(p.tutorial).length());
            characterfile.newLine();
            characterfile.write("run-energy = ", 0, 13);
            characterfile.write(Integer.toString(p.runEnergy), 0, Integer.toString(p.runEnergy).length());
            characterfile.newLine();
            characterfile.write("character-height = ", 0, 19);
            characterfile.write(Integer.toString(p.heightLevel), 0, Integer.toString(p.heightLevel).length());
            characterfile.newLine();
            characterfile.write("character-posx = ", 0, 17);
            characterfile.write(Integer.toString(p.absX), 0, Integer.toString(p.absX).length());
            characterfile.newLine();
            characterfile.write("character-posy = ", 0, 17);
            characterfile.write(Integer.toString(p.absY), 0, Integer.toString(p.absY).length());
            characterfile.newLine();
            characterfile.write("character-rights = ", 0, 19);
            characterfile.write(Integer.toString(p.playerRights), 0, Integer.toString(p.playerRights).length());
            characterfile.newLine();
            characterfile.write("crystal-bow-shots = ", 0, 20);
            characterfile.write(Integer.toString(p.crystalBowArrowCount), 0, Integer.toString(p.crystalBowArrowCount).length());
            characterfile.newLine();
            characterfile.write("skull-timer = ", 0, 14);
            characterfile.write(Integer.toString(p.skullTimer), 0, Integer.toString(p.skullTimer).length());
            characterfile.newLine();
            characterfile.write("magic-book = ", 0, 13);
            characterfile.write(Integer.toString(p.playerMagicBook), 0, Integer.toString(p.playerMagicBook).length());
            characterfile.newLine();
            for (int b = 0; b < p.barrowsNpcs.length; b++) {
                characterfile.write("brother-info = ", 0, 15);
                characterfile.write(Integer.toString(b), 0, Integer.toString(b).length());
                characterfile.write("	", 0, 1);
                characterfile.write(p.barrowsNpcs[b][1] <= 1 ? Integer.toString(0) : Integer.toString(p.barrowsNpcs[b][1]), 0, Integer.toString(p.barrowsNpcs[b][1]).length());
                characterfile.newLine();
            }
            characterfile.write("special-amount = ", 0, 17);
            characterfile.write(Double.toString(p.specAmount), 0, Double.toString(p.specAmount).length());
            characterfile.newLine();
            characterfile.write("selected-coffin = ", 0, 18);
            characterfile.write(Integer.toString(p.randomCoffin), 0, Integer.toString(p.randomCoffin).length());
            characterfile.newLine();
            characterfile.write("barrows-killcount = ", 0, 20);
            characterfile.write(Integer.toString(p.barrowsKillCount), 0, Integer.toString(p.barrowsKillCount).length());
            characterfile.newLine();
            characterfile.write("teleblock-length = ", 0, 19);
            characterfile.write(Integer.toString(tbTime), 0, Integer.toString(tbTime).length());
            characterfile.newLine();
            characterfile.write("pc-points = ", 0, 12);
            characterfile.write(Integer.toString(p.pcPoints), 0, Integer.toString(p.pcPoints).length());
            characterfile.newLine();
            characterfile.write("slayerTask = ", 0, 13);
            characterfile.write(Integer.toString(p.slayerTask), 0, Integer.toString(p.slayerTask).length());
            characterfile.newLine();
            characterfile.write("taskAmount = ", 0, 13);
            characterfile.write(Integer.toString(p.taskAmount), 0, Integer.toString(p.taskAmount).length());
            characterfile.newLine();
            characterfile.write("magePoints = ", 0, 13);
            characterfile.write(Integer.toString(p.magePoints), 0, Integer.toString(p.magePoints).length());
            characterfile.newLine();
            characterfile.write("autoRet = ", 0, 10);
            characterfile.write(Integer.toString(p.autoRet), 0, Integer.toString(p.autoRet).length());
            characterfile.newLine();
            characterfile.write("barrowskillcount = ", 0, 19);
            characterfile.write(Integer.toString(p.barrowsKillCount), 0, Integer.toString(p.barrowsKillCount).length());
            characterfile.newLine();
            characterfile.write("flagged = ", 0, 10);
            characterfile.write(Boolean.toString(p.accountFlagged), 0, Boolean.toString(p.accountFlagged).length());
            characterfile.newLine();
            characterfile.write("wave = ", 0, 7);
            characterfile.write(Integer.toString(p.waveId), 0, Integer.toString(p.waveId).length());
            characterfile.newLine();
            characterfile.write("gwkc = ", 0, 7);
            characterfile.write(Integer.toString(p.killCount), 0, Integer.toString(p.killCount).length());
            characterfile.newLine();
            characterfile.write("fightMode = ", 0, 12);
            characterfile.write(Integer.toString(p.fightMode), 0, Integer.toString(p.fightMode).length());
            characterfile.newLine();
            characterfile.write("void = ", 0, 7);
            String toWrite = p.voidStatus[0] + "\t" + p.voidStatus[1] + "\t" + p.voidStatus[2] + "\t" + p.voidStatus[3] + "\t" + p.voidStatus[4];
            characterfile.write(toWrite);
            characterfile.newLine();
            characterfile.newLine();

            /*EQUIPMENT*/
            characterfile.write("[EQUIPMENT]", 0, 11);
            characterfile.newLine();
            for (int i = 0; i < p.playerEquipment.length; i++) {
                characterfile.write("character-equip = ", 0, 18);
                characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
                characterfile.write("	", 0, 1);
                characterfile.write(Integer.toString(p.playerEquipment[i]), 0, Integer.toString(p.playerEquipment[i]).length());
                characterfile.write("	", 0, 1);
                characterfile.write(Integer.toString(p.playerEquipmentN[i]), 0, Integer.toString(p.playerEquipmentN[i]).length());
                characterfile.write("	", 0, 1);
                characterfile.newLine();
            }
            characterfile.newLine();

            /*LOOK*/
            characterfile.write("[LOOK]", 0, 6);
            characterfile.newLine();
            for (int i = 0; i < p.playerAppearance.length; i++) {
                characterfile.write("character-look = ", 0, 17);
                characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
                characterfile.write("	", 0, 1);
                characterfile.write(Integer.toString(p.playerAppearance[i]), 0, Integer.toString(p.playerAppearance[i]).length());
                characterfile.newLine();
            }
            characterfile.newLine();

            /*SKILLS*/
            characterfile.write("[SKILLS]", 0, 8);
            characterfile.newLine();
            for (int i = 0; i < p.playerLevel.length; i++) {
                characterfile.write("character-skill = ", 0, 18);
                characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
                characterfile.write("	", 0, 1);
                characterfile.write(Integer.toString(p.playerLevel[i]), 0, Integer.toString(p.playerLevel[i]).length());
                characterfile.write("	", 0, 1);
                characterfile.write(Integer.toString(p.playerXP[i]), 0, Integer.toString(p.playerXP[i]).length());
                characterfile.newLine();
            }
            characterfile.newLine();

            /*ITEMS*/
            characterfile.write("[ITEMS]", 0, 7);
            characterfile.newLine();
            for (int i = 0; i < p.playerItems.length; i++) {
                if (p.playerItems[i] > 0) {
                    characterfile.write("character-item = ", 0, 17);
                    characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
                    characterfile.write("	", 0, 1);
                    characterfile.write(Integer.toString(p.playerItems[i]), 0, Integer.toString(p.playerItems[i]).length());
                    characterfile.write("	", 0, 1);
                    characterfile.write(Integer.toString(p.playerItemsN[i]), 0, Integer.toString(p.playerItemsN[i]).length());
                    characterfile.newLine();
                }
            }
            characterfile.newLine();

            /*BANK*/
            characterfile.write("[BANK]", 0, 6);
            characterfile.newLine();
            for (int i = 0; i < p.bankItems.length; i++) {
                if (p.bankItems[i] > 0) {
                    characterfile.write("character-bank = ", 0, 17);
                    characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
                    characterfile.write("	", 0, 1);
                    characterfile.write(Integer.toString(p.bankItems[i]), 0, Integer.toString(p.bankItems[i]).length());
                    characterfile.write("	", 0, 1);
                    characterfile.write(Integer.toString(p.bankItemsN[i]), 0, Integer.toString(p.bankItemsN[i]).length());
                    characterfile.newLine();
                }
            }
            characterfile.newLine();

            /*FRIENDS*/
            characterfile.write("[FRIENDS]", 0, 9);
            characterfile.newLine();
            for (int i = 0; i < p.friends.length; i++) {
                if (p.friends[i] > 0) {
                    characterfile.write("character-friend = ", 0, 19);
                    characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
                    characterfile.write("	", 0, 1);
                    characterfile.write("" + p.friends[i]);
                    characterfile.newLine();
                }
            }
            characterfile.newLine();

            /*IGNORES*/
 /*characterfile.write("[IGNORES]", 0, 9);
			characterfile.newLine();
			for (int i = 0; i < ignores.length; i++) {
				if (ignores[i] > 0) {
					characterfile.write("character-ignore = ", 0, 19);
					characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
					characterfile.write("	", 0, 1);
					characterfile.write(Long.toString(ignores[i]), 0, Long.toString(ignores[i]).length());
					characterfile.newLine();
				}
			}
			characterfile.newLine();*/
 /*EOF*/
            characterfile.write("[QUESTS]");
            characterfile.newLine();
            for (RadiantQuest quest : p.radiantQuests) {
                characterfile.write("quest-id = " + quest.id);
                for (RadiantQuestObjective obj : quest.objectives) {
                    characterfile.write("obj-score" + obj.score);
                }
            }
            characterfile.write("[EOF]", 0, 5);
            characterfile.newLine();
            characterfile.newLine();
            characterfile.close();
        } catch (IOException ioexception) {
            Misc.println(p.playerName + ": error writing file.");
            return false;
        }
        return true;
    }

    public static boolean XMLSaver(Client p) {
        BufferedWriter file = null;
        try {
            file = new BufferedWriter(new FileWriter("./Data/characters/" + p.playerName + ".txt"));
            file.write("<player>");
            file.newLine();
            file.write("<username>" + p.playerName + "</username>");
            file.newLine();
            file.write("<password>" + Misc.basicEncrypt(p.playerPass) + "</password>");
            file.newLine();
            file.write("<perks>");
            file.newLine();
            for(Perk perk : p.perks){
                file.write("<stat>" + perk.getId() + "</stat>");
                file.newLine();
            }
            file.write("</perks>");
            file.newLine();
            file.write("<stats>");
            file.newLine();
            for(int i : p.stats){
                file.write("<stat>" + i + "</stat>");
                file.newLine();
            }
            file.write("</stats>");
            file.newLine();
            file.write("<energy>" + p.runEnergy + "</energy>");
            file.newLine();
            file.write("<coords>");
            file.newLine();
            file.write("<x>" + p.absX + "</x>");
            file.newLine();
            file.write("<y>" + p.absY + "</y>");
            file.newLine();
            file.write("<z>" + p.heightLevel + "</z>");
            file.newLine();
            file.write("</coords>");
            file.newLine();
            file.write("<outfit>");
            file.newLine();
            for(int i = 0; i < p.playerEquipment.length; i++){
                file.write("<equipment>");
                file.newLine();
                file.write("<position>" + i + "</position>");
                file.newLine();
                file.write("<equipment>" + p.playerEquipment[i] + "</equipment>");
                file.newLine();
                file.write("<equipmentN>" + p.playerEquipmentN[i] + "</equipmentN>");
                file.newLine();
                file.write("</equipment>");
                file.newLine();
            }
            file.write("</outfit>");
            file.newLine();
            file.write("<look>");
            file.newLine();
            for(int i = 0; i < p.playerAppearance.length; i++){
                file.write("<position>" + i + "</position>");
                file.newLine();
                file.write("<value>" + p.playerAppearance[i] + "</value>");
                file.newLine();
            }
            file.write("</look>");
            file.newLine();
            file.write("<skills>");
            file.newLine();
            for(int i = 0; i < p.playerLevel.length; i++){
                file.write("<skill>");
                file.newLine();
                file.write("<id>" + i + "</id>");
                file.newLine();
                file.write("<level>" + p.playerLevel[i] + "</level>");
                file.newLine();
                file.write("<xp>" + p.playerXP[i] + "</xp>");
                file.newLine();
                file.write("</skill>");
                file.newLine();
            }
            file.write("</skills>");
            file.newLine();
            file.write("<inventory>");
            file.newLine();
            for(int i = 0; i < p.playerItems.length; i++){
                file.write("<slot>");
                file.newLine();
                file.write("<position>" + i + "</position>");
                file.newLine();
                file.write("<id>" + p.playerItems[i] + "</id>");
                file.newLine();
                file.write("<amount>" + p.playerItemsN[i] + "</amount>");
                file.newLine();
                file.write("</slot>");
                file.newLine();
            }
            file.write("</inventory>");
            file.newLine();
            file.write("<bank>");
            file.newLine();
            for(int i = 0; i < p.bankItems.length; i++){
                file.write("<slot>");
                file.newLine();
                file.write("<position>" + i + "</position>");
                file.newLine();
                file.write("<id>" + p.bankItems[i] + "</id>");
                file.newLine();
                file.write("<amount>" + p.bankItemsN[i] + "</amount>");
                file.newLine();
                file.write("</slot>");
                file.newLine();
            }
            file.write("</bank>");
            file.newLine();
            /* HAVE TO IMPLEMENT FRIENDS
            file.write("<friends>");
            file.newLine();
            for(long i = 0; i < p.playerItems.length; i++){
                file.write("<name>" + p.friends + "</name>");
                file.newLine();
            }
            file.write("</friends>");
            */
            file.write("<radiantquests>");
            file.newLine();
            for(RadiantQuest quest : p.radiantQuests){
                file.write("<quest>");
                file.newLine();
                file.write("<id>" + quest.id + "</id>");
                file.newLine();
                for(RadiantQuestObjective obj : quest.objectives){
                    file.write("<score>" + obj.score + "</score>");
                    file.newLine();
                }
                file.write("</quest>");
                file.newLine();
            }
            file.write("</radiantquests>");
            file.newLine();
            file.write("</player>");
            file.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
