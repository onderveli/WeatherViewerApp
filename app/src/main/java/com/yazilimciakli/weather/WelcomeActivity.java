package com.yazilimciakli.weather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yazilimciakli.weather.Utils.FileHelper;
import com.yazilimciakli.weather.Utils.SharedPreferenceHelper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

//https://bintray.com/ adresinden kütüphanelerin güncel hallerine ulaş

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String LOCATION = "location";

    AutoCompleteTextView txtLocation;
    // cities string-array dosyasından şehir listesini al.
    List<String> cities;

    FileHelper fileHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Dosya işlemleri yapabilmek için FileHelper nesnemizi oluşturduk.
        fileHelper = new FileHelper(WelcomeActivity.this);

        // cities değişkenini StringArray 'ın içindeki şehirlerle doldur.
        cities = Arrays.asList(getResources().getStringArray(R.array.cities));

        // Preferences de konum değeri yoksa " " boş değer alacağız.
        // Böylece konum yoksa, ayar yaptıracağız, varsa main activity'e geçiş yapacağız.
        String location = SharedPreferenceHelper.getSharedPreferenceString(WelcomeActivity.this, LOCATION, "");

        // Ayarlardan gelen konum bilgisi cities isimli listede mevcut ise MainActivity 'e git.
        // Konum bilgisi yoksa ya da yanlış ise (dışarıdan müdahale ile değiştirilmiş vs.) if 'i atla ayar yaptır.
        if (containsCaseInsensitive(location, cities)) {
            startActivity(new Intent(WelcomeActivity.this, MainActivity.class)); // çağırılacak activity'e (MainActivity) değer aktarma işlemi yapmayacağımız için tek satırda cağırdık.
            finish(); // finish fonksiyonu main activity den welcome activity'e dönüş yapılmasını engellemek için.
            return; // activity çağırıldıktan sonra diğer işlemleri pass geç.
        }

        // Program ilk kez çalışacak ise, welcome activity'ye ait controllerin ayarlarını yapıyoruz.

        // AutoCompleteTextView nesnesi için adapter oluştur.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, cities);

        // AutoCompleteTextView nesnesinin instance'ını al, ve adapter nesnesini ata.
        txtLocation = (AutoCompleteTextView) findViewById(R.id.txtLocation);
        txtLocation.setAdapter(adapter);
        // Autocomplete'in aktifleşmesi için gereken Threshold (eşik değeri) 2 olarak ayarladık.
        txtLocation.setThreshold(2);

        // Button nesnesinin instance'ını al ve setOnClickListener eventini activity'e bağla.
        Button btnOk = (Button) findViewById(R.id.btnOk);
        btnOk.setOnClickListener(WelcomeActivity.this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnOk:

                // Kullanıcının girdiği şehir değerini aldık.
                String city = txtLocation.getText().toString();

                // Girilen şehir adı listeden seçilmediyse hata ver.
                if (!containsCaseInsensitive(city, cities)) {
                    new AlertDialog.Builder(WelcomeActivity.this)
                            .setMessage("Listeden bir şehir seçin!")
                            .setCancelable(false)
                            .setPositiveButton("Tamam", null)
                            .create()
                            .show();
                    return;
                    // Return yazarak, hata aldıysak işlem yaptırmıyoruz.
                }

                // Ne zaman ki hata almayı pass geçerse işlemlerini yapıyoruz.

                // SharedPreference'e girilen şehir adını kaydettik.
                SharedPreferenceHelper.setSharedPreferenceString(WelcomeActivity.this, LOCATION, city);

                // Ayarlarımızı kaydettikten sonra, verileri çekip depolamak için istekte bulunuyoruz.


                // Volley için istek oluşturuldu
                StringRequest request = new StringRequest(Request.Method.GET, getString(R.string.apiUrl, city), new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            // Gelen json verisi, offline da kullanılmak üzere dosyaya yazıldı.
                            fileHelper.write(response);


                            // Ana activity'e geçiş yap.
                            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                            //finish(); methodu ile bu activity'e (WelcomeActivity) geri dönülmesini engelliyoruz.
                            finish();


                        } catch (IOException e) {

                            Log.d("ERROR: IOException -> ", e.getMessage());
                            new AlertDialog.Builder(WelcomeActivity.this)
                                    .setMessage("İstek işlenirken hata oluştu!")
                                    .setCancelable(false)
                                    .setPositiveButton("Tamam", null)
                                    .create()
                                    .show();

                        }

                    }
                }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("ERROR: VolleyError -> ", error.getMessage());
                        new AlertDialog.Builder(WelcomeActivity.this)
                                .setMessage("Bağlantı sırasında hata oluştu!")
                                .setCancelable(false)
                                .setPositiveButton("Tamam", null)
                                .create()
                                .show();
                    }
                });

                // Volley RequestQueue oluşturuldu.
                RequestQueue requestQueue = Volley.newRequestQueue(WelcomeActivity.this);
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