package com.example.contacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TaskPerform extends AppCompatActivity {

    TextView textView;
    EditText smsEdit;
    MyDBHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_perform);

        textView = findViewById(R.id.textView);
        smsEdit = findViewById(R.id.smsedit);
        db = new MyDBHandler(TaskPerform.this);

        Intent intent = getIntent();
        String number = intent.getStringExtra(MainActivity.MSG);
        textView.setText(number);
    }
    public void call(View view)
    {
        String number = textView.getText().toString();
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+number));
        if (ActivityCompat.checkSelfPermission(TaskPerform.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(getApplicationContext(),"Please Grant Permission",Toast.LENGTH_LONG).show();
            requestPermission();
        }
        else
        {
            startActivity(callIntent);
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(TaskPerform.this,new String[]{Manifest.permission.CALL_PHONE},1);
    }
    public void sms(View view)
    {
        int permission = ContextCompat.checkSelfPermission(TaskPerform.this,Manifest.permission.SEND_SMS);
        if (permission == PackageManager.PERMISSION_GRANTED)
        {
            sendSMS();
        }
        else
        {
            ActivityCompat.requestPermissions(TaskPerform.this,new String[]{Manifest.permission.SEND_SMS},0);
        }
    }

    private void sendSMS() {
        String number = textView.getText().toString();
        String text = smsEdit.getText().toString();
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(number,null,text,null,null);
        Toast.makeText(getApplicationContext(),"Message send",Toast.LENGTH_LONG).show();
    }
    public void deleteContact(View view)
    {
        String number = textView.getText().toString();

        db.delete(number);

        Intent intent = new Intent(TaskPerform.this,MainActivity.class);
        startActivity(intent);
    }
}
