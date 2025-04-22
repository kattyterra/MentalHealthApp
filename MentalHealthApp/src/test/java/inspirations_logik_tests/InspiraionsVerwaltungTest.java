package inspirations_logik_tests;

import inspirations_logik.InspirationsRepository;
import inspirations_logik.InspirationsVerwaltung;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InspirationsVerwaltungTest {

    @Test
    void gibtZufaelligenSatzWennListeVorhanden() {
        // Fake-Repository mit 3 Sätzen
        InspirationsRepository fakeRepo = () -> List.of("Satz A", "Satz B", "Satz C");
        InspirationsVerwaltung verwaltung = new InspirationsVerwaltung(fakeRepo);

        String ergebnis = verwaltung.gibZufaelligenSatz();

        assertTrue(List.of("Satz A", "Satz B", "Satz C").contains(ergebnis),
                "Der zurückgegebene Satz sollte aus der Repository-Liste stammen");
    }

    @Test
    void gibtHinweisWennListeLeer() {
        InspirationsRepository leeresRepo = List::of; // gibt leere Liste zurück
        InspirationsVerwaltung verwaltung = new InspirationsVerwaltung(leeresRepo);

        String ergebnis = verwaltung.gibZufaelligenSatz();

        assertEquals("Keine Inspirationssätze verfügbar.", ergebnis);
    }
}
