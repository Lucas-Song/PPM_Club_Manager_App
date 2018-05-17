package com.example.lucassong.clubsandwich.post;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Lucas Song on 14/2/2018.
 */

@Dao
public interface PostDao {

    @Query("SELECT * FROM Post")
    LiveData<List<Post>> getAllPosts();

    @Query("SELECT * FROM Post WHERE post_ID LIKE :postID LIMIT 1")
    Post findByID(String postID);

    @Query("DELETE FROM Post")
    void clearAll();

    @Insert
    void insertAll(Post... posts);

    @Update
    void update(Post post);

    @Delete
    void delete(Post post);
}
