package ir.ttic.footweight.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ir.ttic.footweight.MainActivity;
import ir.ttic.footweight.R;
import ir.ttic.footweight.adapters.WeightListAdapter;
import ir.ttic.footweight.database.WeightDB;
import ir.ttic.footweight.model.WeightModel;

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
    weightListAdapter = new WeightListAdapter(getContext(),this::onDeleteClick);

    recyclerView.setAdapter(weightListAdapter);
    btnAddItem.setOnClickListener(v -> new NewWeightDialog(getContext()).show());
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

  }

  public void onDeleteClick(int position) {
    WeightModel model = MainActivity.getWeightItems().get(position);
    MainActivity.getWeightItems().remove(model);
    WeightDB.getInstance(getContext()).remove(model);
    weightListAdapter.notifyDataSetChanged();
  }


  class NewWeightDialog extends Dialog {

    public NewWeightDialog(@NonNull Context context) {
      super(context);
    }

    private EditText edtWeight;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      setContentView(R.layout.dialog_add_weight);
      getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

      edtWeight = findViewById(R.id.edt_weight);
      btnAdd = findViewById(R.id.btn_add);

      btnAdd.setOnClickListener(v -> {

        WeightModel weightModel = new WeightModel(
          System.currentTimeMillis(),
          Double.parseDouble(edtWeight.getText().toString())
        );

        MainActivity.getWeightItems().add(weightModel);
        WeightDB.getInstance(getContext()).insertNewWeight(weightModel);

        WeightFragment.this.weightListAdapter.notifyItemInserted(
          MainActivity.getWeightItems().indexOf(weightModel)
        );

        dismiss();
      });

    }
  }

}
