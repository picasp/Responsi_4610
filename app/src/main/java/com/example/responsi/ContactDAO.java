package com.example.responsi;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.responsi.ContactModel;

import java.util.List;

@Dao
public interface ContactDAO {
    @Query("SELECT * FROM contactmodel")
    List<ContactModel> getAll();

    @Insert
    void insertAll(ContactModel... contactModels);

    @Query("UPDATE contactmodel SET nama=:nama, nomer=:nomer WHERE id=:id")
    void update(int id, String nama, String nomer);

    @Query("SELECT * FROM contactmodel WHERE id=:id")
    ContactModel get(int id);

    @Delete
    void delete(ContactModel contactModel);
}
