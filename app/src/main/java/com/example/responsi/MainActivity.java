package com.example.responsi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.responsi.ContactAdapter;
import com.example.responsi.AppDatabase;
import com.example.responsi.ContactModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvContact;
    Button btnTambah;
    ImageView btnCall;
    AppDatabase database;
    ContactAdapter contactAdapter;
    List<ContactModel> list;
    AlertDialog.Builder dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvContact = findViewById(R.id.rvContact);
        btnTambah = findViewById(R.id.btnTambah);

        database = AppDatabase.getInstance(getApplicationContext());
        list = new ArrayList<>();
        list.clear();
        list.addAll(database.contactDAO().getAll());
        contactAdapter = new ContactAdapter(list,getApplicationContext());
        contactAdapter.setDialog(new ContactAdapter.Dialog() {
            @Override
            public void onClick(int position) {
                final CharSequence[] dialogItem = {"Edit", "Hapus"};
                dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                Intent intent = new Intent(MainActivity.this, ContactAddActivity.class);
                                intent.putExtra("id", list.get(position).id);
                                startActivity(intent);
                                break;
                            case 1:
                                ContactModel contactModel = list.get(position);
                                database.contactDAO().delete(contactModel);
                                onStart();
                                break;
                        }
                    }
                });
                dialog.show();
            }
        });

        rvContact.setHasFixedSize(true);
        rvContact.setLayoutManager(new LinearLayoutManager(this));
        rvContact.setAdapter(contactAdapter);

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ContactAddActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        list.clear();
        list.addAll(database.contactDAO().getAll());
        contactAdapter.notifyDataSetChanged();
    }
}