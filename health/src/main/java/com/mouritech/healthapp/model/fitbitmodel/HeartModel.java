
package com.mouritech.healthapp.model.fitbitmodel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HeartModel {

    @SerializedName("activities-heart")
    @Expose
    private List<ActivitiesHeart> activitiesHeart = null;

    public List<ActivitiesHeart> getActivitiesHeart() {
        return activitiesHeart;
    }

    public void setActivitiesHeart(List<ActivitiesHeart> activitiesHeart) {
        this.activitiesHeart = activitiesHeart;
    }

}
