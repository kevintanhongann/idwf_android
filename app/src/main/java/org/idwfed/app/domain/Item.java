package org.idwfed.app.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevintanhongann on 9/15/14.
 */
public class Item {

    @SerializedName("api_url")
    @Expose
    private String apiUrl;
    @Expose
    private String effective;
    @Expose
    private List<Object> tags = new ArrayList<Object>();
    @Expose
    private String url;
    @SerializedName("portal_type")
    @Expose
    private String portalType;
    @Expose
    private String description;
    @Expose
    private String modified;
    @Expose
    private String created;
    @Expose
    private String title;
    @Expose
    private String type;
    @Expose
    private String id;
    @Expose
    private String uid;

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getEffective() {
        return effective;
    }

    public void setEffective(String effective) {
        this.effective = effective;
    }

    public List<Object> getTags() {
        return tags;
    }

    public void setTags(List<Object> tags) {
        this.tags = tags;
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

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
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
                ", effective='" + effective + '\'' +
                ", tags=" + tags +
                ", url='" + url + '\'' +
                ", portalType='" + portalType + '\'' +
                ", description='" + description + '\'' +
                ", modified='" + modified + '\'' +
                ", created='" + created + '\'' +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }
}