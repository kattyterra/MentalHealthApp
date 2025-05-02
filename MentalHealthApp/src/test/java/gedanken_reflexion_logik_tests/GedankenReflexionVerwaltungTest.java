package gedanken_reflexion_logik_tests;
import gedanken_reflexion_logik.GedankenReflexionEintrag;
import gedanken_reflexion_logik.GedankenReflexionRepository;
import gedanken_reflexion_logik.GedankenReflexionVerwaltung;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class GedankenReflexionVerwaltungTest {

    static class MockGedankenReflexionRepository implements GedankenReflexionRepository {
        boolean speichernAufgerufen = false;
        GedankenReflexionEintrag letzterEintrag;
        List<String> gespeicherteEintraege = new ArrayList<>();

        @Override
        public void speichern(GedankenReflexionEintrag eintrag) {
            speichernAufgerufen = true;
            letzterEintrag = eintrag;
        }

        @Override
        public List<String> lesenAlle() {
            return gespeicherteEintraege;
        }
    }

    private MockGedankenReflexionRepository mockRepo;
    private GedankenReflexionVerwaltung verwaltung;

    @BeforeEach
    void setup() {
        mockRepo = new MockGedankenReflexionRepository();
        verwaltung = new GedankenReflexionVerwaltung(mockRepo);
    }

    @Test
    void neuerEintrag_speichertEintragMitScannerInput() {
        String simulatedInput = String.join("\n",
                "7",
                "Ich bin müde",
                "Ich habe wenig geschlafen",
                "Ich kann früher ins Bett gehen",
                "Ich schaffe das",
                "Ich gönne mir eine Pause") + "\n";

        Scanner scanner = new Scanner(simulatedInput);

        verwaltung.neuerEintrag(scanner);

        assertTrue(mockRepo.speichernAufgerufen);
        assertNotNull(mockRepo.letzterEintrag);
        assertEquals("7", mockRepo.letzterEintrag.belastung());
        assertEquals(5, mockRepo.letzterEintrag.antworten().size());
        assertEquals("Ich bin müde", mockRepo.letzterEintrag.antworten().getFirst());
    }

    @Test
    void alleEintraegeAnzeigen_ohneEintraege_gibtHinweis() {
        mockRepo.gespeicherteEintraege.clear();
        verwaltung.alleEintraegeAnzeigen();
    }

    @Test
    void alleEintraegeAnzeigen_mitEintraegen_gibtAus() {
        mockRepo.gespeicherteEintraege.add("2025-04-25 10:00 - Belastung: 6");
        mockRepo.gespeicherteEintraege.add("Was beschäftigt dich gerade? Testfrage.");
        verwaltung.alleEintraegeAnzeigen();
    }
}
