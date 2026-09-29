package com.gestion;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Facture {
    private int id;
    private LocalDate date;
    private Client client;
    private List<LigneFacture> lignes = new ArrayList<>();

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Facture(int id, LocalDate date, Client client) {
        this.id = id;
        this.date = date;
        this.client = client;
    }

    public int getId() { return id; }
    public LocalDate getDate() { return date; }
    public Client getClient() { return client; }
    public List<LigneFacture> getLignes() { return lignes; }

    public void addLigne(LigneFacture ligne) {
        lignes.add(ligne);
    }

    public double getTotal() {
        return lignes.stream().mapToDouble(LigneFacture::getTotalLigne).sum();
    }

    // ligne format pour sauvegarde: idproduit|quantite,idproduit|quantite,...
    public String lignesToCsvPart() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lignes.size(); i++) {
            sb.append(lignes.get(i).toString());
            if (i < lignes.size() - 1) sb.append(",");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return id + ";" + date.format(FORMAT) + ";" + client.getId() + ";" + lignesToCsvPart();
    }

    public String display() {
        StringBuilder sb = new StringBuilder();
        sb.append("Facture ID: ").append(id).append("\n");
        sb.append("Date: ").append(date.format(FORMAT)).append("\n");
        sb.append("Client: ").append(client.display()).append("\n");
        sb.append("Lignes:\n");
        for (LigneFacture l : lignes) {
            sb.append("  - ").append(l.display()).append("\n");
        }
        sb.append(String.format("Total: %.2f €\n", getTotal()));
        return sb.toString();
    }
}

