package org.idwfed.app.response;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.idwfed.app.domain.Item;
import org.idwfed.app.domain.UserDocItem;

public class CreateWccDocResponse {

    @Expose
    private String error;
    @Expose
    private String message;
    @Expose
    private Boolean success;
    @SerializedName("_runtime")
    @Expose
    private Double runtime;

    @Expose
    private Integer count;
    @Expose
    private List<UserDocItem> items = new ArrayList<UserDocItem>();


    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public Double getRuntime() {
        return runtime;
    }

    public Integer getCount() {
        return count;
    }

    public List<UserDocItem> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "CreateWccDocResponse{" +
                "error='" + error + '\'' +
                ", message='" + message + '\'' +
                ", success=" + success +
                ", runtime=" + runtime +
                ", count=" + count +
                ", items=" + items +
                '}';
    }
}



