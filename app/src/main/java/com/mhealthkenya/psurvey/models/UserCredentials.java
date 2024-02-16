package com.mhealthkenya.psurvey.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Entity class representing user credentials in the database.
 * This class is annotated with @Entity to specify it as a table in the database with the name "user_credentials".
 */
@Entity(tableName = "user_credentials")
public class UserCredentials {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String phoneNumber;
    private String password;

    /**
     * Constructs a new UserCredentials object with the given phone number and password.
     *
     * @param phoneNumber The phone number associated with the user.
     * @param password    The password associated with the user.
     */
    public UserCredentials(String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    /**
     * Retrieves the unique identifier of the user credentials.
     *
     * @return The unique identifier of the user credentials.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the user credentials.
     *
     * @param id The unique identifier to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the phone number associated with the user.
     *
     * @return The phone number associated with the user.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number associated with the user.
     *
     * @param phoneNumber The phone number to set.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Retrieves the password associated with the user.
     *
     * @return The password associated with the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password associated with the user.
     *
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
