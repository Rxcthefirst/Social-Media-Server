package com.revature.dtos;

import com.revature.models.Profile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProfileEducationDTO {
    private String schoolName;

    public ProfileEducationDTO(Profile profile) {
        this.setSchoolName(profile.getSchoolName());
    }

}
