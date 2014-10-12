package org.idwfed.app.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevintanhongann on 10/3/14.
 */
public class ValidatorResponse {

    private String url;

    private Integer count;

    @SerializedName("_runtime")
    private Double runtime;

    @SerializedName("items")
    private List<User> users;


    public String getUrl() {
        return url;
    }

    public Integer getCount() {
        return count;
    }

    public Double getRuntime() {
        return runtime;
    }

    public List<User> getUsers() {
        return users;
    }

    @Override
    public String toString() {
        return "ValidatorResponse{" +
                "url='" + url + '\'' +
                ", count=" + count +
                ", runtime=" + runtime +
                ", users=" + users +
                '}';
    }

    public class User {

        @Expose
        private String username;
        @Expose
        private Boolean authenticated;
        @SerializedName("last_login_time")
        @Expose
        private String lastLoginTime;
        @Expose
        private List<String> roles = new ArrayList<String>();
        @Expose
        private String url;
        @Expose
        private String email;
        @Expose
        private List<String> groups = new ArrayList<String>();
        @Expose
        private String fullname;
        @Expose
        private Integer id;
        @SerializedName("login_time")
        @Expose
        private String loginTime;

        public String getUsername() {
            return username;
        }

        public Boolean getAuthenticated() {
            return authenticated;
        }

        public String getLastLoginTime() {
            return lastLoginTime;
        }

        public List<String> getRoles() {
            return roles;
        }

        public String getUrl() {
            return url;
        }

        public String getEmail() {
            return email;
        }

        public List<String> getGroups() {
            return groups;
        }

        public String getFullname() {
            return fullname;
        }

        public Integer getId() {
            return id;
        }

        public String getLoginTime() {
            return loginTime;
        }

        @Override
        public String toString() {
            return "User{" +
                    "username='" + username + '\'' +
                    ", authenticated=" + authenticated +
                    ", lastLoginTime='" + lastLoginTime + '\'' +
                    ", roles=" + roles +
                    ", url='" + url + '\'' +
                    ", email='" + email + '\'' +
                    ", groups=" + groups +
                    ", fullname='" + fullname + '\'' +
                    ", id=" + id +
                    ", loginTime='" + loginTime + '\'' +
                    '}';
        }
    }

}
