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
     * @param ziele Liste aller Ziele
     */
    public void zeigeStatistik(List<Ziel> ziele) {
        System.out.println(generiereStatistikText(ziele));
    }

    public String generiereStatistikText(List<Ziel> ziele) {
        long erledigt = ziele.stream().filter(Ziel::isErledigt).count();
        StringBuilder sb = new StringBuilder();
        sb.append("\n✅ Erledigte Ziele: ").append(erledigt).append(" von ").append(ziele.size());

        Map<ZielKategorie, Long> proKategorie = ziele.stream()
                .filter(Ziel::isErledigt)
                .collect(Collectors.groupingBy(Ziel::getKategorie, Collectors.counting()));

        sb.append("\n📊 Zielerreichung nach Kategorien:");
        for (Map.Entry<ZielKategorie, Long> entry : proKategorie.entrySet()) {
            sb.append("\n - ").append(entry.getKey().name()).append(": ").append(entry.getValue()).append(" erledigt");
        }
        return sb.toString();
    }
}
