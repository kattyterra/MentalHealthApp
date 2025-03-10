package StimmungskalenderLogik;

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
    }

    public void eintraegeAlsGraphAnzeigen() {
        List<String> eintraege = repository.lesenAlle();

        if (eintraege.isEmpty()) {
            System.out.println("📭 Keine Einträge vorhanden.");
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
}
