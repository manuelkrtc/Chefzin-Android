package com.future333.chefzin.Fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.future333.chefzin.MainActivity;
import com.future333.chefzin.R;
import com.future333.chefzin.model.Chef;

import java.util.ArrayList;
import java.util.zip.Inflater;

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

        View v = inflater.inflate(R.layout.fragment_record, container, false);

         slider = (SliderLayout)v.findViewById(R.id.slider);

        ArrayList<Chef> chefs = new ArrayList<>();
        chefs.add(new Chef("manuel","Vegetariano","Comida saludable, acopañada con moluscos ", null));
        chefs.add(new Chef("Paez","Oriental","En la cocina oriental podemos encontrar platos exóticos y fáciles de preparar para sorprender a nuestra familia o deleitar a nuestros invitados.", null));
        chefs.add(new Chef("Jose Vernicio","Carnivoro","super mega  dss play", null));
        chefs.add(new Chef("Davis loquen","Marina","Sudado de tramboyo, sabroso plato en donde el pescado se cocina en su propio jugo\n" , null));

        initSlider(chefs);

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


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
            View view = LayoutInflater.from(getContext()).inflate(R.layout.row_record , null);

            LinearLayout zoneSteps = (LinearLayout) view.findViewById(R.id.zoneSteps);

            zoneSteps.addView(initSteps(1, "Recibido y cocinando",false));
            zoneSteps.addView(initSteps(2, "Listo para entregar",false));
            zoneSteps.addView(initSteps(3, "En camino",true));
            zoneSteps.addView(initSteps(4, "Entregado",false));


            return view;
        }

        private View initSteps(int number, String nameStep, boolean isSelect){
            View stepView = LayoutInflater.from(getActivity()).inflate(R.layout.row_record_step, null);
            stepView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1));

            TextView tvNumber       = (TextView)stepView.findViewById(R.id.tvNumber);
            TextView tvNameStep     = (TextView)stepView.findViewById(R.id.tvNameStep);
            ViewGroup zoneStroke    = (ViewGroup) stepView.findViewById(R.id.zoneStroke);

            tvNumber.setText(String.valueOf(number));
            tvNameStep.setText(String.valueOf(nameStep));

            if(!isSelect){
                zoneStroke.setBackgroundResource(R.drawable.shape_rectangle_corner_green);
                tvNameStep.setVisibility(View.INVISIBLE);
                tvNumber.setTextColor(Color.WHITE);
            }

            return stepView;
        }
    }


}
