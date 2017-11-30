
package codeartist.com.groseryshop.fragments;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import codeartist.com.groseryshop.R;
import codeartist.com.groseryshop.adapter.ProductAdapter;
import codeartist.com.groseryshop.database.Database;
import codeartist.com.groseryshop.datamodel.CouponDataModel;
import codeartist.com.groseryshop.datamodel.ProductDataModel;

/**
 * Created by bjit-16 on 11/28/17.
 */

public class EnterCouponFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ProductAdapter mAdapter;
    private Button addItem;
    private TextView couponTextView;
    private EditText discountEditText;
    private List<ProductDataModel> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_all_product, container, false);
        Database.init(getActivity());
        mRecyclerView = rootView.findViewById(R.id.productRecyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        couponTextView = rootView.findViewById(R.id.coupon_number);
        addItem = rootView.findViewById(R.id.add);
        discountEditText = rootView.findViewById(R.id.discount);
        list = Database.getAllProduct();

        couponTextView.setText("Coupon Number: " + getCouponNumber());
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (discountEditText.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please insert discount amount",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                List<String> discountProduct = new ArrayList<>();

                for (ProductDataModel item : list) {
                    if (item.getSelected()) {
                        discountProduct.add(item.getProductName());
                    }
                }

                if (discountProduct.size() == 0) {
                    Toast.makeText(getActivity(), "Please choose product for discount",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                CouponDataModel couponDiscountDataModel = new CouponDataModel();
                String segments[] = couponTextView.getText().toString().split(":");
                String couponNumber = segments[1].trim();
                couponDiscountDataModel.setCouponNumber(Integer.parseInt(couponNumber));
                couponDiscountDataModel
                        .setDiscount(Float.parseFloat(discountEditText.getText().toString()));
                Database.insertCouponRateData(couponDiscountDataModel);

                for (String product : discountProduct) {
                    CouponDataModel item = new CouponDataModel();
                    item.setCouponNmber(Integer.parseInt(couponNumber));
                    item.setItem(product);
                    long k = Database.insertCouponItemData(item);
                    Log.e("insert item", k + "");
                }
                couponTextView.setText("Coupon Number: " + getCouponNumber());
                discountEditText.setText("");
                resetAllCheckedItem();
                disableUsedItem();
            }
        });

        mAdapter = new ProductAdapter(getActivity(), list);
        mRecyclerView.setAdapter(mAdapter);
        disableUsedItem();
        return rootView;
    }

    private int getCouponNumber() {
        Random rnd = new Random();
        int couponNumber = 100000 + rnd.nextInt(900000);
        boolean isValid = Database.isValidCouponNumber(couponNumber);
        if (isValid) {
            return couponNumber;
        } else {
            getCouponNumber();
        }
        return 0;
    }

    private void resetAllCheckedItem() {
        for (ProductDataModel model : list) {
            model.setSelected(false);
            mAdapter.notifyDataSetChanged();
        }
    }

    private void disableUsedItem() {
        List<String> usedProduct = Database.getAllCouponItem();
        for (ProductDataModel item : list) {
            if (usedProduct.contains(item.getProductName().trim())) {
                item.setCheckable(false);
            }
        }
        mAdapter.notifyDataSetChanged();
    }
}
