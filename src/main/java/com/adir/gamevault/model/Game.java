package com.adir.gamevault.model;

import java.time.LocalDate;

// משחק בחנות.
// כל השדות המשותפים נמצאים ב־StoreItem. כאן נשאר רק מה שייחודי למשחק: הז׳אנר.
public class Game extends StoreItem {

    private final Genre genre;

    public Game(String id, String title, double price,
                LocalDate releaseDate, double rating, Genre genre) {
        // super חייב להיות השורה הראשונה: הוא בודק ומאתחל את השדות המשותפים
        super(id, title, price, releaseDate, rating);

        if (genre == null) {
            throw new IllegalArgumentException("genre can not be null");
        }
        this.genre = genre;
    }

    public Genre getGenre() {
        return genre;
    }

    @Override
    public String getTypeLabel() {
        return "Game";
    }
}
