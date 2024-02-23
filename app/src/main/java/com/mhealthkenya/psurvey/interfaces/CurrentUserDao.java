package com.mhealthkenya.psurvey.interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mhealthkenya.psurvey.models.CurrentUser;
import com.mhealthkenya.psurvey.models.UserDetails;

/**
 * Data Access Object (DAO) interface for accessing and manipulating the current user's data in a database.
 * This interface defines methods for retrieving the current user's details and inserting/updating them in the database.
 */
@Dao
public interface CurrentUserDao {

    /**
     * Retrieves the details of the current user from the database.
     *
     * @return The UserDetails object representing the current user, or null if not found.
     */
    @Query("SELECT * FROM current_user WHERE id = 1")
    UserDetails getCurrentUser();

    /**
     * Inserts or replaces the current user's details in the database.
     * If the current user already exists, it will be replaced with the new details.
     *
     * @param currentUser The CurrentUser object containing the details of the current user to be inserted/replaced.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCurrentUser(CurrentUser currentUser);

    @Query("DELETE FROM current_user")
    void deleteUserDetails();
}

