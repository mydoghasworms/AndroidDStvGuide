package net.ceronio.dstvguide.data;

import java.util.Date;

public class ChannelEvent {
    int channel;
    Date date;
    String id;
    Long startTimeRaw;
    Long durationRaw;
    String description;
    String longDescription;
    String rating;

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getStartTimeRaw() {
        return startTimeRaw;
    }

    public void setStartTimeRaw(Long startTimeRaw) {
        this.startTimeRaw = startTimeRaw;
    }

    public Long getDurationRaw() {
        return durationRaw;
    }

    public void setDurationRaw(Long durationRaw) {
        this.durationRaw = durationRaw;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
