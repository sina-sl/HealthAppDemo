package ir.ttic.footweight.model;

import java.util.Date;
import java.util.Objects;

public class WeightModel {

  private final Long date;
  private final double weight;

  public WeightModel(Long date, double weight) {
    this.date = date;
    this.weight = weight;
  }

  public Long getDate() {
    return date;
  }

  public double getWeight() {
    return weight;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    WeightModel that = (WeightModel) o;
    return Double.compare(that.weight, weight) == 0 && date.equals(that.date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(date, weight);
  }
}
