package com.gestion;

public class Produit {
    private int id;
    private String description;
    private double prix;

    public Produit(int id, String description, double prix) {
        this.id = id;
        this.description = description;
        this.prix = prix;
    }

    public int getId() { return id; }
    public String getDescription() { return description; }
    public double getPrix() { return prix; }

    @Override
    public String toString() {
        return id + ";" + description + ";" + prix;
    }

    public String display() {
        return String.format("ID:%d - %s - %.2f €", id, description, prix);
    }
}
