package ir.ttic.footweight.model;

public class Track {

  private final double longitude , latitude ,speed;
  private final String user;
  private final long id;
  private String date;

  public Track(long id,String user,double longitude, double latitude, double speed) {
    this.longitude = longitude;
    this.latitude = latitude;
    this.speed = speed;
    this.user = user;
    this.id = id;
  }

  public double getLongitude() {
    return longitude;
  }

  public double getLatitude() {
    return latitude;
  }

  public double getSpeed() {
    return speed ;
  }

  public String getDate() {
    return date;
  }

  public Track setDate(String date) {
    this.date = date;
    return this;
  }

  public String getUser(){
    return user;
  }

  public long getId() {
    return id;
  }
}
