package com.apolongo.apolongo.DB;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface CardDao {
    @Insert
    void insert(Card card);

    @Delete
    void delete(Card card);

    @Query("DELETE FROM cards_table")
    void deleteAll();

    @Query("SELECT * from cards_table ORDER BY cards_name ASC")
    LiveData<List<Card>> getAllCards();
}
