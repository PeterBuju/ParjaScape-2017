/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model.objects;

import server.model.players.Client;
import server.util.Misc;
import server.world.RockManager;

/**
 *
 * @author Alex Macocian
 */
public class BaseObject {
    int posX, posY, initobjId, usedobjId;
    double respawnTimer, lastClickedTimer;
    boolean isUsed;
    int charges;
    int MAXCHARGES = 5;
    
    public BaseObject(Client c){
        posX = c.objectX;
        posY = c.objectY;
        initobjId = c.objectId;
        isUsed = false;
        usedobjId = InitializeUsedObjId(initobjId);
        SetLastClickedTimer();
    }
    
    void SetMaxCharges(int val){
        MAXCHARGES = val;
    }
    
    void SetCharges(){
        charges = Misc.random(MAXCHARGES);
        if (charges == 0){
            charges = 1;
        }
    }
    
    void SetLastClickedTimer(){
        lastClickedTimer = System.currentTimeMillis();
    }
  
    public int InitializeUsedObjId(int objId) {
        switch (objId) {
            case 2090:
            case 2092:
            case 2094:
            case 2098:
            case 2100:
            case 2102:
            case 2104:
                return 450;
            case 2091:
            case 2093:
            case 2095:
            case 2099:
            case 2101:
            case 2103:
            case 2105:
                return 451;
            case 1276:
            case 1278:
            case 1315:
            case 1307:
            case 4674:
                return 1342;
            case 1282:
            case 1283:
                return 1347;
            case 1284:
                return 1350;
            case 1286:
            case 1289:
            case 1291:
            case 1384:
                return 1352;
            case 1318:
            case 1319:
            case 1316:
            case 1330:
            case 1331:
            case 1332:
                return 1355;
            case 1281:
                return 1356;
            case 1308:
                return 7399;
            case 5551:
            case 5552:
            case 5553:
                return 5554;
            case 1309:
                return 7402;
            case 1277:
            case 1279:
            case 1280:
            case 1285:
            case 1287:
            case 1288:
            case 1290:
            case 1301:
            case 1303:
            case 1304:
            case 1305:
            case 1333:
            case 1383:
            case 2409:
            case 2447:
            case 2448:
            case 3033:
            case 3034:
            case 3035:
            case 3036:
            case 3879:
            case 3881:
            case 3883:
            case 3893:
            case 3885:
            case 3886:
            case 3887:
            case 3888:
            case 3889:
            case 3890:
            case 3891:
            case 3892:
            case 3967:
            case 3968:
            case 4048:
            case 4049:
            case 4050:
            case 4051:
            case 4052:
            case 4053:
            case 4054:
            case 4060:
            case 5004:
            case 5005:
            case 5045:
            case 5902:
            case 5903:
            case 5904:
            case 8973:
            case 8974:
            case 3037:
            case 8462:
            case 8463:
            case 8464:
            case 8465:
            case 8466:
            case 8467:
            case 10083:
            case 13413:
            case 13420:
            case 8481:
            case 8482:
            case 8483:
            case 8484:
            case 8485:
            case 8486:
            case 8487:
            case 8488:
            case 8496:
            case 8497:
            case 8498:
            case 8499:
            case 8500:
            case 8501:
            case 8435:
            case 8436:
            case 8437:
            case 8438:
            case 8439:
            case 8440:
            case 8441:
            case 8442:
            case 8443:
            case 8444:
            case 8454:
            case 8455:
            case 8456:
            case 8457:
            case 8458:
            case 8459:
            case 8460:
            case 8461:
            case 8503:
            case 8504:
            case 8505:
            case 8506:
            case 8507:
            case 8508:
            case 8509:
            case 8510:
            case 8511:
            case 8512:
            case 8513:
            case 1306:
            case 8396:
            case 8397:
            case 8398:
            case 8399:
            case 8400:
            case 8401:
            case 8402:
            case 8403:
            case 8404:
            case 8405:
            case 8406:
            case 8407:
            case 8408:
            case 8409:
                return 1341;
        }
        return -1;
    }
    
    public void Deplete(){
        isUsed = true;
        respawnTimer = System.currentTimeMillis();
    }
    
    public void Respawn(){
        isUsed = false;
        SetCharges();
    }
    
    public int GetPosX(){
        return posX;
    }
    
    public int GetPosY(){
        return posY;
    }
    
    public int GetInitObjId(){
        return initobjId;
    }
    
    public int GetUsedObjId(){
        return usedobjId;
    }
    
    public int GetCharges(){
        return charges;
    }
    
    public boolean IsUsed(){
        return isUsed;
    }
    
    public double GetRespawnTimer(){
        return respawnTimer;
    }
    
    public double GetLastClickedTimer(){
        return lastClickedTimer;
    }
    
}
