package com.projects.bhumil.shoppy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.projects.bhumil.shoppy.Adapters.OrderListAdapter;
import com.projects.bhumil.shoppy.Models.Product;
import com.projects.bhumil.shoppy.UtilityClasses.Utility;

public class OrderListActivity extends AppCompatActivity
{
    private android.support.v7.widget.Toolbar tbToolBarOrderList;
    RecyclerView rvOrderList;
    TextView tvNoData;
    Button btnCheckout;
    private OrderListAdapter mOrderListAdapter;
    protected SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        mSharedPreferences = Utility.getPreference(this);
        tbToolBarOrderList = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_order_list);
        rvOrderList = (RecyclerView) findViewById(R.id.order_recycler_view);
        tvNoData = (TextView) findViewById(R.id.tv_no_orders);
        btnCheckout = (Button) findViewById(R.id.btn_checkout);
        setSupportActionBar(tbToolBarOrderList);
        tbToolBarOrderList.setNavigationIcon(getResources().getDrawable(R.drawable.action_back));
        tbToolBarOrderList.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        if(Utility.GetBagItems().size()>0)
        {
            //Show Recycler view
            mOrderListAdapter = new OrderListAdapter(OrderListActivity.this, Utility.GetBagItems(), new OrderListAdapter.OrderRemoveClickListener() {
                @Override
                public void DeleteProduct(Product product)
                {
                    Utility.RemoveFromBag(product);
                    Toast.makeText(OrderListActivity.this, "Product removed from bag!",Toast.LENGTH_SHORT).show();
                    if(Utility.GetBagItems().size()<=0)
                    {
                        rvOrderList.setVisibility(View.GONE);
                        tvNoData.setVisibility(View.VISIBLE);
                        btnCheckout.setText(R.string.go_to_products);
                    }
                    mOrderListAdapter.notifyDataSetChanged();
                }
            });
            rvOrderList.setAdapter(mOrderListAdapter);
            rvOrderList.setHasFixedSize(true);
            rvOrderList.setLayoutManager(new LinearLayoutManager(OrderListActivity.this));
        }
        else
        {
            rvOrderList.setVisibility(View.GONE);
            tvNoData.setVisibility(View.VISIBLE);
            btnCheckout.setText(R.string.go_to_products);
        }

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(btnCheckout.getText().equals("GO TO PRODUCTS"))
                {
                    Intent checkoutActivity = new Intent(OrderListActivity.this, ProductsActivity.class);
                    startActivity(checkoutActivity);
                }
                else
                {
                    Intent checkoutActivity = new Intent(OrderListActivity.this, CheckoutAcitivity.class);
                    startActivity(checkoutActivity);
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu1)
    {
        getMenuInflater().inflate(R.menu.action_logout, menu1);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        mSharedPreferences.edit().clear().commit();
        Utility.ClearAll();
        startActivity(new Intent(OrderListActivity.this, LoginActivity.class));
        finish();
        return super.onOptionsItemSelected(item);
    }
}
