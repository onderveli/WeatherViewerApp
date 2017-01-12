package com.yazilimciakli.weather;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yazilimciakli.weather.Utils.FileHelper;
import com.yazilimciakli.weather.Utils.SharedPreferenceHelper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    AutoCompleteTextView txtLocation;
    Button btnSave;
    List<String> cities;
    FileHelper fileHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Requesti kaydetmek için FileHelper nesnesinin instance'ını aldık.
        fileHelper = new FileHelper(SettingsActivity.this);

        // cities değişkenini StringArray 'ın içindeki şehirlerle doldur.
        cities = Arrays.asList(getResources().getStringArray(R.array.cities));

        // AutoCompleteTextView nesnesi için adapter oluştur.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, cities);

        // AutoCompleteTextView nesnesinin instance'ını al, ve adapter nesnesini ata.
        txtLocation = (AutoCompleteTextView) findViewById(R.id.txtLocation);
        txtLocation.setAdapter(adapter);
        // Autocomplete'in aktifleşmesi için gereken Threshold (eşik değeri) 2 olarak ayarladık.
        txtLocation.setThreshold(2);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(SettingsActivity.this);

        String city = SharedPreferenceHelper.getSharedPreferenceString(SettingsActivity.this, WelcomeActivity.LOCATION, "");
        txtLocation.setText(city);


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        // Geri tuşuna basılması halinde MainActivity 'e dönüş yaptık.
        startActivity(new Intent(SettingsActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSave:

                // Kullanıcının girdiği şehir değerini aldık.
                String city = txtLocation.getText().toString();

                // Girilen şehir adı listeden seçilmediyse hata ver.
                if (!containsCaseInsensitive(city, cities)) {
                    new AlertDialog.Builder(SettingsActivity.this)
                            .setMessage("Listeden bir şehir seçin!")
                            .setCancelable(false)
                            .setPositiveButton("Tamam", null)
                            .create()
                            .show();
                    return;
                    // Return yazarak, hata aldıysak işlem yaptırmıyoruz.
                }

                // SharedPreference'e girilen şehir adını kaydettik.
                SharedPreferenceHelper.setSharedPreferenceString(SettingsActivity.this, WelcomeActivity.LOCATION, city);

                // Volley için istek oluşturuldu
                StringRequest request = new StringRequest(Request.Method.GET, getString(R.string.apiUrl, city), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            // Gelen json verisi, offline da kullanılmak üzere dosyaya yazıldı.
                            fileHelper.write(response);
                            Toast.makeText(SettingsActivity.this, "Güncellendi!", Toast.LENGTH_SHORT).show();

                            // Ayarlar yenilendiği için MainActivity'i tekrar çağırarak ekranların yenilenmesini sağladık.
                            startActivity(new Intent(SettingsActivity.this, MainActivity.class));
                            //finish(); methodu ile bu activity'e geri dönülmesini engelliyoruz.
                            finish();

                        } catch (IOException e) {

                            Log.d("ERROR: IOException -> ", e.getMessage());
                            new AlertDialog.Builder(SettingsActivity.this)
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
                        new AlertDialog.Builder(SettingsActivity.this)
                                .setMessage("Bağlantı sırasında hata oluştu!")
                                .setCancelable(false)
                                .setPositiveButton("Tamam", null)
                                .create()
                                .show();
                    }
                });

                // Volley RequestQueue oluşturuldu.
                RequestQueue requestQueue = Volley.newRequestQueue(SettingsActivity.this);
                // İstek RequestQueue 'ya eklendi.
                requestQueue.add(request);

                break;
        }
    }

    // Listede girilen String değerin olup olmadığını equalsIgnoreCase ile kontrol eder.
    public boolean containsCaseInsensitive(String s, List<String> stringList) {
        for (String item : stringList) {
            if (item.equalsIgnoreCase(s)) {
                return true;
            }
        }
        return false;
    }
}
