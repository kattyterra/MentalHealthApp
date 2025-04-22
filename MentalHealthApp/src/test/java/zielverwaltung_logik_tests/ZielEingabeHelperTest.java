package zielverwaltung_logik_tests;
import org.junit.jupiter.api.Test;
import zielverwaltung_logik.Wiederholungstyp;
import zielverwaltung_logik.Ziel;
import zielverwaltung_logik.ZielEingabeHelper;
import zielverwaltung_logik.ZielKategorie;

import java.time.LocalDate;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class ZielEingabeHelperTest {

    @Test
    void indexAbfragen_gibtIndexZurueckBeiGueltigerEingabe() {
        ZielEingabeHelper helper = new ZielEingabeHelper();
        Scanner scanner = new Scanner("3\n");
        int index = helper.indexAbfragen(scanner);
        assertEquals(2, index);
    }

    @Test
    void indexAbfragen_gibtMinus1ZurueckBeiUngueltigerEingabe() {
        ZielEingabeHelper helper = new ZielEingabeHelper();
        Scanner scanner = new Scanner("abc\n");
        int index = helper.indexAbfragen(scanner);
        assertEquals(-1, index);
    }

    @Test
    void erstelleZielVomBenutzer_liestAlleWerteKorrektEin() {
        String input = String.join("\n",
                "1",                    // Kategorie
                "Mehr trinken",        // Beschreibung
                "2",                   // Priorität
                "TAEGLICH",            // Wiederholung
                "2025-06-01",          // Fälligkeitsdatum
                "Für die Gesundheit"    // Motivationsnotiz
        );

        Scanner scanner = new Scanner(input);
        ZielEingabeHelper helper = new ZielEingabeHelper();
        Ziel ziel = helper.erstelleZielVomBenutzer(scanner);

        assertEquals(ZielKategorie.values()[0], ziel.getKategorie());
        assertEquals("Mehr trinken", ziel.getBeschreibung());
        assertEquals(2, ziel.getPrioritaet());
        assertEquals(Wiederholungstyp.TAEGLICH, ziel.getWiederholung());
        assertEquals(LocalDate.parse("2025-06-01"), ziel.getFaelligkeit());
        assertEquals("Für die Gesundheit", ziel.getMotivationsnotiz());
    }

    @Test
    void erstelleZielVomBenutzer_verwendetStandardDatumWennLeer() {
        String input = String.join("\n",
                "1",                // Kategorie
                "Mehr Schlaf",      // Beschreibung
                "1",                // Priorität
                "MONATLICH",        // Wiederholung
                "",                 // Kein Datum eingegeben
                "Brauche Energie"   // Motivationsnotiz
        );

        Scanner scanner = new Scanner(input);
        ZielEingabeHelper helper = new ZielEingabeHelper();
        Ziel ziel = helper.erstelleZielVomBenutzer(scanner);

        LocalDate expected = LocalDate.now().plusWeeks(1);
        assertEquals(expected, ziel.getFaelligkeit());
    }

    @Test
    void erstelleZielVomBenutzer_verwendetFallbackKategorieBeiUngueltigemIndex() {
        String input = String.join("\n",
                "999",              // Ungültiger Index → letzter gültiger wird genommen
                "Sport machen",     // Beschreibung
                "3",                // Priorität
                "WOECHENTLICH",     // Wiederholung
                "2025-05-01",       // Datum
                "Ziele erreichen"   // Notiz
        );

        Scanner scanner = new Scanner(input);
        ZielEingabeHelper helper = new ZielEingabeHelper();
        Ziel ziel = helper.erstelleZielVomBenutzer(scanner);

        assertEquals(ZielKategorie.values()[ZielKategorie.values().length - 1], ziel.getKategorie());
    }
}
