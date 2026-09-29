# Gestion commerciale

Programme Java en ligne de commande (interface console, sans interface graphique) pour gérer des clients, des produits et des factures, avec sauvegarde des données dans des fichiers CSV et **export des factures en PDF**. Projet réalisé pour mettre en pratique la **programmation orientée objet (POO)**.

## Fonctionnalités

Menu principal avec trois modules :

**Clients**
- Ajouter, lister, rechercher (par ID) et supprimer un client

**Produits**
- Ajouter, lister, rechercher (par ID) et supprimer un produit

**Factures**
- Créer une facture : choix du client, puis ajout de plusieurs produits avec leur quantité
- Lister les factures, afficher le détail d'une facture (lignes et total)
- Supprimer une facture
- **Générer le PDF** d'une facture (fichier `facture_<id>.pdf`)

Les identifiants sont générés automatiquement. Les données sont sauvegardées à la demande ou automatiquement en quittant, puis rechargées au démarrage.

## Modélisation POO

| Classe | Rôle |
|---|---|
| `Client` | Informations d'un client (nom, prénom, email, adresse, code postal, ville) |
| `Produit` | Description et prix d'un produit |
| `LigneFacture` | Un produit et sa quantité, avec calcul du total de la ligne |
| `Facture` | Date, client et liste de lignes, avec calcul du total |
| `GestionFichiers` | Lecture et écriture des fichiers CSV (clients, produits, factures) |
| `GestionFactures` | Génération du PDF d'une facture avec la bibliothèque iText |
| `Main` | Menus et interaction avec l'utilisateur |

Notions mises en pratique :

- Composition d'objets (une `Facture` contient un `Client` et plusieurs `LigneFacture`, qui référencent chacune un `Produit`)
- Encapsulation (attributs privés, accesseurs)
- Collections Java (`List`, `ArrayList`, `Map`, `HashMap`) et API Stream (`filter`, `mapToInt`, `removeIf`)
- API `java.nio.file` pour la lecture/écriture de fichiers et `java.time` pour la gestion des dates
- Gestion des exceptions
- Génération de documents PDF avec une bibliothèque externe (iText)
- Gestion des dépendances avec **Maven**

## Structure du projet

```
Gest_com/
├── pom.xml
└── src/main/java/com/gestion/
    ├── Main.java
    ├── Client.java
    ├── Produit.java
    ├── LigneFacture.java
    ├── Facture.java
    ├── GestionFichiers.java
    └── GestionFactures.java
```

## Technologies

- Java 24
- Maven
- iText 5.5.13.3 (génération de PDF)

## Fichiers de données

Créés automatiquement dans le dossier d'exécution lors de la première sauvegarde :

- `clients.csv` : `id;nom;prenom;email;adresse;codePostal;ville`
- `produits.csv` : `id;description;prix`
- `factures.csv` : `id;date;idClient;idProduit|quantite,idProduit|quantite,...`

## Lancer le projet

Prérequis : JDK 24 (ou modifier la version dans le `pom.xml`) et Maven.

```
mvn compile
mvn exec:java -Dexec.mainClass="com.gestion.Main"
```

Le projet s'ouvre aussi directement dans IntelliJ IDEA (Maven charge iText automatiquement) : lancer la classe `com.gestion.Main`.

## Pistes d'amélioration

- Ajouter une interface graphique (JavaFX ou Swing)
- Modifier un client ou un produit existant
- Gérer les erreurs de saisie (valeurs non numériques)
- Remplacer les fichiers CSV par une base de données (SQL)
- Ajouter la TVA et un numéro de facture personnalisé

## Auteur

Salah Eddine EL MANSOURY – Étudiant L3 MIAGE, Université de Toulouse
[LinkedIn](https://linkedin.com/in/salah-eddine-el-mansoury-1686a82b4/)
