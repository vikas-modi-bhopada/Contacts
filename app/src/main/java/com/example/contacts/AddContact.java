package com.example.contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddContact extends AppCompatActivity {

    EditText nameEdit,phoneEdit;
    Button saveBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        nameEdit = findViewById(R.id.nameedit);
        phoneEdit = findViewById(R.id.phoneedit);
        saveBtn = findViewById(R.id.save);
        final MyDBHandler db = new MyDBHandler(this);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEdit.getText().toString();
                String phone = phoneEdit.getText().toString();

                if (name.isEmpty() || phone.isEmpty())
                    Toast.makeText(getApplicationContext(),"Please fill the entries",Toast.LENGTH_LONG).show();
                else
                {
                    if (phone.length()<10 || phone.length()>10)
                        Toast.makeText(getApplicationContext(),"Please Enter 10 digit number",Toast.LENGTH_LONG).show();
                    else
                    {
                        Contact contact = new Contact();
                        contact.setName(name);
                        contact.setPhoneNumber(phone);
                        db.addContact(contact);
                        Toast.makeText(getApplicationContext(),"Contact Saved",Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(AddContact.this,MainActivity.class);
                        startActivity(intent);
                    }
                }

            }
        });
    }
}
