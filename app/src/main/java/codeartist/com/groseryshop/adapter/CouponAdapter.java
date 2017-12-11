package codeartist.com.groseryshop.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import codeartist.com.groseryshop.R;
import codeartist.com.groseryshop.datamodel.CouponDataModel;

/**
 * Created by bjit-16 on 11/30/17.
 */

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.ViewHolder> {
    private List<CouponDataModel> list = new ArrayList<>();
    private Context mContext;
    private View.OnClickListener mOnClickListener;
    private int lastCheckedPosition = -1;

    public CouponAdapter(Context context, List<CouponDataModel> list, View.OnClickListener clickListener) {
        this.list = list;
        this.mContext = context;
        this.mOnClickListener = clickListener;
    }

    @Override
    public CouponAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coupon, null);
        CouponAdapter.ViewHolder viewHolder = new CouponAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CouponAdapter.ViewHolder holder, int position) {
        holder.mTextViewCoupon.setText(list.get(position).getCouponNumber()+"");
        holder.mTextViewCoupon.setTag(position);
        if(position == lastCheckedPosition){
            holder.radioButton.setChecked(true);
            list.get(position).setDeletable(true);
        } else {
            holder.radioButton.setChecked(false);
            list.get(position).setDeletable(false);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }




    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextViewCoupon;
        public RadioButton radioButton;

        public ViewHolder(View v) {
            super(v);
            radioButton = (RadioButton) v.findViewById(R.id.radio);
            mTextViewCoupon = (TextView) v.findViewById(R.id.coupon_item);

            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastCheckedPosition = getAdapterPosition();
                    //because of this blinking problem occurs so
                    //i have a suggestion to add notifyDataSetChanged();
                    //   notifyItemRangeChanged(0, list.length);//blink list problem
                    notifyDataSetChanged();

                }
            });
            mTextViewCoupon.setOnClickListener(mOnClickListener);

        }
    }
}
