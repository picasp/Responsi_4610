package com.example.responsi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Index;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.responsi.AppDatabase;
import com.example.responsi.ContactModel;

public class ContactAddActivity extends AppCompatActivity {

    EditText etNama, etNomer;
    Button btnSimpan;
    AppDatabase database;
    int id = 0;
    boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_add);
        etNama = findViewById(R.id.etNama);
        etNomer = findViewById(R.id.etNomer);
        btnSimpan = findViewById(R.id.btnSimpan);
        database = AppDatabase.getInstance(getApplicationContext());

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        if (id > 0) {
            isEdit = true;
            ContactModel contactModel = database.contactDAO().get(id);
            etNama.setText(contactModel.nama);
            etNomer.setText(contactModel.nomer);
        } else {
            isEdit = false;
        }

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContactModel contactModel = new ContactModel();
                contactModel.nama = etNama.getText().toString();
                contactModel.nomer = etNomer.getText().toString();

                if (isEdit) {
                    database.contactDAO().update(id, contactModel.nama, contactModel.nomer);
                } else {
                    database.contactDAO().insertAll(contactModel);
                }
                finish();
            }
        });
    }
}