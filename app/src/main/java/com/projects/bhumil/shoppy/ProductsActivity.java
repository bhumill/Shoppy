package com.projects.bhumil.shoppy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.projects.bhumil.shoppy.Adapters.ProductsAdapter;
import com.projects.bhumil.shoppy.Models.Product;
import com.projects.bhumil.shoppy.UtilityClasses.Utility;

import java.util.List;

public class ProductsActivity extends AppCompatActivity
{

    private RecyclerView recyclerView;
    private ProductsAdapter adapter;
    private List<Product> productsList;
    private android.support.v7.widget.Toolbar tbToolBarProduct;
    protected SharedPreferences mSharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        //initCollapsingToolbar();
        mSharedPreferences = Utility.getPreference(this);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        tbToolBarProduct = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_products);

        setSupportActionBar(tbToolBarProduct);
        productsList = Utility.GetProductList();
        adapter = new ProductsAdapter(this, productsList, new ProductsAdapter.ProductClickListener() {
            @Override
            public void SelectProduct(Product product)
            {
                Intent singleProduct = new Intent(ProductsActivity.this, SingleProduct.class);
                singleProduct.putExtra("productId", product.getProductId());
                startActivity(singleProduct);
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        tbToolBarProduct.setNavigationIcon(getResources().getDrawable(R.drawable.action_back));
        tbToolBarProduct.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               finish();
            }
        });

    }
    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp)
    {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
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
        startActivity(new Intent(ProductsActivity.this, LoginActivity.class));
        finish();
        return super.onOptionsItemSelected(item);
    }
}

