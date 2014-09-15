package org.idwfed.app.response;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PublicDocumentsResponse {

    @Expose
    private String url;
    @Expose
    private Integer count;
    @SerializedName("_runtime")
    @Expose
    private Double runtime;
    @Expose
    private List<org.idwfed.app.domain.Item> items = new ArrayList<org.idwfed.app.domain.Item>();

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getRuntime() {
        return runtime;
    }

    public void setRuntime(Double runtime) {
        this.runtime = runtime;
    }

    public List<org.idwfed.app.domain.Item> getItems() {
        return items;
    }

    public void setItems(List<org.idwfed.app.domain.Item> items) {
        this.items = items;
    }

}