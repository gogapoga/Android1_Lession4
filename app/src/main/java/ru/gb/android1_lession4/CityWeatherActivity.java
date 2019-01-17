package ru.gb.android1_lession4;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CityWeatherActivity extends AppCompatActivity {

    private Boolean showWind;
    private Boolean showHumidity;
    private Boolean showPressure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);
        Bundle bundle = getIntent().getExtras();
        showWind = bundle.getBoolean(Param.ShowWind.toString());
        showHumidity = bundle.getBoolean(Param.ShowHumidity.toString());
        showPressure = bundle.getBoolean(Param.ShowPressure.toString());
        CityWeather city = (CityWeather)bundle.getSerializable(Param.CityWeather.toString());
        setTitle(makeTitle(city.getName()));
        LinearLayout timeweather = (LinearLayout) findViewById(R.id.timeweather);
        for(int i = 0; i < 24; i++) {
            Button timeWeather = new Button(this);
            timeWeather.setTypeface(null, Typeface.ITALIC);
            timeWeather.setTextColor(getResources().getColor(R.color.colorButton));
            timeWeather.setClickable(false);
            timeWeather.setGravity(Gravity.LEFT | Gravity.TOP);
            StringBuilder str = new StringBuilder("Время: ");
            str.append(i);
            str.append(":00\n");
            str.append("Температура: ");
            int t = city.getStrTemp(i);
            if(t > 0)  str.append("+");
            str.append(t);
            str.append(" °C");
            t = city.getHumidity(i);
            if(showHumidity) {
                str.append("\n");
                str.append("Влажность: ");
                str.append(t);
                str.append("%");
            }
            if(showWind) {
                str.append("\n");
                str.append("Ветер: ");
                str.append(city.getWind(i));
                str.append(" м/с");
            }
            if(showPressure) {
                str.append("\n");
                str.append("Давление: ");
                str.append(city.getPressure(i));
                str.append(" мм.рт.ст.");
            }
            timeWeather.setText(str);
            Drawable img;
            if (t < 40) img = getResources().getDrawable(R.drawable.sun);
            else if (t > 80) img = getResources().getDrawable(R.drawable.cloud_r);
            else img = getResources().getDrawable(R.drawable.cloud);
            img.setBounds( 0, 0, 60, 60 );
            timeWeather.setCompoundDrawables(null, null, img, null);
            timeweather.addView(timeWeather);
        }
    }
    private String makeTitle(String cityName) {
        StringBuilder str = new StringBuilder("");
        str.append(cityName);
        str.append("     ");
        SimpleDateFormat sdf = new SimpleDateFormat("E  d MMM", Locale.forLanguageTag("ru-RU"));
        str.append(sdf.format(Calendar.getInstance().getTime()));
        return str.toString();
    }
}
