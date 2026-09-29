package com.gestion;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;

public class GestionFactures {
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void genererPdf(Facture facture, String outputFilePath) throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(outputFilePath));
        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
        Font normal = FontFactory.getFont(FontFactory.HELVETICA, 12);

        // Titre
        Paragraph titre = new Paragraph("FACTURE", titleFont);
        titre.setAlignment(Element.ALIGN_CENTER);
        document.add(titre);
        document.add(Chunk.NEWLINE);

        // Infos facture / client
        PdfPTable tableInfo = new PdfPTable(2);
        tableInfo.setWidthPercentage(100);
        tableInfo.setWidths(new int[]{1,2});

        tableInfo.addCell(getCell("Facture ID :", PdfPCell.ALIGN_LEFT));
        tableInfo.addCell(getCell(String.valueOf(facture.getId()), PdfPCell.ALIGN_LEFT));

        tableInfo.addCell(getCell("Date :", PdfPCell.ALIGN_LEFT));
        tableInfo.addCell(getCell(facture.getDate().format(FORMAT), PdfPCell.ALIGN_LEFT));

        tableInfo.addCell(getCell("Client :", PdfPCell.ALIGN_LEFT));
        tableInfo.addCell(getCell(facture.getClient().getPrenom() + " " + facture.getClient().getNom(), PdfPCell.ALIGN_LEFT));

        tableInfo.addCell(getCell("Adresse :", PdfPCell.ALIGN_LEFT));
        tableInfo.addCell(getCell(facture.getClient().getAdresse() + " " + facture.getClient().getCodePostal() + " " + facture.getClient().getVille(), PdfPCell.ALIGN_LEFT));

        document.add(tableInfo);
        document.add(Chunk.NEWLINE);

        // Table lignes
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{3f, 1f, 1f, 1f});
        table.addCell(getHeaderCell("Description"));
        table.addCell(getHeaderCell("Prix U."));
        table.addCell(getHeaderCell("Quantité"));
        table.addCell(getHeaderCell("Total"));

        for (LigneFacture l : facture.getLignes()) {
            table.addCell(getCell(l.getProduit().getDescription(), PdfPCell.ALIGN_LEFT));
            table.addCell(getCell(String.format("%.2f €", l.getProduit().getPrix()), PdfPCell.ALIGN_RIGHT));
            table.addCell(getCell(String.valueOf(l.getQuantite()), PdfPCell.ALIGN_RIGHT));
            table.addCell(getCell(String.format("%.2f €", l.getTotalLigne()), PdfPCell.ALIGN_RIGHT));
        }

        // Total général
        PdfPCell empty = new PdfPCell(new Phrase(""));
        empty.setColspan(3);
        empty.setBorder(Rectangle.NO_BORDER);
        table.addCell(empty);

        PdfPCell totalCell = new PdfPCell(new Phrase(String.format("Total: %.2f €", facture.getTotal()), normal));
        totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(totalCell);

        document.add(table);
        document.close();
    }

    private static PdfPCell getHeaderCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }

    private static PdfPCell getCell(String text, int align) {
        PdfPCell cell = new PdfPCell(new Phrase(text, FontFactory.getFont(FontFactory.HELVETICA, 11)));
        cell.setHorizontalAlignment(align);
        cell.setPadding(5);
        return cell;
    }
}

