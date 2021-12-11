package ir.ttic.footweight.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import ir.ttic.footweight.MainActivity;
import ir.ttic.footweight.R;
import ir.ttic.footweight.model.Track;
import ir.ttic.footweight.model.Weight;

public class NavigatioListAdapter extends RecyclerView.Adapter<NavigatioListAdapter.ViewHolder> {


  private Context context;
  private List<Weight> weights = new ArrayList<>();
  private OnDeleteClick onDeleteClick;
  private OnItemClick onItemClick;

  public NavigatioListAdapter(Context context,OnItemClick onItemClick ,OnDeleteClick onDeleteClick) {
    this.context = context;
    this.onItemClick = onItemClick;
    this.onDeleteClick = onDeleteClick;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_navigation_item, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.itemView.setOnClickListener((itemView) -> onItemClick.onClick(position));
    holder.bind(MainActivity.getNavigationItem().get(position), (btnDelete) -> onDeleteClick.onClick(position));
  }

  @Override
  public int getItemCount() {
    return MainActivity.getNavigationItem().size();
  }


  public static class ViewHolder extends RecyclerView.ViewHolder {

    private TextView txtDate;
    private Button btnDelete;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      txtDate = itemView.findViewById(R.id.txt_navigation_date);
      btnDelete = itemView.findViewById(R.id.btn_navigation_delete);
    }

    public void bind(Track model, View.OnClickListener onDelete) {
      txtDate.setText(model.getDate());
      btnDelete.setOnClickListener(onDelete);
    }

  }

  public interface OnDeleteClick {
    void onClick(int position);
  }

  public interface OnItemClick{
    void onClick(int position);
  }

}
