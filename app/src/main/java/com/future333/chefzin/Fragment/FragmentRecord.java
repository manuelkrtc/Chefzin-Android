package com.future333.chefzin.Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.future333.chefzin.AppHandler;
import com.future333.chefzin.MainActivity;
import com.future333.chefzin.R;
import com.future333.chefzin.id.IdState;
import com.future333.chefzin.model.Chef;
import com.future333.chefzin.model.Ingredient;
import com.future333.chefzin.model.Order;
import com.future333.chefzin.model.Product;
import com.future333.chefzin.tools.ToolsApi;
import com.future333.chefzin.view.BtnStep;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by manuel on 12/09/16.
 */
public class FragmentRecord extends Fragment {

    public static final String  NAME = "FragmentRecord";

    Activity    ctx;
    AppHandler app;

    SliderLayout slider;

    public static FragmentRecord newInstance() {
        return new FragmentRecord();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ctx = getActivity();
        app = ((AppHandler)getActivity().getApplication());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_record, container, false);

         slider = (SliderLayout)v.findViewById(R.id.slider);

//        ArrayList<Chef> chefs = new ArrayList<>();
//        chefs.add(new Chef("manuel","Vegetariano","Comida saludable, acopañada con moluscos ", null));
//        chefs.add(new Chef("Paez","Oriental","En la cocina oriental podemos encontrar platos exóticos y fáciles de preparar para sorprender a nuestra familia o deleitar a nuestros invitados.", null));
//        chefs.add(new Chef("Jose Vernicio","Carnivoro","super mega  dss play", null));
//        chefs.add(new Chef("Davis loquen","Marina","Sudado de tramboyo, sabroso plato en donde el pescado se cocina en su propio jugo\n" , null));

//        initSlider(chefs);

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initSlider(app.ctrUser.getUser().getOrderRecord());

    }

    private void initSlider(ArrayList<Order> orders) {
        slider.stopAutoCycle();
        for(Order order: orders){
            slider.addSlider(new CustomSliderView(order));
        }
    }


    //----------------------------------------------------------------------------------------------
    //---------------------------------------- SliderView ------------------------------------------
    //----------------------------------------------------------------------------------------------
    public class CustomSliderView extends BaseSliderView{

        BtnStep btnStep1;
        BtnStep btnStep2;
        BtnStep btnStep3;
        BtnStep btnStep4;

        private Order order;

        public CustomSliderView(Order order) {
            super(getActivity());
            this.order = order;
        }

        @Override
        public View getView() {

            View v = LayoutInflater.from(getContext()).inflate(R.layout.row_record, null);

            btnStep1    = (BtnStep)v.findViewById(R.id.btnStep1);
            btnStep2    = (BtnStep)v.findViewById(R.id.btnStep2);
            btnStep3    = (BtnStep)v.findViewById(R.id.btnStep3);
            btnStep4    = (BtnStep)v.findViewById(R.id.btnStep4);



            RecyclerView rvProduct       = (RecyclerView)v.findViewById(R.id.rvProduct);
            TextView        tvOrderNumber   = (TextView)v.findViewById(R.id.tvOrderNumber);

            //recycler
            rvProduct.setHasFixedSize(true);
            AdaptadorProducts adapaterRecicler = new AdaptadorProducts(ctx, order.getPlatos());
            rvProduct.setAdapter(adapaterRecicler);
            rvProduct.setLayoutManager(new LinearLayoutManager(ctx,LinearLayoutManager.VERTICAL,false));

            tvOrderNumber.setText(order.getId_orden());

            selectStep(order.getId_estado());

            return v;
        }

        private void selectStep(int idSelect){
            btnStep1.select(idSelect == IdState.ID_REC_Y_COC?       true:false);
            btnStep2.select(idSelect == IdState.ID_LISTO_ENTREGAR?  true:false);
            btnStep3.select(idSelect == IdState.ID_EN_CAMINO?       true:false);
            btnStep4.select(idSelect == IdState.ID_ENTREGADO?       true:false);

        }

    }

    //----------------------------------------------------------------------------------------------
    //----------------------------------------- RecycleView ----------------------------------------
    //----------------------------------------------------------------------------------------------
    public static class AdaptadorProducts extends RecyclerView.Adapter<AdaptadorProducts.ProductsViewHolder> {

        private Activity ctxAdap;
        private ArrayList<Product> products;
        private AdaptadorProducts thisAdatpter;

        public AdaptadorProducts(Activity ctx, ArrayList<Product> products) {
            this.ctxAdap        = ctx;
            this.products       = products;
            this.thisAdatpter   = this;
        }

        @Override
        public ProductsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_product_checkout, parent, false);
            ProductsViewHolder productsViewHolder = new ProductsViewHolder(itemView);
            return productsViewHolder;
        }

        @Override
        public void onBindViewHolder(ProductsViewHolder holder, int position) {
            final Product product = products.get(position);

            holder.tvName.setText(product.getNombre());
            holder.btnDeleteProduct.setVisibility(View.GONE);

            holder.lyAdditions.removeAllViews();
            for(Ingredient ingredient: product.getIngredientes()){

                TextView textView = new TextView(ctxAdap);
                textView.setText(ingredient.getNombre());

                holder.lyAdditions.addView(textView);
            }

            if(product.getIngredientes().size()==0) holder.zoneAdditions.setVisibility(View.GONE);

        }

        @Override
        public int getItemCount() {
            return products.size();
        }

        public static class ProductsViewHolder extends RecyclerView.ViewHolder {

            private TextView    tvName;
            private ViewGroup   lyAdditions;
            private ViewGroup   zoneAdditions;
            private ImageButton btnDeleteProduct;

            public ProductsViewHolder(View itemView) {
                super(itemView);

                tvName              = (TextView)    itemView.findViewById(R.id.tvName);
                lyAdditions         = (ViewGroup)   itemView.findViewById(R.id.lyAdditions);
                zoneAdditions       = (ViewGroup)   itemView.findViewById(R.id.zoneAdditions);
                btnDeleteProduct    = (ImageButton) itemView.findViewById(R.id.btnDeleteProduct);
            }
        }
    }


}
