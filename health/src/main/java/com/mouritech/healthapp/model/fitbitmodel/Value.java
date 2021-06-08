
package com.mouritech.healthapp.model.fitbitmodel;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Value {

    @SerializedName("customHeartRateZones")
    @Expose
    private List<Object> customHeartRateZones = null;
    @SerializedName("heartRateZones")
    @Expose
    private List<HeartRateZone> heartRateZones = null;

    public List<Object> getCustomHeartRateZones() {
        return customHeartRateZones;
    }

    public void setCustomHeartRateZones(List<Object> customHeartRateZones) {
        this.customHeartRateZones = customHeartRateZones;
    }

    public List<HeartRateZone> getHeartRateZones() {
        return heartRateZones;
    }

    public void setHeartRateZones(List<HeartRateZone> heartRateZones) {
        this.heartRateZones = heartRateZones;
    }

}
