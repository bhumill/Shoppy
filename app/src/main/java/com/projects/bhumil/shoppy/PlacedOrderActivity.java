package com.projects.bhumil.shoppy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.projects.bhumil.shoppy.Adapters.PlacedOrdersAdapter;
import com.projects.bhumil.shoppy.UtilityClasses.Utility;

public class PlacedOrderActivity extends AppCompatActivity
{
    private android.support.v7.widget.Toolbar tbToolBarPlacedOrderList;
    RecyclerView rvPlacedOrderList;
    TextView tvNoOrders;
    Button btnExploreMore;
    private  long mBackPressedTime;
    private  int counter = 0;
    private  Intent mIntent;
    private PlacedOrdersAdapter mMyOrderListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placed_order);

        tbToolBarPlacedOrderList = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_placed_orders);
        rvPlacedOrderList = (RecyclerView) findViewById(R.id.placed_order_recycler_view);
        tvNoOrders = (TextView) findViewById(R.id.tv_no_placed_orders);
        btnExploreMore = (Button) findViewById(R.id.btn_explore_more);
        setSupportActionBar(tbToolBarPlacedOrderList);
        Utility.CopyBagToOrders();
        if(Utility.GetPlacedOrders().size()>0)
        {
            //Show Recycler view
            Toast.makeText(PlacedOrderActivity.this, ""+Utility.GetPlacedOrders().size()+" Orders placed !",Toast.LENGTH_LONG).show();
            mMyOrderListAdapter = new PlacedOrdersAdapter(PlacedOrderActivity.this, Utility.GetPlacedOrders());
            rvPlacedOrderList.setAdapter(mMyOrderListAdapter);
            rvPlacedOrderList.setHasFixedSize(true);
            rvPlacedOrderList.setLayoutManager(new LinearLayoutManager(PlacedOrderActivity.this));
        }
        else
        {
            rvPlacedOrderList.setVisibility(View.GONE);
            tvNoOrders.setVisibility(View.VISIBLE);
        }

        btnExploreMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent homeActivity = new Intent(PlacedOrderActivity.this, MainActivity.class);
                homeActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeActivity);
            }
        });
    }
    @Override
    public void onBackPressed()
    {
        Intent mintent = new Intent(PlacedOrderActivity.this, MainActivity.class);
        mintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mintent);
        //finish();
//        mBackPressedTime = System.currentTimeMillis();
//        if(counter == 0)
//        {
//            Toast.makeText(PlacedOrderActivity.this, getString(R.string.st_back_pressed), Toast.LENGTH_SHORT).show();
//            counter = 1;
//        }
//        else
//        {
//            if (mBackPressedTime + 5000 > System.currentTimeMillis())
//            {
//                mIntent = new Intent(Intent.ACTION_MAIN);
//                mIntent.addCategory(Intent.CATEGORY_HOME);
//                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(mIntent);
//                finish();
//                super.onBackPressed();
//
//            } else
//            {
//                Toast.makeText(PlacedOrderActivity.this, getString(R.string.st_back_pressed), Toast.LENGTH_SHORT).show();
//            }
//        }
    }
}
