package com.future333.chefzin;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.future333.chefzin.model.Chef;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by manuel on 12/09/16.
 */
public class FragmentChef extends Fragment {

    SliderLayout slider;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chef, container, false);

        inicializate(view);

        ArrayList<Chef> chefs = new ArrayList<>();
        chefs.add(new Chef("manuel","Vergetariano","super mega play", null));
        chefs.add(new Chef("man Paez","Vergetariano","supe play", null));
        chefs.add(new Chef("Jose Vernicio","Carnivoro","super mega  dss play", null));
        chefs.add(new Chef("Davis loquis","Vergetariano","super mega sdsac play", null));
        chefs.add(new Chef("linis oillis","Cervecina","super mega play", null));
        chefs.add(new Chef("ivan tardiusl","Vergetariano","super mega play", null));

        initSlider(chefs);

        return view;
    }

    private void inicializate(View v){
        slider = (SliderLayout)v.findViewById(R.id.slider);
    }

    private void initSlider(ArrayList<Chef> chefs) {

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

            ImageView   ivChef             = (ImageView)v.findViewById(R.id.ivChef);
            TextView    tvNameChef         = (TextView) v.findViewById(R.id.tvNameChef);
            TextView    tvDescription      = (TextView) v.findViewById(R.id.tvDescription);
            TextView    tvSpecialtyChef    = (TextView) v.findViewById(R.id.tvSpecialtyChef);

            if(tvNameChef != null)  tvNameChef.setText(chef.name);
            else                    tvNameChef.setVisibility(View.GONE);

            if(tvDescription != null)   tvDescription.setText(chef.description);
            else                        tvDescription.setVisibility(View.GONE);

            if(tvSpecialtyChef != null) tvSpecialtyChef.setText(chef.specialty);
            else                        tvSpecialtyChef.setVisibility(View.GONE);

            return v;
        }


    }


}
