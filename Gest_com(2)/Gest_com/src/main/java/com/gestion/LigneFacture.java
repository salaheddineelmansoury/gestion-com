package com.gestion;

public class LigneFacture {
    private Produit produit;
    private int quantite;

    public LigneFacture(Produit produit, int quantite) {
        this.produit = produit;
        this.quantite = quantite;
    }

    public Produit getProduit() { return produit; }
    public int getQuantite() { return quantite; }
    public double getTotalLigne() {
        return produit.getPrix() * quantite;
    }

    @Override
    public String toString() {
        return produit.getId() + "|" + quantite;
    }

    public String display() {
        return String.format("%s x%d = %.2f €", produit.getDescription(), quantite, getTotalLigne());
    }
}

