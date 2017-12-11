package codeartist.com.groseryshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import codeartist.com.groseryshop.R;
import codeartist.com.groseryshop.datamodel.ProductDataModel;

/**
 * Created by ASUS on 02-Dec-17.
 */

public class FinalPriceAdapter extends RecyclerView.Adapter<FinalPriceAdapter.ViewHolder> {
    private List<ProductDataModel> list = new ArrayList<>();
    private Context mContext;
    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener;


    public FinalPriceAdapter(Context context, List<ProductDataModel> list,
                             CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        this.list = list;
        this.mContext = context;
        this.onCheckedChangeListener = onCheckedChangeListener;
    }
    @Override
    public FinalPriceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, null);
        FinalPriceAdapter.ViewHolder viewHolder = new FinalPriceAdapter.ViewHolder(view, onCheckedChangeListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FinalPriceAdapter.ViewHolder holder, final int position) {
        holder.mTextViewProductName.setText(list.get(position).getProductName());
        holder.mTextViewPrice.setText(list.get(position).getPrice()+"");

        if(list.get(position).getSelected()){
            holder.selection.setChecked(true);
        } else{
            holder.selection.setChecked(false);
        }
        holder.selection.setTag(position);
        //holder.selection.setOnCheckedChangeListener(onCheckedChangeListener);

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextViewProductName, mTextViewPrice;
        public CheckBox selection;

        public ViewHolder(View v, CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
            super(v);
            mTextViewProductName = (TextView) v.findViewById(R.id.name);
            mTextViewPrice = (TextView) v.findViewById(R.id.price);
            selection = (CheckBox) v.findViewById(R.id.selectItem);
            selection.setOnCheckedChangeListener(onCheckedChangeListener);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

  /*  public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextViewProductName, mTextViewPrice;
        public CheckBox selection;

        public ViewHolder(View v) {
            super(v);
            mTextViewProductName = (TextView) v.findViewById(R.id.name);
            mTextViewPrice = (TextView) v.findViewById(R.id.price);
            selection = (CheckBox) v.findViewById(R.id.selectItem);


        }
    }*/
}
