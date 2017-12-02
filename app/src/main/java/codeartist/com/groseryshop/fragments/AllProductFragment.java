package codeartist.com.groseryshop.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import codeartist.com.groseryshop.R;
import codeartist.com.groseryshop.adapter.AllProductAdapter;
import codeartist.com.groseryshop.adapter.ProductAdapter;
import codeartist.com.groseryshop.database.Database;
import codeartist.com.groseryshop.datamodel.ProductDataModel;

/**
 * Created by bjit-16 on 11/28/17.
 */

public class AllProductFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private AllProductAdapter mAdapter;
    private Button updateItem;
    private List<Integer> changeItemPosition = new ArrayList<>();
    List<ProductDataModel> list;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_product_list, container, false);
        mRecyclerView = rootView.findViewById(R.id.productRecyclerView);
        updateItem = rootView.findViewById(R.id.update);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        Database.init(getActivity());
        list= Database.getAllProduct();
        mAdapter = new AllProductAdapter(getActivity(), list, changeItemPosition);
        mRecyclerView.setAdapter(mAdapter);


        updateItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(Integer position: changeItemPosition){
                    AllProductAdapter.ViewHolder holder = (AllProductAdapter.ViewHolder)
                            mRecyclerView.findViewHolderForAdapterPosition(position);
                    if(holder.mEditTextPrice != null){
                        String price = holder.mEditTextPrice.getText().toString();
                        if(price  != null  && !price.isEmpty()){
                            list.get(position).setPrice(Float.parseFloat(price));
                            Database.updateProductPrice(list.get(position));
                            //changeItemPosition.remove(position);
                        }
                    }

                }

                resetAllCheckedItem();
                changeItemPosition.clear();
               // mAdapter.notifyDataSetChanged();
            }
        });
        return rootView;
    }

    private void resetAllCheckedItem() {
        for (ProductDataModel model : list) {
            model.setSelected(false);
        }
        mAdapter.notifyDataSetChanged();
    }
}
