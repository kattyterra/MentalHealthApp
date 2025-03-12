package gedanken_reflexion_logik;

import java.util.*;
public record GedankenReflexionEintrag(String datum, String uhrzeit, String belastung, List<String> antworten) {
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
