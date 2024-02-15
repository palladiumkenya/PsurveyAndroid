package com.mhealthkenya.psurvey.interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.mhealthkenya.psurvey.models.QuestionnaireEntity;
import com.mhealthkenya.psurvey.models.UniqueIdentifierEntity;
import com.mhealthkenya.psurvey.models.UserResponseEntity;
import com.mhealthkenya.psurvey.models.UserResponseEntity2;

import java.util.List;

@Dao
public interface UserResponseDao {
    @Insert
    void insertResponse(UserResponseEntity userResponse);


    @Update
    void updateResponse(UserResponseEntity userResponse);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertResponse1(List<UserResponseEntity> userResponseEntityList);

    @Update
    Void updateResponse1(UserResponseEntity userResponseEntity);

    @Query("SELECT * FROM question_answers WHERE Sessionid = :Sessionid")
    UserResponseEntity getUserResponseById(int Sessionid);

//list quetionnaires then responses
    @Query("SELECT * FROM question_answers WHERE questionnaireId = :questionnaireId")
    List<UserResponseEntity> getUserResponsesForQuestionnaire(int questionnaireId);


    @Query("SELECT * FROM question_answers WHERE  uniqueIdentifier = :uniqueIdentifier")
    List<UserResponseEntity> getUserResponsesForuniqueIdentifier(String uniqueIdentifier);

    @Query("SELECT * FROM question_answers WHERE  uniqueIdentifier = :uniqueIdentifier")
    List<UserResponseEntity> getUserResponsesForuniqueIdentifier2(String uniqueIdentifier);



    @Query("SELECT DISTINCT uniqueIdentifier FROM question_answers WHERE questionnaireId = :questionnaireId")
    List<UniqueIdentifierEntity> getSessions(int questionnaireId);







    //   uniqueIdentifier

    //list UUID then responses
    //display responses based on quetionnaire
    //select * from user_response where UUID=:



    // Add more queries and operations as needed
    @Query("SELECT * FROM question_answers WHERE questionnaireId = :questionnaireId")
    LiveData<List<UserResponseEntity>> getDraftAnswers(int questionnaireId);
}
