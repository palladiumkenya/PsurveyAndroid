package com.mhealthkenya.psurvey.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mhealthkenya.psurvey.R;
import com.mhealthkenya.psurvey.activities.AllQuestionDatabase;
import com.mhealthkenya.psurvey.activities.ResponseData;
import com.mhealthkenya.psurvey.activities.SessionList;
import com.mhealthkenya.psurvey.depedancies.Tools;
import com.mhealthkenya.psurvey.depedancies.ViewAnimation;
import com.mhealthkenya.psurvey.models.ActiveSurveys;
import com.mhealthkenya.psurvey.models.QuestionnaireEntity;
import com.mhealthkenya.psurvey.models.QuetionnaireID;
import com.mhealthkenya.psurvey.models.SessionID;
import com.mhealthkenya.psurvey.models.UniqueIdentifierEntity;

import java.util.ArrayList;
import java.util.List;

public class QuestionnairesAdapterOffline extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<QuestionnaireEntity> questionnaireEntities= new ArrayList<>();
    public Context context;
    AllQuestionDatabase allQuestionDatabase;
    List<UniqueIdentifierEntity> userResponses;

    int   IDvalue;
    public OnItemClickListener onItemClickListener;

    public  interface OnItemClickListener{
        void onItemClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener Listener){
        this. onItemClickListener = Listener;

    }

    public  QuestionnairesAdapterOffline (Context context){

        this.context = context;


    }

    public void setUser(List<QuestionnaireEntity> questionnaireEntities){
        this. questionnaireEntities =  questionnaireEntities;
        //notifyDataSetChanged();
        //notifyAll();
        notifyDataSetChanged();
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView surveyTitle;
        public TextView surveyDescription, tv_survey_count;
        public ImageButton bt_expand;
        public ImageButton bt_expand2;
        public View lyt_expand;
        public View lyt_parent;




        public OriginalViewHolder(View v) {
            super(v);
            surveyTitle = (TextView) v.findViewById(R.id.tv_survey_title);
            surveyDescription = (TextView) v.findViewById(R.id.tv_survey_description);
            bt_expand = (ImageButton) v.findViewById(R.id.bt_expand);
            bt_expand2 = (ImageButton) v.findViewById(R.id.bt_expand0);
            lyt_expand = (View) v.findViewById(R.id.lyt_expand);
            lyt_parent = (View) v.findViewById(R.id.lyt_parent);

            tv_survey_count = (TextView) v.findViewById(R.id.tv_survey_count);

            surveyDescription.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position =getAdapterPosition();
                    if (onItemClickListener !=null && position != RecyclerView.NO_POSITION);
                   // onItemClickListener.onItemClick(questionnaireEntities.get(position));
                    onItemClickListener.onItemClick(position);
                }
            });


        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.offline_questionnaire_adapter, parent, false);
        vh = new QuestionnairesAdapterOffline.OriginalViewHolder(v);
        allQuestionDatabase = AllQuestionDatabase.getInstance(context);
        return vh;
        //return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        QuestionnaireEntity obj = questionnaireEntities.get(position);
        if (holder instanceof QuestionnairesAdapterOffline.OriginalViewHolder) {
            QuestionnairesAdapterOffline.OriginalViewHolder view = (QuestionnairesAdapterOffline.OriginalViewHolder) holder;

            view.surveyTitle.setText(obj.getName());
            userResponses = allQuestionDatabase.userResponseDao().getSessions(obj.getId());
            view.tv_survey_count.setText(String.valueOf(userResponses.size()));


           // view.surveyDescription.setText(obj.getDescription());
            view.surveyDescription.setText(obj.getDescription());

            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //This  is onclick implementation
                    if (onItemClickListener != null) {
                        //Toast.makeText(context,items.get(position).getLogo(), Toast.LENGTH_LONG).show();
                        onItemClickListener.onItemClick(position);
                    }
                }
            });


            view.bt_expand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* boolean show = toggleLayoutExpand(!obj.expanded, v, view.lyt_expand);
                    questionnaireEntities.get(position).expanded = show;*/
                    Intent intent = new Intent(context, SessionList.class);
                  //  intent.putExtra("Quetionnaire_ID", obj.getId());

                   // QuetionnaireID.deleteAll(QuetionnaireID.class);
                    QuetionnaireID quetionnaireID = new QuetionnaireID(obj.getId());
                    quetionnaireID.save();


                    context.startActivity(intent);
                  //  Toast.makeText(context, "ID is" + obj.getId(), Toast.LENGTH_LONG).show();
                }
            });
            view.bt_expand2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   boolean show = toggleLayoutExpand(!obj.expanded1, v, view.lyt_expand);
                  questionnaireEntities.get(position).expanded1 = show;


                }
            });


            if (obj.expanded1) {
                view.lyt_expand.setVisibility(View.VISIBLE);
                Toast.makeText(context, "Your Quetionnaire_ID is" + " " + String.valueOf(IDvalue), Toast.LENGTH_LONG).show();
            } else {
                view.lyt_expand.setVisibility(View.GONE);
            }
            Tools.toggleArrow(obj.expanded1, view.bt_expand, false);


        }
    }


    private boolean toggleLayoutExpand(boolean show, View view, View lyt_expand) {
        Tools.toggleArrow(show, view);
        if (show) {
            ViewAnimation.expand(lyt_expand);
        } else {
            ViewAnimation.collapse(lyt_expand);
        }
        return show;

    }
  //      }
//}

   @Override
    public int getItemCount() {
        return questionnaireEntities.size();
    }

    }
