package com.mhealthkenya.psurvey.interfaces;

import com.mhealthkenya.psurvey.models.UserDetails;

/**
 * An interface for handling callbacks related to the current user's data loading process.
 * Classes implementing this interface can receive notifications upon successful loading
 * of the current user's details or upon encountering a failure during the loading process.
 */
public interface CurrentUserCallback {

    /**
     * Callback method invoked when the current user's details are successfully loaded.
     *
     * @param currentUser The UserDetails object containing the loaded current user's details.
     */
    void onCurrentUserLoaded(UserDetails currentUser);

    /**
     * Callback method invoked when there is a failure in loading the current user's details.
     *
     * @param errorMessage A String containing an error message describing the reason for the failure.
     */
    void onCurrentUserLoadFailed(String errorMessage);
}

