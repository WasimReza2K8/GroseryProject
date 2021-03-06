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
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import codeartist.com.groseryshop.R;
import codeartist.com.groseryshop.adapter.FinalPriceAdapter;
import codeartist.com.groseryshop.database.Database;
import codeartist.com.groseryshop.datamodel.CouponDataModel;
import codeartist.com.groseryshop.datamodel.ProductDataModel;
import codeartist.com.groseryshop.utils.Utils;

/**
 * Created by ASUS on 02-Dec-17.
 */

public class FinalPriceFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private FinalPriceAdapter mAdapter;
    private Button finalPrice;
    private TextView totalTextView, discountTextView;
    private List<ProductDataModel> list;
    private ArrayList<CouponDataModel> couponList;

    private ArrayList<ProductDataModel> mainSelectedItemList = new ArrayList<>();


    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            int position = (Integer) compoundButton.getTag();
            if(b){
                list.get(position).setSelected(true);
                mainSelectedItemList.add(list.get(position));
            } else{
                list.get(position).setSelected(false);
                mainSelectedItemList.remove(list.get(position));
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
                float totalPrice = 0f, discount = 0f;
                for(ProductDataModel item : mainSelectedItemList){
                    totalPrice = totalPrice + item.getPrice();
                }
               // selectedCoupon.clear();
                ArrayList<ProductDataModel> selectedList = new ArrayList<>(mainSelectedItemList);
                ArrayList<CouponDataModel> allCalculatableCoupon, finalCoupns = new ArrayList<>();
                while(true){
                    allCalculatableCoupon = new ArrayList<>(getElizible(selectedList));
                    ArrayList<ProductDataModel> removeList = new ArrayList<>();
                    allCalculatableCoupon = Utils.getCouponWithoutConflicts(allCalculatableCoupon);
                    if((allCalculatableCoupon.size()  <= 0) || selectedList.size() <= 0){break;}
                    finalCoupns.addAll(allCalculatableCoupon);
                    for(int i = 0; i < selectedList.size(); i++){
                        for(int j = 0; j < allCalculatableCoupon.size(); j++){
                            for(int n = 0; n < allCalculatableCoupon.get(j).getItemList().size(); n++){
                                if(selectedList.get(i).getProductName()
                                        .equals(allCalculatableCoupon.get(j).getItemList().get(n))){
                                    removeList.add(selectedList.get(i));
                                }
                            }
                        }
                    }

                    selectedList.removeAll(removeList);
                }


                for(CouponDataModel model: finalCoupns){
                        discount = discount + model.getDiscount();

                }
                totalTextView.setText("Total Cost: " + (totalPrice - discount));
                discountTextView.setText("Discount: " + discount);

                Log.e("CouponList", allCalculatableCoupon.size()+" "+ "total cost: "+totalPrice + " discount :"+discount );
            }
        });
        //disableUsedItem();
        return rootView;
    }


    private HashSet<CouponDataModel> getElizible(ArrayList<ProductDataModel> selectedList){
        HashSet<CouponDataModel> selectedCoupon = new HashSet<>();
        for(int i = 0; i < couponList.size(); i++){
            int itemNumber = couponList.get(i).getItemList().size();
            int count = 0;
            for(int j = 0; j < selectedList.size(); j++){
                if (couponList.get(i).getItemList().contains(selectedList.get(j).getProductName())) {
                    count++;
                }
                if (count == itemNumber) {
                    selectedCoupon.add(couponList.get(i));
                }
            }
        }
        return selectedCoupon;
    }

}
