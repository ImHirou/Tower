package me.secretlovers.tower.boss;

import java.util.ArrayList;

public class BossPool extends ArrayList<Boss> {

    public void init() {
        add(new Boss("minotaur", 1));
        add(new Boss("bee", 2));
        add(new Boss("fish", 3));
    }

    public Boss BossByFloor(int floor) {
        for(Boss boss : this) {
            if(boss.getFloor()==floor) return boss;
        }
        return null;
    }

}
