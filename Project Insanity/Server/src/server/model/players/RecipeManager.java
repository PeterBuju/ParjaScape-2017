/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model.players;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import server.util.Misc;

/**
 *
 * @author Alex Macocian
 */
public class RecipeManager {
    public static ArrayList<Recipe> Recipes = new ArrayList<>();
  
    public static boolean LoadRecipes(String filename) {
        String line = "";
        String token = "";
        String token2 = "";
        String token2_2 = "";
        String[] token3 = new String[6];
        boolean EndOfFile = false;
        int ReadMode = 0;
        BufferedReader recipefile = null;
        try {
            recipefile = new BufferedReader(new FileReader("./Data/cfg/" + filename));
        } catch (FileNotFoundException fileex) {
            Misc.println(filename + ": file not found.");
            return false;
        }
        try {
            recipefile.readLine();
            line = recipefile.readLine();
        } catch (IOException ioexception) {
            Misc.println(filename + ": error loading file.");
            return false;
        }
        while (EndOfFile == false && line != null) {
            line = line.trim();
            token = line.replaceAll("\t\t", "\t");
            token3 = token.split("\t");
            if (token3.length == 1){
                break;
            }
            try {
                Recipe recipe = new Recipe(Integer.parseInt(token3[1]), Integer.parseInt(token3[2]), Integer.parseInt(token3[3]), Integer.parseInt(token3[4]), Integer.parseInt(token3[5]), Integer.parseInt(token3[6]));
                RecipeManager.Recipes.add(recipe);
                Misc.println_debug("Recipe " + recipe.preCookedId + " Loaded!");
                if (line.equals("[ENDOFITEMLIST]")) {
                    try {
                        recipefile.close();
                    } catch (IOException ioexception) {
                    }
                    return true;
                }
                try {
                    line = recipefile.readLine();
                } catch (IOException ioexception1) {
                    EndOfFile = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            recipefile.close();
        } catch (IOException ioexception) {
        }
        return false;
    }

    public static Recipe GetRecipe(int itemId){
        for(Recipe recipe : Recipes){
            if (recipe.preCookedId == itemId){
                return recipe;
            }
        }
        return null;
    }
}
