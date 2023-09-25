package com.revature.dtos;

import com.revature.models.Profile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProfileMaritalStatusDTO {
    private String maritalStatus;
    

    public ProfileMaritalStatusDTO(Profile profile) {
        this.setMaritalStatus(profile.getMaritalStatus());
    }
}
