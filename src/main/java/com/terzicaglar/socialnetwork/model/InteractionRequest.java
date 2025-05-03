package com.terzicaglar.socialnetwork.model;

import com.fasterxml.jackson.annotation.JsonProperty;

// This class represents a request for an interaction between two users, such as a visit and like.

public class InteractionRequest {

    @JsonProperty("source_user_id")
    private Long sourceUserId;

    @JsonProperty("target_user_id")
    private Long targetUserId;

    public InteractionRequest() {
    }

    public InteractionRequest(Long sourceUserId, Long targetUserId) {
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
