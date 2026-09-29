package com.gestion;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static final Path CLIENTS_FILE = Paths.get("clients.csv");
    private static final Path PRODUITS_FILE = Paths.get("produits.csv");
    private static final Path FACTURES_FILE = Paths.get("factures.csv");

    private static List<Client> clients = new ArrayList<>();
    private static List<Produit> produits = new ArrayList<>();
    private static List<Facture> factures = new ArrayList<>();

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            clients = GestionFichiers.chargerClients(CLIENTS_FILE);
            produits = GestionFichiers.chargerProduits(PRODUITS_FILE);
            factures = GestionFichiers.chargerFactures(FACTURES_FILE, clients, produits);
        } catch (Exception e) {
            System.out.println("Erreur au chargement des fichiers: " + e.getMessage());
        }

        boolean running = true;
        while (running) {
            showMenu();
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": gererClients(); break;
                case "2": gererProduits(); break;
                case "3": gererFactures(); break;
                case "4": sauvegarderTout(); break;
                case "0": running = false; sauvegarderTout(); break;
                default: System.out.println("Choix invalide.");
            }
        }
        System.out.println("Au revoir.");
    }

    private static void showMenu() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1. Gestion clients");
        System.out.println("2. Gestion produits");
        System.out.println("3. Gestion factures");
        System.out.println("4. Sauvegarder fichiers");
        System.out.println("0. Quitter (sauvegarde automatique)");
        System.out.print("Choix: ");
    }

    // --- Clients ---
    private static void gererClients() {
        while (true) {
            System.out.println("\n-- Gestion Clients --");
            System.out.println("1. Ajouter client");
            System.out.println("2. Lister clients");
            System.out.println("3. Rechercher client par ID");
            System.out.println("4. Supprimer client");
            System.out.println("0. Retour");
            System.out.print("Choice: ");
            String c = sc.nextLine().trim();
            switch (c) {
                case "1": ajouterClient(); break;
                case "2": listerClients(); break;
                case "3": rechercherClient(); break;
                case "4": supprimerClient(); break;
                case "0": return;
                default: System.out.println("Choix invalide.");
            }
        }
    }

    private static void ajouterClient() {
        int newId = clients.stream().mapToInt(Client::getId).max().orElse(0) + 1;
        System.out.print("Nom: "); String nom = sc.nextLine().trim();
        System.out.print("Prénom: "); String prenom = sc.nextLine().trim();
        System.out.print("Email: "); String email = sc.nextLine().trim();
        System.out.print("Adresse: "); String adresse = sc.nextLine().trim();
        System.out.print("Code postal: "); String cp = sc.nextLine().trim();
        System.out.print("Ville: "); String ville = sc.nextLine().trim();
        Client c = new Client(newId, nom, prenom, email, adresse, cp, ville);
        clients.add(c);
        System.out.println("Client ajouté: " + c.display());
    }

    private static void listerClients() {
        if (clients.isEmpty()) { System.out.println("Aucun client."); return; }
        clients.forEach(c -> System.out.println(c.display()));
    }

    private static void rechercherClient() {
        System.out.print("ID client: ");
        int id = Integer.parseInt(sc.nextLine().trim());
        clients.stream().filter(c -> c.getId() == id).findFirst()
                .ifPresentOrElse(c -> System.out.println(c.display()), () -> System.out.println("Client non trouvé"));
    }

    private static void supprimerClient() {
        System.out.print("ID client à supprimer: ");
        int id = Integer.parseInt(sc.nextLine().trim());
        boolean removed = clients.removeIf(c -> c.getId() == id);
        System.out.println(removed ? "Client supprimé." : "Client introuvable.");
    }

    // --- Produits ---
    private static void gererProduits() {
        while (true) {
            System.out.println("\n-- Gestion Produits --");
            System.out.println("1. Ajouter produit");
            System.out.println("2. Lister produits");
            System.out.println("3. Rechercher produit par ID");
            System.out.println("4. Supprimer produit");
            System.out.println("0. Retour");
            System.out.print("Choice: ");
            String c = sc.nextLine().trim();
            switch (c) {
                case "1": ajouterProduit(); break;
                case "2": listerProduits(); break;
                case "3": rechercherProduit(); break;
                case "4": supprimerProduit(); break;
                case "0": return;
                default: System.out.println("Choix invalide.");
            }
        }
    }

    private static void ajouterProduit() {
        int newId = produits.stream().mapToInt(Produit::getId).max().orElse(0) + 1;
        System.out.print("Description: "); String desc = sc.nextLine().trim();
        System.out.print("Prix: "); double prix = Double.parseDouble(sc.nextLine().trim());
        Produit p = new Produit(newId, desc, prix);
        produits.add(p);
        System.out.println("Produit ajouté: " + p.display());
    }

    private static void listerProduits() {
        if (produits.isEmpty()) { System.out.println("Aucun produit."); return; }
        produits.forEach(p -> System.out.println(p.display()));
    }

    private static void rechercherProduit() {
        System.out.print("ID produit: ");
        int id = Integer.parseInt(sc.nextLine().trim());
        produits.stream().filter(p -> p.getId() == id).findFirst()
                .ifPresentOrElse(p -> System.out.println(p.display()), () -> System.out.println("Produit non trouvé"));
    }

    private static void supprimerProduit() {
        System.out.print("ID produit à supprimer: ");
        int id = Integer.parseInt(sc.nextLine().trim());
        boolean removed = produits.removeIf(p -> p.getId() == id);
        System.out.println(removed ? "Produit supprimé." : "Produit introuvable.");
    }

    // --- Factures ---
    private static void gererFactures() {
        while (true) {
            System.out.println("\n-- Gestion Factures --");
            System.out.println("1. Créer une facture");
            System.out.println("2. Lister factures");
            System.out.println("3. Afficher facture par ID");
            System.out.println("4. Supprimer facture");
            System.out.println("5. Générer PDF d'une facture");
            System.out.println("0. Retour");
            System.out.print("Choice: ");
            String c = sc.nextLine().trim();
            switch (c) {
                case "1": creerFacture(); break;
                case "2": listerFactures(); break;
                case "3": afficherFacture(); break;
                case "4": supprimerFacture(); break;
                case "5": genererPdfFacture(); break;
                case "0": return;
                default: System.out.println("Choix invalide.");
            }
        }
    }

    private static void creerFacture() {
        int newId = factures.stream().mapToInt(Facture::getId).max().orElse(0) + 1;
        System.out.print("ID client: ");
        int idClient = Integer.parseInt(sc.nextLine().trim());
        Optional<Client> optClient = clients.stream().filter(c -> c.getId() == idClient).findFirst();
        if (!optClient.isPresent()) { System.out.println("Client introuvable."); return; }
        Facture f = new Facture(newId, LocalDate.now(), optClient.get());

        // ajouter produits en boucle
        while (true) {
            System.out.print("ID produit à ajouter (ou 0 pour terminer): ");
            int idProd = Integer.parseInt(sc.nextLine().trim());
            if (idProd == 0) break;
            Optional<Produit> optProd = produits.stream().filter(p -> p.getId() == idProd).findFirst();
            if (!optProd.isPresent()) { System.out.println("Produit introuvable."); continue; }
            System.out.print("Quantité: ");
            int q = Integer.parseInt(sc.nextLine().trim());
            f.addLigne(new LigneFacture(optProd.get(), q));
            System.out.println("Ajouté: " + optProd.get().getDescription() + " x" + q);
        }
        factures.add(f);
        System.out.println("Facture créée:\n" + f.display());
    }

    private static void listerFactures() {
        if (factures.isEmpty()) { System.out.println("Aucune facture."); return; }
        factures.forEach(f -> System.out.println("ID:" + f.getId() + " - Date:" + f.getDate() + " - Client:" + f.getClient().getPrenom() + " " + f.getClient().getNom() + " - Total: " + String.format("%.2f €", f.getTotal())));
    }

    private static void afficherFacture() {
        System.out.print("ID facture: ");
        int id = Integer.parseInt(sc.nextLine().trim());
        factures.stream().filter(f -> f.getId() == id).findFirst()
                .ifPresentOrElse(f -> System.out.println(f.display()), () -> System.out.println("Facture non trouvée"));
    }

    private static void supprimerFacture() {
        System.out.print("ID facture à supprimer: ");
        int id = Integer.parseInt(sc.nextLine().trim());
        boolean removed = factures.removeIf(f -> f.getId() == id);
        System.out.println(removed ? "Facture supprimée." : "Facture introuvable.");
    }

    private static void genererPdfFacture() {
        System.out.print("ID facture à exporter en PDF: ");
        int id = Integer.parseInt(sc.nextLine().trim());
        Optional<Facture> opt = factures.stream().filter(f -> f.getId() == id).findFirst();
        if (!opt.isPresent()) { System.out.println("Facture introuvable."); return; }
        String filename = "facture_" + id + ".pdf";
        try {
            GestionFactures.genererPdf(opt.get(), filename);
            System.out.println("PDF généré: " + filename);
        } catch (Exception e) {
            System.out.println("Erreur génération PDF: " + e.getMessage());
        }
    }

    // sauvegarde fichiers
    private static void sauvegarderTout() {
        try {
            GestionFichiers.enregistrerClients(CLIENTS_FILE, clients);
            GestionFichiers.enregistrerProduits(PRODUITS_FILE, produits);
            GestionFichiers.enregistrerFactures(FACTURES_FILE, factures);
            System.out.println("Fichiers sauvegardés.");
        } catch (Exception e) {
            System.out.println("Erreur sauvegarde: " + e.getMessage());
        }
    }
}

