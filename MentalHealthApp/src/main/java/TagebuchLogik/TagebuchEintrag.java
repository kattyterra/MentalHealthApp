package TagebuchLogik;

public record TagebuchEintrag(String datum, String uhrzeit, String text) {
    public String formatForFile() {
        return "Eingetragen um " + uhrzeit + ":\n" + text;
    }
}