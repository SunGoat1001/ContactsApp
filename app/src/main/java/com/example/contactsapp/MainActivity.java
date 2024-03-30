package com.example.contactsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.example.contactsapp.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private ArrayList<Contact> contactList;
    private ContactsAdapter contactAdapter;

    private AppDatabase appDatabase;
    private ContactDao contactDao;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        binding.rvContacts.setLayoutManager(new LinearLayoutManager(this));
        contactList = new ArrayList<Contact>();
        contactAdapter = new ContactsAdapter(contactList);
        binding.rvContacts.setAdapter(contactAdapter);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase = AppDatabase.getInstance(getApplicationContext());
                contactDao = appDatabase.contactDao();

                for (Contact contact : contactDao.getAll()) {
                    contactList.add(contact);
                }
                contactAdapter.notifyDataSetChanged();
            }
        });

        binding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewContact.class);
                startActivity(intent);
            }
        });
    }
}