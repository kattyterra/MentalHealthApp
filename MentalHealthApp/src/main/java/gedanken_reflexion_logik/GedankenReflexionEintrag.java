package gedanken_reflexion_logik;

import java.util.*;

/**
 * Repräsentiert einen einzelnen Eintrag der geführten Gedankenreflexion
 * (aus dem Modul „Gedankenkarussell stoppen“).
 *
 * Jeder Eintrag enthält:
 * - das Datum der Reflexion,
 * - die Uhrzeit,
 * - eine subjektive Belastungsskala (1–10),
 * - eine Liste von Antworten auf strukturierte Reflexionsfragen.
 */
public record GedankenReflexionEintrag(String datum, String uhrzeit, String belastung, List<String> antworten) {

    /**
     * Formatiert den Eintrag zur textlichen Speicherung in einer Datei.
     * Die Ausgabe erfolgt in strukturierter, menschenlesbarer Form.
     *
     * @return formatierter String zur Ablage in einer Textdatei.
     */
    public String formatForFile() {
        StringBuilder sb = new StringBuilder();
        sb.append("🧠 Gedankenreflexion – Eintrag vom ").append(datum).append(" ").append(uhrzeit).append("\n");
        sb.append("Belastungsskala: ").append(belastung).append("/10\n");

        int nr = 1;
        for (String a : antworten) {
            sb.append(nr++).append(") ").append(a).append("\n");
        }

        sb.append("==============================\n");
        return sb.toString();
    }
}
