package com.projects.bhumil.shoppy.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.projects.bhumil.shoppy.Models.Product;
import com.projects.bhumil.shoppy.R;

import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.BagOrdersViewHolder>
{
    private Context context;
    private List<Product> orderList;
    public OrderRemoveClickListener mOnClickListener;

    public OrderListAdapter(Context context, List<Product> orderList, OrderRemoveClickListener mOnClickListener)
    {
        this.context = context;
        this.orderList = orderList;
        this.mOnClickListener = mOnClickListener;
    }

    @NonNull
    @Override
    public BagOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_list_card, parent, false);
        return new BagOrdersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BagOrdersViewHolder holder, int position)
    {
        final Product orderedProduct = orderList.get(position);
        holder.tvProductName.setText(orderedProduct.getProductName());
        holder.tvProductQty.setText(""+orderedProduct.getProdcutQty());
        holder.tvProductPrice.setText("$"+orderedProduct.getProductTotal());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mOnClickListener.DeleteProduct(orderedProduct);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class BagOrdersViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvProductName, tvProductPrice, tvProductQty;
        Button btnDelete;

        public BagOrdersViewHolder(@NonNull View view)
        {
            super(view);

            tvProductName = (TextView) itemView.findViewById(R.id.item_product_name);
            tvProductPrice = (TextView) itemView.findViewById(R.id.item_product_price);
            tvProductQty = (TextView) itemView.findViewById(R.id.item_product_qty);
            btnDelete = (Button) itemView.findViewById(R.id.btnOrderDelete);
        }
    }

    public interface OrderRemoveClickListener
    {
        void DeleteProduct(Product product);
    }

}
