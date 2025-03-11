package routinen_logik;

public enum RoutinenArt {
    MORGEN("Morgen"),
    MITTAG("Mittag"),
    ABEND("Abend"),
    NACHT("Nacht");

    private final String anzeigeName;

    RoutinenArt(String anzeigeName) {
        this.anzeigeName = anzeigeName;
    }

    public String getAnzeigeName() {
        return anzeigeName;
    }

    public static RoutinenArt fromText(String text) {
        for (RoutinenArt art : values()) {
            if (art.anzeigeName.equalsIgnoreCase(text.trim())) {
                return art;
            }
        }
        throw new IllegalArgumentException("Ungültige Routinenart: " + text);
    }

    public static RoutinenArt fromInt(int eingabe) {
        return switch (eingabe) {
            case 1 -> MORGEN;
            case 2 -> MITTAG;
            case 3 -> ABEND;
            case 4 -> NACHT;
            default -> throw new IllegalArgumentException("Ungültige Routinenart-Auswahl");
        };
    }
}