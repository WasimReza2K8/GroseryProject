package codeartist.com.groseryshop.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import codeartist.com.groseryshop.R;
import codeartist.com.groseryshop.datamodel.CouponDataModel;

/**
 * Created by bjit-16 on 12/6/17.
 */

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder> {
    private List<CouponDataModel> list = new ArrayList<>();
    private Context mContext;


    public ShoppingListAdapter(Context context, List<CouponDataModel> list) {
        this.list = list;
        this.mContext = context;
    }
    @Override
    public ShoppingListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shopping_list, null);
        ShoppingListAdapter.ViewHolder viewHolder = new ShoppingListAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(ShoppingListAdapter.ViewHolder holder, final int position) {
        String items = "";

        for(String item: list.get(position).getItemList()) {
            items = items + item + ", ";
        }
            if (items != null) {
                items = items.substring(0, items.length() - 2);
            }
        holder.mTextViewProductName.setText(items);
        holder.mTextViewPrice.setText(list.get(position).getDiscount()+"");
    }

    public void addItem(CouponDataModel data){
        list.add(data);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextViewProductName, mTextViewPrice;
        public CheckBox selection;

        public ViewHolder(View v) {
            super(v);
            mTextViewProductName = (TextView) v.findViewById(R.id.name);
            mTextViewPrice = (TextView) v.findViewById(R.id.price);

        }
    }
}
