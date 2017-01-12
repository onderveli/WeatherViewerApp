package com.yazilimciakli.weather.Utils;

import android.content.Context;
import android.provider.MediaStore;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.FileHandler;


public class FileHelper {

    private String fileName = "weather.json";
    private Context context;

    public FileHelper(Context context){
        this.context = context;
    }

    public void write(String data) throws IOException {
        FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeUTF(data);
        objectOutputStream.flush();
        objectOutputStream.close();
        fileOutputStream.close();
    }

    public String read() throws IOException {
        FileInputStream fileInputStream = context.openFileInput(fileName);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        String data = objectInputStream.readUTF();
        objectInputStream.close();
        return data;
    }

    /*
    public boolean isJson() {
        try {
            Gson gson = new Gson();
            gson.fromJson(this.read(), Object.class);
            return true;
        } catch (com.google.gson.JsonSyntaxException ex) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }
    */
}