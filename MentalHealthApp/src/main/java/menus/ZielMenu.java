package menus;

import zielverwaltung_logik.*;

import java.util.List;
import java.util.Scanner;

public class ZielMenu {
    private final ZielService zielService;
    private final ZielStatistikService statistikService;

    public ZielMenu() {
        ZielRepository repo = new DateibasierterZielRepository();
        this.zielService = new ZielService(repo);
        this.statistikService = new ZielStatistikService();
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
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ungültige Eingabe. Bitte eine Zahl eingeben.");
                continue;
            }
            switch (choice) {
                case 1:
                {
                    zielService.hinzufuegen(ZielEingabeHelper.erstelleZielVomBenutzer(scanner));
                    break;
                }
                case 2:
                {
                    zielService.abhaken(ZielEingabeHelper.indexAbfragen(scanner));
                    break;
                }
                case 3:
                {
                    zielService.bearbeiten(ZielEingabeHelper.indexAbfragen(scanner), ZielEingabeHelper.erstelleZielVomBenutzer(scanner));
                    break;
                }
                case 4:
                {
                    zielService.loeschen(ZielEingabeHelper.indexAbfragen(scanner));
                    break;
                }
                case 5:
                {
                    List<Ziel> alleZiele = zielService.getZiele();
                    if (alleZiele.isEmpty()) {
                        System.out.println("⚠ Es sind aktuell keine Ziele vorhanden.");
                    } else {
                        alleZiele.forEach(System.out::println);
                    }
                    break;
                }
                case 6:
                {
                    statistikService.zeigeStatistik(zielService.getZiele());
                    break;
                }
                case 7:
                {
                    zielService.filterNachKategorieMitAuswahl(scanner).forEach(System.out::println);
                    break;
                }
                case 8:
                {
                    System.out.println("\nStatus auswählen:");
                    System.out.println("1 - Erledigt");
                    System.out.println("2 - Offen");
                    System.out.print("Auswahl: ");
                    try {
                        int statusWahl = Integer.parseInt(scanner.nextLine());
                        boolean erledigt = (statusWahl == 1);
                        zielService.filterNachStatus(erledigt, true).forEach(System.out::println);
                    } catch (Exception e) {
                        System.out.println("⚠ Ungültige Statusauswahl.");
                    }
                    break;
                }
                case 9:
                {
                    return;
                }
                default:
                {
                    System.out.println("Ungültige Eingabe");
                    break;
                }
            }
        }
    }
}