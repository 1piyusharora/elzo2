package com.elzo.elzo;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class wpFragment extends Fragment {

    private CardView cardView;
    public static int i;



    public static Fragment getInstance(int position) {
        wpFragment f = new wpFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        f.setArguments(args);
        return f;
    }


    @SuppressLint("DefaultLocale")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_viewpager, container, false);

         ArrayList<itemsBean> itemsBeanArrayList= (ArrayList<itemsBean>) getArguments().getSerializable("itemsList");
        //itemsBean itemsBean = (com.elzo.elzo.itemsBean) getArguments().getSerializable("itemsBean");
        LinearLayout linearLayout=view.findViewById(R.id.textViewsLayout);


        cardView = (CardView) view.findViewById(R.id.cardView);
        cardView.setMaxCardElevation(cardView.getCardElevation() * CardAdapter.MAX_ELEVATION_FACTOR);

        Toast.makeText(getContext(),""+ itemsBeanArrayList.size(),Toast.LENGTH_SHORT).show();
           /* for(int j=0;j<itemsBean.getFeatures().size();j++){
                TextView textView = new TextView(getContext());
                textView.setText(itemsBean.getFeatures().get(j));
                linearLayout.addView(textView);
            }*/


       /*
        Button button = (Button)view.findViewById(R.id.buyNow);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Button in Card " + getArguments().getInt("position")
                        + "Clicked!", Toast.LENGTH_SHORT).show();
            }
        });
        */

        return view;
    }



    public CardView getCardView() {
        return cardView;
    }

}
