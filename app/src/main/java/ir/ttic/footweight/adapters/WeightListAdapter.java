package ir.ttic.footweight.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ir.ttic.footweight.MainActivity;
import ir.ttic.footweight.R;
import ir.ttic.footweight.model.Weight;

public class WeightListAdapter extends RecyclerView.Adapter<WeightListAdapter.ViewHolder>{


  private Context context;
  private List<Weight> weights = new ArrayList<>();
  private OnDeleteClick onDeleteClick;
  static final DateFormat df = new SimpleDateFormat("MMM dd");

  public WeightListAdapter(Context context, OnDeleteClick onDeleteClick) {
    this.context = context;
    this.onDeleteClick = onDeleteClick;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_weight_item,parent,false));
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.bind(MainActivity.getWeightItems().get(position),(btnDelete)->onDeleteClick.onClick(position));
  }

  @Override
  public int getItemCount() {
    return MainActivity.getWeightItems().size();
  }


  public static class ViewHolder extends RecyclerView.ViewHolder {

    private TextView txtWeight;
    private TextView txtDate;
    private Button btnDelete;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);

      txtDate = itemView.findViewById(R.id.txt_date);
      txtWeight = itemView.findViewById(R.id.txt_weight);
      btnDelete = itemView.findViewById(R.id.btn_delete);


    }


    public void bind(Weight model, View.OnClickListener onDelete){

      txtDate.setText(df.format(model.getDate()));
      txtWeight.setText(String.valueOf(model.getWeight()));
      btnDelete.setOnClickListener(onDelete);

    }

  }

  public interface OnDeleteClick{
    void onClick(int position);
  }

}
