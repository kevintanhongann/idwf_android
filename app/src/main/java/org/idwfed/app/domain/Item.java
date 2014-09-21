package org.idwfed.app.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by kevintanhongann on 9/15/14.
 */
public class Item {

    @SerializedName("api_url")
    private String apiUrl;

    @SerializedName("effective")
    private Date effective;

    private String url;

    @Expose
    private List<String> tags = new ArrayList<String>();

    @SerializedName("portal_type")
    private String portalType;

    private String description;

    @SerializedName("modified")
    private Date modified;

    @SerializedName("created")
    private Date created;

    private String title;

    private String type;

    private String id;

    private String uid;

    public List<String> getTags() {
        return tags;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public Date getEffective() {
        return effective;
    }

    public void setEffective(Date effective) {
        this.effective = effective;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPortalType() {
        return portalType;
    }

    public void setPortalType(String portalType) {
        this.portalType = portalType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "Item{" +
                "apiUrl='" + apiUrl + '\'' +
                ", effective=" + effective +
                ", url='" + url + '\'' +
                ", tags=" + tags +
                ", portalType='" + portalType + '\'' +
                ", description='" + description + '\'' +
                ", modified=" + modified +
                ", created=" + created +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }
}