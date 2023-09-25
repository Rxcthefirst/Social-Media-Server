package com.revature.dtos;

import com.revature.models.Profile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProfileWorkDTO {
    private String jobTitle;

    private String companyName;
    private String companyUrl;

    public ProfileWorkDTO(Profile profile) {
        this.setJobTitle(profile.getJobTitle());
        this.setCompanyName(profile.getCompanyName());
        this.setCompanyUrl(profile.getCompanyUrl());
    }

}
