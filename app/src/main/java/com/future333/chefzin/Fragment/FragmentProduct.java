
package com.future333.chefzin.Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.future333.chefzin.AppHandler;
import com.future333.chefzin.MainActivity;
import com.future333.chefzin.R;
import com.future333.chefzin.model.Ingredient;
import com.future333.chefzin.model.Product;
import com.future333.chefzin.tools.ToolsApi;
import com.future333.chefzin.tools.ToolsFormat;
import com.future333.chefzin.tools.ImageLoader;
import com.future333.chefzin.tools.ToolsView;
import com.future333.chefzin.view.CheckProductPrice;

import java.util.ArrayList;

/**
 * Created by manuel on 12/09/16.
 */
public class FragmentProduct extends Fragment {

    Activity        ctx;
    AppHandler      app;
    ImageLoader     _imageL;

    Button          btnAdd;
    Button          btnCheckout;
    TextView        tvQuantity;
    SliderLayout    slider;

    ArrayList<Product> products;
    ArrayList<CustomSliderView> arraySliderView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx         = getActivity();
        app         = ((AppHandler)getActivity().getApplication());

        _imageL         = new ImageLoader(ctx);
        products        = app.chefSelect.getPlatos();
        arraySliderView = new ArrayList<>();
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
        listen();
        initSlider(products);
    }

    //----------------------------------------------------------------------------------------------
    private void listen(){
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(app.ctrUser.getUser() == null){
                    ((MainActivity)ctx).goFragmentLogin();
                    return;
                }

                Product product = (Product) arraySliderView.get(slider.getCurrentPosition()).productSelect.clone();
                app.ctrCart.addProduct(product);
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
            CustomSliderView customSliderView = new CustomSliderView(product);
            slider.addSlider(customSliderView);
            arraySliderView.add(customSliderView);
        }

    }

    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public class CustomSliderView extends BaseSliderView {

        private Product product;
        private Product productSelect;

        public CustomSliderView(Product product) {
            super(getActivity());
            this.product = product;
        }

        @Override
        public View getView() {
            View v = LayoutInflater.from(getContext()).inflate(R.layout.row_product, null);

            Button      btnIngredients          = (Button)   v.findViewById(R.id.btnIngredients);
            ImageView   ivProduct               = (ImageView)v.findViewById(R.id.ivProduct);
            TextView    tvNameProduct           = (TextView) v.findViewById(R.id.tvNameProduct);
            TextView    tvPriceProduct          = (TextView) v.findViewById(R.id.tvPriceProduct);
            TextView    tvDescriptionProduct    = (TextView) v.findViewById(R.id.tvDescriptionProduct);
            ImageButton btnCloseIngredients     = (ImageButton) v.findViewById(R.id.btnCloseIngredients);

            final ViewGroup   zoneIngredients       = (ViewGroup)v.findViewById(R.id.zoneIngredients);
            final ViewGroup   zoneListIngredients   = (ViewGroup)v.findViewById(R.id.zoneListIngredients);
            final ViewGroup   zoneDescription       = (ViewGroup)v.findViewById(R.id.zoneDescription);

            productSelect = (Product) product.clone();
            if(productSelect.getIngredientes() != null) productSelect.getIngredientes().clear();

            zoneIngredients.setVisibility(View.GONE);

            _imageL.loadAndDisplayImage(ToolsApi.URL_IMG_PLATO + product.getImagen(), ivProduct);

            if(product.getNombre() != null)     tvNameProduct.setText(product.getNombre());
            else                                tvNameProduct.setVisibility(View.GONE);

            if(product.getPrecio() != 0)        tvPriceProduct.setText(ToolsFormat.int_to_price(product.getPrecio()));
            else                                tvPriceProduct.setVisibility(View.GONE);

            if(product.getInfo_adicional() != null)     tvDescriptionProduct.setText(product.getInfo_adicional());
            else                                        tvDescriptionProduct.setVisibility(View.GONE);

            if(product.getIngredientes() != null){
                if(product.getIngredientes().size() == 0)btnIngredients.setVisibility(View.GONE);
                for(final Ingredient ingredient : product.getIngredientes()){
                    CheckProductPrice checkIngredient = new CheckProductPrice(ctx, ingredient);
                    zoneListIngredients.addView(checkIngredient);

                    checkIngredient.getCheck().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            if(b == true){
                                productSelect.getIngredientes().add((Ingredient) ingredient.clone());
                            }else {
                                productSelect.deleteIngredient(ingredient);
                            }
                        }
                    });
                }
            }
            else btnIngredients.setVisibility(View.GONE);

            btnIngredients.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToolsView.switchVisibisilite(zoneDescription);
                    ToolsView.switchVisibisilite(zoneIngredients);
                }
            });

            btnCloseIngredients.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToolsView.switchVisibisilite(zoneDescription);
                    ToolsView.switchVisibisilite(zoneIngredients);
                }
            });

            return v;
        }
    }

    public void updateView(){
        tvQuantity.setText(app.ctrCart.quantityProducts());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        app.ctrCart.clear();
    }
}
