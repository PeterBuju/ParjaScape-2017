/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model.players;

/**
 *
 * @author Alex Macocian
 */
public class Stats {
    
    static final String[] statNames = {
		"wisdom", "constitution", "speed", "endurance", "intelligence", "dexterity", "charisma" 
	};
    
    static final int[][] StatIdInterface = {
        {12,15,18}, {0,1,3}, {4,9,10}, {2,5,6}, {7, 8, 11, 14}, {13, 16, 17, 20}
    };
    
    public static String GetStatName(int id){
        if (id >= 0 && id <= 6)
            return statNames[id];
        else
            return null;
    }
    
    public static int GetStatId(String name){
        for (int i = 0; i <= 6; i++){
            if (statNames[i].equalsIgnoreCase(name)){
                return i;
            }
        }
        return -1;
    }
    
    public static int GetStatIdOfSkill (int skillId){
        for (int i = 0; i < statNames.length; i++){
            for (int j = 0; j < StatIdInterface[i].length; j++){
                if (skillId == StatIdInterface[i][j])
                    return i;
            }
        }
        return -1;
    }
}
