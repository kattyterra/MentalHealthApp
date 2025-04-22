package zielverwaltung_logik_tests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import zielverwaltung_logik.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class ZielVerwaltungTest {

    private Ziel testZiel;
    private MockZielRepository mockRepo;
    private ZielVerwaltung verwaltung;

    static class MockZielRepository implements ZielRepository {
        public boolean speichernAufgerufen = false;
        List<Ziel> gespeicherteZiele = new ArrayList<>();

        @Override
        public void speichern(List<Ziel> ziele) {
            speichernAufgerufen = true;
            gespeicherteZiele = new ArrayList<>(ziele);
        }

        @Override
        public List<Ziel> laden() {
            return new ArrayList<>();
        }
    }

    @BeforeEach
    void setup() {
        testZiel = new Ziel(ZielKategorie.GESUNDHEIT, "Test-Ziel", 1, LocalDate.now(), Wiederholungstyp.KEINE);
        testZiel.setMotivationsnotiz("Test-Notiz");
        mockRepo = new MockZielRepository();
        verwaltung = new ZielVerwaltung(mockRepo);
    }

    @Test
    void hinzufuegen_fuegtZielHinzuUndSpeichert() {
        verwaltung.hinzufuegen(testZiel);
        assertTrue(mockRepo.speichernAufgerufen);
        assertTrue(verwaltung.getZiele().contains(testZiel));
    }

    @Test
    void abhaken_setztErledigtTrueUndSpeichert() {
        verwaltung.hinzufuegen(testZiel);
        verwaltung.abhaken(0);
        assertTrue(verwaltung.getZiele().getFirst().isErledigt());
        assertTrue(mockRepo.speichernAufgerufen);
    }

    @Test
    void bearbeiten_aktualisiertZielUndSpeichert() {
        verwaltung.hinzufuegen(testZiel);
        Ziel aktualisiert = new Ziel(ZielKategorie.GESUNDHEIT, "Neu", 2, LocalDate.now().plusDays(1), Wiederholungstyp.TAEGLICH);
        aktualisiert.setMotivationsnotiz("Neu Notiz");

        verwaltung.bearbeiten(0, aktualisiert);
        Ziel bearbeitet = verwaltung.getZiele().getFirst();

        assertEquals("Neu", bearbeitet.getBeschreibung());
        assertEquals(2, bearbeitet.getPrioritaet());
        assertEquals(Wiederholungstyp.TAEGLICH, bearbeitet.getWiederholung());
        assertEquals("Neu Notiz", bearbeitet.getMotivationsnotiz());
        assertTrue(mockRepo.speichernAufgerufen);
    }

    @Test
    void loeschen_entferntZielUndSpeichert() {
        verwaltung.hinzufuegen(testZiel);
        verwaltung.loeschen(0);
        assertTrue(verwaltung.getZiele().isEmpty());
        assertTrue(mockRepo.speichernAufgerufen);
    }

    @Test
    void filterNachKategorie_liefertNurPassendeZiele() {
        verwaltung.hinzufuegen(testZiel);
        List<Ziel> gefiltert = verwaltung.filterNachKategorie("GESUNDHEIT");
        assertEquals(1, gefiltert.size());
        assertEquals(testZiel, gefiltert.getFirst());
    }

    @Test
    void filterNachKategorie_beiUngueltigemString_gibtLeereListeZurueck() {
        List<Ziel> gefiltert = verwaltung.filterNachKategorie("UNGUELTIG");
        assertTrue(gefiltert.isEmpty());
    }

    @Test
    void filterNachStatus_liefertNurErledigteOderOffeneZiele() {
        verwaltung.hinzufuegen(testZiel);
        testZiel.setErledigt(true);
        List<Ziel> erledigt = verwaltung.filterNachStatus(true, false);
        assertEquals(1, erledigt.size());
        List<Ziel> offen = verwaltung.filterNachStatus(false, false);
        assertEquals(0, offen.size());
    }

    @Test
    void filterNachKategorieMitAuswahl_beiGueltigerEingabe() {
        verwaltung.hinzufuegen(testZiel);
        Scanner scanner = new Scanner("1\n");
        List<Ziel> gefiltert = verwaltung.filterNachKategorieMitAuswahl(scanner);
        assertEquals(1, gefiltert.size());
    }

    @Test
    void filterNachKategorieMitAuswahl_beiUngueltigerEingabe() {
        Scanner scanner = new Scanner("abc\n");
        List<Ziel> gefiltert = verwaltung.filterNachKategorieMitAuswahl(scanner);
        assertTrue(gefiltert.isEmpty());
    }
}