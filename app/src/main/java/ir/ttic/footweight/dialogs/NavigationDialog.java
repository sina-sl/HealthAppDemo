package ir.ttic.footweight.dialogs;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ir.ttic.footweight.MainActivity;
import ir.ttic.footweight.R;
import ir.ttic.footweight.adapters.NavigatioListAdapter;
import ir.ttic.footweight.database.Database;
import ir.ttic.footweight.fragments.FootRaceFragment;
import ir.ttic.footweight.model.Track;

public class NavigationDialog extends DialogFragment {

  private FootRaceFragment footRaceFragment;
  private RecyclerView recyclerView;

  public NavigationDialog(FootRaceFragment footRaceFragment) {
    this.footRaceFragment = footRaceFragment;
  }


  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
    return inflater.inflate(R.layout.dialog_navigatios, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    recyclerView = view.findViewById(R.id.rec_navigatios);
    recyclerView.setAdapter(new NavigatioListAdapter(getContext(), this::onItemClick, this::onDeleteClick));
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

  }

  @Override
  public void onStart() {
    super.onStart();

    int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.9);

    getDialog().getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
    getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

  }


  public void onDeleteClick(int position) {

  }

  public void onItemClick(int position) {

    List<Track> tracks = Database.getInstance(getContext()).getNavigationTracks(
      MainActivity.getNavigationItem().get(position).getId()
    );

    footRaceFragment.previewOldNavigation(tracks);

    dismiss();

  }

}
