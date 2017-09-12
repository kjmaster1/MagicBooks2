package com.kjmaster.magicbooks2.common.capabilities.skillpoints;

/**
 * Created by pbill_000 on 12/09/2017.
 */
public interface ISkillPoints {
    public void consumePoints(int points, String type);
    public void addPoints(int points, String type);
    public void setPoints(int points, String type);

    public int getPoints(String type);
}
