package com.revature.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.exceptions.NotUsersFoundException;
import com.revature.models.Profile;
import com.revature.models.User;
import com.revature.repositories.ProfileRepository;
import com.revature.repositories.UserRepository;

@Service
@AllowSysOut
public class SearchService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Allows to get specified amount of profiles from the DB in <b>inefficient way</b>. 
     */
    public List<Profile> getProfiles(long limitResponseLength) {
        List<Profile> profiles = new LinkedList<>();
        long countedProfiles = profileRepository.count();

        int[] ints = new Random()
                                .ints(1, ((int) countedProfiles) + 1 )
                                .distinct()
                                .limit(limitResponseLength > countedProfiles ? countedProfiles : limitResponseLength)
                                .toArray();
   
        for (int i : ints) 
            profiles.add(profileRepository.findById(i).get());

        return profiles;
    }


    /**
     * Allows to retrieve specific profiles from the DB in <b>inefficient way</b>. 
     * @throws NotUsersFoundException
     */
    public List<Profile> getSpecificProfiles(String firstName, String lastName) throws NotUsersFoundException {
        Optional<List<User>> optionalUsers = null;

        if (firstName != null && lastName != null)
            optionalUsers = userRepository.findAllMatchesByFirstAndLastNames(firstName, lastName);
        else if (firstName != null && lastName == null)
            optionalUsers = userRepository.findAllMatchesByEitherFirstOrLastName(firstName);
        else if (firstName == null && lastName != null)
            optionalUsers = userRepository.findAllMatchesByEitherFirstOrLastName(lastName);
        else return null;

        List<Profile> profiles = new LinkedList<>();

        if(optionalUsers.isEmpty())
            throw new NotUsersFoundException("Not found users with " + firstName + " " + lastName + " name");
        
        for (User user : optionalUsers.get())
            profiles.add(profileRepository.findByOwner(user).get());

        return profiles;
    }
}
