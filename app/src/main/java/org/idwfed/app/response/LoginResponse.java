
package org.idwfed.app.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.idwfed.app.domain.Item;

import java.util.ArrayList;
import java.util.List;


public class LoginResponse {

    /*@Expose
    private String url;*/

    @Expose
    private Integer count;

    @SerializedName("_runtime")
    @Expose
    private Double runtime;

    @Expose
    private List<Item> items = new ArrayList<Item>();

    /*public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }*/

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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                //"url='" + url + '\'' +
                ", count=" + count +
                ", runtime=" + runtime +
                ", items=" + items +
                '}';
    }
}
