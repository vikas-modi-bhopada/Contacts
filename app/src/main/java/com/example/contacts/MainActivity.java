package com.example.contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private long backPressedTime;
    Button addContactBtn;
    ListView listView;
     MyDBHandler db;
     ArrayList<String> arrayList;

     public final static String MSG="com.example.contacts.Task";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addContactBtn = findViewById(R.id.addcontact);
        listView = findViewById(R.id.listview);


        db = new MyDBHandler(this);

        showContacts();
    }
    public void showContacts()
    {
        arrayList = new ArrayList<>();
        List<Contact> allContacts= db.getAllContacts();
        for (Contact cont:allContacts)
        {
            arrayList.add(cont.getName()+" :- "+cont.getPhoneNumber());
        }
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String string = arrayList.get(position);
               String string1 = string.substring(string.length()-10);
               Intent intent = new Intent(MainActivity.this,TaskPerform.class);
               intent.putExtra(MSG, string1);
               startActivity(intent);
          }
        });

    }
    public void addContacts(View view)
    {
        Intent intent = new Intent(MainActivity.this,AddContact.class);
        startActivity(intent);
    }
    public void deleteAllContact(View view)
    {
        int totalcont = db.totalContacts();
        for (int i=0;i<totalcont;i++)
        {
            String string = arrayList.get(i);
            String number = string.substring(string.length()-10);
            db.delete(number);
            Intent intent = new Intent(MainActivity.this,MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);

        super.onBackPressed();


    }
}
