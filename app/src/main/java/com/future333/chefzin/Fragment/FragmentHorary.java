package com.future333.chefzin.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.future333.chefzin.MainActivity;
import com.future333.chefzin.R;
import com.future333.chefzin.model.Chef;
import com.future333.chefzin.model.Horary;

import java.util.ArrayList;

/**
 * Created by manuel on 12/09/16.
 */
public class FragmentHorary extends Fragment {

    SliderLayout slider;
    PagerIndicator pagerIndicator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_horary, container, false);

        inicializate(view);

        ArrayList<Horary> horaries = new ArrayList<>();
        horaries.add(new Horary("DESAYUNO", R.drawable.img_breakfast));
        horaries.add(new Horary("ALMUERZO", R.drawable.img_lunch));
        horaries.add(new Horary("CENA",     R.drawable.img_dinner));

        initSlider(horaries);

        return view;
    }

    private void inicializate(View v){
        slider = (SliderLayout)v.findViewById(R.id.slider);
        pagerIndicator = (PagerIndicator) v.findViewById(R.id.custom_indicator);
    }

    private void initSlider(ArrayList<Horary> horaries) {
        slider.setCustomIndicator(pagerIndicator);
        slider.stopAutoCycle();
        for(Horary horary: horaries){
            slider.addSlider(new CustomSliderView(horary));
        }
    }

    public class CustomSliderView extends BaseSliderView {

        private Horary horary;

        public CustomSliderView(Horary horary) {
            super(getActivity());
            this.horary = horary;
        }

        @Override
        public View getView() {
            View v = LayoutInflater.from(getContext()).inflate(R.layout.row_horary, null);

            ImageView   ivHorary    = (ImageView)   v.findViewById(R.id.ivHorary);
            TextView    tvHorary    = (TextView)    v.findViewById(R.id.tvHorary);
            ViewGroup   lyParent    = (ViewGroup)   v.findViewById(R.id.lyParent);

            tvHorary.setText(horary.name);
            ivHorary.setImageResource(horary.image);

            lyParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity)getActivity()).goFragmentChef();
                }
            });

            return v;
        }


    }


}
