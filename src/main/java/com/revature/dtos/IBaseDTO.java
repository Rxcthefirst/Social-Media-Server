package com.revature.dtos;

import com.revature.models.Profile;

public interface IBaseDTO <T> {
    T getInstanceFromProfile(Profile profile);
}
