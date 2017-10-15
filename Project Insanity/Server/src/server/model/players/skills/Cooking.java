package server.model.players.skills;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import server.model.players.Client;
import server.util.Misc;
import server.Config;
import server.model.players.Recipe;
import server.model.players.RecipeManager;

public class Cooking {

    Client c;
    int objectId;
    SecureRandom cookingRandom = new SecureRandom();
    
    public Cooking(Client c) {
        this.c = c;
    }
    private int[][] cookingItems = {{317, 315, 7954, 1, 30}, {335, 333, 323, 20, 70}, {331, 329, 323, 30, 90}, {359, 361, 363, 35, 100}, {377, 379, 381, 40, 120}, {371, 373, 375, 50, 140}, {7944, 7946, 7948, 62, 150}, {383, 385, 387, 80, 210}, {389, 391, 393, 91, 169}};

    public void itemOnObject(int id, int objectId) {
        for (int j = 0; j < cookingItems.length; j++) {
            if (cookingItems[j][0] == id) {
                cookFish(cookingItems[j][0], j);
                this.objectId = objectId;
            }
        }
    }

    public void setupCooking() {
        if (c.getItems().playerHasItem(c.currentRecipe.preCookedId)) {
            if (c.playerLevel[c.playerCooking] >= c.currentRecipe.requiredLevel) {
                c.cookingTimer = 1;
                c.isCooking = true;
            } else {
                c.sendMessage("You need a cooking level of " + c.currentRecipe.requiredLevel + " to cook this fish.");
            }
        } else {
        }
    }
    
    public void cookFish(int id, int slot) {
        for (int j = 0; j < 28; j++) {
            if (c.getItems().playerHasItem(id, 1)) {
                if (c.playerLevel[c.playerCooking] >= cookingItems[slot][3]) {
                    c.currentRecipe = RecipeManager.GetRecipe(id);
                    c.cookingTimer = 1;
                    c.isCooking = true;
                } else {
                    c.sendMessage("You need a cooking level of " + cookingItems[slot][3] + " to cook this fish.");
                    break;
                }
            } else {
                break;
            }
        }
    }

    public void ResetCooking(){
        c.isCooking = false;
    }
    
    public int CalculateBurnBonus(){
        if (objectId == 114){
            return 5;
        }
        return 0;
    }
    
    public boolean isBurnt() {
        if (c.playerLevel[c.playerCooking] < c.currentRecipe.stopBurnLevel) {
            double burn_chance = (55.0 - CalculateBurnBonus());
            double cook_level = (double) c.playerLevel[c.playerCooking];
            double lev_needed = (double) c.currentRecipe.requiredLevel;
            double burn_stop = (double) c.currentRecipe.stopBurnLevel;
            double multi_a = (burn_stop - lev_needed);
            double burn_dec = (burn_chance / multi_a);
            double multi_b = (cook_level - lev_needed);
            burn_chance -= (multi_b * burn_dec);
            double randNum = cookingRandom.nextDouble() * 100.0;
            // "With a " + burn_chance + "% chance of burning.."
            if (burn_chance <= randNum) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }    
    
    public void Cook() {
        if (c.getItems().playerHasItem(c.currentRecipe.preCookedId) && c.cookAmount > 0) {
            c.getItems().deleteItem(c.currentRecipe.preCookedId, c.getItems().getItemSlot(c.currentRecipe.preCookedId), 1);
            if (isBurnt()) {
                c.sendMessage("You accidently burn " + c.getItems().getItemName(c.currentRecipe.succesfulCookedId));                
                c.getItems().addItem(c.currentRecipe.failedCookedId, 1);
            } else {
                c.sendMessage("You cooked a " + c.getItems().getItemName(c.currentRecipe.succesfulCookedId));
                c.getItems().addItem(c.currentRecipe.succesfulCookedId, 1);
                c.getPA().addSkillXP(c.currentRecipe.gainExperience * Config.COOKING_EXPERIENCE, c.playerCooking);
            }
            if (objectId == 114){
                c.startAnimation(883);
            }
            else{
                c.startAnimation(897);
            }
            c.cookingTimer = 3;
            c.cookAmount--;
        }
    }

}
