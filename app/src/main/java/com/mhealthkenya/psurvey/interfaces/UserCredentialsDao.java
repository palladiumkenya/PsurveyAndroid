package com.mhealthkenya.psurvey.interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mhealthkenya.psurvey.models.UserCredentials;

/**
 * Data Access Object (DAO) interface for managing user credentials in the database.
 * This interface provides methods for inserting, retrieving, and deleting user credentials.
 */
@Dao
public interface UserCredentialsDao {

    /**
     * Inserts a new user credential into the database.
     *
     * @param userCredentials The user credentials to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserCredentials userCredentials);

    /**
     * Retrieves user credentials from the database.
     *
     * @return UserCredentials object representing the user credentials.
     */
    @Query("SELECT * FROM user_credentials LIMIT 1")
    UserCredentials getUserCredentials();

    /**
     * Deletes all user credentials from the database.
     */
    @Query("DELETE FROM user_credentials")
    void deleteUserCredentials();
}

