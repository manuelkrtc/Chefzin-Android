package com.future333.chefzin.Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.future333.chefzin.AppHandler;
import com.future333.chefzin.MainActivity;
import com.future333.chefzin.R;
import com.future333.chefzin.model.Horary;
import com.future333.chefzin.tools.ToolsApi;
import com.future333.chefzin.tools.ToolsDate;
import com.future333.chefzin.tools.ToolsNotif;
import com.future333.chefzin.tools.ToolsView;

import java.util.ArrayList;

/**
 * Created by manuel on 12/09/16.
 */
public class FragmentHorary extends Fragment {

    public static final String  NAME = "FragmentHorary";

    Activity    ctx;
    AppHandler  app;

    ToolsNotif      toolsNotif;

    SliderLayout    slider;
    PagerIndicator  pagerIndicator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ctx = getActivity();
        app = ((AppHandler)getActivity().getApplication());

        toolsNotif = new ToolsNotif(ctx);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_horary, container, false);

        inicializate(view);

        initSlider(app.ctrHorary.getHoraries());

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

            TextView    tvName      = (TextView)    v.findViewById(R.id.tvName);
            TextView    tvHorary    = (TextView)    v.findViewById(R.id.tvHorary);
            ImageView   ivHorary    = (ImageView)   v.findViewById(R.id.ivHorary);
            ViewGroup   lyParent    = (ViewGroup)   v.findViewById(R.id.lyParent);

            if(horary.getNombre() != null)  tvName.setText(horary.getNombre().toUpperCase());
            else                            tvName.setVisibility(View.GONE);

            String startTime = ToolsDate.dateFormat(horary.getHora_ini());
            String finalTime = ToolsDate.dateFormat(horary.getHora_fin());
            if(startTime!=null && finalTime!=null) tvHorary.setText(startTime + " - " + finalTime);
            else tvHorary.setVisibility(View.GONE);

//            if(horary.foto_movil != null)  ;
                if(horary.id.equals("1")) ivHorary.setImageResource(R.drawable.desayunos);
                if(horary.id.equals("2")) ivHorary.setImageResource(R.drawable.almuerzos);
                if(horary.id.equals("3")) ivHorary.setImageResource(R.drawable.cena);

            lyParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toolsNotif.showDialogProgress();
                    app.ctrChef.getApiChefs(ctx, horary.getId(), new ToolsApi.OnApiListenerError() {
                        @Override
                        public void onSuccessful() {
                            app.ctrHorary.setPositionSelect(slider.getCurrentPosition());
                            ((MainActivity)getActivity()).goFragmentChef();
                            toolsNotif.cancelDialogProgress();
                        }
                        @Override
                        public void onError(String error) {
                            ToolsView.msj(ctx,error);
                            toolsNotif.cancelDialogProgress();
                        }
                    });

                }
            });

            return v;
        }
    }




}
