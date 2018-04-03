package com.example.kraken.lab2.dao_access;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.kraken.lab2.models.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insertOnlySingleUser (User user);
    @Insert
    void insertMultipleUsers (List<User> userList);
    @Query("SELECT * FROM User WHERE userId = :userId")
    User fetchOneUserbyUserId (int userId);
    @Update
    void updateUser (User user);
    @Delete
    void deleteUser (User user);
}