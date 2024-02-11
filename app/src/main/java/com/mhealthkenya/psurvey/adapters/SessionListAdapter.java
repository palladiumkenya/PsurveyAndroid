package com.mhealthkenya.psurvey.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mhealthkenya.psurvey.R;
import com.mhealthkenya.psurvey.activities.ResponseData;
import com.mhealthkenya.psurvey.activities.SessionList;
import com.mhealthkenya.psurvey.depedancies.Tools;
import com.mhealthkenya.psurvey.depedancies.ViewAnimation;
import com.mhealthkenya.psurvey.models.QuestionnaireEntity;
import com.mhealthkenya.psurvey.models.SessionID;
import com.mhealthkenya.psurvey.models.UniqueIdentifierEntity;
import com.mhealthkenya.psurvey.models.UserResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class SessionListAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<UniqueIdentifierEntity> userResponseEntities= new ArrayList<>();
    public Context context;

    public SessionListAdapter.OnItemClickListener onItemClickListener;

    public  interface OnItemClickListener{
        void onItemClick(int position);

    }

    public void setOnItemClickListener(SessionListAdapter.OnItemClickListener Listener){
        this. onItemClickListener = Listener;

    }

    public  SessionListAdapter (Context context){

        this.context = context;


    }

    public void setUser(List<UniqueIdentifierEntity> userResponseEntities){
        this.userResponseEntities =userResponseEntities;
        //notifyDataSetChanged();
        //notifyAll();
        notifyDataSetChanged();
    }


    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView surveyTitle;
        public TextView surveyDescription;
        public ImageButton bt_expand;
        public ImageButton bt_expand2;
        public View lyt_expand;
        public View lyt_parent;



        public OriginalViewHolder(View v) {
            super(v);
            surveyTitle = (TextView) v.findViewById(R.id.sessionId);


        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.session_adapter_layout, parent, false);
        vh = new SessionListAdapter.OriginalViewHolder(v);
        return vh;
        //return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        UniqueIdentifierEntity obj = userResponseEntities.get(position);
        if (holder instanceof SessionListAdapter.OriginalViewHolder) {
            SessionListAdapter.OriginalViewHolder view = (SessionListAdapter.OriginalViewHolder) holder;

            view.surveyTitle.setText(obj.getUniqueIdentifier());
            // view.surveyDescription.setText(obj.getDescription());
//            view.surveyDescription.setText(obj.getDescription());


            view.surveyTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* boolean show = toggleLayoutExpand(!obj.expanded, v, view.lyt_expand);
                    questionnaireEntities.get(position).expanded = show;*/
                    Intent intent = new Intent(context, ResponseData.class);
                //    intent.putExtra("Session_ID", obj.getUniqueIdentifier());

                    SessionID.deleteAll(SessionID.class);
                    SessionID sessionID = new SessionID(obj.getUniqueIdentifier());
                    sessionID.save();
                    context.startActivity(intent);
                   // Toast.makeText(context, "ID is" + obj.getUniqueIdentifier(), Toast.LENGTH_LONG).show();
                }
            });


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
        return userResponseEntities.size();
    }



}
