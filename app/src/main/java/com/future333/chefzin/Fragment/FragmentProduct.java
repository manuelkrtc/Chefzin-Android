
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
import com.future333.chefzin.model.Chef;
import com.future333.chefzin.model.Product;
import com.future333.chefzin.tools.ApiTools;
import com.future333.chefzin.tools.FormatTools;
import com.future333.chefzin.tools.ImageLoader;

import java.util.ArrayList;

/**
 * Created by manuel on 12/09/16.
 */
public class FragmentProduct extends Fragment {

    Activity ctx;
    AppHandler app;
    ImageLoader _imageL;


    Button      btnAdd;
    Button      btnCheckout;
    TextView    tvQuantity;
    SliderLayout slider;

    ArrayList<Product> products;
//    Chef chef;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx         = getActivity();
        app         = ((AppHandler)getActivity().getApplication());

        _imageL     = new ImageLoader(ctx);
        products    = app.chefSelect.getPlatos();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_product, container, false);

        btnAdd      = (Button) v.findViewById(R.id.btnAdd);
        btnCheckout = (Button) v.findViewById(R.id.btnCheckout);
        tvQuantity  = (TextView)v.findViewById(R.id.tvQuantity);
        slider      = (SliderLayout)v.findViewById(R.id.slider);

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

            _imageL.loadAndDisplayImage(ApiTools.URL_IMG_PLATO + product.getImagen(), ivProduct);

            if(product.getNombre() != null)     tvNameProduct.setText(product.getNombre());
            else                                tvNameProduct.setVisibility(View.GONE);

            if(product.getPrecio() != 0)        tvPriceProduct.setText(FormatTools.int_to_price(product.getPrecio()));
            else                                tvPriceProduct.setVisibility(View.GONE);

            if(product.getInfo_adicional() != null)     tvDescriptionProduct.setText(product.getInfo_adicional());
            else                                        tvDescriptionProduct.setVisibility(View.GONE);

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
