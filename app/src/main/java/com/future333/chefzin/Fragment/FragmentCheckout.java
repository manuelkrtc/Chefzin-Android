package com.future333.chefzin.Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.future333.chefzin.AppHandler;
import com.future333.chefzin.R;
import com.future333.chefzin.model.Controller.CtrCart;
import com.future333.chefzin.model.Ingredient;
import com.future333.chefzin.model.Product;
import com.future333.chefzin.tools.ToolsApi;
import com.future333.chefzin.tools.ToolsFormat;
import com.future333.chefzin.tools.ToolsNotif;
import com.future333.chefzin.tools.ToolsView;
import com.future333.chefzin.view.CheckProductPrice;

import java.util.ArrayList;

/**
 * Created by manuel on 12/09/16.
 */
public class FragmentCheckout extends Fragment {

    public static final String  NAME = "FragmentCheckout";


    Activity    ctx;
    AppHandler  app;
    ToolsNotif  toolsNotif;

    RecyclerView rvProduct;
    ViewPager    viewPager;

    CheckoutPageAdapter     checkoutAdapter;
    RecyclerView.Adapter    adapaterRecicler;

    Button viewController1;
    Button viewController2;
    Button viewController3;

    public static FragmentCheckout newInstance() {
        return new FragmentCheckout();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = getActivity();
        app = ((AppHandler)getActivity().getApplication());

        toolsNotif = new ToolsNotif(ctx);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_checkout, container, false);

        rvProduct   = (RecyclerView)v.findViewById(R.id.rvProduct);
        viewPager   = (ViewPager)v.findViewById(R.id.viewPager);

        viewController1 = (Button) v.findViewById(R.id.viewController1);
        viewController2 = (Button) v.findViewById(R.id.viewController2);
        viewController3 = (Button) v.findViewById(R.id.viewController3);

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //ViewPager
        checkoutAdapter = new CheckoutPageAdapter(ctx, app.ctrCart);
        viewPager.setAdapter(checkoutAdapter);

        //RecyclerView
        rvProduct.setHasFixedSize(true);
        adapaterRecicler = new AdaptadorProducts(ctx, app, checkoutAdapter);
        rvProduct.setAdapter(adapaterRecicler);
        rvProduct.setLayoutManager(new LinearLayoutManager(ctx,LinearLayoutManager.VERTICAL,false));

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateViewController(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


//        checkoutAdapter.notifyDataSetChanged();
    }

    private void updateViewController(int position){

        viewController1.setBackgroundResource(R.drawable.shape_circle_stroke_blue);
        viewController2.setBackgroundResource(R.drawable.shape_circle_stroke_blue);
        viewController3.setBackgroundResource(R.drawable.shape_circle_stroke_blue);

        switch (position){
            case 0: viewController1.setBackgroundResource(R.drawable.shape_circle_blue);
                break;
            case 1: viewController2.setBackgroundResource(R.drawable.shape_circle_blue);
                break;
            case 2: viewController3.setBackgroundResource(R.drawable.shape_circle_blue);
                break;
        }
    }

    //----------------------------------------------------------------------------------------------
    //----------------------------------------- Adapter --------------------------------------------
    //----------------------------------------------------------------------------------------------
    public static class AdaptadorProducts extends RecyclerView.Adapter<AdaptadorProducts.ProductsViewHolder> {

        private Activity    ctxAdap;
        private AppHandler  app;
        private ArrayList<Product> products;
        private AdaptadorProducts thisAdatpter;
        private CheckoutPageAdapter checkoutAdapter;

