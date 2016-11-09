
package com.future333.chefzin.Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.future333.chefzin.AppHandler;
import com.future333.chefzin.MainActivity;
import com.future333.chefzin.R;
import com.future333.chefzin.model.Addition;
import com.future333.chefzin.model.Product;

import java.util.ArrayList;

/**
 * Created by manuel on 12/09/16.
 */
public class FragmentProduct extends Fragment {

    Activity ctx;
    AppHandler app;

    Button      btnAdd;
    Button      btnCheckout;
    TextView    tvQuantity;
    SliderLayout slider;

    ArrayList<Product> products;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx         = getActivity();
        products    = new ArrayList<>();
        app         = ((AppHandler)getActivity().getApplication());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_product, container, false);

        btnAdd      = (Button) v.findViewById(R.id.btnAdd);
        btnCheckout = (Button) v.findViewById(R.id.btnCheckout);
        tvQuantity  = (TextView)v.findViewById(R.id.tvQuantity);
        slider      = (SliderLayout)v.findViewById(R.id.slider);

//        ArrayList<Addition> additions = new ArrayList<>();
//        additions.add(new Addition("sal",1000));
//        additions.add(new Addition("azucar",100));
//        additions.add(new Addition("salsa",1500));

//        products.add(new Product("Papas", 500, R.drawable.img_dinner, "So la papas mas ricas del mercado con slasa rosa y aji",additions));
//        products.add(new Product("Limonada", 9500, R.drawable.img_lunch, "So la papas mas ricas del mercado con slasa rosa y aji  ad4mas de una suculemta limosna con carast esperciales de diferente tipṕ",additions));
//        products.add(new Product("Torta Banano", 98000, R.drawable.img_breakfast, "So la papas mas ricas del mercado con slasa rosa y aji  ad4mas de una suculemta limosna con carast esperciales de diferente tipṕ y abana del alpes suizos comelona natuarl e indigesta segura. topor por tan como lo es",additions));
//        products.add(new Product("Mango biche", 808000, R.drawable.img_breakfast, "So la papas mas ricas del mercado con slasa rosa y aji  ad4mas de una suculemta limosna con carast esperciales de diferente tipṕ y abana del alpes suizos comelona natuarl e indigesta segura. topor por tan como lo es, So la papas mas ricas del mercado con slasa rosa y aji  ad4mas de una suculemta limosna con carast esperciales de diferente tipṕ y abana del alpes suizos comelona natuarl e indigesta segura. topor por tan como lo es",additions));

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        liste();
        initSlider(products);
    }

    //----------------------------------------------------------------------------------------------
    private void liste(){
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.shopCart.addProduct(products.get(slider.getCurrentPosition()));
                updateView();
            }
        });

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).goFragmentCheckout();
            }
        });
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
            View v = LayoutInflater.from(getContext()).inflate(R.layout.row_product, null);

            ImageView   ivProduct               = (ImageView)v.findViewById(R.id.ivProduct);
            TextView    tvNameProduct           = (TextView) v.findViewById(R.id.tvNameProduct);
            TextView    tvPriceProduct          = (TextView) v.findViewById(R.id.tvPriceProduct);
            TextView    tvDescriptionProduct    = (TextView) v.findViewById(R.id.tvDescriptionProduct);

//            if(product.image != 0)      ivProduct.setImageResource(product.image);
//
//            if(product.name != null)    tvNameProduct.setText(product.name);
//            else                        tvNameProduct.setVisibility(View.GONE);
//
//            if(product.precio != 0)      tvPriceProduct.setText("$" + String.valueOf(product.precio));
//            else                        tvPriceProduct.setVisibility(View.GONE);
//
//            if(product.description != null)    tvDescriptionProduct.setText(product.description);
//            else                                tvDescriptionProduct.setVisibility(View.GONE);

            return v;
        }
    }

    public void updateView(){
        tvQuantity.setText(app.shopCart.quantityProducts());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        app.shopCart.clear();
    }
}
