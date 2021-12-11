package ir.ttic.footweight.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import ir.ttic.footweight.MainActivity;
import ir.ttic.footweight.R;
import ir.ttic.footweight.database.Database;
import ir.ttic.footweight.dialogs.NavigationDialog;
import ir.ttic.footweight.model.Track;

public class FootRaceFragment extends Fragment implements LocationListener {

  private GoogleMap googleMap;
  private Button btnNavigation;
  private Button btnNavigationList;

  private boolean inNavigation = false;
  private Polyline polyline;
  private BitmapDescriptor markerIcon;

  private List<LatLng> currentLatlng = new ArrayList<>();

  private long navigationId = 0;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_maps, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    markerIcon = bitmapFromVector(getContext(), R.drawable.location_market);

    btnNavigation = view.findViewById(R.id.btn_navigation);
    btnNavigationList = view.findViewById(R.id.btn_navigation_list);

    btnNavigation.setOnClickListener(this::onBtnNavigationClick);
    btnNavigationList.setOnClickListener(this::onBtnNavigationListClick);

    SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
    if (mapFragment != null) mapFragment.getMapAsync(this::onMapReady);

  }

  private void onBtnNavigationListClick(View v) {

    new NavigationDialog(this).show(getParentFragmentManager(),null);

  }

  void onBtnNavigationClick(View v) {

    if (inNavigation) {

      btnNavigation.setText("start navigation");

    } else {

      navigationId = System.currentTimeMillis();
      btnNavigation.setText("end navigation");
      googleMap.clear();
      polyline.remove();

    }

    inNavigation = !inNavigation;
  }

  @SuppressLint("MissingPermission")
  public void onMapReady(@NonNull GoogleMap googleMap) {

    this.googleMap = googleMap;

    googleMap.getUiSettings().setMapToolbarEnabled(true);
    googleMap.setMyLocationEnabled(true);
    googleMap.setMapStyle(
      MapStyleOptions.loadRawResourceStyle(
        getContext(),
        R.raw.google_map_style
      )
    );

    polyline = googleMap.addPolyline(
      new PolylineOptions()
        .width(5)
        .geodesic(true)
        .color(Color.BLUE)
    );

    LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
    locationManager.requestLocationUpdates(
      LocationManager.GPS_PROVIDER,
      1000,
      1,
      this);

  }

  public void previewOldNavigation(List<Track> tracks) {

    currentLatlng = new ArrayList<>();

    for (Track track : tracks) {
      currentLatlng.add(new LatLng(track.getLatitude(), track.getLongitude()));
    }

    polyline.setPoints(currentLatlng);

    googleMap.animateCamera(
      CameraUpdateFactory.newLatLngZoom(
        new LatLng(
          tracks.get(0).getLatitude(),
          tracks.get(0).getLongitude()
        ),
        18
      )
    );

  }

  @Override
  public void onLocationChanged(@NonNull Location location) {

    LatLng newPoint = new LatLng(location.getLatitude(), location.getLongitude());

    if (inNavigation) {

      googleMap.addMarker(
        new MarkerOptions()
          .position(newPoint)
          .icon(markerIcon)
      );

      currentLatlng.add(newPoint);
      polyline.setPoints(currentLatlng);

      Database.getInstance(getContext()).insertNewTrack(
        new Track(
          navigationId,
          MainActivity.getUserName(),
          location.getLongitude(),
          location.getLatitude(),
          location.getSpeed() * 3.6
        )
      );
    }

  }

  @Override
  public void onStatusChanged(String provider, int status, Bundle extras) {

  }

  @Override
  public void onProviderEnabled(@NonNull String provider) {

  }

  @Override
  public void onProviderDisabled(@NonNull String provider) {

  }


  private BitmapDescriptor bitmapFromVector(Context context, int vectorResId) {
    Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
    vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
    Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);
    vectorDrawable.draw(canvas);
    return BitmapDescriptorFactory.fromBitmap(bitmap);
  }


}