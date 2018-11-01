package com.elzo.elzo;

import android.content.Context;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.google.firebase.firestore.FirebaseFirestore;

public class wellnessPackages extends AppCompatActivity {

    FirebaseFirestore db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellness_packages);

        ViewPager viewPager = (ViewPager) findViewById(R.id.vpPager);

        CardFragmentPagerAdapter pagerAdapter = new CardFragmentPagerAdapter(getSupportFragmentManager(), dpToPixels(2, this));
        ShadowTransformer fragmentCardShadowTransformer = new ShadowTransformer(viewPager, pagerAdapter);
        fragmentCardShadowTransformer.enableScaling(true);

        viewPager.setAdapter(pagerAdapter);
        viewPager.setPageTransformer(false, fragmentCardShadowTransformer);
        viewPager.setOffscreenPageLimit(3);

        db=FirebaseFirestore.getInstance();

    }

    /**
     * Change value in dp to pixels
     * @param dp
     * @param context
     * @return
     */
    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }


}
