
package com.mouritech.healthapp.model.fitbitmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HeartRateZone {

    @SerializedName("max")
    @Expose
    private Integer max;
    @SerializedName("min")
    @Expose
    private Integer min;
    @SerializedName("name")
    @Expose
    private String name;

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