        public AdaptadorProducts(Activity ctx, AppHandler app, CheckoutPageAdapter checkoutAdapter) {
            this.ctxAdap    = ctx;
            this.app        = app;
            this.products   = app.ctrCart.getProducts();
            this.thisAdatpter = this;
            this.checkoutAdapter = checkoutAdapter;
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
            holder.tvPrice.setText(ToolsFormat.int_to_price(product.getPrecio()));

            holder.lyAdditions.removeAllViews();
            for(Ingredient ingredient: product.getIngredientes()){
                CheckProductPrice viewIngredient = new CheckProductPrice(ctxAdap, ingredient);
                viewIngredient.setName(" - " + ingredient.getNombre());
                viewIngredient.getCheck().setVisibility(View.GONE);
                viewIngredient.setPadding(0,0, ToolsView.dpToPx(ctxAdap,20),0);

                holder.lyAdditions.addView(viewIngredient);
            }

            if(product.getIngredientes().size()==0) holder.zoneAdditions.setVisibility(View.GONE);

            holder.btnDeleteProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((AppHandler)ctxAdap.getApplication()).ctrCart.deleteProduct(ctxAdap, app, product, new ToolsApi.OnApiListenerError() {
                        @Override
                        public void onSuccessful() {
                            thisAdatpter.notifyDataSetChanged();
                            checkoutAdapter.updatePriceView();
                        }

                        @Override
                        public void onError(String error) {
                            ToolsView.msj(ctxAdap, error);
                        }
                    });
                }
            });
        }

        @Override
        public int getItemCount() {
            return products.size();
        }

        public static class ProductsViewHolder extends RecyclerView.ViewHolder {

            private TextView tvName;
            private TextView tvPrice;

            private ViewGroup   lyAdditions;
            private ViewGroup   zoneAdditions;
            private ImageButton btnDeleteProduct;

            public ProductsViewHolder(View itemView) {
                super(itemView);

                tvName              = (TextView)    itemView.findViewById(R.id.tvName);
                tvPrice             = (TextView)    itemView.findViewById(R.id.tvPrice);
                lyAdditions         = (ViewGroup)   itemView.findViewById(R.id.lyAdditions);
                zoneAdditions       = (ViewGroup)   itemView.findViewById(R.id.zoneAdditions);
                btnDeleteProduct    = (ImageButton) itemView.findViewById(R.id.btnDeleteProduct);
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    //----------------------------------------- Adapter --------------------------------------------
    //----------------------------------------------------------------------------------------------
    public static class CheckoutPageAdapter extends PagerAdapter{

        Activity ctx;

        View dataView   = null;
        View priceView  = null;
        View paymentView= null;

        CtrCart _shopCart;

        public CheckoutPageAdapter(Activity ctx, CtrCart shopCart){
            this.ctx = ctx;
            this._shopCart = shopCart;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            if(position==0 && priceView==null){
                priceView = LayoutInflater.from(ctx).inflate(R.layout.row_checkout_price, container,false);
                updatePriceView();
                container.addView(priceView);
                return priceView;
            }

            if(position==1 && dataView==null){
                dataView = LayoutInflater.from(ctx).inflate(R.layout.row_checkout_data, container,false);
                container.addView(dataView);
                return dataView;
            }

            if(position==2 && paymentView==null){
                paymentView = LayoutInflater.from(ctx).inflate(R.layout.row_checkout_payment, container,false);
                container.addView(paymentView);
                updatePaymentView();
                return paymentView;
            }

            return null;
        }

        // Removes the page from the container for the given position.
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            container.removeView((View) object);
        }

        public void updatePriceView(){
            TextView tvIva      = (TextView)priceView.findViewById(R.id.tvIva);
            TextView tvTotal    = (TextView)priceView.findViewById(R.id.tvTotal);
            TextView tvDomicile = (TextView)priceView.findViewById(R.id.tvDomicile);
            TextView tvSubtotal = (TextView)priceView.findViewById(R.id.tvSubtotal);

            tvIva       .setText(_shopCart.getIva());
            tvTotal     .setText(_shopCart.getTotal());
            tvDomicile  .setText(_shopCart.getDomicile());
            tvSubtotal  .setText(_shopCart.getSubTotal());
        }

        public void updatePaymentView(){
            Spinner spinner      = (Spinner) paymentView.findViewById(R.id.spinner);

//            ArrayAdapter<CharSequence> adapter =
//                            ArrayAdapter.createFromResource(ctx,
//                            R.array.payment_method,
//                            android.R.layout.simple_spinner_item);

            ArrayAdapter<CharSequence> adapter =
                    ArrayAdapter.createFromResource(ctx,
                            R.array.payment_method,
                            android.R.layout.simple_list_item_1);

            spinner.setAdapter(adapter);
        }

    }


}
