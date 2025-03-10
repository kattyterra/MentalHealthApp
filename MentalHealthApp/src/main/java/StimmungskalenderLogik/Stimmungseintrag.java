package StimmungskalenderLogik;
public record Stimmungseintrag(String datum, String uhrzeit, int stimmung) {
    public String formatForFile() {
        return datum + " " + uhrzeit + " - Stimmung: " + stimmung;
    }
}

