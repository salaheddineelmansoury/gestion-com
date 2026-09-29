package com.gestion;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class GestionFichiers {
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Clients
    public static List<Client> chargerClients(Path path) throws IOException {
        List<Client> clients = new ArrayList<>();
        if (!Files.exists(path)) return clients;
        List<String> lines = Files.readAllLines(path);
        for (String line : lines) {
            if (line.trim().isEmpty()) continue;
            String[] parts = line.split(";");
            if (parts.length < 7) continue;
            int id = Integer.parseInt(parts[0]);
            clients.add(new Client(id, parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]));
        }
        return clients;
    }

    public static void enregistrerClients(Path path, List<Client> clients) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Client c : clients) lines.add(c.toString());
        Files.write(path, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    // Produits
    public static List<Produit> chargerProduits(Path path) throws IOException {
        List<Produit> produits = new ArrayList<>();
        if (!Files.exists(path)) return produits;
        List<String> lines = Files.readAllLines(path);
        for (String line : lines) {
            if (line.trim().isEmpty()) continue;
            String[] parts = line.split(";");
            if (parts.length < 3) continue;
            int id = Integer.parseInt(parts[0]);
            double prix = Double.parseDouble(parts[2]);
            produits.add(new Produit(id, parts[1], prix));
        }
        return produits;
    }

    public static void enregistrerProduits(Path path, List<Produit> produits) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Produit p : produits) lines.add(p.toString());
        Files.write(path, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    // Factures
    public static List<Facture> chargerFactures(Path path, List<Client> clients, List<Produit> produits) throws IOException {
        List<Facture> factures = new ArrayList<>();
        if (!Files.exists(path)) return factures;
        Map<Integer, Client> mapClient = new HashMap<>();
        for (Client c : clients) mapClient.put(c.getId(), c);
        Map<Integer, Produit> mapProduit = new HashMap<>();
        for (Produit p : produits) mapProduit.put(p.getId(), p);

        List<String> lines = Files.readAllLines(path);
        for (String line : lines) {
            if (line.trim().isEmpty()) continue;
            String[] parts = line.split(";");
            if (parts.length < 4) continue;
            int idFact = Integer.parseInt(parts[0]);
            LocalDate date = LocalDate.parse(parts[1], FORMAT);
            int idClient = Integer.parseInt(parts[2]);
            Client client = mapClient.get(idClient);
            if (client == null) continue; // client inconnu -> skip
            Facture f = new Facture(idFact, date, client);
            String lignesPart = parts[3];
            // lignes séparées par ",", et chaque ligne: idProduit|quantite
            String[] items = lignesPart.split(",");
            for (String item : items) {
                if (item.trim().isEmpty()) continue;
                String[] kv = item.split("\\|");
                if (kv.length != 2) continue;
                int idProd = Integer.parseInt(kv[0]);
                int qte = Integer.parseInt(kv[1]);
                Produit prod = mapProduit.get(idProd);
                if (prod != null) {
                    f.addLigne(new LigneFacture(prod, qte));
                }
            }
            factures.add(f);
        }
        return factures;
    }

    public static void enregistrerFactures(Path path, List<Facture> factures) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Facture f : factures) lines.add(f.toString());
        Files.write(path, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
}

