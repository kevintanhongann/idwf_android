package org.idwfed.app.response;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateWccDocResponse {

    @Expose
    private String title;
    @Expose
    private String effective;
    @Expose
    private String expires;
    @Expose
    private String description;
    @Expose
    private List<String> subjects = new ArrayList<String>();
    @SerializedName("document_type")
    @Expose
    private String documentType;
    @SerializedName("document_owner")
    @Expose
    private String documentOwner;
    @Expose
    private String text;
    @SerializedName("related_links")
    @Expose
    private List<RelatedLink> relatedLinks = new ArrayList<RelatedLink>();
    @Expose
    private File file;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEffective() {
        return effective;
    }

    public void setEffective(String effective) {
        this.effective = effective;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentOwner() {
        return documentOwner;
    }

    public void setDocumentOwner(String documentOwner) {
        this.documentOwner = documentOwner;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<RelatedLink> getRelatedLinks() {
        return relatedLinks;
    }

    public void setRelatedLinks(List<RelatedLink> relatedLinks) {
        this.relatedLinks = relatedLinks;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "CreateWccDocResponse{" +
                "title='" + title + '\'' +
                ", effective='" + effective + '\'' +
                ", expires='" + expires + '\'' +
                ", description='" + description + '\'' +
                ", subjects=" + subjects +
                ", documentType='" + documentType + '\'' +
                ", documentOwner='" + documentOwner + '\'' +
                ", text='" + text + '\'' +
                ", relatedLinks=" + relatedLinks +
                ", file=" + file +
                '}';
    }

    public class File {

        @Expose
        private String filename;
        @Expose
        private String data;

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "File{" +
                    "filename='" + filename + '\'' +
                    ", data='" + data + '\'' +
                    '}';
        }
    }

    public class RelatedLink {

        @Expose
        private String url;
        @Expose
        private String description;
        @Expose
        private String label;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return "RelatedLink{" +
                    "url='" + url + '\'' +
                    ", description='" + description + '\'' +
                    ", label='" + label + '\'' +
                    '}';
        }
    }

}



