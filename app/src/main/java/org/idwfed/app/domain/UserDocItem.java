package org.idwfed.app.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevintanhongann on 9/26/14.
 */
public class UserDocItem {


    @SerializedName("api_url")
    @Expose
    private String apiUrl;
    @SerializedName("modification_date")
    @Expose
    private String modificationDate;
    @Expose
    private String text;
    @Expose
    private List<Object> contributors = new ArrayList<Object>();
    @Expose
    private Object expirationDate;
    @Expose
    private Boolean allowDiscussion;
    @SerializedName("parent_uid")
    @Expose
    private String parentUid;
    @Expose
    private String location;
    @Expose
    private List<Object> relatedItems = new ArrayList<Object>();
    @Expose
    private List<Object> subject = new ArrayList<Object>();
    @Expose
    private String rights;
    @SerializedName("parent_url")
    @Expose
    private String parentUrl;
    @Expose
    private List<String> creators = new ArrayList<String>();
    @Expose
    private String id;
    @SerializedName("creation_date")
    @Expose
    private String creationDate;
    @Expose
    private String title;
    @SerializedName("workflow_info")
    @Expose
    private WorkflowInfo workflowInfo;
    @Expose
    private Boolean presentation;
    @Expose
    private String description;
    @Expose
    private Boolean tableContents;
    @Expose
    private String language;
    @Expose
    private Object effectiveDate;
    @Expose
    private Boolean excludeFromNav;
    @SerializedName("parent_id")
    @Expose
    private String parentId;

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(String modificationDate) {
        this.modificationDate = modificationDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Object> getContributors() {
        return contributors;
    }

    public void setContributors(List<Object> contributors) {
        this.contributors = contributors;
    }

    public Object getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Object expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Boolean getAllowDiscussion() {
        return allowDiscussion;
    }

    public void setAllowDiscussion(Boolean allowDiscussion) {
        this.allowDiscussion = allowDiscussion;
    }

    public String getParentUid() {
        return parentUid;
    }

    public void setParentUid(String parentUid) {
        this.parentUid = parentUid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Object> getRelatedItems() {
        return relatedItems;
    }

    public void setRelatedItems(List<Object> relatedItems) {
        this.relatedItems = relatedItems;
    }

    public List<Object> getSubject() {
        return subject;
    }

    public void setSubject(List<Object> subject) {
        this.subject = subject;
    }

    public String getRights() {
        return rights;
    }

    public void setRights(String rights) {
        this.rights = rights;
    }

    public String getParentUrl() {
        return parentUrl;
    }

    public void setParentUrl(String parentUrl) {
        this.parentUrl = parentUrl;
    }

    public List<String> getCreators() {
        return creators;
    }

    public void setCreators(List<String> creators) {
        this.creators = creators;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public WorkflowInfo getWorkflowInfo() {
        return workflowInfo;
    }

    public void setWorkflowInfo(WorkflowInfo workflowInfo) {
        this.workflowInfo = workflowInfo;
    }

    public Boolean getPresentation() {
        return presentation;
    }

    public void setPresentation(Boolean presentation) {
        this.presentation = presentation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getTableContents() {
        return tableContents;
    }

    public void setTableContents(Boolean tableContents) {
        this.tableContents = tableContents;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Object getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Object effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Boolean getExcludeFromNav() {
        return excludeFromNav;
    }

    public void setExcludeFromNav(Boolean excludeFromNav) {
        this.excludeFromNav = excludeFromNav;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }


    @Override
    public String toString() {
        return "UserDocItem{" +
                "apiUrl='" + apiUrl + '\'' +
                ", modificationDate='" + modificationDate + '\'' +
                ", text='" + text + '\'' +
                ", contributors=" + contributors +
                ", expirationDate=" + expirationDate +
                ", allowDiscussion=" + allowDiscussion +
                ", parentUid='" + parentUid + '\'' +
                ", location='" + location + '\'' +
                ", relatedItems=" + relatedItems +
                ", subject=" + subject +
                ", rights='" + rights + '\'' +
                ", parentUrl='" + parentUrl + '\'' +
                ", creators=" + creators +
                ", id='" + id + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", title='" + title + '\'' +
                ", workflowInfo=" + workflowInfo +
                ", presentation=" + presentation +
                ", description='" + description + '\'' +
                ", tableContents=" + tableContents +
                ", language='" + language + '\'' +
                ", effectiveDate=" + effectiveDate +
                ", excludeFromNav=" + excludeFromNav +
                ", parentId='" + parentId + '\'' +
                '}';
    }

    public class WorkflowInfo {
        @Expose
        private String workflow;
        @Expose
        private List<Transition> transitions = new ArrayList<Transition>();
        @SerializedName("review_state")
        @Expose
        private String reviewState;
        @Expose
        private String status;

        public String getWorkflow() {
            return workflow;
        }

        public void setWorkflow(String workflow) {
            this.workflow = workflow;
        }

        public List<Transition> getTransitions() {
            return transitions;
        }

        public void setTransitions(List<Transition> transitions) {
            this.transitions = transitions;
        }

        public String getReviewState() {
            return reviewState;
        }

        public void setReviewState(String reviewState) {
            this.reviewState = reviewState;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "WorkflowInfo{" +
                    "workflow='" + workflow + '\'' +
                    ", transitions=" + transitions +
                    ", reviewState='" + reviewState + '\'' +
                    ", status='" + status + '\'' +
                    '}';
        }
    }

    public class Transition {

        @Expose
        private String value;
        @Expose
        private String display;
        @Expose
        private String url;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "Transition{" +
                    "value='" + value + '\'' +
                    ", display='" + display + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }
}
