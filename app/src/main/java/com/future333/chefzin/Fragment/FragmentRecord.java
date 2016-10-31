package com.future333.chefzin.Fragment;

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
import com.future333.chefzin.MainActivity;
import com.future333.chefzin.R;
import com.future333.chefzin.model.Chef;

import java.util.ArrayList;

/**
 * Created by manuel on 12/09/16.
 */
public class FragmentRecord extends Fragment {

    SliderLayout slider;

    public static FragmentRecord newInstance() {
        return new FragmentRecord();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_record, container, false);

        inicializate(view);

        ArrayList<Chef> chefs = new ArrayList<>();
        chefs.add(new Chef("manuel","Vegetariano","Comida saludable, acopañada con moluscos ", null));
        chefs.add(new Chef("Paez","Oriental","En la cocina oriental podemos encontrar platos exóticos y fáciles de preparar para sorprender a nuestra familia o deleitar a nuestros invitados.", null));
        chefs.add(new Chef("Jose Vernicio","Carnivoro","super mega  dss play", null));
        chefs.add(new Chef("Davis loquen","Marina","Sudado de tramboyo, sabroso plato en donde el pescado se cocina en su propio jugo\n" , null));

        initSlider(chefs);

        return view;
    }

    private void inicializate(View v){
        slider = (SliderLayout)v.findViewById(R.id.slider);
    }

    private void initSlider(ArrayList<Chef> chefs) {
        slider.stopAutoCycle();
        for(Chef chef: chefs){
            slider.addSlider(new CustomSliderView(chef));
        }
    }

    public class CustomSliderView extends BaseSliderView {


        public CustomSliderView(Chef chef) {
            super(getActivity());
        }

        @Override
        public View getView() {
            View v = LayoutInflater.from(getContext()).inflate(R.layout.row_record , null);

//            ViewGroup   lyParent           = (ViewGroup)v.findViewById(R.id.lyParent);
//            ImageView   ivChef             = (ImageView)v.findViewById(R.id.ivChef);
//            TextView    tvNameChef         = (TextView) v.findViewById(R.id.tvNameChef);
//            TextView    tvDescription      = (TextView) v.findViewById(R.id.tvDescription);
//            TextView    tvSpecialtyChef    = (TextView) v.findViewById(R.id.tvSpecialtyChef);
//
//            RatingBar       ratingBar       = (RatingBar) v.findViewById(R.id.ratingBar);
//
//            ratingBar.setRating(3.2F);
//
//            if(chef.name != null)  tvNameChef.setText(chef.name);
//            else                    tvNameChef.setVisibility(View.GONE);
//
//            if(chef.description != null)   tvDescription.setText(chef.description);
//            else                        tvDescription.setVisibility(View.GONE);
//
//            if(chef.specialty != null) tvSpecialtyChef.setText(chef.specialty);
//            else                        tvSpecialtyChef.setVisibility(View.GONE);
//
//            lyParent.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    ((MainActivity)getActivity()).goFragmentMenu();
//                }
//            });

            return v;
        }
    }


}