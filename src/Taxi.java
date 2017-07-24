import java.util.Date;

public class Taxi {
	private Integer id;
	private Date data;
	private Double latitude;
	private Double longitude;

	public Taxi() {
		
	}
	
	public Taxi(Integer id, Date data, Double latitude, Double longitude) {
		this.id = id;
		this.data = data;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	@Override
	public String toString() {
		return this.getId()+" | "+this.getData()+" | "+this.getLatitude()+" | "+this.getLongitude();
	}
}
