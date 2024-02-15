package com.mhealthkenya.psurvey.activities.auth;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mhealthkenya.psurvey.models.UserResponseEntity;

import java.lang.reflect.Type;
import java.util.List;

public class Converters {
    @TypeConverter
    public static List<UserResponseEntity> fromString(String value) {
        Type listType = new TypeToken<List<UserResponseEntity>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromList(List<UserResponseEntity> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
