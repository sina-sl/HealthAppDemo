package ir.ttic.footweight.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.anychart.core.Chart;
import com.anychart.core.cartesian.series.Line;
import com.anychart.core.cartesian.series.Spline;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Fill;
import com.anychart.graphics.vector.SolidFill;
import com.anychart.graphics.vector.Stroke;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import ir.ttic.footweight.MainActivity;
import ir.ttic.footweight.R;
import ir.ttic.footweight.model.Weight;

public class WeightCurveFragment extends Fragment {


  private AnyChartView anyChart;
  private Cartesian cartesian;
  private Spline spline;
  private Set set;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_weight_curve, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    anyChart = view.findViewById(R.id.any_chart_view);

    set = Set.instantiate();
    cartesian = AnyChart.line();
    cartesian.animation(true);
    cartesian.legend().enabled(true);
    cartesian.legend().fontSize(13d);
    cartesian.background().enabled();
    cartesian.crosshair().yLabel(true);
    cartesian.crosshair().enabled(true);
    cartesian.padding(10d, 20d, 5d, 20d);
    cartesian.background().fill("#151515");
    cartesian.yAxis(0).title("Your Weight (Kg)");
    cartesian.yAxis(0).title().fontColor("#FBFCD4");
    cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);
    cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

    set.data(


      (MainActivity.getWeightItems().isEmpty() ? arr : new ArrayList<>(MainActivity.getWeightItems()))

    );
//    set.data(new ArrayList<>(MainActivity.getWeightItems()));
    spline = cartesian.spline(
      set.mapAs(
        "{ x: 'x', value: 'value' }"),
      ""
    ).stroke("#FBFCD4");

    anyChart.setChart(cartesian);

  }


  static final private ArrayList arr = new ArrayList();

  static {
    arr.add(new Weight(System.currentTimeMillis(), 0));
  }

  @Override
  public void onResume() {
    super.onResume();

    spline.data(MainActivity.getWeightItems().isEmpty() ? arr : new ArrayList<>(MainActivity.getWeightItems()));

  }

  @Override
  public void onStart() {
    super.onStart();
  }

  @Override
  public void onAttach(@NonNull Context context) {
    super.onAttach(context);
  }
}
