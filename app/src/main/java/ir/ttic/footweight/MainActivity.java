package ir.ttic.footweight;

import android.os.Bundle;
import android.provider.MediaStore;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import ir.ttic.footweight.database.WeightDB;
import ir.ttic.footweight.model.WeightModel;

public class MainActivity extends AppCompatActivity  implements TabLayout.OnTabSelectedListener{


  private TabLayout tableLayout;
  private ViewPager2 viewPager;

  private static List<WeightModel> weightItems;


  public static List<WeightModel> getWeightItems(){
    return weightItems;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    viewPager = findViewById(R.id.view_pager);
    tableLayout = findViewById(R.id.tab_layout);
    tableLayout.addOnTabSelectedListener(this);

    viewPager.setUserInputEnabled(false);
    viewPager.setAdapter(new FragmentsAdapter(this));

    new TabLayoutMediator(tableLayout, viewPager, (tab, position) -> tab.setText(String.valueOf(position))).attach();


    if (weightItems == null){
      weightItems = WeightDB.getInstance(this).getAllWeights();
    }
  }

  @Override
  public void onTabSelected(TabLayout.Tab tab) {
    viewPager.setCurrentItem(tab.getPosition());
  }

  @Override
  public void onTabUnselected(TabLayout.Tab tab) {

  }

  @Override
  public void onTabReselected(TabLayout.Tab tab) {

  }
}