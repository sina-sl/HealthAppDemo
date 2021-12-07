package ir.ttic.footweight.fragments;

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
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import ir.ttic.footweight.MainActivity;
import ir.ttic.footweight.R;

public class WeightCurveFragment extends Fragment {


  private AnyChartView anyChart;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_weight_curve,container,false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    anyChart = view.findViewById(R.id.any_chart_view);

    Cartesian cartesian =  AnyChart.line();

    cartesian.animation(true);

    cartesian.padding(10d, 20d, 5d, 20d);

    cartesian.crosshair().enabled(true);
    cartesian.crosshair()
      .yLabel(true)
      .yStroke((Stroke) null, null, null, (String) null, (String) null);

    cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

    cartesian.yAxis(0).title("Your Weight (Kg)");
    cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

    ArrayList<DataEntry> dataEntries = new ArrayList<>(MainActivity.getWeightItems());

    Set set = Set.instantiate();
    set.data(dataEntries);
    Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");

//    Line series1 =
//    series1.name("Weight");
//    series1.hovered().markers().enabled(true);
//    series1.hovered().markers()
//      .type(MarkerType.CIRCLE)
//      .size(4d);
//    series1.tooltip()
//      .position("right")
//      .anchor(Anchor.LEFT_CENTER)
//      .offsetX(5d)
//      .offsetY(5d);

    cartesian.line(series1Mapping);
    cartesian.legend().enabled(true);
    cartesian.legend().fontSize(13d);
    cartesian.legend().padding(0d, 0d, 10d, 0d);




    cartesian.data(dataEntries);


    anyChart.setChart(cartesian);
  }
}
