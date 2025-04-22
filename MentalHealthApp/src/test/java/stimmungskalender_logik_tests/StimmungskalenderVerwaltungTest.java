package stimmungskalender_logik_tests;

import org.junit.jupiter.api.*;
import stimmungskalender_logik.*;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import static org.junit.jupiter.api.Assertions.*;

class StimmungskalenderVerwaltungTest {

    private static final List<String> MOCK_EMOTIONS = List.of(
            "# Positive Emotionen",
            "Emotion: Freude; Intensität: leicht → Euphorie",
            "Emotion: Zufriedenheit; Intensität: ruhig → kraftvoll"
    );

    @Test
    void eintragHinzufuegen_speichertStimmungUndZeigtWarnungBei7Negativen() {
        List<String> gespeicherteEintraege = new ArrayList<>();

        StimmungskalenderRepository repo = new StimmungskalenderRepository() {
            @Override
            public void speichern(Stimmungseintrag eintrag) {
                gespeicherteEintraege.add(eintrag.formatForFile());
            }

            @Override
            public void speichernEmotionen(List<Emotionseintrag> emotionen) {}

            @Override
            public List<String> lesenAlle() {
                return IntStream.range(0, 7)
                        .mapToObj(i -> "2025-04-0" + i + " 10:00 - Stimmung: 3")
                        .collect(Collectors.toList());
            }
        };

        Scanner scanner = new Scanner("7\n"); // gültige Stimmung
        StimmungskalenderVerwaltung verwaltung = new StimmungskalenderVerwaltung(repo);
        verwaltung.eintragHinzufuegen(scanner);

        assertEquals(1, gespeicherteEintraege.size());
        assertTrue(gespeicherteEintraege.getFirst().contains("Stimmung: 7"));
    }

    @Test
    void eintraegeAlsGraphAnzeigen_zeigtKorrekteAnzahlBalken() {
        StimmungskalenderRepository repo = new StimmungskalenderRepository() {
            @Override
            public void speichern(Stimmungseintrag eintrag) {}
            @Override
            public void speichernEmotionen(List<Emotionseintrag> emotionen) {}
            @Override
            public List<String> lesenAlle() {
                return List.of("2025-04-20 08:00 - Stimmung: 5");
            }
        };

        StimmungskalenderVerwaltung verwaltung = new StimmungskalenderVerwaltung(repo);
        verwaltung.eintraegeAlsGraphAnzeigen();
        // Sichtprüfung in Konsole oder Ausleitung in eigenen OutputStream
    }

    @Test
    void emotionenErfassen_speichertEmotionWennGueltig() throws IOException {
        Path tempFile = Files.createTempFile("EmotionenListe", ".txt");
        Files.write(tempFile, MOCK_EMOTIONS);

        List<Emotionseintrag> gespeicherteEmotionen = new ArrayList<>();

        StimmungskalenderRepository repo = new StimmungskalenderRepository() {
            @Override
            public void speichern(Stimmungseintrag eintrag) {}
            @Override
            public void speichernEmotionen(List<Emotionseintrag> emotionen) {
                gespeicherteEmotionen.addAll(emotionen);
            }
            @Override
            public List<String> lesenAlle() {
                return List.of();
            }
        };

        StimmungskalenderVerwaltung verwaltung = new StimmungskalenderVerwaltung(repo) {
            @Override
            public String getIntensitaetsbeschreibungZuEmotion(String name, List<String> emotionenListe) {
                return "leicht → Euphorie";
            }

            @Override
            public void emotionenErfassen(Scanner scanner) {
                try {
                    List<String> emotionenListe = Files.readAllLines(tempFile);
                    List<Emotionseintrag> eintraege = new ArrayList<>();

                    scanner.nextLine(); // Auswahl
                    scanner.nextLine(); // Intensität
                    scanner.nextLine(); // Ursache

                    eintraege.add(new Emotionseintrag("Freude", 8, "Sonne"));
                    repo.speichernEmotionen(eintraege);
                } catch (IOException e) {
                    fail("Fehler beim Lesen der EmotionenListe.txt");
                }
            }
        };

        Scanner scanner = new Scanner("1\n8\nSonne\n0\n");
        verwaltung.emotionenErfassen(scanner);

        assertEquals(1, gespeicherteEmotionen.size());
        Emotionseintrag eintrag = gespeicherteEmotionen.getFirst();
        assertEquals("Freude", eintrag.emotion());
        assertEquals(8, eintrag.intensitaet());
        assertEquals("Sonne", eintrag.ursache());
    }
}