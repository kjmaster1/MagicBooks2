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

    public static enum WandTypes implements IStringSerializable {
        AIR("air", 0),
        EARTH("earth", 1),
        FIRE("fire", 2),
        WATER("water", 3),
        ARCANE("arcane", 4),
        ULTIMATE("ultimate", 5)
        ;

        private int ID;
        private String name;

        private WandTypes(String name, int ID) {
            this.ID = ID;
            this.name = name;
        }

        @Override
        public String toString() { return getName(); }

        @Override
        public String getName() { return this.name; }

        public int getID() { return ID; }
    }

    public static enum AirSpellTypes implements IStringSerializable {
        LIGHTNING("lightning", 0),
        INVISIBILITY("invisibility", 1)
        ;

        private int ID;
        private String name;

        private AirSpellTypes(String name, int iD) {
            this.ID = iD;
            this.name = name;
        }

        @Override
        public String toString() {
            return getName();
        }


        @Override
        public String getName() {
            return this.name;
        }

        public int getID() { return ID; }
    }

    public static enum EarthSpellTypes implements IStringSerializable {
        GROW("grow", 0),
        WALLING("walling", 1)
        ;

        private int ID;
        private String name;

        private EarthSpellTypes(String name, int iD) {
            this.ID = iD;
            this.name = name;
        }

        @Override
        public String toString() {
            return getName();
        }


        @Override
        public String getName() {
            return this.name;
        }

        public int getID() { return ID; }
    }

    public static enum FireSpellTypes implements IStringSerializable {
        FIREBLAST("fireblast", 0),
        ;

        private int ID;
        private String name;

        private FireSpellTypes(String name, int iD) {
            this.ID = iD;
            this.name = name;
        }

        @Override
        public String toString() {
            return getName();
        }


        @Override
        public String getName() {
            return this.name;
        }

        public int getID() { return ID; }
    }
}
