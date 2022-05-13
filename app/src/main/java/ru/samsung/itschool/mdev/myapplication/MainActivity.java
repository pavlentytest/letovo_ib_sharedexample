package ru.samsung.itschool.mdev.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

class User {
    private String name;
    private int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }
}


public class MainActivity extends AppCompatActivity {

    private EditText name, age;
    private Gson gson = new Gson();
    private ArrayList<User> userArrayList;
    private ListView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        result = findViewById(R.id.result);
        userArrayList = new ArrayList<>();
        //loadData();
    }

    public void addArrayListData(View v) {
        User user = new User(name.getText().toString(), Integer.parseInt(age.getText().toString()));
        userArrayList.add(user);
        name.setText("");
        age.setText("");
    }

    public void saveArrayListData(View v) {
        SharedPreferences preferences = getSharedPreferences("DATA", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String s = gson.toJson(userArrayList);
        editor.putString("USER_ARRAYLIST_DATA", s);
        editor.apply();
        loadArrayListData();
    }

    public void loadArrayListData() {
        SharedPreferences preferences = getSharedPreferences("DATA", MODE_PRIVATE);
        String str_value = preferences.getString("USER_ARRAYLIST_DATA", "Default value!" );
        Type type = new TypeToken<ArrayList<User>>(){}.getType();
        ArrayList<User> arrayList = gson.fromJson(str_value,type);
        ArrayList<String> stringArrayList = new ArrayList<>();
        for(int i=0;i<arrayList.size();i++) {
            stringArrayList.add(arrayList.get(i).getName() + " - age: "+arrayList.get(i).getAge());
            //Log.d("RRR",arrayList.get(i).getName());
       }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                stringArrayList);
        result.setAdapter(arrayAdapter);
    }
    /*
    public void saveData(View v) {
        User user = new User(name.getText().toString(), Integer.parseInt(age.getText().toString()));
        SharedPreferences preferences = getSharedPreferences("DATA" , MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String s = gson.toJson(user);
        editor.putString("USER_DATA", s);
        editor.apply();
    }

    public void loadData() {
        SharedPreferences preferences = getSharedPreferences("DATA", MODE_PRIVATE);
        String str_value = preferences.getString("USER_DATA", "Default value!" );
        User user_value = gson.fromJson(str_value,User.class);
        name.setText(user_value.getName());
        age.setText(Integer.toString(user_value.getAge()));
    }
*/
}