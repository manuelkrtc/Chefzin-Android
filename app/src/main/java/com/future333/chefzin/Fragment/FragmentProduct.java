
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
import com.future333.chefzin.R;
import com.future333.chefzin.model.Chef;
import com.future333.chefzin.model.Product;

import java.util.ArrayList;

/**
 * Created by manuel on 12/09/16.
 */
public class FragmentProduct extends Fragment {

    SliderLayout slider;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        inicializate(view);

        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("Papas", 500, R.drawable.img_dinner, "So la papas mas ricas del mercado con slasa rosa y aji"));
        products.add(new Product("Limonada", 9500, R.drawable.img_lunch, "So la papas mas ricas del mercado con slasa rosa y aji  ad4mas de una suculemta limosna con carast esperciales de diferente tipṕ"));
        products.add(new Product("Torta Banano", 98000, R.drawable.img_breakfast, "So la papas mas ricas del mercado con slasa rosa y aji  ad4mas de una suculemta limosna con carast esperciales de diferente tipṕ y abana del alpes suizos comelona natuarl e indigesta segura. topor por tan como lo es"));
        products.add(new Product("Mango biche", 808000, R.drawable.img_breakfast, "So la papas mas ricas del mercado con slasa rosa y aji  ad4mas de una suculemta limosna con carast esperciales de diferente tipṕ y abana del alpes suizos comelona natuarl e indigesta segura. topor por tan como lo es, So la papas mas ricas del mercado con slasa rosa y aji  ad4mas de una suculemta limosna con carast esperciales de diferente tipṕ y abana del alpes suizos comelona natuarl e indigesta segura. topor por tan como lo es"));

        initSlider(products);

        return view;
    }

    private void inicializate(View v){
        slider = (SliderLayout)v.findViewById(R.id.slider);
    }

    private void initSlider(ArrayList<Product> products) {
        slider.stopAutoCycle();
        for(Product product: products){
            slider.addSlider(new CustomSliderView(product));
        }
    }

    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public class CustomSliderView extends BaseSliderView {

        private Product product;

        public CustomSliderView(Product product) {
            super(getActivity());
            this.product = product;
        }

        @Override
        public View getView() {
            View v = LayoutInflater.from(getContext()).inflate(R.layout.row_main, null);

            ImageView   ivProduct               = (ImageView)v.findViewById(R.id.ivProduct);
            TextView    tvNameProduct           = (TextView) v.findViewById(R.id.tvNameProduct);
            TextView    tvPriceProduct          = (TextView) v.findViewById(R.id.tvPriceProduct);
            TextView    tvDescriptionProduct    = (TextView) v.findViewById(R.id.tvDescriptionProduct);

            if(product.image != 0)      ivProduct.setImageResource(product.image);

            if(product.name != null)    tvNameProduct.setText(product.name);
            else                        tvNameProduct.setVisibility(View.GONE);

            if(product.price != 0)      tvPriceProduct.setText("$" + String.valueOf(product.price));
            else                        tvPriceProduct.setVisibility(View.GONE);

            if(product.description != null)    tvDescriptionProduct.setText(product.description);
            else                                tvDescriptionProduct.setVisibility(View.GONE);

            return v;
        }


    }


}
