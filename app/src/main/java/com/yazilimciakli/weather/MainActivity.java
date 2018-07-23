package com.yazilimciakli.weather;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;
import com.yazilimciakli.weather.Models.WeatherApi;
import com.yazilimciakli.weather.Utils.FileHelper;
import com.yazilimciakli.weather.Utils.SharedPreferenceHelper;
import com.yazilimciakli.weather.Utils.ViewPagerAdapter;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    FileHelper fileHelper;

    private Toolbar toolbar;
    private ViewPager viewPager;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, "ca-app-pub-5142565439495272~8956908216");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager, true);




        // Dosya işlemi için FileHelper sınıfının instance'ını al.
        fileHelper = new FileHelper(MainActivity.this);
        try {
            // Dosyada var olan json verisini okuduk.
            String jsonData = fileHelper.read();
            Gson gson = new Gson();
            // Gson aracılığıyla json verisini parse (WeatherApi türünden nesne olarak aldık) ettik.
            Log.d("HELP", "onCreate: "+jsonData);
            WeatherApi weatherApi = gson.fromJson(jsonData, WeatherApi.class);
            // ViewPagerAdapter nesnesinin instance'ını al.
            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
            // adapter nesnesine TodayFragment türünden bir nesne ekle.
            adapter.addFragment(TodayFragment.newInstance(weatherApi), "Bugün");
            adapter.addFragment(ThisWeekFragment.newInstance(weatherApi), "Bu Hafta");
            viewPager.setAdapter(adapter);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:

                // SharedPreference'e girilen şehir adını aldık.
                String city = SharedPreferenceHelper.getSharedPreferenceString(MainActivity.this, WelcomeActivity.LOCATION, "");

                if (city == "") {
                    Toast.makeText(this, "Konum bilgisi alınamadı!", Toast.LENGTH_SHORT).show();
                    return false;
                }

                // Volley için istek oluşturuldu
                StringRequest request = new StringRequest(Request.Method.GET, getString(R.string.apiUrl, city), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            // Gelen json verisi, offline da kullanılmak üzere dosyaya yazıldı.
                            fileHelper.write(response);
                            Toast.makeText(MainActivity.this, "Güncellendi!", Toast.LENGTH_SHORT).show();


                            // FRAGMENTLER YENİLENİYOR...

                            // Dosyada var olan json verisini okuduk.
                            String jsonData = fileHelper.read();

                            Gson gson = new Gson();
                            // Gson aracılığıyla json verisini parse (WeatherApi türünden nesne olarak aldık) ettik.
                            WeatherApi weatherApi = gson.fromJson(jsonData, WeatherApi.class);

                            // ViewPagerAdapter nesnesinin instance'ını al.
                            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
                            // adapter nesnesine TodayFragment türünden bir nesne ekle.
                            adapter.addFragment(TodayFragment.newInstance(weatherApi), "Bugün");
                            adapter.addFragment(ThisWeekFragment.newInstance(weatherApi), "Bu Hafta");
                            viewPager.setAdapter(adapter);



                        } catch (IOException e) {

                            Log.d("ERROR: IOException -> ", e.getMessage());
                            new AlertDialog.Builder(MainActivity.this)
                                    .setMessage("İstek işlenirken hata oluştu!")
                                    .setCancelable(false)
                                    .setPositiveButton("Tamam", null)
                                    .create()
                                    .show();

                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("ERROR: VolleyError -> ", error.getMessage());
                        new AlertDialog.Builder(MainActivity.this)
                                .setMessage("Bağlantı sırasında hata oluştu!")
                                .setCancelable(false)
                                .setPositiveButton("Tamam", null)
                                .create()
                                .show();
                    }
                });

                // Volley RequestQueue oluşturuldu.
                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                // İstek RequestQueue 'ya eklendi.
                requestQueue.add(request);

                break;
            case R.id.settings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
