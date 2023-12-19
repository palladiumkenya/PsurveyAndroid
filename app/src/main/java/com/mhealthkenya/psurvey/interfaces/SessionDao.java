package com.mhealthkenya.psurvey.interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mhealthkenya.psurvey.models.SessionOffline;
import com.mhealthkenya.psurvey.models.UserResponseEntity;

import java.util.List;

@Dao
public interface SessionDao {

    @Insert
    long insertsession(SessionOffline sessionOffline);

    @Update
    void updateEndTime(SessionOffline sessionOffline);

    @Query("SELECT * FROM SessionOffline WHERE questionnaireId = :questionnaireId")
    List<SessionOffline> getSessionForQuestionnaire(int questionnaireId);


    @Query("SELECT * FROM SessionOffline WHERE questionnaireId = :questionnaireId AND end_time IS NOT NULL ORDER BY start_time DESC")
    List<SessionOffline> getCompletedSessionsByQuestionnaireId(int questionnaireId);


    @Query("SELECT * FROM SessionOffline ORDER BY start_time DESC")
    List<SessionOffline> getAllSessions();
}
