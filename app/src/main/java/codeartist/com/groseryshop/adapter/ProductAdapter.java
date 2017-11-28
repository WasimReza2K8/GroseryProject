package codeartist.com.groseryshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import codeartist.com.groseryshop.R;
import codeartist.com.groseryshop.datamodel.ProductDataModel;

/**
 * Created by bjit-16 on 11/28/17.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<ProductDataModel> list = new ArrayList<>();
    private Context mContext;


    public ProductAdapter(Context context, List<ProductDataModel> list) {
        this.list = list;
        this.mContext = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextViewProductName.setText(list.get(position).getProductName());
        holder.mTextViewPrice.setText(list.get(position).getPrice()+"");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextViewProductName, mTextViewPrice,
                mTextViewVenue;
        public CheckBox selection;

        public ViewHolder(View v) {
            super(v);
            mTextViewProductName = (TextView) v.findViewById(R.id.name);
            mTextViewPrice = (TextView) v.findViewById(R.id.price);
            selection = (CheckBox) v.findViewById(R.id.selectItem);


        }
    }
}
