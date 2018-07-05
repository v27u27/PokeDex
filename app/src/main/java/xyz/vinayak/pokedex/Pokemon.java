package xyz.vinayak.pokedex;

public class Pokemon {
    String name, imageUrl;
    int id;

    public Pokemon(String name, String imageUrl, int id) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getId() {
        return id;
    }
}
