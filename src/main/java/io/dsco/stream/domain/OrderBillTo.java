package io.dsco.stream.domain;


@SuppressWarnings("unused")
public class OrderBillTo {
	//MEMBERS
	private String attention;
	private String firstName;
	private String lastName;
	private String company;
	private String address1;
	private String address2;
	private String city;
	/* The state if in the US otherwise the region if outside */
	private String region;
	/* The zipcode if in the US, the postal code outside of the US */
	private String postal;
	private String country;
	private String phone;
	private String email;

	//CONSTRUCTORS
	public OrderBillTo() {}

	//ACCESSORS / MUTATORS
	public String getAttention() { return attention; }
	public void setAttention(String val) { attention = val; }
	public String getFirstName() { return firstName; }
	public void setFirstName(String val) { firstName = val; }
	public String getLastName() { return lastName; }
	public void setLastName(String val) { lastName = val; }
	public String getCompany() { return company; }
	public void setCompany(String val) { company = val; }
	public String getAddress1() { return address1; }
	public void setAddress1(String val) { address1 = val; }
	public String getAddress2() { return address2; }
	public void setAddress2(String val) { address2 = val; }
	public String getCity() { return city; }
	public void setCity(String val) { city = val; }
	public String getRegion() { return region; }
	public void setRegion(String val) { region = val; }
	public String getPostal() { return postal; }
	public void setPostal(String val) { postal = val; }
	public String getCountry() { return country; }
	public void setCountry(String val) { country = val; }
	public String getPhone() { return phone; }
	public void setPhone(String val) { phone = val; }
	public String getEmail() { return email; }
	public void setEmail(String val) { email = val; }
}