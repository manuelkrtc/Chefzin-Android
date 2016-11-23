package com.future333.chefzin.Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.future333.chefzin.AppHandler;
import com.future333.chefzin.MainActivity;
import com.future333.chefzin.R;
import com.future333.chefzin.model.Chef;
import com.future333.chefzin.tools.ToolsApi;
import com.future333.chefzin.tools.ImageLoader;

import java.util.ArrayList;

/**
 * Created by manuel on 12/09/16.
 */
public class FragmentChef extends Fragment {

    Activity    ctx;
    AppHandler  app;

    SliderLayout slider;
    ImageLoader  _imageL;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ctx = getActivity();
        app = ((AppHandler)getActivity().getApplication());

        _imageL = new ImageLoader(ctx);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_chef, container, false);

        slider = (SliderLayout)v.findViewById(R.id.slider);

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initSlider(app.ctrChef.getChefs());
    }

    private void initSlider(ArrayList<Chef> chefs) {
        slider.stopAutoCycle();
        for(Chef chef: chefs){
            slider.addSlider(new CustomSliderView(chef));
        }
    }

    public class CustomSliderView extends BaseSliderView {

        private Chef chef;

        public CustomSliderView(Chef chef) {
            super(getActivity());
            this.chef = chef;
        }

        @Override
        public View getView() {
            View v = LayoutInflater.from(getContext()).inflate(R.layout.row_chef, null);

            TextView    tvNameChef          = (TextView) v.findViewById(R.id.tvNameChef);
            TextView    tvDescription       = (TextView) v.findViewById(R.id.tvDescription);
            TextView    tvSpecialtyChef     = (TextView) v.findViewById(R.id.tvSpecialtyChef);
            RatingBar   ratingBar           = (RatingBar)v.findViewById(R.id.ratingBar);
            ImageView   ivChef              = (ImageView)v.findViewById(R.id.ivChef);
            ViewGroup   lyParent            = (ViewGroup)v.findViewById(R.id.lyParent);

            ratingBar.setRating(chef.getCalificacion());
            _imageL.loadAndDisplayImage(ToolsApi.URL_IMG_CHEF + chef.getFoto(), ivChef);

            if(chef.getNombres() != null)   tvNameChef.setText(chef.getNombres());
            else                            tvNameChef.setVisibility(View.GONE);

            if(chef.getDescripcion() != null)   tvDescription.setText(chef.getDescripcion());
            else                                tvDescription.setVisibility(View.GONE);

            if(chef.getEspecializacion() != null)   tvSpecialtyChef.setText(chef.getEspecializacion());
            else                                    tvSpecialtyChef.setVisibility(View.GONE);


            lyParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    app.chefSelect = chef;
                    ((MainActivity)getActivity()).goFragmentMenu();
                }
            });

            return v;
        }
    }
}
