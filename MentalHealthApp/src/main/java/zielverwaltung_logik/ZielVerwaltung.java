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
        aktualisiereUndSpeichere(() -> ziele.add(ziel));
    }

    public void abhaken(int index) {
        aktualisiereUndSpeichere(() -> {
            if (index >= 0 && index < ziele.size()) {
                ziele.get(index).setErledigt(true);
            }
        });
    }

    public void bearbeiten(int index, Ziel aktualisiert) {
        aktualisiereUndSpeichere(() -> {
            if (index >= 0 && index < ziele.size()) {
                Ziel ziel = ziele.get(index);
                ziel.setBeschreibung(aktualisiert.getBeschreibung());
                ziel.setPrioritaet(aktualisiert.getPrioritaet());
                ziel.setFaelligkeit(aktualisiert.getFaelligkeit());
                ziel.setWiederholung(aktualisiert.getWiederholung());
                ziel.setMotivationsnotiz(aktualisiert.getMotivationsnotiz());
            }
        });
    }

    public void loeschen(int index) {
        aktualisiereUndSpeichere(() -> {
            if (index >= 0 && index < ziele.size()) {
                ziele.remove(index);
            }
        });
    }

    public List<Ziel> getZiele() {
        return ziele;
    }

    public List<Ziel> filterNachKategorie(String kategorie) {
        try {
            return ziele.stream()
                    .filter(z -> z.getKategorie() == ZielKategorie.valueOf(kategorie.toUpperCase()))
                    .toList();
        } catch (IllegalArgumentException e) {
            System.out.println("⚠ Ungültige Kategorie: " + kategorie);
            return List.of();
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

    public List<Ziel> filterNachKategorieMitAuswahl(Scanner scanner) {
        ZielKategorie gewaehlteKategorie = waehleKategorie(scanner);
        List<Ziel> gefiltert = filterNachKategorie(gewaehlteKategorie.name());
        if (gefiltert.isEmpty()) {
            System.out.println("⚠ Keine Ziele in Kategorie \"" + gewaehlteKategorie + "\" gefunden.");
        }
        return gefiltert;
    }

    private ZielKategorie waehleKategorie(Scanner scanner) {
        ZielKategorie[] kategorien = ZielKategorie.values();
        System.out.println("\nWähle eine Zielkategorie:");
        for (int i = 0; i < kategorien.length; i++) {
            System.out.println((i + 1) + " - " + kategorien[i]);
        }
        System.out.print("Auswahl: ");
        try {
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            return kategorien[Math.max(0, Math.min(index, kategorien.length - 1))];
        } catch (Exception e) {
            System.out.println("⚠ Ungültige Kategorieauswahl. Standard: GESUNDHEIT.");
            return ZielKategorie.GESUNDHEIT;
        }
    }

    private void aktualisiereUndSpeichere(Runnable aenderung) {
        aenderung.run();
        repository.speichern(ziele);
    }

}
