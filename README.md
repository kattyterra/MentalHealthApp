# 🧠 MentalHealthApp – Deine tägliche Begleiterin für psychische Gesundheit

## 📋 Projektübersicht

Die **MentalHealthApp** ist eine rein textbasierte Konsolenanwendung in Java zur Förderung von Achtsamkeit, Selbstreflexion und persönlicher Entwicklung. Sie kombiniert verschiedene psychologische Selbsthilfe-Tools in einem modular aufgebauten System – ideal für den privaten Gebrauch oder als Grundlage für erweiterbare Anwendungen im Mental-Health-Bereich.

---

## 🌟 Hauptfunktionen

### ✅ Ziele verwalten
- Ziele erstellen, bearbeiten, löschen, abhaken
- Kategorien (Gesundheit, Arbeit, Finanzen etc.)
- Priorisierung (hoch, mittel, niedrig)
- Wiederkehrende Ziele (täglich, wöchentlich, monatlich)
- Fälligkeitsdatum & Motivationsnotiz
- Ziel-Statistiken & Verlauf

### 📅 Routinen planen & dokumentieren
- Routinen nach Tageszeit (Morgen, Mittag, Abend, Nacht)
- Tägliche Checkliste zum Abhaken
- Historie & Erfolgsstatistik
- Vorschläge aus Textdatei auswählbar

### 🧘 Atem- & Achtsamkeitsübungen
- Lade Übungen aus Textdateien (Atmung, Achtsamkeit)
- Ziel & Schritt-für-Schritt-Anleitung
- Einfache Anzeige und Navigation

### 📓 Tagebuchfunktion
- Schreibe tägliche Einträge mit Uhrzeit
- Bearbeiten & Löschen einzelner Einträge
- Verwaltung über tägliche Dateien (pro Datum)

### 🌦 Stimmungskalender mit Emotionstracking
- Tägliche Stimmung (1–10) mit Textanzeige und Graph
- Auswahl & Bewertung von Emotionen mit Intensität und Ursache
- Automatische Verlaufswarnung bei langanhaltender negativer Stimmung

### 🔁 Gedankenkarussell stoppen (Reflexionsmodul)
- Strukturierte Fragen zur Selbstreflexion
- Bewertung der Belastung (1–10)
- Speicherung nach Datum inkl. Übersicht aller Einträge

### 💡 Inspirationssätze
- Zufällige motivierende Sätze aus Textdatei
- Jederzeit abrufbar für tägliche Impulse

### 📊 Persönlicher Monatsrückblick
- Durchschnittliche Stimmung
- Tagebuch- und Reflexionseinträge
- Routinen-Erfüllungsquote
- Zielerreichung & offene Ziele
- Emotionenstatistik (Intensität & Häufigkeit)

---

## 🧱 Projektstruktur

```
MentalHealthApp/
├── App.java                          # Einstiegspunkt
├── Main.java                         # Menüführung
├── zielverwaltung_logik/            # Zielsetzungsmodul
├── routinen_logik/                  # Routinenverwaltung
├── uebungen/                        # Achtsamkeit & Atmung
├── tagebuch_logik/                 # Tagebuchfunktion
├── stimmungskalender_logik/        # Stimmung + Emotionen
├── gedanken_reflexion_logik/       # Gedankenkarussell stoppen
├── inspirations_logik/             # Inspirationssätze
├── fortschrittsbericht_logik/      # Monatsauswertung
├── Textvorlagen(nicht_ändern!)/    # Vorgefertigte Textvorlagen für die verschiedene Funktionen der App
└── Datenordner (automatisch)
    ├── Ziele/
    ├── Routinen/
    ├── Tagebuch/
    ├── Reflexionen/
    └── Stimmungskalender/
```

---

## ⚙️ Voraussetzungen

- Java 17 oder neuer
- Textbasiertes Terminal
- Keine externen Bibliotheken notwendig

---

## 🚀 Projekt starten

1. Projekt klonen:
   ```bash
   git clone https://github.com/dein-benutzername/MentalHealthApp.git
   cd MentalHealthApp
   ```

2. Kompilieren:
   ```bash
   javac App.java
   ```

3. Ausführen:
   ```bash
   java App
   ```

> Hinweis: Beim ersten Start werden automatisch Verzeichnisse und leere Dateien erstellt.

