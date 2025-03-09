package TagebuchLogik;

public class TagebuchEintrag {
    private final String datum;
    private final String uhrzeit;
    private final String text;

    public TagebuchEintrag(String datum, String uhrzeit, String text) {
        this.datum = datum;
        this.uhrzeit = uhrzeit;
        this.text = text;
    }

    public String getDatum() {
        return datum;
    }

    public String getUhrzeit() {
        return uhrzeit;
    }

    public String getText() {
        return text;
    }

    public String formatForFile() {
        return "Eingetragen um " + uhrzeit + ":\n" + text;
    }
}
