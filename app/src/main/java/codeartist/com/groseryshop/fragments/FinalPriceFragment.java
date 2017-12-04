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
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;

import codeartist.com.groseryshop.R;
import codeartist.com.groseryshop.adapter.FinalPriceAdapter;
import codeartist.com.groseryshop.adapter.ProductAdapter;
import codeartist.com.groseryshop.database.Database;
import codeartist.com.groseryshop.datamodel.CouponDataModel;
import codeartist.com.groseryshop.datamodel.DiscountResult;
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
    private HashSet<CouponDataModel> selectedCoupon = new HashSet<>();
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
                float totalPrice = 0f, discount = 0f;
                for(ProductDataModel item : selectedItemList){
                    totalPrice = totalPrice + item.getPrice();
                }
                selectedCoupon.clear();
                getElizible();
                ArrayList<CouponDataModel>allCalculatableCoupon = getCouponList();
                for(CouponDataModel model : allCalculatableCoupon){
                    if(!model.isDeletable()){
                        for(String items : model.getItemList()){
                            for(int i = 0; i < selectedItemList.size(); i++){
                                if(items.contains(selectedItemList.get(i).getProductName())){
                                    selectedItemList.remove(i);
                                }
                            }
                        }
                    }
                }

                for(CouponDataModel model: allCalculatableCoupon){
                    if(!model.isDeletable()){
                        discount = discount + model.getDiscount();
                    }

                }
                totalTextView.setText("Total Cost: " + (totalPrice - discount));
                discountTextView.setText("Discount: " + discount);

                Log.e("CouponList", allCalculatableCoupon.size()+"");
            }
        });
        //disableUsedItem();
        return rootView;
    }

    private ArrayList<CouponDataModel> getCouponList(){
        ArrayList<CouponDataModel> allCalculatableCoupon = new ArrayList<>(selectedCoupon);

        for(int i = 0; i < allCalculatableCoupon.size(); i++ ){
            for(int j = 0; j < allCalculatableCoupon.size(); j++){
                if( i == j){
                    continue;
                }

                for(int k = 0; k < allCalculatableCoupon.get(i).getItemList().size(); k++){
                    String item =  allCalculatableCoupon.get(i).getItemList().get(k);
                    for(int n = 0; n < allCalculatableCoupon.get(j).getItemList().size(); n++){
                        String matchItem =  allCalculatableCoupon.get(j).getItemList().get(n);
                        if(item.equals(matchItem)){
                            if(allCalculatableCoupon.get(i).getDiscount()
                                    >= allCalculatableCoupon.get(j).getDiscount()){
                                allCalculatableCoupon.get(j).setDeletable(true);

                            } else{
                                allCalculatableCoupon.get(i).setDeletable(true);
                            }
                        }
                    }

                }
            }

        }

        return allCalculatableCoupon;

    }

    private HashSet<CouponDataModel> getElizible(){
        for(int i = 0; i < couponList.size(); i++){
            int itemNumber = couponList.get(i).getItemList().size();
            int count = 0;
            for(int j= 0; j < selectedItemList.size(); j++){
                if (couponList.get(i).getItemList().contains(selectedItemList.get(j).getProductName())) {
                    count++;
                }
                if (count == itemNumber) {
                    selectedCoupon.add(couponList.get(i));
                }
            }
        }
        return selectedCoupon;
    }

    boolean contains(List<?> list, List<?> sublist) {
        return Collections.indexOfSubList(list, sublist) != -1;
    }

  /*  public static DiscountResult generateLargestDiscount(ArrayList<CouponDataModel> coupons){
        return generateLargestDiscount(coupons, -1);
    }

    public static DiscountResult generateLargestDiscount(ArrayList<CouponDataModel> coupons, double budget) {
        DiscountResult result = new DiscountResult();
        int[] curset = new int[coupons.size()];
        int[] bestset = new int[coupons.size()];
        for (int i = 0; i < curset.length; i++) {
            curset[i] = 0;
            bestset[i] = 0;
        }

        //run algorithm
        double bestDis = 0.00;
        double bestCost = 0.00;
        boolean done = false;
        while(updateFrom(curset,0)){
            int conflictIndex = getConflictsOn(curset,coupons);
//            Log.d("curset",join(curset));
            while(conflictIndex >= 0) {
                if (!updateFrom(curset, conflictIndex)) {
                    done = true;
                    break;
                }
                conflictIndex = getConflictsOn(curset,coupons);
            }
            if(done)
                break;
            double[] costAndDis = getCostAndDiscount(curset,coupons);
            double cost   = costAndDis[0];
            double curDis = costAndDis[1];
            if((cost <= budget || budget < 0) && curDis > bestDis) {
                bestDis = curDis;
                bestCost = cost;
                for (int i = 0; i < curset.length; i++) {
                    bestset[i] = curset[i];
                }
            }
        }

        result.cost = bestCost;
        result.discount = bestDis;
        result.preCost = bestCost + bestDis;
        for (int i = 0; i < bestset.length; i++) {
            if(bestset[i] == 1)
                result.coupons.add(coupons.get(i));
        }
        return result;
    }

    private static boolean updateFrom(int[] arr, int index) {
        if(arr.length <= index)
            return false;
        while(arr[index] == 1){
            arr[index++] = 0;
            if(index >= arr.length)
                return false;
        }
        arr[index] = 1;
        return true;
    }

    private static int getConflictsOn(int[] set, ArrayList<CouponDataModel> coupons){
        CouponDataModel conflicter = new CouponDataModel();
        for(int i = set.length - 1; i >= 0; i--){
            if(set[i] == 1) {
                if (conflicter.conflicts(coupons.get(i))) {
//                    Toast.makeText(this,"confliction!",Toast.LENGTH_SHORT).show();
//                    Log.d("conflict on",join(set) + " " +String.valueOf(i));
                    return i;
                }
                else
                    conflicter.addProducts(coupons.get(i).getProducts());
            }
        }
        return -1;
    }

    private static double[] getCostAndDiscount(int[] set, ArrayList<Coupon> coupons){
        double cost = 0.00;
        double dis = 0.00;
        for (int i = 0; i < set.length; i++) {
            if(set[i] == 1) {
                dis += coupons.get(i).getDiscount();
                for (int j = 0; j < coupons.get(i).getProducts().size(); j++) {
                    cost += coupons.get(i).getProducts().get(j).getPrice();
                }
            }
        }
        return new double[]{cost - dis, dis};
    }*/

}
