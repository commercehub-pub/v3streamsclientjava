package io.dsco.stream.domain;

import org.jetbrains.annotations.NotNull;

public abstract class Address
{
    private String address1;
    private String city;
    private String firstName;
    private String lastName;
    private String postal;
    private String region;
    private String address2;
    private String attention;
    private String company;
    private String country;
    private String email;
    private String phone;

    public Address() {}

    public Address(
            @NotNull String address1, @NotNull String city, @NotNull String firstName, @NotNull String lastName,
            @NotNull String postal, @NotNull String region, String address2, String attention, String company,
            String country, String email, String phone)
    {
        this.address1 = address1;
        this.city = city;
        this.firstName = firstName;
        this.lastName = lastName;
        this.postal = postal;
        this.region = region;
        this.address2 = address2;
        this.attention = attention;
        this.company = company;
        this.country = country;
        this.email = email;
        this.phone = phone;
    }

    public String getAddress1()
    {
        return address1;
    }

    public void setAddress1(String address1)
    {
        this.address1 = address1;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getPostal()
    {
        return postal;
    }

    public void setPostal(String postal)
    {
        this.postal = postal;
    }

    public String getRegion()
    {
        return region;
    }

    public void setRegion(String region)
    {
        this.region = region;
    }

    public String getAddress2()
    {
        return address2;
    }

    public void setAddress2(String address2)
    {
        this.address2 = address2;
    }

    public String getAttention()
    {
        return attention;
    }

    public void setAttention(String attention)
    {
        this.attention = attention;
    }

    public String getCompany()
    {
        return company;
    }

    public void setCompany(String company)
    {
        this.company = company;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }
}
