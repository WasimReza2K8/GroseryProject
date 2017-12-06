package codeartist.com.groseryshop.utils;

import java.util.ArrayList;

import codeartist.com.groseryshop.datamodel.CouponDataModel;

/**
 * Created by bjit-16 on 12/6/17.
 */

public class Utils {
    public static ArrayList<CouponDataModel> getCouponWithoutConflicts(ArrayList<CouponDataModel> allCalculatableCoupon){
        //ArrayList<CouponDataModel> allCalculatableCoupon = new ArrayList<>(selectedCoupon);
        ArrayList<CouponDataModel> removable = new ArrayList<>();

        for(int i = 0; i < allCalculatableCoupon.size(); i++ ){
            for(int j = 0; j < allCalculatableCoupon.size(); j++){
                if( (i == j) || removable.contains(allCalculatableCoupon.get(j))
                        || removable.contains(allCalculatableCoupon.get(i))){
                    continue;
                }

                for(int k = 0; k < allCalculatableCoupon.get(j).getItemList().size(); k++){
                    if(allCalculatableCoupon.get(i).getItemList()
                            .contains(allCalculatableCoupon.get(j).getItemList().get(k))){
                        if(allCalculatableCoupon.get(i).getDiscount()
                                >= allCalculatableCoupon.get(j).getDiscount()){
                            removable.add(allCalculatableCoupon.get(j));

                        } else{
                            removable.add(allCalculatableCoupon.get(i));
                        }
                    }
                }
            }

        }
        allCalculatableCoupon.removeAll(removable);

        return allCalculatableCoupon;

    }
}
