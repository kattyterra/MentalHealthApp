package zielverwaltung_logik;

import java.util.*;

public class ZielVerwaltung {
    private final List<Ziel> ziele;
    private final ZielRepository repository;

    public ZielVerwaltung() {
        this.repository = new ZielSpeicher();
        this.ziele = repository.laden();
    }

    /** Injektion-Konstruktor */
    public ZielVerwaltung(ZielRepository repo) {
        this.repository = repo;
        this.ziele = repository.laden();
    }

    public void hinzufuegen(Ziel ziel) {
        ziele.add(ziel);
        speichern();
    }

    public void abhaken(int index) {
        if (index >= 0 && index < ziele.size()) {
            ziele.get(index).setErledigt(true);
            speichern();
        }
    }

    public void bearbeiten(int index, Ziel aktualisiert) {
        if (index >= 0 && index < ziele.size()) {
            Ziel ziel = ziele.get(index);
            ziel.setBeschreibung(aktualisiert.getBeschreibung());
            ziel.setPrioritaet(aktualisiert.getPrioritaet());
            ziel.setFaelligkeit(aktualisiert.getFaelligkeit());
            ziel.setWiederholung(aktualisiert.getWiederholung());
            ziel.setMotivationsnotiz(aktualisiert.getMotivationsnotiz());
            speichern();
        }
    }

    public void loeschen(int index) {
        if (index >= 0 && index < ziele.size()) {
            ziele.remove(index);
            speichern();
        }
    }

    public List<Ziel> getZiele() {
        return ziele;
    }

    public List<Ziel> filterNachKategorie(String kategorie) {
        try {
            ZielKategorie filterKategorie = ZielKategorie.valueOf(kategorie.toUpperCase());
            return ziele.stream()
                    .filter(z -> z.getKategorie() == filterKategorie)
                    .toList();
        } catch (IllegalArgumentException e) {
            System.out.println("⚠ Ungültige Kategorie: " + kategorie);
            return List.of(); // leere Liste zurückgeben
        }
    }

    public List<Ziel> filterNachStatus(boolean erledigt, boolean mitMeldung) {
        List<Ziel> gefiltert = ziele.stream()
                .filter(z -> z.isErledigt() == erledigt)
                .toList();

        if (mitMeldung && gefiltert.isEmpty()) {
            String statusText = erledigt ? "erledigt" : "offen";
            System.out.println("⚠ Es wurden keine " + statusText + "en Ziele gefunden.");
        }

        return gefiltert;
    }

    private void speichern() {
        repository.speichern(ziele);
    }

    public List<Ziel> filterNachKategorieMitAuswahl(Scanner scanner) {
        ZielKategorie[] kategorien = ZielKategorie.values();
        System.out.println("\nWähle eine Zielkategorie:");
        for (int i = 0; i < kategorien.length; i++) {
            System.out.println((i + 1) + " - " + kategorien[i]);
        }
        System.out.print("Auswahl: ");
        try {
            int katIndex = Integer.parseInt(scanner.nextLine()) - 1;
            ZielKategorie gewaehlteKategorie = kategorien[Math.max(0, Math.min(katIndex, kategorien.length - 1))];
            List<Ziel> gefiltert = filterNachKategorie(gewaehlteKategorie.name());

            if (gefiltert.isEmpty()) {
                System.out.println("⚠ Es wurden keine Ziele in der Kategorie \"" + gewaehlteKategorie + "\" gefunden.");
            }

            return gefiltert;
        } catch (Exception e) {
            System.out.println("⚠ Ungültige Kategorieauswahl.");
            return List.of();
        }
    }

}
