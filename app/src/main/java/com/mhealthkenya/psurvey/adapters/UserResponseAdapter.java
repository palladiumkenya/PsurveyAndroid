package com.mhealthkenya.psurvey.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mhealthkenya.psurvey.R;
import com.mhealthkenya.psurvey.activities.EditActivity;
import com.mhealthkenya.psurvey.activities.ResponseData;
import com.mhealthkenya.psurvey.models.QuestionnaireEntity;
import com.mhealthkenya.psurvey.models.ResponseIntent;
import com.mhealthkenya.psurvey.models.UserResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class UserResponseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<UserResponseEntity> userResponseEntities= new ArrayList<>();
    public Context context;

    public UserResponseAdapter.OnItemClickListener onItemClickListener;

    public  interface OnItemClickListener{
        void onItemClick(int position);

    }

    public void setOnItemClickListener(UserResponseAdapter.OnItemClickListener Listener){
        this. onItemClickListener = Listener;

    }
    // Constructor to initialize userResponses
    public UserResponseAdapter(Context context) {
        this.context = context;
    }

    public void setUser2(List<UserResponseEntity> userResponseEntities){
        this.userResponseEntities = userResponseEntities;
        notifyDataSetChanged();
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView surveyID;
        public TextView queryID;
        public TextView btnOpen;
        public Button bt_expand;
        public View lyt_expand;
        public View lyt_parent;


        public ImageButton editbt;
        public ImageButton deletebt;



        public OriginalViewHolder(View v) {
            super(v);
            surveyID = (TextView) v.findViewById(R.id.tv_survey_id);
            queryID = (TextView) v.findViewById(R.id.tv_query_id);
            btnOpen = (TextView) v.findViewById(R.id.tv_open_id);
          //  bt_expand = (Button) v.findViewById(R.id.tv_btn);

            editbt = (ImageButton) v.findViewById(R.id.bt_edit);
            deletebt = (ImageButton) v.findViewById(R.id.bt_delete);
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_response_item, parent, false);
        vh = new UserResponseAdapter.OriginalViewHolder(v);
        return vh;
       // return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        UserResponseEntity obj = userResponseEntities.get(position);
        if (holder instanceof UserResponseAdapter.OriginalViewHolder) {
            UserResponseAdapter.OriginalViewHolder view = (UserResponseAdapter.OriginalViewHolder) holder;


            view.surveyID.setText(obj.getQuetion_A());



            if (obj.getQuestionType()==3   || obj.getQuestionType()==2){
                view.btnOpen.setText(obj.getMulti());
            }else{
                view.btnOpen.setText(obj.getOption());
            }


            view.editbt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (onItemClickListener != null) {
                        Toast.makeText(context,"Edit"+obj.getQuestionType(), Toast.LENGTH_LONG).show();
               //         onItemClickListener.onItemClick(position);

                        Intent ii=new Intent(context, EditActivity.class);
                         ResponseIntent responseIntent = new ResponseIntent(obj.getIdA(), obj.getSessionid(), obj.getAnswerId(), obj.getQuestionId(), obj.getQuestionType(), obj.getQuestionnaireId(), obj.getQuetion_A(), obj.getOption(), obj.getUniqueIdentifier(), obj.getDateValidation(), obj.isRequired(), obj.isRepeatable());
                         responseIntent.save();
                        context.startActivity(ii);
                    }

                }
            });


            view.deletebt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (onItemClickListener != null) {
                         Toast.makeText(context,"delete"+obj.getAnswerId(), Toast.LENGTH_LONG).show();
                    }

                }
            });


}}

    @Override
    public int getItemCount() {
        return userResponseEntities.size();
    }
}
