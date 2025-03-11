package StimmungskalenderLogik;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StimmungskalenderVerwaltung {
    private final StimmungskalenderRepository repository;

    public StimmungskalenderVerwaltung(StimmungskalenderRepository repository) {
        this.repository = repository;
    }

    public void eintragHinzufuegen(Scanner scanner) {
        String datum = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String uhrzeit = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        int stimmung;
        while (true) {
            System.out.print("Wie ist deine Stimmung (1 = schlecht, 10 = sehr gut)? ");
            try {
                stimmung = Integer.parseInt(scanner.nextLine());
                if (stimmung >= 1 && stimmung <= 10) break;
                else System.out.println("Bitte eine Zahl zwischen 1 und 10 eingeben.");
            } catch (NumberFormatException e) {
                System.out.println("Ungültige Eingabe. Bitte eine Zahl eingeben.");
            }
        }

        Stimmungseintrag eintrag = new Stimmungseintrag(datum, uhrzeit, stimmung);
        repository.speichern(eintrag);
        System.out.println("Stimmungseintrag gespeichert!");
        warnungBeiTiefemStimmungsverlauf();
    }

    public void eintraegeAlsGraphAnzeigen() {
        List<String> eintraege = repository.lesenAlle();

        if (eintraege.isEmpty()) {
            System.out.println("Keine Einträge vorhanden.");
            return;
        }

        System.out.println("\nStimmungskalender:");

        for (String eintrag : eintraege) {
            if (eintrag.contains("Stimmung:")) {
                String[] teile = eintrag.split("Stimmung:");
                if (teile.length < 2) continue;

                String beschriftung = teile[0].trim();
                int stimmung;
                try {
                    stimmung = Integer.parseInt(teile[1].trim());
                } catch (NumberFormatException e) {
                    stimmung = 0;
                }

                String balken = "▇".repeat(stimmung);
                System.out.println(beschriftung + " " + balken + " " + "(" + stimmung +")");
            }
        }
    }

    public void warnungBeiTiefemStimmungsverlauf() {
        List<String> eintraege = repository.lesenAlle();
        if (eintraege.isEmpty()) return;

        List<Integer> letzte7Stimmungen = new ArrayList<>();

        for (int i = eintraege.size() - 1; i >= 0 && letzte7Stimmungen.size() < 7; i--) {
            String eintrag = eintraege.get(i);
            if (eintrag.contains("Stimmung:")) {
                String[] teile = eintrag.split("Stimmung:");
                try {
                    int stimmung = Integer.parseInt(teile[1].trim());
                    letzte7Stimmungen.add(stimmung);
                } catch (NumberFormatException ignored) {}
            }
        }

        if (letzte7Stimmungen.size() == 7 &&
                letzte7Stimmungen.stream().allMatch(wert -> wert < 5)) {
            System.out.println("\n⚠️ Hinweis: Deine Stimmung war in den letzten 7 Tagen " +
                    "durchgehend unter 5.");
            System.out.println("Vielleicht wäre es hilfreich, mit jemandem darüber zu sprechen " +
                    "oder dir etwas Gutes zu tun 💛");
        }
    }

    public void emotionenErfassen(Scanner scanner) {
        List<Emotionseintrag> emotionseintraege = new ArrayList<>();

        List<String> emotionenListe;
        try {
            emotionenListe = Files.readAllLines(Paths.get("Textvorlagen(nicht_ändern!)/EmotionenListe.txt"));
        } catch (IOException e) {
            System.err.println("Fehler beim Lesen der EmotionenListe.txt: " + e.getMessage());
            return;
        }

        System.out.println("\nVerfügbare Emotionen:");
        List<String> emotionenNamen = new ArrayList<>();
        for (String zeile : emotionenListe) {
            if (zeile.startsWith("Emotion:")) {
                String[] teile = zeile.split(";");
                String name = teile[0].replace("Emotion:", "").trim();
                emotionenNamen.add(name);
                System.out.println(emotionenNamen.size() + ". " + name);
            }
        }

        while (true) {
            System.out.print("\nNummer der Emotion wählen (oder 0 zum Beenden): ");
            String eingabe = scanner.nextLine();
            if (eingabe.equals("0")) break;

            try {
                int index = Integer.parseInt(eingabe) - 1;
                if (index < 0 || index >= emotionenNamen.size()) {
                    System.out.println("Ungültige Auswahl.");
                    continue;
                }

                String emotion = emotionenNamen.get(index);

                int intensitaet;

                while (true) {
                    String beschreibung = getIntensitaetsbeschreibungZuEmotion(emotion, emotionenListe);
                    System.out.print("Wie stark fühlst du diese Emotion (1-10)? (" + beschreibung + "): ");
                    try {
                        intensitaet = Integer.parseInt(scanner.nextLine());
                        if (intensitaet >= 1 && intensitaet <= 10) break;
                        else System.out.println("Bitte eine Zahl von 1 bis 10 eingeben.");
                    } catch (NumberFormatException e) {
                        System.out.println("Ungültige Zahl.");
                    }
                }

                System.out.print("Was ist die Ursache für diese Emotion? ");
                String ursache = scanner.nextLine();

                emotionseintraege.add(new Emotionseintrag(emotion, intensitaet, ursache));
            } catch (NumberFormatException e) {
                System.out.println("Ungültige Eingabe.");
            }
        }

        if (!emotionseintraege.isEmpty()) {
            if (repository instanceof StimmungskalenderSpeicher speicher) {
                speicher.speichernEmotionen(emotionseintraege);
                System.out.println("Emotionen gespeichert.");
            }
        }
    }

    private String getIntensitaetsbeschreibungZuEmotion(String emotionName, List<String> emotionenListe) {
        for (String zeile : emotionenListe) {
            if (zeile.startsWith("Emotion:") && zeile.contains(emotionName)) {
                String[] teile = zeile.split("Intensität:");
                if (teile.length > 1) {
                    return teile[1].replace(";", "").trim();
                }
            }
        }
        return "keine Beschreibung gefunden";
    }

    public void eintraegeTextuellAnzeigen() {
        List<String> eintraege = repository.lesenAlle();

        if (eintraege.isEmpty()) {
            System.out.println("Keine Einträge vorhanden.");
            return;
        }

        System.out.println("\nDein Stimmungskalender:");

        String letztesDatum = "";
        for (String zeile : eintraege) {
            if (zeile.contains("- Stimmung:")) {
                String[] teile = zeile.split(" - Stimmung:");
                String datumUhrzeit = teile[0].trim();
                String stimmung = teile[1].trim();

                String aktuellesDatum = "";
                String uhrzeit = datumUhrzeit;

                if (datumUhrzeit.length() >= 10) {
                    aktuellesDatum = datumUhrzeit.substring(0, 10);
                    if (datumUhrzeit.length() > 11) {
                        uhrzeit = datumUhrzeit.substring(11);
                    }
                }

                if (!aktuellesDatum.equals(letztesDatum)) {
                    System.out.println("\n===== " + aktuellesDatum + " =====");
                    System.out.println(uhrzeit + " - Stimmung: " + stimmung);
                    letztesDatum = aktuellesDatum;
                } else {
                    System.out.println(uhrzeit + " - Stimmung: " + stimmung);
                }

            } else if (zeile.startsWith("- Emotion:")) {
                System.out.println(zeile);
            }
        }
    }

}