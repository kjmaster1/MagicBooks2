package com.kjmaster.magicbooks2.common.capabilities.skillpoints;

/**
 * Created by pbill_000 on 12/09/2017.
 */
public interface ISkillPoints {
    void consumePoints(int points, String type);
    void addPoints(int points, String type);
    void setPoints(int points, String type);

    int getPoints(String type);
}
