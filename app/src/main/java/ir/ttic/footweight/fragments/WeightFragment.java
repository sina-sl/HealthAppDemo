package ir.ttic.footweight.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ir.ttic.footweight.MainActivity;
import ir.ttic.footweight.R;
import ir.ttic.footweight.adapters.WeightListAdapter;
import ir.ttic.footweight.database.Database;
import ir.ttic.footweight.dialogs.NewWeightDialog;
import ir.ttic.footweight.model.Weight;

public class WeightFragment extends Fragment {

  private WeightListAdapter weightListAdapter;
  private RecyclerView recyclerView;
  private Button btnAddItem;


  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_weight, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    btnAddItem = view.findViewById(R.id.btn_add_item);
    recyclerView = view.findViewById(R.id.weight_recycler_list);
    weightListAdapter = new WeightListAdapter(getContext(), this::onDeleteClick);

    recyclerView.setAdapter(weightListAdapter);
    btnAddItem.setOnClickListener(this::onAddNewWeightClick);
    btnAddItem.setOnLongClickListener(this::randomWeights);

    recyclerView.setLayoutManager(
      new LinearLayoutManager(
        getContext(),
        LinearLayoutManager.VERTICAL,
        false
      )
    );

  }

  public void onDeleteClick(int position) {
    Weight model = MainActivity.getWeightItems().get(position);
    MainActivity.getWeightItems().remove(model);
    Database.getInstance(getContext()).remove(model);
    weightListAdapter.notifyDataSetChanged();
  }

  public void onAddWeight(Weight weightModel) {

    MainActivity.getWeightItems().add(weightModel);
    Database.getInstance(getContext()).insertNewWeight(weightModel);

    weightListAdapter.notifyItemInserted(
      MainActivity.getWeightItems().indexOf(weightModel)
    );
  }


  public boolean randomWeights(View v) {

    List<Weight> weights = MainActivity.getWeightItems();

    if (weights.size() == 0) {

      Weight weightModel = new Weight(
        System.currentTimeMillis(),
        ((int) ((Math.random() * 50) + 40))
      );

      MainActivity.getWeightItems().add(weightModel);

      weightListAdapter.notifyItemInserted(
        MainActivity.getWeightItems().indexOf(weightModel)
      );

    }

    for (int i = 0; i < 50; i++) {

      Weight weightModel = new Weight(weights.get(weights.size() - 1).getDate() + 86400000,
        ((int) ((Math.random() * 50) + 40))
      );

      MainActivity.getWeightItems().add(weightModel);

      weightListAdapter.notifyItemInserted(
        MainActivity.getWeightItems().indexOf(weightModel)
      );

    }

    return true;

  }

  public void onAddNewWeightClick(View v) {

    List<Weight> weights = MainActivity.getWeightItems();

    if (weights.size() > 0 && System.currentTimeMillis() - weights.get(weights.size() - 1).getDate() < 86400000) {

      Toast.makeText(
        getContext(),
        "You can only add one weight per 24h",
        Toast.LENGTH_SHORT
      ).show();

    } else {

      new NewWeightDialog(this).show(getParentFragmentManager(), null);

    }

  }

}
