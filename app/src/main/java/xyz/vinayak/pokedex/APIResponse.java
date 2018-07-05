package xyz.vinayak.pokedex;

import java.util.ArrayList;

public class APIResponse {

    // Forms -----------------------------------------------------

    ArrayList<Forms> forms;

    public class Forms {
        String name;

        public String getName() {
            return name;
        }
    }

    public ArrayList<Forms> getForms() {
        return forms;
    }

    // Abilities -----------------------------------------------------

    ArrayList<Abilities> abilities;

    public class Abilities {
        Ability ability;

        public class Ability {
            String name;

            public String getName() {
                return name;
            }
        }

        public Ability getAbility() {
            return ability;
        }
    }

    public ArrayList<Abilities> getAbilities() {
        return abilities;
    }

    // Stats -----------------------------------------------------


    ArrayList<Stats> stats;

    public class Stats {
        Stat stat;
        int base_stat;

        public class Stat {
            String name;

            public String getName() {
                return name;
            }
        }

        public Stat getStat() {
            return stat;
        }

        public int getBase_stat() {
            return base_stat;
        }
    }

    public ArrayList<Stats> getStats() {
        return stats;
    }


    // Name -----------------------------------------------------

    String name;

    public String getName() {
        return name;
    }

    // Weight -----------------------------------------------------

    int weight;

    public int getWeight() {
        return weight;
    }

    // Height -----------------------------------------------------

    int height;

    public int getHeight() {
        return height;
    }

    // Id -----------------------------------------------------

    int id;

    public int getId() {
        return id;
    }

    // Order -----------------------------------------------------

    int order;

    public int getOrder() {
        return order;
    }

    // base_experience -----------------------------------------------------

    int base_experience;

    public int getBase_experience() {
        return base_experience;
    }

    // Moves -----------------------------------------------------

    ArrayList<Moves> moves;


    public class Moves {
        Move move;

        public class Move {
            String name;

            public String getName() {
                return name;
            }
        }

        public Move getMove() {
            return move;
        }
    }

    public ArrayList<Moves> getMoves() {
        return moves;
    }

    // Sprites -----------------------------------------------------

    Sprites sprites;

    public class Sprites {
        String front_default;

        public String getFront_default() {
            return front_default;
        }
    }

    public Sprites getSprites() {
        return sprites;
    }

    // Types -----------------------------------------------------

    ArrayList<Types> types;

    public class Types {
        int slot;
        Type type;

        public class Type{
            String name;

            public String getName() {
                return name;
            }
        }

        public int getSlot() {
            return slot;
        }

        public Type getType() {
            return type;
        }
    }

    public ArrayList<Types> getTypes() {
        return types;
    }

}
