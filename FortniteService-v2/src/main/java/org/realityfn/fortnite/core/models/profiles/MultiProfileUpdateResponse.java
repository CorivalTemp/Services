package org.realityfn.fortnite.core.models.profiles;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MultiProfileUpdateResponse {
    public List<ProfileChangeRequest> profileChanges;
    public int profileChangesBaseRevision;
    public int profileCommandRevision;
    public String profileId;
    public int profileRevision;

    public MultiProfileUpdateResponse() {}

    public MultiProfileUpdateResponse(List<ProfileChangeRequest> profileChanges,
                                      int profileChangesBaseRevision,
                                      int profileCommandRevision,
                                      String profileId,
                                      int profileRevision) {
        this.profileChanges = profileChanges;
        this.profileChangesBaseRevision = profileChangesBaseRevision;
        this.profileCommandRevision = profileCommandRevision;
        this.profileId = profileId;
        this.profileRevision = profileRevision;
    }

    // Builder pattern for easier construction
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<ProfileChangeRequest> profileChanges;
        private int profileChangesBaseRevision;
        private int profileCommandRevision;
        private String profileId;
        private int profileRevision;

        public Builder profileChanges(List<ProfileChangeRequest> profileChanges) {
            this.profileChanges = profileChanges;
            return this;
        }

        public Builder profileChangesBaseRevision(int profileChangesBaseRevision) {
            this.profileChangesBaseRevision = profileChangesBaseRevision;
            return this;
        }

        public Builder profileCommandRevision(int profileCommandRevision) {
            this.profileCommandRevision = profileCommandRevision;
            return this;
        }

        public Builder profileId(String profileId) {
            this.profileId = profileId;
            return this;
        }

        public Builder profileRevision(int profileRevision) {
            this.profileRevision = profileRevision;
            return this;
        }

        public MultiProfileUpdateResponse build() {
            return new MultiProfileUpdateResponse(
                    profileChanges,
                    profileChangesBaseRevision,
                    profileCommandRevision,
                    profileId,
                    profileRevision
            );
        }
    }
}