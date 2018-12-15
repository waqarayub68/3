package com.example.waqarayub.taskasyntask;

import java.io.Serializable;

public class ApiInfo implements Serializable{
    String userId;
    String Id;

    public ApiInfo(){}
    public ApiInfo(String userId, String id)
    {
        this.userId = userId;
        this.Id = userId;

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }


}

