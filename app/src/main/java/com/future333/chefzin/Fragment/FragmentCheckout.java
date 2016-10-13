package com.future333.chefzin.Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.future333.chefzin.AppHandler;
import com.future333.chefzin.R;
import com.future333.chefzin.model.Addition;
import com.future333.chefzin.model.Product;
import com.future333.chefzin.tools.ToolsNotif;
import com.future333.chefzin.view.FontTextView;

import java.util.ArrayList;

/**
 * Created by manuel on 12/09/16.
 */
public class FragmentCheckout extends Fragment {

    Activity ctx;
    AppHandler app;
    ToolsNotif toolsNotif;

    RecyclerView rvProduct;


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

        rvProduct = (RecyclerView)v.findViewById(R.id.rvProduct);

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Inicialización RecyclerView
        rvProduct.setHasFixedSize(true);
        AdaptadorProducts adaptador = new AdaptadorProducts(app.shopCart.getProducts());

        rvProduct.setAdapter(adaptador);

        rvProduct.setLayoutManager(new LinearLayoutManager(ctx,LinearLayoutManager.VERTICAL,false));
    }

    public class AdaptadorProducts extends RecyclerView.Adapter<AdaptadorProducts.ProductsViewHolder> {

        private ArrayList<Product> products;

        public AdaptadorProducts(ArrayList<Product> products) {
            this.products = products;
        }

        @Override
        public ProductsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_product_checkout, parent, false);

            ProductsViewHolder productsViewHolder = new ProductsViewHolder(itemView);

            return productsViewHolder;
        }

        @Override
        public void onBindViewHolder(ProductsViewHolder holder, int position) {
            Product product = products.get(position);

            holder.bindProduct(product);
        }

        @Override
        public int getItemCount() {
            return products.size();
        }

        public class ProductsViewHolder
                extends RecyclerView.ViewHolder {

            private TextView tvName;
            private TextView tvPrice;

            private ViewGroup lyAdditions;
            private ViewGroup zoneAdditions;

            public ProductsViewHolder(View itemView) {
                super(itemView);
                tvName          = (TextView)itemView.findViewById(R.id.tvName);
                tvPrice         = (TextView)itemView.findViewById(R.id.tvPrice);
                lyAdditions     = (ViewGroup) itemView.findViewById(R.id.lyAdditions);
                zoneAdditions   = (ViewGroup) itemView.findViewById(R.id.zoneAdditions);
            }

            public void bindProduct(Product product) {
                tvName.setText(product.name);
                tvPrice.setText("$"+String.valueOf(product.price));

                for(Addition addition: product.additions){
                    FontTextView textView = new FontTextView(ctx);
                    textView.setText(" -" + addition.name);
                    lyAdditions.addView(textView);
                }

                if(product.additions.size()==0) zoneAdditions.setVisibility(View.GONE);
            }
        }
    }

}
