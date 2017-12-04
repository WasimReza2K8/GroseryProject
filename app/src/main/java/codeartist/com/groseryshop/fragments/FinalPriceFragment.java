package codeartist.com.groseryshop.fragments;

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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;

import codeartist.com.groseryshop.R;
import codeartist.com.groseryshop.adapter.FinalPriceAdapter;
import codeartist.com.groseryshop.adapter.ProductAdapter;
import codeartist.com.groseryshop.database.Database;
import codeartist.com.groseryshop.datamodel.CouponDataModel;
import codeartist.com.groseryshop.datamodel.FinalPriceModel;
import codeartist.com.groseryshop.datamodel.ProductDataModel;

/**
 * Created by ASUS on 02-Dec-17.
 */

public class FinalPriceFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private FinalPriceAdapter mAdapter;
    private Button finalPrice;
    private TextView totalTextView, discountTextView;
    private EditText discountEditText;
    private List<ProductDataModel> list;
    private ArrayList<CouponDataModel> couponList;
    private ArrayList<ProductDataModel> selectedItemList = new ArrayList<>();


    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            int position = (Integer) compoundButton.getTag();
            if(b){
                selectedItemList.add(list.get(position));
            } else{
                selectedItemList.remove(list.get(position));
            }

        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_final_price, container, false);
        Database.init(getActivity());
        mRecyclerView = rootView.findViewById(R.id.productRecyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        totalTextView = rootView.findViewById(R.id.total_price);
        discountTextView = rootView.findViewById(R.id.discount_price);
        finalPrice = rootView.findViewById(R.id.final_price);
        list = Database.getAllProduct();
        mAdapter = new FinalPriceAdapter(getActivity(), list, onCheckedChangeListener);
        mRecyclerView.setAdapter(mAdapter);
        couponList = Database.getAllCouponWithFullInfo();
        //Log.e("CouponList", Database.getAllCouponWithFullInfo().size()+"");
        finalPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //disableUsedItem();
        return rootView;
    }
}
