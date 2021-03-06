package com.mhealthkenya.psurvey.fragments.survey;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.mhealthkenya.psurvey.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class CompleteSurveyFragment extends Fragment {

    private Unbinder unbinder;
    private View root;
    private Context context;



    @BindView(R.id.btn_new_survey)
    Button btn_new_survey;

    @BindView(R.id.btn_done)
    Button btn_done;

    @Override
    public void onAttach(Context ctx) {
        super.onAttach(ctx);
        this.context = ctx;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_complete_survey, container, false);
        unbinder = ButterKnife.bind(this, root);

        btn_new_survey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NavHostFragment.findNavController(CompleteSurveyFragment.this).navigate(R.id.nav_select_survey);
               // NavHostFragment.findNavController(CompleteSurveyFragment.this).navigate(R.id.i);

              //  Intent integer = new Intent(getContext(), InformedConsentFragment.class);
                //startActivity(integer);


            }
        });

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NavHostFragment.findNavController(CompleteSurveyFragment.this).navigate(R.id.nav_home);

            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}