package gedanken_reflexion_logik;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GedankenReflexionVerwaltung {
    private final GedankenReflexionRepository repository;

    public GedankenReflexionVerwaltung(GedankenReflexionRepository repository) {
        this.repository = repository;
    }

    public void neuerEintrag(Scanner scanner) {
        String datum = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
        String uhrzeit = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        List<String> antworten = new ArrayList<>();

        System.out.println("Was beschäftigt dich gerade?");
        antworten.add(scanner.nextLine());

        System.out.print("Wie stark belastet dich das (1–10)? ");
        String belastung = scanner.nextLine();

        System.out.println("Was spricht objektiv dafür?");
        antworten.add(scanner.nextLine());

        System.out.println("Was spricht dagegen?");
        antworten.add(scanner.nextLine());

        System.out.println("Welche neue Sichtweise könntest du einnehmen?");
        antworten.add(scanner.nextLine());

        System.out.println("Was könntest du dir jetzt Gutes tun?");
        antworten.add(scanner.nextLine());

        GedankenReflexionEintrag eintrag = new GedankenReflexionEintrag(datum, uhrzeit, belastung, antworten);
        repository.speichern(eintrag);
        System.out.println("✅ Reflexion gespeichert.");
    }

    public void alleEintraegeAnzeigen() {
        List<String> eintraege = repository.lesenAlle();
        if (eintraege.isEmpty()) {
            System.out.println("Keine Einträge vorhanden.");
            return;
        }
        eintraege.forEach(System.out::println);
    }
}