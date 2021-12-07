package ir.ttic.footweight;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;
import ir.ttic.footweight.database.WeightDB;
import ir.ttic.footweight.model.WeightModel;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {


  private TabLayout tableLayout;
  private ViewPager2 viewPager;

  private static List<WeightModel> weightItems;

  private static boolean DEVICE_LOCATION_PERMISSIONS_GRANTED = false;
  private static final String FINE_LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION;
  private static final String COARSE_LOCATION_PERMISSION = Manifest.permission.ACCESS_COARSE_LOCATION;
  private static final int locationRequestCode = 1234;


  public static List<WeightModel> getWeightItems() {
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

    checkLocationPermission();

    if (weightItems == null) {
      weightItems = WeightDB.getInstance(this).getAllWeights();
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    if (requestCode == locationRequestCode && grantResults.length > 0) {

      DEVICE_LOCATION_PERMISSIONS_GRANTED = true;

      for (int grantResult : grantResults) {
        if (grantResult != PackageManager.PERMISSION_GRANTED) {
          DEVICE_LOCATION_PERMISSIONS_GRANTED = false;
          break;
        }
      }

    }

  }

  @Override
  public void onTabSelected(TabLayout.Tab tab) {
    viewPager.setCurrentItem(tab.getPosition());
  }

  private void checkLocationPermission() {

    if (ContextCompat.checkSelfPermission(this, FINE_LOCATION_PERMISSION) == PackageManager.PERMISSION_GRANTED) {
      if (ContextCompat.checkSelfPermission(this, COARSE_LOCATION_PERMISSION) == PackageManager.PERMISSION_GRANTED) {
        DEVICE_LOCATION_PERMISSIONS_GRANTED = true;
      } else getLocationPermission();
    } else getLocationPermission();
  }


  private void getLocationPermission() {
    ActivityCompat.requestPermissions(this, new String[]{FINE_LOCATION_PERMISSION, COARSE_LOCATION_PERMISSION}, locationRequestCode);
  }


  @Override
  public void onTabUnselected(TabLayout.Tab tab) {

  }

  @Override
  public void onTabReselected(TabLayout.Tab tab) {

  }
}