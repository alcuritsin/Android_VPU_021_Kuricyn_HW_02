package com.example.task_06;

public class Film {
    //--Class members---------------------------------
    /**
     * Название фильма
     */
    public String title;
    /**
     * Жанр фильма
     */
    public String genre;
    /**
     * Год выпуска фильма
     */
    public int year;
    //--Class methods---------------------------------

    public Film(String title, String genre, int year)
    {
        this.title = title;
        this.genre = genre;
        this.year = year;
    }
}
