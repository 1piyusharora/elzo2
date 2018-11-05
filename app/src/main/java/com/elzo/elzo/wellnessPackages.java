package com.elzo.elzo;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class wellnessPackages extends AppCompatActivity {

    FirebaseFirestore db;
    itemsBean itemsBean;
    ArrayList<itemsBean> itemsBeanArrayLists;
    ProgressDialog pd;
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellness_packages);

         viewPager = (ViewPager) findViewById(R.id.vpPager);



        db=FirebaseFirestore.getInstance();
        itemsBean=new itemsBean();
        itemsBeanArrayLists = new ArrayList<>();
        pd=new ProgressDialog(this);
        pd.setMessage("Please Wait");
        pd.show();
        fetchItems();

    }

    private void fetchItems() {
        db.collection("Items").whereEqualTo("type","wellness").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()){
                    itemsBean=doc.toObject(itemsBean.class);
                    itemsBeanArrayLists.add(itemsBean);
                }
                CardFragmentPagerAdapter pagerAdapter = new CardFragmentPagerAdapter(getSupportFragmentManager(), dpToPixels(2, wellnessPackages.this),itemsBeanArrayLists);
                ShadowTransformer fragmentCardShadowTransformer = new ShadowTransformer(viewPager, pagerAdapter);
                fragmentCardShadowTransformer.enableScaling(true);
                viewPager.setAdapter(pagerAdapter);
                viewPager.setPageTransformer(false, fragmentCardShadowTransformer);
                viewPager.setOffscreenPageLimit(3);
                pd.dismiss();
               // Toast.makeText(wellnessPackages.this,"Size "+itemsBeanArrayLists.size(),Toast.LENGTH_LONG).show();
            }
        });
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
