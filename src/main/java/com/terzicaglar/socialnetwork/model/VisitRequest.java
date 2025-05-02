package com.terzicaglar.socialnetwork.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VisitRequest {

    @JsonProperty("source_user_id")
    private Long sourceUserId;

    @JsonProperty("target_user_id")
    private Long targetUserId;

    public VisitRequest() {
    }

    public VisitRequest(Long sourceUserId, Long targetUserId) {
        this.sourceUserId = sourceUserId;
        this.targetUserId = targetUserId;
    }

    public Long getSourceUserId() {
        return sourceUserId;
    }

    public void setSourceUserId(Long sourceUserId) {
        this.sourceUserId = sourceUserId;
    }

    public Long getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(Long targetUserId) {
        this.targetUserId = targetUserId;
    }
}
