package ru.gb.android1_lession4;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private LinearLayout cities;
    private CityWeather[] arrayCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(makeTitle());
        cities = (LinearLayout) findViewById(R.id.cities);
        arrayCity = getArrayCity();
        for(int i = 0; i < arrayCity.length; i++) {
            Button button = new Button(this);
            button.setTypeface(null, Typeface.BOLD);
            button.setTextColor(getResources().getColor(R.color.colorButton));
            button.setText(arrayCity[i].getName());
            button.setTag(i);
            cities.addView(button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    arrayCity[(int)v.getTag()].loadWeather();
                    Intent SecAct = new Intent(getApplicationContext(), CityWeatherActivity.class);
                    SecAct.putExtra(Param.ShowWind.toString(), ((CheckBox)findViewById(R.id.checkWind)).isChecked());
                    SecAct.putExtra(Param.ShowHumidity.toString(), ((CheckBox)findViewById(R.id.checkHumidity)).isChecked());
                    SecAct.putExtra(Param.ShowPressure.toString(), ((CheckBox)findViewById(R.id.checkPressure)).isChecked());
                    SecAct.putExtra(Param.CityWeather.toString(), arrayCity[(int)v.getTag()]);
                    startActivity(SecAct);
                }
            });
        }

    }
    private CityWeather[] getArrayCity() { //загрузка списка нужных городов
        CityWeather[] arrayCity = {new CityWeather("Москва"), new CityWeather("Воронеж"),
                new CityWeather("Брянск"), new CityWeather("Липецк"), new CityWeather("Рязань"),
                new CityWeather("Новосибирск"),new CityWeather("Владивосток"), new CityWeather("Хабаровск"),
                new CityWeather("Чита"), new CityWeather("Уфа"), new CityWeather("Калуга")};
        return arrayCity;
    }
    private String makeTitle() {
        StringBuilder str = new StringBuilder("Погода     ");
        SimpleDateFormat sdf = new SimpleDateFormat("E  d MMM", Locale.forLanguageTag("ru-RU"));
        str.append(sdf.format(Calendar.getInstance().getTime()));
        return str.toString();
    }
}
