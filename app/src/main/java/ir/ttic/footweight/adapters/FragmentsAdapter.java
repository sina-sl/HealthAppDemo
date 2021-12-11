package ir.ttic.footweight.adapters;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.WithHint;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import ir.ttic.footweight.fragments.FootRaceFragment;
import ir.ttic.footweight.fragments.WeightCurveFragment;
import ir.ttic.footweight.fragments.WeightFragment;

public class FragmentsAdapter extends FragmentStateAdapter {

  Fragment[] fragments;


  public FragmentsAdapter(@NonNull FragmentActivity fragmentActivity, Fragment... fragments) {
    super(fragmentActivity);
    this.fragments = fragments;
  }

  @NonNull
  @Override
  public Fragment createFragment(int position) {
    return fragments[position];
  }

  @Override
  public int getItemCount() {
    return fragments.length;
  }

}
