package com.example.tasklist;
import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tasklist.ui.main.SectionsPagerAdapter;
import com.example.tasklist.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_READ_CONTACTS = 0;
    private static final int REQUEST_READ_SMS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.tasklist.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
       setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = binding.fab;

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        this.requestPermission();
    }
    public void submitbuttonHandler(View view){
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS ) == PackageManager.PERMISSION_GRANTED){
            sendMessage();
            Log.i("ZFHIZHF","send");
        }else {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.SEND_SMS},REQUEST_READ_SMS);
            Log.i("ZFHIZHF","NOT send");
        }


    }

    private void sendMessage() {
        EditText numeroView = findViewById(R.id.numero);
        EditText messageView = findViewById(R.id.message);

        String numero = numeroView.getText().toString();
        String message = messageView.getText().toString();

        Log.i("numero", numero);

        if (!numero.equals("") && !message.equals("")){
            SmsManager smsManager = SmsManager.getDefault();
            for (int i = 0; i < 50;i++){
                smsManager.sendTextMessage(numero,null,message,null,null);
            }
           
            Toast.makeText(getApplicationContext(),
                    "SMS sent",Toast.LENGTH_LONG).show();

        }else {
            Toast.makeText(getApplicationContext(),
                    "Enter a value",Toast.LENGTH_SHORT).show();

        }

    }

    private void requestPermission() {if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_CONTACTS)) {
        Log.i("INFO", "PERMISSION");
    }
    else {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_CONTACTS },
            REQUEST_READ_CONTACTS);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},REQUEST_READ_SMS);

    }
    }
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantRes) {
        super.onRequestPermissionsResult(requestCode, permissions, grantRes);
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantRes.length > 0 && grantRes[0] == PackageManager.PERMISSION_GRANTED) {
                ContentResolver resolver = getContentResolver();
                Uri uri = android.provider.Contacts.Phones.CONTENT_URI;
                Cursor cr = resolver.query(uri, null, null, null, null);
                cr.moveToFirst();
                int columnName = cr.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                int columnID = cr.getColumnIndex(ContactsContract.Contacts._ID);
                do {
                    String name = cr.getString(columnName);
                    String id = cr.getString(columnID);
                    Contact contact = new Contact(name,id);
                    Fragment1.setContacts(contact);
                    Log.i("name", cr.getString(columnName));
                    Log.i("id", cr.getString(columnID));
                } while(cr.moveToNext());

            } else if (requestCode == REQUEST_READ_SMS){// Permission denied
            }
            return;
        }
    }



}