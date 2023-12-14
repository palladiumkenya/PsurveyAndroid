package com.mhealthkenya.psurvey.interfaces;

import androidx.room.Dao;
import androidx.room.Insert;

import com.mhealthkenya.psurvey.models.SessionOffline;

@Dao
public interface SessionDao {

    @Insert
    void insertsession(SessionOffline sessionOffline);
}
