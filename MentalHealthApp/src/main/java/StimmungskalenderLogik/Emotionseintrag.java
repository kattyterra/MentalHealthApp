package StimmungskalenderLogik;

public record Emotionseintrag(String emotion, int intensitaet, String ursache) {
    public String formatForFile() {
        return "- Emotion: " + emotion + " | Intensität: " + intensitaet + " | Ursache: " + ursache;
    }
}