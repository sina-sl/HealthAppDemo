package ir.ttic.footweight;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;
import ir.ttic.footweight.adapters.FragmentsAdapter;
import ir.ttic.footweight.database.Database;
import ir.ttic.footweight.fragments.FootRaceFragment;
import ir.ttic.footweight.fragments.WeightCurveFragment;
import ir.ttic.footweight.fragments.WeightFragment;
import ir.ttic.footweight.model.Track;
import ir.ttic.footweight.model.Weight;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {


  private TabLayout tableLayout;
  private ViewPager2 viewPager;

  private static List<Weight> weightItems;
  private static List<Track> navigationItems;

  private static boolean DEVICE_LOCATION_PERMISSIONS_GRANTED = false;
  private static final String FINE_LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION;
  private static final String COARSE_LOCATION_PERMISSION = Manifest.permission.ACCESS_COARSE_LOCATION;
  private static final int locationRequestCode = 1234;


  public static List<Weight> getWeightItems() {
    return weightItems;
  }

  public static List<Track> getNavigationItem() {
    return navigationItems;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    if (weightItems == null) {

      ProgressDialog progressDialog = new ProgressDialog(this);
      progressDialog.setTitle("Loading , Please Wait");
      progressDialog.setCancelable(false);
      progressDialog.show();

      new Thread(() -> {

        weightItems = Database.getInstance(this).getAllWeights();
        navigationItems = Database.getInstance(this).getNavigations();

        runOnUiThread(()->{

          viewPager = findViewById(R.id.view_pager);
          tableLayout = findViewById(R.id.tab_layout);
          tableLayout.addOnTabSelectedListener(this);

          viewPager.setUserInputEnabled(false);
          viewPager.setAdapter(
            new FragmentsAdapter(
              this,
              new WeightFragment(),
              new WeightCurveFragment(),
              new FootRaceFragment()
            )
          );

          new TabLayoutMediator(
            tableLayout, viewPager, (tab, position) -> tab.setText(
              "Weight,Curve,Race".split(",")[position])
          ).attach();

          progressDialog.dismiss();

          checkLocationPermission();

        });
      }).start();

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