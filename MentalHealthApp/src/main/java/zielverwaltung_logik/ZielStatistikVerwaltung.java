package zielverwaltung_logik;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Serviceklasse für statistische Auswertungen der Zielsetzung.
 */
public class ZielStatistikVerwaltung {

    /**
     * Zeigt eine Auswertung der Zielerreichung an.
     * Enthält Gesamtanzahl, erledigte Ziele und Anzahl pro Kategorie.
     *
     * @param ziele Liste aller Ziele
     */
    public void zeigeStatistik(List<Ziel> ziele) {
        long erledigt = ziele.stream()
                .filter(Ziel::isErledigt)
                .count();

        System.out.println("\n✅ Erledigte Ziele: " + erledigt + " von " + ziele.size());

        Map<ZielKategorie, Long> proKategorie = ziele.stream()
                .filter(Ziel::isErledigt)
                .collect(Collectors.groupingBy(Ziel::getKategorie, Collectors.counting()));

        System.out.println("\n📊 Zielerreichung nach Kategorien:");
        for (Map.Entry<ZielKategorie, Long> entry : proKategorie.entrySet()) {
            System.out.println(" - " + entry.getKey().name() + ": " + entry.getValue() + " erledigt");
        }
    }
}
