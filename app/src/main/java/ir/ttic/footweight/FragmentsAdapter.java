package ir.ttic.footweight;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.WithHint;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import ir.ttic.footweight.fragments.FootRaceFragment;
import ir.ttic.footweight.fragments.WeightCurveFragment;
import ir.ttic.footweight.fragments.WeightFragment;

public class FragmentsAdapter extends FragmentStateAdapter {

  public FragmentsAdapter(@NonNull FragmentActivity fragmentActivity) {
    super(fragmentActivity);
  }

  public FragmentsAdapter(@NonNull Fragment fragment) {
    super(fragment);
  }

  public FragmentsAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
    super(fragmentManager, lifecycle);
  }

  @NonNull
  @Override
  public Fragment createFragment(int position) {
    switch (position){
      case 0: return new FootRaceFragment();
      case 1: return new WeightFragment();
      default: return new WeightCurveFragment();
    }
  }

  @Override
  public int getItemCount() {
    return 3;
  }
}
