package routinen_logik_tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import routinen_logik.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class RoutinenVerwaltungTest {

    static class MockRepository implements RoutineRepository {
        List<Routine> gespeicherteRoutinen = new ArrayList<>();
        boolean speichernAufgerufen = false;

        @Override
        public void hinzufuegen(Routine routine) {
            gespeicherteRoutinen.add(routine);
        }

        @Override
        public List<Routine> getRoutinen() {
            return new ArrayList<>(gespeicherteRoutinen);
        }

        @Override
        public void bearbeiten(int index, RoutinenArt neueArt, String neueBeschreibung) {
            gespeicherteRoutinen.set(index, new Routine(neueArt, neueBeschreibung));
        }

        @Override
        public void loeschen(int index) {
            gespeicherteRoutinen.remove(index);
        }

        @Override
        public void speichern() {
            speichernAufgerufen = true;
        }
    }

    static class MockVorschlagsService extends RoutineVorschlagsService {
        public MockVorschlagsService() {
            super("test.txt");
        }

        @Override
        public List<String> getVorschlaegeZuArt(RoutinenArt art) {
            return List.of("Trinken", "Meditation");
        }
    }

    MockRepository repo;
    RoutinenVerwaltung verwaltung;

    @BeforeEach
    void setup() {
        repo = new MockRepository();
        verwaltung = new RoutinenVerwaltung(repo, new MockVorschlagsService());
    }

    @Test
    void routineHinzufuegen_mitEigenerBeschreibung() throws RoutineException {
        Scanner scanner = new Scanner("1\n1\nZähne putzen\n");
        verwaltung.routineHinzufuegen(scanner);

        assertEquals(1, repo.getRoutinen().size());
        assertEquals("Zähne putzen", repo.getRoutinen().getFirst().getBeschreibung());
    }

    @Test
    void routineHinzufuegen_mitVorschlag() throws RoutineException {
        Scanner scanner = new Scanner("1\n2\n1\n");
        verwaltung.routineHinzufuegen(scanner);

        assertEquals(1, repo.getRoutinen().size());
        assertEquals("Trinken", repo.getRoutinen().getFirst().getBeschreibung());
    }

    @Test
    void routineBearbeiten_aendertDaten() throws RoutineException {
        repo.hinzufuegen(new Routine(RoutinenArt.MORGEN, "Spazieren"));

        Scanner scanner = new Scanner("1\n2\n2\n1\n");
        verwaltung.routineBearbeiten(scanner);

        assertEquals("Trinken", repo.getRoutinen().getFirst().getBeschreibung());
        assertEquals(RoutinenArt.MITTAG, repo.getRoutinen().getFirst().getArt());
    }

    @Test
    void routineLoeschen_entferntEintrag() throws RoutineException {
        repo.hinzufuegen(new Routine(RoutinenArt.MORGEN, "Joggen"));

        Scanner scanner = new Scanner("1\n");
        verwaltung.routineLoeschen(scanner);

        assertTrue(repo.getRoutinen().isEmpty());
    }
}
