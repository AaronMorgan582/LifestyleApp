package com.example.lifestyleapp;

import static com.example.lifestyleapp.MainActivity.locx;
import static com.example.lifestyleapp.MainActivity.locy;

import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.lifestyleapp.MainActivity;


public class WeatherFragment extends Fragment{


    TextView temp, humid, pres,place;
    private ImageView therm, hum;
    private Drawable therm_image;
    private Drawable hum_image;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_weather, container, false);


        temp = view.findViewById(R.id.temperature);
        humid = view.findViewById(R.id.humidity);
        pres = view.findViewById(R.id.pressure);
        place = view.findViewById(R.id.place);

        //Changes the color of the temperature and humidity icons to white.
        therm = view.findViewById(R.id.imageView);
        hum = view.findViewById(R.id.imageView2);
        therm_image = therm.getDrawable();
        therm_image.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
        hum_image = hum.getDrawable();
        hum_image.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);

        displayWeather();
        return view;
    }



            @RequiresApi(api = Build.VERSION_CODES.N)
            private void displayWeather(){


                String URL = "https://api.openweathermap.org/data/2.5/weather?lat="+locy+"&lon="+locx+ "&appid=c1a1e58da8b47c04fbd012a495ba933a";
                RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            JSONObject ob = response.getJSONObject("main");
                                JSONObject pl = response.getJSONObject("sys");
                            //String name = response.getString("name");
                            String tempvalue = ob.getString("temp");
                            double tempvaluedouble = Double.parseDouble(tempvalue);
                            tempvaluedouble = (tempvaluedouble - 273.15) * 9/5 + 32;
                            tempvalue = Double.toString(tempvaluedouble).substring(0,5);
                            String humidvalue = ob.getString("humidity");
                            String presvalue = ob.getString("pressure");
                            //String placestr =  name + ", "+pl.getString("country");
                            //place.setText(placestr);
                            temp.setText("Temperature : " + tempvalue + " F");
                            humid.setText("Humidity : " + humidvalue + "%");
                            pres.setText("Pressure : " + presvalue + " Pa");

                        } catch (JSONException e) {
    e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        temp.setText("");
                        humid.setText("");
                        pres.setText("");
                        error.printStackTrace();

                    }
                });
                queue.add(request);

            }

}