package org.idwfed.app.domain;

import java.util.Arrays;

/**
 * Created by kevintanhongann on 10/2/14.
 */
public class Image {

    private int size;

    private String base64Encode;

    private String contentType;

    public Image(int size, String base64Encode, String contentType, String caption) {
        this.size = size;
        this.base64Encode = base64Encode;
        this.contentType = contentType;
        this.caption = caption;
    }

    public String getContentType() {
        return contentType;
    }

    private String caption;

    public String getCaption() {
        return caption;
    }

    public int getSize() {
        return size;
    }

    public String getBase64Encode() {
        return base64Encode;
    }

    @Override
    public String toString() {
        return "Image{" +
                "size=" + size +
                ", base64Encode=" + base64Encode +
                ", contentType='" + contentType + '\'' +
                ", caption='" + caption + '\'' +
                '}';
    }
}
