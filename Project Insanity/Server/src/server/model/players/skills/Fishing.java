package server.model.players.skills;

import server.Config;
import server.model.objects.FishBank;
import server.util.Misc;
import server.model.players.Client;
import static server.model.players.packets.Commands.SendSyntaxError;
import server.world.FishBankManager;

/**
 * Fishing.java
 *
 * @author Sanity
 *
 *
 */
public class Fishing {

    private Client c;
    private int fishType;
    private int exp;
    private int req;
    private int equipmentType;
    private final int SALMON_EXP = 70;
    private final int SWORD_EXP = 100;
    private final int SALMON_ID = 331;
    private final int SWORD_ID = 371;
    public boolean fishing = false;

    private final int[] REQS = {1, 20, 40, 35, 62, 76, 81};
    private final int[] FISH_TYPES = {635, 1496, 3417, 3419, 3657, 3913, 389};
    private final int[] EXP = {10, 50, 80, 90, 120, 110, 46};

    public Fishing(Client c) {
        this.c = c;
    }

    public void setupFishing(int fishType) {
        if (c.getItems().playerHasItem(getEquipment(fishType))) {
            if (c.playerLevel[c.playerFishing] >= req) {
                int slot = getSlot(fishType);
                if (slot > -1) {
                    this.req = REQS[slot];
                    this.fishType = FISH_TYPES[slot];
                    this.equipmentType = getEquipment(fishType);
                    this.exp = EXP[slot];
                    c.fishing = true;
                    c.fishTimer = 3;
                    Animate();
                }
            } else {
                c.sendMessage("You need a fishing level of " + req + " to fish here.");
                resetFishing();
            }
        } else {
            c.sendMessage("You do not have the correct equipment to use this fishing spot.");
            resetFishing();
        }
    }

    public void catchFish() {
        if (c.getItems().playerHasItem(getEquipment(fishType))) {
            if (c.playerLevel[c.playerFishing] >= req) {
                if (c.getItems().freeSlots() > 0) {
                    if (canFishOther(fishType)) {
                        c.getItems().addItem(otherFishId(fishType), 1);
                        c.getPA().addSkillXP(otherFishXP(fishType), c.playerFishing);
                    } else {
                        c.getItems().addItem(fishType, 1);
                        c.getPA().addSkillXP(exp * Config.FISHING_EXPERIENCE, c.playerFishing);
                    }
                    c.sendMessage("You catch a fish.");
                }
            } else {
                c.sendMessage("You need a fishing level of " + req + " to fish here.");
                resetFishing();
            }
        } else {
            c.sendMessage("You do not have the correct equipment to use this fishing spot.");
            resetFishing();
        }
    }

    public void Animate(){
        switch (getEquipment(fishType)) {
            case 301:   //LOBSTER POT
                c.startAnimation(619);
                break;
            case 303:   //SMALL FISHING NET
                c.startAnimation(621);
                break;
            case 305:   //BIG FISHING NET
                c.startAnimation(620);
                break;
            case 307:    //FISHING ROD
                c.startAnimation(622);
                break;
            case 309:   //FLY FISHING ROD
                c.startAnimation(623);
                break;
            case 311:   //HARPOON
                c.startAnimation(618);
                break;
        }
    }
    
    public void Fish() {
        Animate();
        c.fishTimer = 3;
        if (Misc.random(10) > 4) {
            if (c.getItems().freeSlots() > 0) {
                catchFish();
            } else {
                resetFishing();
            }
        }
    }

    private int getSlot(int fishType) {
        for (int j = 0; j < REQS.length; j++) {
            if (FISH_TYPES[j] == fishType) {
                return j;
            }
        }
        return -1;
    }

    private int getEquipment(int fish) {
        if (fish == 317) //shrimp
        {
            return 303;
        }
        if (fish == 335) //trout + salmon
        {
            return 309;
        }
        if (fish == 337) //lobs
        {
            return 301;
        }
        if (fish == 361)//tuna
        {
            return 311;
        }
        if (fish == 7944)//monks
        {
            return 303;
        }
        if (fish == 383)//sharks
        {
            return 311;
        }
        if (fish == 389)//mantas
        {
            return 303;
        }
        return -1;
    }

    private boolean canFishOther(int fishType) {
        if (fishType == 335 && c.playerLevel[c.playerFishing] >= 30) {
            return true;
        }
        if (fishType == 361 && c.playerLevel[c.playerFishing] >= 50) {
            return true;
        }
        return false;
    }

    private int otherFishId(int fishType) {
        if (fishType == 335) {
            return SALMON_ID;
        } else if (fishType == 361) {
            return SWORD_ID;
        }
        return -1;
    }

    private int otherFishXP(int fishType) {
        if (fishType == 335) {
            return SALMON_EXP;
        } else if (fishType == 361) {
            return SWORD_EXP;
        }
        return 0;
    }

    public void resetFishing() {
        this.exp = 0;
        this.fishType = -1;
        this.equipmentType = -1;
        this.req = 0;
        c.fishTimer = -1;
        c.fishing = false;
        c.startAnimation(Config.IdleEmote);
    }
 
    public static void ParseCommand(Client c, String command) {
        String[] args = command.split(" ");
        if (args[0].equalsIgnoreCase("help")) {
            if (args.length == 1) {
                c.sendMessage("Use ::fishing.commands for a list of commands");
                c.sendMessage("Use ::fishing.help <command> for help regarding a command");
            } else if (args.length == 2) {
                if (args[1].equalsIgnoreCase("showFishBanks")) {
                    c.sendMessage("Usage: ::fishing.showFishBanks");
                    c.sendMessage("Shows a list of currently managed fishbanks");
                } else if (args[1].equalsIgnoreCase("showFishBanks")) {
                    c.sendMessage("Usage: ::fishing.respawnFishBank [<id>]");
                    c.sendMessage("Forces the specified fishbank to respawn. If no id is specified,");
                    c.sendMessage("it forces all fishbanks to respawn");
                } else {
                    SendSyntaxError(c, "fishing.help <command>");
                }
            } else {
                SendSyntaxError(c, "fishing.help <command>");
            }
        } else if (args[0].equalsIgnoreCase("commands")){
            c.sendMessage("Fishing commands:");
            c.sendMessage(" showFishBanks");
            c.sendMessage(" respawnFishBank");
        } else if (args[0].equalsIgnoreCase("showFishBanks")) {
            for (int i = 0; i < FishBankManager.fishBanks.size(); i++) {
                FishBank fishbank = FishBankManager.fishBanks.get(i);
                double timeToRespawn = fishbank.GetRespawnTime() - (System.currentTimeMillis() - fishbank.GetRespawnTimer());
                c.sendMessage("Id: " + i + " InitId: " + fishbank.GetNPC().npcType + " PosX: " + fishbank.GetCurX() + " PosY: " + fishbank.GetCurY() + "Respawning: " + timeToRespawn + "ms");
            }
        } else if (args[0].equalsIgnoreCase("respawnFishBank")) {
            if (args.length == 2){
                try{
                    FishBankManager.RespawnFishBank(FishBankManager.fishBanks.get(Integer.parseInt(args[1])));
                }
                catch(Exception e){
                    SendSyntaxError(c, "fishing.respawnFishBank [<id>]");
                }
            }
            else{
                for(FishBank fishbank : FishBankManager.fishBanks){
                    FishBankManager.RespawnFishBank(fishbank);
                }
            }
        } else {
            SendSyntaxError(c, "fishing.help");
        }
    }
}
