package ir.ttic.footweight.model;

import android.annotation.SuppressLint;

import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class WeightModel extends DataEntry {

  Long date;
  double weight;
  @SuppressLint("SimpleDateFormat")
  static final DateFormat df = new SimpleDateFormat("dd\\sMMMMM");


  public WeightModel(Long date, double weight) {
    setValue("x",df.format(new Date(date)));
    setValue("value",weight);
    this.date = date;
    this.weight = weight;
  }

  public Long getDate() {
    return date;
  }

  public Double getWeight() {
    return weight;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    WeightModel that = (WeightModel) o;
    return Double.compare(that.getWeight(),getWeight()) == 0 && getDate().equals(that.getDate());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getDate(), getWeight());
  }
}
