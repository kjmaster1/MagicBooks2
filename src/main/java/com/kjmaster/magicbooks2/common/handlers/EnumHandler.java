package com.kjmaster.magicbooks2.common.handlers;

import net.minecraft.util.IStringSerializable;

/**
 * Created by pbill_000 on 11/09/2017.
 */
public class EnumHandler {
    public static enum ShardTypes implements IStringSerializable {
        AIR("air", 0),
        EARTH("earth", 1),
        FIRE("fire", 2),
        WATER("water", 3),
        ARCANE("arcane", 4)
        ;

        private int ID;
        private String name;

        private ShardTypes(String name, int ID) {
            this.ID = ID;
            this.name = name;
        }

        @Override
        public String toString() { return getName(); }

        @Override
        public String getName() { return this.name; }

        public int getID() { return ID; }
    }

    public static enum BookTypes implements IStringSerializable {
        AIR("air", 0),
        EARTH("earth", 1),
        FIRE("fire", 2),
        WATER("water", 3),
        ARCANE("arcane", 4)
        ;

        private int ID;
        private String name;

        private BookTypes(String name, int ID) {
            this.ID = ID;
            this.name = name;
        }

        @Override
        public String toString() { return getName(); }

        @Override
        public String getName() { return this.name; }

        public int getID() { return ID; }
    }
}
