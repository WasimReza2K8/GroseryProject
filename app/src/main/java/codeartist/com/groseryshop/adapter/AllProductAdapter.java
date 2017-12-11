package codeartist.com.groseryshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import codeartist.com.groseryshop.R;
import codeartist.com.groseryshop.datamodel.ProductDataModel;

/**
 * Created by bjit-16 on 11/30/17.
 */

public class AllProductAdapter extends RecyclerView.Adapter<AllProductAdapter.ViewHolder> {
    private List<ProductDataModel> list = new ArrayList<>();
    private Context mContext;
    private List<Integer> changeItemPosition;

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = (Integer) view.getTag();
            if(((CheckBox)view).isChecked()){
                list.get((Integer) view.getTag()).setSelected(true);
            } else{
                list.get((Integer) view.getTag()).setSelected(false);
            }

            notifyItemChanged(position);
        }
    };


    public AllProductAdapter(Context context, List<ProductDataModel> list, List<Integer> changeItemPosition) {
        this.list = list;
        this.mContext = context;
        this.changeItemPosition = changeItemPosition;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public AllProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_all_product, null);
        AllProductAdapter.ViewHolder viewHolder = new AllProductAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AllProductAdapter.ViewHolder holder, final int position) {
        holder.mTextViewProductName.setText(list.get(position).getProductName());
        holder.mEditTextPrice.setText(list.get(holder.getAdapterPosition()).getPrice()+"");
        if( list.get(position).getSelected()){
          /*  holder.mEditTextPrice.setInputType(InputType.TYPE_CLASS_NUMBER);
            holder.mEditTextPrice.setFocusable(true);
            holder.mEditTextPrice.setFocusableInTouchMode(true); // user touches widget on phone with touch screen
            holder.mEditTextPrice.setClickable(true);*/
            holder.selection.setChecked(true);
        } else{
         /* *//*  holder.mEditTextPrice.setInputType(InputType.TYPE_NULL);
            holder.mEditTextPrice.setFocusable(false);
            holder.mEditTextPrice.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
            holder.mEditTextPrice.setClickable(false);*/
            holder.selection.setChecked(false);
           /// holder.mEditTextPrice.setEd(false);
        }
        holder.selection.setTag(position);
        holder.mEditTextPrice.setTag(position);
//        holder.myCustomEditTextListener.updatePosition(holder.getAdapterPosition());
        holder.mEditTextPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String item = charSequence.toString();
                if(item != null && !item.isEmpty()){
                    list.get(position).setPrice(Float.parseFloat(item));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });


    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextViewProductName, mTextViewPrice;
        public EditText mEditTextPrice;
        public CheckBox selection;

        public ViewHolder(View v) {
            super(v);
            mTextViewProductName =  v.findViewById(R.id.name);
            mEditTextPrice =  v.findViewById(R.id.price);
            selection = v.findViewById(R.id.selectItem);
          //  this.myCustomEditTextListener = myCustomEditTextListener;
           // selection.setOnCheckedChangeListener(onCheckedChangeListener);
            selection.setOnClickListener(clickListener);
           // mEditTextPrice.addTextChangedListener(myCustomEditTextListener);


        }
    }


}
