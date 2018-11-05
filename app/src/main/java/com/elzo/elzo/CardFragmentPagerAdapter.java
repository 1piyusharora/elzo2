package com.elzo.elzo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.CardView;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CardFragmentPagerAdapter extends FragmentStatePagerAdapter implements CardAdapter {

    private List<wpFragment> fragments;
    private float baseElevation;
    ArrayList<itemsBean> itemsBeanArrayList;

    public CardFragmentPagerAdapter(FragmentManager fm, float baseElevation,ArrayList<itemsBean> itemsBeanArrayList) {
        super(fm);
        fragments = new ArrayList<>();
        this.baseElevation = baseElevation;
        this.itemsBeanArrayList=itemsBeanArrayList;
        for(int i = 0; i< itemsBeanArrayList.size(); i++){
            addCardFragment(new wpFragment());
        }
    }

    @Override
    public float getBaseElevation() {
        return baseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return fragments.get(position).getCardView();
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new wpFragment();
        Bundle args = new Bundle();
        args.putSerializable("itemsList",itemsBeanArrayList);
        fragment.setArguments(args);
        return fragment;
        //return wpFragment.getInstance(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object fragment = super.instantiateItem(container, position);
        fragments.set(position, (wpFragment) fragment);
        return fragment;
    }

    public void addCardFragment(wpFragment fragment) {
        fragments.add(fragment);
    }

}
