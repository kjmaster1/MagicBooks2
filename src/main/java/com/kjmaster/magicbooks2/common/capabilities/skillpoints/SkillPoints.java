package com.kjmaster.magicbooks2.common.capabilities.skillpoints;

/**
 * Created by pbill_000 on 12/09/2017.
 */
public class SkillPoints implements ISkillPoints {

    public static String[] pointTypes = {"Air", "Arcane", "Earth", "Fire", "Water"};

    private int airPoints = 1;
    private int arcanePoints = 1;
    private int earthPoints = 1;
    private int firePoints = 1;
    private int waterPoints = 1;

    @Override
    public void addPoints(int points, String type) {
        int newPoints = points + getPoints(type);
        setPoints(newPoints, type);
    }

    @Override
    public void consumePoints(int points, String type) {
        int newPoints = getPoints(type) - points;
        setPoints(newPoints, type);
    }

    @Override
    public void setPoints(int points, String type) {
        switch (type) {
            case "Air":
                this.airPoints = points;
                break;
            case "Arcane":
                this.arcanePoints = points;
                break;
            case "Earth":
                this.earthPoints = points;
                break;
            case "Fire":
                this.firePoints = points;
                break;
            case "Water":
                this.waterPoints = points;
                break;
            default:
                break;
        }
    }

    @Override
    public int getPoints(String type) {
        switch (type) {
            case "Air":
                return this.airPoints;
            case "Arcane":
                return this.arcanePoints;
            case "Earth":
                return this.earthPoints;
            case "Fire":
                return this.firePoints;
            case "Water":
                return this.waterPoints;
            default:
                return 0;
        }
    }
}
