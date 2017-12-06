package codeartist.com.groseryshop.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import codeartist.com.groseryshop.R;
import codeartist.com.groseryshop.adapter.ShoppingListAdapter;
import codeartist.com.groseryshop.database.Database;
import codeartist.com.groseryshop.datamodel.CouponDataModel;
import codeartist.com.groseryshop.datamodel.ProductDataModel;
import codeartist.com.groseryshop.utils.Utils;

/**
 * Created by bjit-16 on 12/6/17.
 */

public class ShoppingListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ShoppingListAdapter mAdapter;
    private Button finalPrice;
    private TextView totalTextView, discountTextView;
    private EditText budget;
    private Button generateListButton;
    private List<ProductDataModel> list;
    private ArrayList<CouponDataModel> couponList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shopping_list, container, false);
        Database.init(getActivity());
        mRecyclerView = rootView.findViewById(R.id.productRecyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        totalTextView = rootView.findViewById(R.id.total_price);
        discountTextView = rootView.findViewById(R.id.discount_price);
        finalPrice = rootView.findViewById(R.id.final_price);
        budget = rootView.findViewById(R.id.budget_amount);
        generateListButton= rootView.findViewById(R.id.shopping_list);
        couponList = new ArrayList<>();
        mAdapter = new ShoppingListAdapter(getActivity(), couponList);
        mRecyclerView.setAdapter(mAdapter);
        generateListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                couponList.clear();
                ArrayList<CouponDataModel> fullCouponList = Database.getAllCouponWithFullInfo();
                float budgetAmount = Float.parseFloat(budget.getText().toString());
                fullCouponList = getCouponWithinBudget(fullCouponList, budgetAmount);
                fullCouponList = Utils.getCouponWithoutConflicts(fullCouponList);
                couponList.addAll(fullCouponList);
                mAdapter.notifyDataSetChanged();
                float totalPrice = 0f, discount = 0f;
                for(CouponDataModel data : fullCouponList){
                    for(ProductDataModel product : data.getProductList()){
                        totalPrice = totalPrice + product.getPrice();
                    }
                    discount = discount + data.getDiscount();
                }
                totalTextView.setText("Total Price: " + (totalPrice - discount));
                discountTextView.setText("Discount: " + discount);
            }
        });
        return rootView;
    }

    private ArrayList<CouponDataModel> getCouponWithinBudget(ArrayList<CouponDataModel> list, float budget){
        ArrayList<CouponDataModel> removeableList = new ArrayList<>();
        for(CouponDataModel model:list){
            float totalPrice = 0f;
            for(ProductDataModel product : model.getProductList()){
                totalPrice = totalPrice + product.getPrice();
            }

            totalPrice = totalPrice - model.getDiscount();
            if(totalPrice > budget){
                removeableList.add(model);
            }

        }

        list.removeAll(removeableList);
        return list;
    }
}
