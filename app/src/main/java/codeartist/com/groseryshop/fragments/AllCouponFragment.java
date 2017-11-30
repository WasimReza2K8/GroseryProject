package codeartist.com.groseryshop.fragments;

import java.util.List;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import codeartist.com.groseryshop.R;
import codeartist.com.groseryshop.adapter.CouponAdapter;
import codeartist.com.groseryshop.database.Database;
import codeartist.com.groseryshop.datamodel.CouponDataModel;

/**
 * Created by bjit-16 on 11/30/17.
 */

public class AllCouponFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private CouponAdapter mAdapter;
    List<CouponDataModel> list;

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = (Integer) view.getTag();
            Database.getOneCouponItem(list.get(position).getCouponNumber(), list.get(position));
            itemDetail(list.get(position));
        }
    };

    private void itemDetail(CouponDataModel model){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.AlertDialogCustom));

        // set title
      //  alertDialogBuilder.setTitle("Coupon Details");
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.coupon_detail_popup, null);
        alertDialogBuilder.setView(dialogView);

        TextView couponNumber = (TextView) dialogView.findViewById(R.id.coupon_number);
        TextView discount = (TextView) dialogView.findViewById(R.id.discount);
        TextView items = (TextView) dialogView.findViewById(R.id.items);
        couponNumber.setText("Coupon Number: "+model.getCouponNumber()+"");
        discount.setText("Discount amount: "+model.getDiscount()+"");
        String couponItems = "Coupon Items: ";
        for(String item : model.getItemList()){
            couponItems = couponItems + item +", ";
        }
        if(couponItems != null){
            couponItems = couponItems.substring(0, couponItems.length() - 2);
        }
        items.setText(couponItems);


        alertDialogBuilder.setNegativeButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // if this button is clicked, just close
                // the dialog box and do nothing
                dialog.cancel();
                return;
            }
        });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_all_coupon, container, false);
        Database.init(getActivity());
        mRecyclerView = rootView.findViewById(R.id.couponRecyclerView);
        Button delete = rootView.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int couponNumber = -1;
                CouponDataModel removeItem = null;
                for(CouponDataModel item : list){
                    if(item.isDeletable()){
                        couponNumber = item.getCouponNumber();
                        removeItem = item;
                    }
                    Database.deleteCouponItem(couponNumber);
                    Database.deleteCoupon(couponNumber);
                    if(removeItem != null){
                        list.remove(removeItem);
                        mAdapter.notifyDataSetChanged();
                    }

                }
            }
        });
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        list= Database.getAllCoupon();
        mAdapter = new CouponAdapter(getActivity(), list, mOnClickListener);
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }
}
