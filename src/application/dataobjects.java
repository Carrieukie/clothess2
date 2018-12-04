package application;

public class dataobjects {
	public dataobjects(int id, String name, String phoneno, String clothtype, String measurements, byte[] imagedata) {
		super();
		this.id = id;
		this.name = name;
		this.phoneno = phoneno;
		this.clothtype = clothtype;
		this.measurements = measurements;
		this.imagedata = imagedata;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneno() {
		return phoneno;
	}
	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}
	public String getClothtype() {
		return clothtype;
	}
	public void setClothtype(String clothtype) {
		this.clothtype = clothtype;
	}
	public String getMeasurements() {
		return measurements;
	}
	public void setMeasurements(String measurements) {
		this.measurements = measurements;
	}
	public byte[] getImagedata() {
		return imagedata;
	}
	public void setImagedata(byte[] imagedata) {
		this.imagedata = imagedata;
	}
	int id;
	String name, phoneno ,clothtype , measurements;
	byte[] imagedata;

}
