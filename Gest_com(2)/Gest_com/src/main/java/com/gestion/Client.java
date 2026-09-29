package com.gestion;

public class Client {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String adresse;
    private String codePostal;
    private String ville;

    public Client(int id, String nom, String prenom, String email, String adresse, String codePostal, String ville) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.adresse = adresse;
        this.codePostal = codePostal;
        this.ville = ville;
    }

    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getEmail() { return email; }
    public String getAdresse() { return adresse; }
    public String getCodePostal() { return codePostal; }
    public String getVille() { return ville; }

    @Override
    public String toString() {
        return id + ";" + nom + ";" + prenom + ";" + email + ";" + adresse + ";" + codePostal + ";" + ville;
    }

    public String display() {
        return String.format("ID:%d - %s %s - %s - %s %s", id, prenom, nom, email, codePostal, ville);
    }
}

