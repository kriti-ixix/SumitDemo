package com.kriti.sumitdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class Weather extends AppCompatActivity {

    EditText editText;
    Button weatherButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        editText = findViewById(R.id.zipCodeEditText);
        weatherButton = findViewById(R.id.weatherButton);

        weatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String zipCode = editText.getText().toString();
                GetWeather getWeather = new GetWeather();
                try {
                    String jsonWeather = getWeather.execute("http://api.openweathermap.org/data/2.5/weather?zip="+ zipCode
                            + ",us&appid=d15e4f8bb247b53efc9e1861ed4c18e0").get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public class GetWeather extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... strings)
        {
            try
            {
                String result = ""; URL url;
                HttpURLConnection connection = null;

                url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);
                int data = reader.read();
                Log.d("First Data", String.valueOf(data));

                while (data != -1)
                {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                    Log.d("Data", String.valueOf(data));
                    Log.d("Character Data", String.valueOf(current));
                }

                return result;

            }
            catch (Exception e)
            {
                e.printStackTrace();
                return "";
            }
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);

            if (s.equals(""))
            {
                Toast.makeText(Weather.this, "Invalid zip code", Toast.LENGTH_LONG).show();
            }
            else
            {
                try
                {
                    //JSON Object
                    Log.d("Response", s);
                    JSONObject json = new JSONObject(s);

                    JSONObject coord = json.getJSONObject("coord");
                    String lat = coord.getString("lat");
                    String lon = coord.getString("lon");

                    String name = json.getString("name");

                    JSONArray weather = json.getJSONArray("weather");

                    Log.d("Response", "Lat: " + lat);
                    Log.d("Response", "Lon: " + lon);
                    Log.d("Response", "Name: " + name);
                    Log.d("Response", "Weather: " + weather);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

}