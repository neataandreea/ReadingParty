package com.example.readingparty;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {

    @Insert
    void registerUser(UserEntity userEntity);




    @Query("SELECT EXISTS(SELECT * FROM users WHERE (username = :username AND password=:password))")
    boolean recordExistsUSername (String username, String password);


}
