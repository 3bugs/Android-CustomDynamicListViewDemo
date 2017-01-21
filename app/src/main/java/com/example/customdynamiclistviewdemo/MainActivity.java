package com.example.customdynamiclistviewdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.customdynamiclistviewdemo.adapter.SpinnerWithHintArrayAdapter;
import com.example.customdynamiclistviewdemo.model.Product;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ListView mListView;
    private MyAdapter mAdapter;
    private View mAddLayout;
    private Button mAddButton;
    private Spinner mProductSpinner;
    private ArrayList<Product> mProductList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Product product = new Product("product1", 10);
        mProductList.add(product);
        product = new Product("product2", 20);
        mProductList.add(product);

        mListView = (ListView) findViewById(R.id.list_view);
        mAdapter = new MyAdapter(this, R.layout.list_item, mProductList);
        mListView.setAdapter(mAdapter);

        Button saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < mAdapter.getCount(); i++) {
                    Product product = (Product) mAdapter.getItem(i);
                    String msg = String.format(
                            Locale.US,
                            "Product Name: %s, Stock Available: %d, Quantity: %d, Pallet Number: %d",
                            product.productName,
                            product.stockAvailable,
                            product.quantity,
                            product.palletNumber
                    );
                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }
        });

        mAddLayout = findViewById(R.id.add_layout);
        mAddButton = (Button) findViewById(R.id.add_button);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddLayout();
            }
        });

        setupAddLayout();
        hideAddLayout();
    }

    private void setupAddLayout() {
        setupSpinner();
        Button saveItemButton = (Button) findViewById(R.id.save_item_button);
        saveItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product product = new Product(
                        mProductSpinner.getSelectedItem().toString(),
                        0
                );

                EditText quantityEditText = (EditText) findViewById(R.id.quantity_edit_text2);
                product.quantity = Integer.valueOf(quantityEditText.getText().toString());
                Log.i(TAG, "quantity: " + product.quantity);

                mProductList.add(product);
                mAdapter.notifyDataSetChanged();

                hideAddLayout();
            }
        });
    }

    private void setupSpinner() {
        mProductSpinner = (Spinner) findViewById(R.id.product_spinner);
        final String[] productList = {"Product1", "Product2", "Product3", "Product4", "เลือก Product"};
        SpinnerWithHintArrayAdapter adapter = new SpinnerWithHintArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                productList
        );
        mProductSpinner.setAdapter(adapter);
        mProductSpinner.setSelection(adapter.getCount());
    }

    private void showAddLayout() {
        mAddLayout.setVisibility(View.VISIBLE);
        mAddButton.setVisibility(View.GONE);
    }

    private void hideAddLayout() {
        mAddLayout.setVisibility(View.GONE);
        mAddButton.setVisibility(View.VISIBLE);
    }

    private static class MyAdapter extends ArrayAdapter {

        private Context context;
        private int resource;
        private ArrayList<Product> productList;

        public MyAdapter(Context context, int resource, ArrayList<Product> productList) {
            super(context, resource, productList);

            this.context = context;
            this.resource = resource;
            this.productList = productList;
        }

        @NonNull
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemLayout = inflater.inflate(resource, null, false);

            TextView productTextView = (TextView) itemLayout.findViewById(R.id.product_text_view);
            TextView stockTextView = (TextView) itemLayout.findViewById(R.id.stock_text_view);
            EditText quantityEditText = (EditText) itemLayout.findViewById(R.id.quantity_edit_text);
            final TextView palletTextView = (TextView) itemLayout.findViewById(R.id.pallet_text_view);

            quantityEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.toString().equals("")) {
                        productList.get(position).quantity = 0;
                    } else {
                        productList.get(position).quantity = Integer.valueOf(s.toString());
                    }

                    palletTextView.setText(s.toString());
                    if (s.toString().equals("")) {
                        productList.get(position).palletNumber = 0;
                    } else {
                        productList.get(position).palletNumber = Integer.valueOf(s.toString());
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            productTextView.setText(productList.get(position).productName);
            stockTextView.setText(String.valueOf(productList.get(position).stockAvailable));
            quantityEditText.setText(String.valueOf(productList.get(position).quantity));
            palletTextView.setText(String.valueOf(productList.get(position).palletNumber));

            return itemLayout;
        }

        @Nullable
        @Override
        public Object getItem(int position) {
            return productList.get(position);
        }

        @Override
        public int getCount() {
            return productList.size();
        }
    }
}
