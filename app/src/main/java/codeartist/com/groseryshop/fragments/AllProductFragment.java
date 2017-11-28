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

import java.util.List;

import codeartist.com.groseryshop.R;
import codeartist.com.groseryshop.adapter.ProductAdapter;
import codeartist.com.groseryshop.database.Database;
import codeartist.com.groseryshop.datamodel.ProductDataModel;

/**
 * Created by bjit-16 on 11/28/17.
 */

public class AllProductFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ProductAdapter mAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_all_product, container, false);
        mRecyclerView = rootView.findViewById(R.id.productRecyclerView); LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        Database.init(getActivity());
        List<ProductDataModel> list= Database.getAllProduct();
        mAdapter = new ProductAdapter(getActivity(), list);
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }
}
