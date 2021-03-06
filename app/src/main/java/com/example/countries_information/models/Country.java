package com.example.countries_information.models;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Country implements Serializable {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("borders")
    @Expose
    private List<String> borders = null;

    @SerializedName("nativeName")
    @Expose
    private String nativeName;

    @SerializedName("alpha3Code")
    @Expose
    private String alpha3Code;

    @SerializedName("area")
    @Expose
    private String area;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getBorders() {
        return borders;
    }

    public void setBorders(List<String> borders) {
        this.borders = borders;
    }

    public String getNativeName() {
        return nativeName;
    }

    public void setNativeName(String nativeName) {
        this.nativeName = nativeName;
    }

    public String getAlpha3Code() {
        return alpha3Code;
    }

    public void setAlpha3Code(String alpha3Code) {
        this.alpha3Code = alpha3Code;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
