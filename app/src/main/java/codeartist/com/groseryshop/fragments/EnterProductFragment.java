package codeartist.com.groseryshop.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import codeartist.com.groseryshop.MainActivity;
import codeartist.com.groseryshop.R;
import codeartist.com.groseryshop.database.Database;
import codeartist.com.groseryshop.datamodel.ProductDataModel;

/**
 * Created by bjit-16 on 11/28/17.
 */

public class EnterProductFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_enter_product, container, false);
        Button add = rootView.findViewById(R.id.add);
        final EditText name = rootView.findViewById(R.id.name);
        final EditText price= rootView.findViewById(R.id.price);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Please enter Product name.", Toast.LENGTH_LONG).show();
                    return;
                }else if(price.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Please enter Product price.", Toast.LENGTH_LONG).show();
                    return;
                }
                ProductDataModel product = new ProductDataModel();
                product.setProductName(name.getText().toString());
                product.setPrice(Float.parseFloat(price.getText().toString()));
                Database.init(getActivity());
                long error = Database.insertProductData(product);
                if(error == -1f){
                    Toast.makeText(getActivity(), "This item already exists", Toast.LENGTH_LONG).show();
                }
                name.setText("");
                price.setText("");

            }
        });
        return rootView;
    }
}
