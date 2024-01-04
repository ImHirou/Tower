package me.secretlovers.tower.boss;

public class Boss {
    private String name;
    private int floor;

    public Boss(String name, int floor) {
        this.name=name;
        this.floor=floor;
    }

    public String getName() {
        return name;
    }

    public int getFloor() {
        return floor;
    }
}
