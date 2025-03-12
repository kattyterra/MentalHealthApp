package zielverwaltung_logik;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Datei-basierte Implementierung des ZielRepository-Interfaces.
 * Speichert Ziele zeilenweise in einer Textdatei.
 */
public class DateibasierterZielRepository implements ZielRepository {
    private final String ordner = "Ziele/";
    private final String pfad = ordner + "ziele.txt";

    public DateibasierterZielRepository() {
        File dir = new File(ordner);
        if (!dir.exists()) {
            boolean success = dir.mkdirs();
            if (!success) {
                System.err.println("\nOrdner '" + ordner + "' konnte nicht erstellt werden!");
            }
        }
    }

    @Override
    public void speichern(List<Ziel> ziele) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pfad))) {
            for (Ziel ziel : ziele) {
                writer.write(ziel.getKategorie().name()); writer.newLine();
                writer.write(ziel.getBeschreibung()); writer.newLine();
                writer.write(String.valueOf(ziel.getPrioritaet())); writer.newLine();
                writer.write(String.valueOf(ziel.isErledigt())); writer.newLine();
                writer.write(ziel.getFaelligkeit().toString()); writer.newLine();
                writer.write(ziel.getWiederholung().name()); writer.newLine();
                writer.write(ziel.getMotivationsnotiz()); writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Speichern der Ziele: " + e.getMessage());
        }
    }

    @Override
    public List<Ziel> laden() {
        List<Ziel> ziele = new ArrayList<>();
        File file = new File(pfad);

        if (!file.exists()) {
            System.out.println("Noch keine Ziele vorhanden");
            return ziele; // Noch keine Datei vorhanden
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while (true) {
                String zeile = reader.readLine();
                if (zeile == null) break; // Datei-Ende erreicht

                ZielKategorie kategorie = ZielKategorie.valueOf(zeile);
                String beschreibung = reader.readLine();
                int prioritaet = Integer.parseInt(reader.readLine());
                boolean erledigt = Boolean.parseBoolean(reader.readLine());
                LocalDate faelligkeit = LocalDate.parse(reader.readLine());
                Wiederholungstyp wiederholung = Wiederholungstyp.valueOf(reader.readLine());
                String motivationsnotiz = reader.readLine();

                Ziel ziel = new Ziel(kategorie, beschreibung, prioritaet, faelligkeit, wiederholung);
                ziel.setErledigt(erledigt);
                ziel.setMotivationsnotiz(motivationsnotiz);
                ziele.add(ziel);
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Fehler beim Laden der Ziele: " + e.getMessage());
        }

        return ziele;
    }
}