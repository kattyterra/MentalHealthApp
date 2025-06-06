package menus;

import utility.AnswerParser;
import zielverwaltung_logik.*;

import java.util.List;
import java.util.Scanner;

public class ZielMenu {
    private final ZielVerwaltung zielVerwaltung;
    private final ZielStatistikVerwaltung statistikService;
    private final ZielEingabeHelper eingabeHelper;
    private final AnswerParser answerParser = new AnswerParser();

    public ZielMenu() {
        this.zielVerwaltung = new ZielVerwaltung();
        this.statistikService = new ZielStatistikVerwaltung();
        this.eingabeHelper = new ZielEingabeHelper();
    }

    public void showMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n🎯 Zielsetzungsmenü – Erreiche deine Ziele Schritt für Schritt!");
            System.out.println("────────────────────────────────────────────");
            System.out.println("1 - ➕ Neues Ziel hinzufügen");
            System.out.println("2 - ✅ Ziel abhaken (als erledigt markieren)");
            System.out.println("3 - ✏️ Ziel bearbeiten");
            System.out.println("4 - 🗑️ Ziel löschen");
            System.out.println("5 - 📋 Alle Ziele anzeigen");
            System.out.println("6 - 📈 Fortschrittsstatistik anzeigen");
            System.out.println("7 - 📂 Ziele nach Kategorie filtern");
            System.out.println("8 - 🔍 Ziele nach Status filtern");
            System.out.println("9 - 🔙 Zurück zum Hauptmenü");
            System.out.println("────────────────────────────────────────────");
            System.out.print("👉 Deine Auswahl: ");

            int choice = answerParser.parsen(scanner);
            if (choice == 99) {
                continue;
            }

            switch (choice) {
                case 1 -> zielVerwaltung.hinzufuegen(eingabeHelper.erstelleZielVomBenutzer(scanner));
                case 2 -> zielVerwaltung.abhaken(eingabeHelper.indexAbfragen(scanner));
                case 3 -> zielVerwaltung.bearbeiten(
                        eingabeHelper.indexAbfragen(scanner),
                        eingabeHelper.erstelleZielVomBenutzer(scanner)
                );
                case 4 -> zielVerwaltung.loeschen(eingabeHelper.indexAbfragen(scanner));
                case 5 -> zeigeAlleZiele(zielVerwaltung.getZiele());
                case 6 -> {
                    System.out.println("\n📈 Deine Fortschritte auf einen Blick:");
                    statistikService.zeigeStatistik(zielVerwaltung.getZiele());
                }
                case 7 -> {
                    System.out.println("\n📂 Gefilterte Ziele nach Kategorie:");
                    zielVerwaltung.filterNachKategorieMitAuswahl(scanner).forEach(System.out::println);
                }
                case 8 -> {
                    boolean erledigt = statusFilterAuswahl(scanner);
                    List<Ziel> gefiltert = zielVerwaltung.filterNachStatus(erledigt, true);
                    if (gefiltert.isEmpty()) {
                        System.out.println("🔍 Keine Ziele mit diesem Status gefunden.");
                    } else {
                        gefiltert.forEach(System.out::println);
                    }
                }
                case 9 -> {
                    return;
                }
                default -> System.out.println("😅 Diese Eingabe kennt mein Menü nicht. Versuch’s nochmal!");
            }
        }
    }

    private void zeigeAlleZiele(List<Ziel> ziele) {
        if (ziele.isEmpty()) {
            System.out.println("⚠️ Du hast momentan noch keine Ziele eingetragen.");
            System.out.println("💡 Wie wäre es, wenn du dir ein neues Ziel setzt?");
        } else {
            System.out.println("\n🎯 Deine aktuellen Ziele:");
            ziele.forEach(System.out::println);
        }
    }

    private boolean statusFilterAuswahl(Scanner scanner) {
        System.out.println("\n📌 Wähle den Status, den du filtern möchtest:");
        System.out.println("1 - ✅ Erledigte Ziele anzeigen");
        System.out.println("2 - ⏳ Offene Ziele anzeigen");
        System.out.print("👉 Deine Auswahl: ");
        try {
            int statusWahl = Integer.parseInt(scanner.nextLine());
            return statusWahl == 1;
        } catch (Exception e) {
            System.out.println("⚠ Ungültige Statusauswahl.");
            return false;
        }
    }
}