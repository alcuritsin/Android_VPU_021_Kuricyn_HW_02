package com.example.task_06;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private EditText etTitle, etGenre, etYear;
    private Button btnAddFilm;

    private Spinner spnFilms;

    private static ArrayList<Map<String, Object>> filmList;
    private static boolean isInit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //--- Инициализация полей виджитов
        etTitle = (EditText) this.findViewById(R.id.etTitle);
        etGenre = (EditText) this.findViewById(R.id.etGenre);
        etYear = (EditText) this.findViewById(R.id.etYear);

        btnAddFilm = (Button) this.findViewById(R.id.btnAddFilm);

        spnFilms = (Spinner) this.findViewById(R.id.spnFilms);

        //--- Назначение слушателя на кнопку 'Добавить'
        btnAddFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filmAdd();
            }
        });

        if (!isInit) {
            initApp();
        }

        showApp();
    }

    private void initApp(){
        //--- Шаг 1. Создание массива фильмов
        Film[]  films = {
                new Film("Интерстелар", "фантастика", 2015),
                new Film("Человек-муравей", "фэнтези", 2015),
                new Film("Выживший", "драма", 2015),
                new Film("Рейд-2", "боевик", 2014),
                new Film("Хоббит: Пустошь Смауга", "фэнтези", 2013)
        };

        //--- Шаг 2. Создание Map и List коллекций для
        // android.widget.SimpleAdapter
        filmList = new ArrayList<>();

        for (int i = 0; i < films.length; i++){
            HashMap<String, Object> filmItem = createFilmItem(films[i]);

            filmList.add(filmItem);
        }

        isInit = true;

    }

    private void showApp(){
        //--- Шаг 3. Создание Адаптера данных
        // android.widget.SimpleAdapter
        SimpleAdapter filmAdapter = new SimpleAdapter(
                this,
                filmList,
                R.layout.spinner_film,
                new String[] {"Title", "Genre", "Year"},
                new int[] {R.id.tvFilmNameSP, R.id.tvGenreSP, R.id.tvYearSP}
        );

        filmAdapter.setDropDownViewResource(R.layout.spinner_film);

        //--- Шаг 4. Назначение списку spnFilms Адаптера данных
        spnFilms.setAdapter(filmAdapter);

        //--- Шаг 5. Назначение обработчика события выбранного
        // элемента для spnFilms
        spnFilms.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //--- Получение выбранного объекта Film из Адаптера данных
                        HashMap<String, Object> filmItem = (HashMap<String, Object>) parent.getAdapter().getItem(position);
                        //--- Формирование строки для вывода в Тосте
                        String msg =
                                "Film: " + filmItem.get("Title") + "\n" +
                                        "Genre: " + filmItem.get("Genre") + "\n" +
                                        "Year: " + filmItem.get("Year");

                        //--- Вывод информации о выбранном фильме в Тосте
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );
    }


    private HashMap<String, Object> createFilmItem (Film film){
        HashMap<String, Object> filmItem = new HashMap<>();

        filmItem.put("Title", film.title);
        filmItem.put("Genre", film.genre);
        filmItem.put("Year", film.year);

        return filmItem;
    }

    private void filmAdd(){
        String title, genre;
        int year;

        title = etTitle.getText().toString();
        genre = etGenre.getText().toString();

        year = Integer.parseInt(etYear.getText().toString());

        Film film = new Film(title, genre, year);

        filmList.add(createFilmItem(film));

        //--- Сообщение для Тост
        String msg = "Добавлен\n\n" +
                "Film: " + title + "\n" +
                "Genre: " + genre + "\n" +
                "Year: " + year;

        //--- Вывод информации о добавлении фильма в Тосте
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();

        //--- Очистка значений полей
        etTitle.setText("");
        etGenre.setText("");
        etYear.setText("");
    }
}