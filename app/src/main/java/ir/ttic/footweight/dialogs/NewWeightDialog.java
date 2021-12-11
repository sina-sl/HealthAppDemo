package ir.ttic.footweight.dialogs;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import ir.ttic.footweight.R;
import ir.ttic.footweight.fragments.WeightFragment;
import ir.ttic.footweight.model.Weight;

public class NewWeightDialog extends DialogFragment {

  private WeightFragment weightFragment;

  private TextView txtWeight;
  private Button btnAddWeight;
  private ImageButton btnAdd, btnMinus;

  private int weight = 54;
  private String weightText = "%sKg";

  public NewWeightDialog(WeightFragment weightFragment) {
    this.weightFragment = weightFragment;
  }


  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
    return inflater.inflate(R.layout.dialog_add_weight, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    btnAdd = view.findViewById(R.id.btn_add);
    btnMinus = view.findViewById(R.id.btn_minus);
    txtWeight = view.findViewById(R.id.txt_weight);
    btnAddWeight = view.findViewById(R.id.btn_login);


    btnAdd.setOnClickListener(this::onAddClick);
    btnMinus.setOnClickListener(this::onMinusClick);
    btnAddWeight.setOnClickListener(this::onAddWeightClick);

    txtWeight.setText(String.format(Locale.US, weightText, weight));

  }

  @Override
  public void onStart() {
    super.onStart();

    int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.9);

    getDialog().getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
    getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

  }

  public void onAddClick(View view) {
    weight++;
    txtWeight.setText( String.format(Locale.US,weightText, weight));
  }

  public void onMinusClick(View view) {
    weight--;
    txtWeight.setText(String.format(Locale.US, weightText, weight));
  }

  public void onAddWeightClick(View view) {

    Weight weightModel = new Weight(
      System.currentTimeMillis(),
      this.weight
    );

    weightFragment.onAddWeight(weightModel);

    dismiss();
  }

}
