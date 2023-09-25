package com.revature.services;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.dtos.SubscriptionDTO;
import com.revature.exceptions.NoSuchSubscriberException;
import com.revature.exceptions.ProfileAlreadySubscribedException;
import com.revature.exceptions.ProfileNotFoundException;
import com.revature.exceptions.SelfSubscriptionException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Profile;
import com.revature.models.User;
import com.revature.repositories.ProfileRepository;

@Service
public class SubscriptionService {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ProfileRepository profileRepository;
    
    public Profile patchSubscription(SubscriptionDTO subscriptionDTO, User sessionUser) throws ProfileNotFoundException, UserNotFoundException, ProfileAlreadySubscribedException, SelfSubscriptionException {
        Profile profile = profileService.getProfileByUser(sessionUser);

        LinkedList<Integer> subscriptionIds = profile.getSubscriptionIds() != null ? profile.getSubscriptionIds() : new LinkedList<>();

        Integer idToSubscribe = subscriptionDTO.getId();

        if (subscriptionDTO.getId() == profile.getId())
            throw new SelfSubscriptionException("You cannot subscribe to yourself.");
        
        if (subscriptionIds.contains(idToSubscribe))
            throw new ProfileAlreadySubscribedException("The profile already subscribed to this profile.");
        
        subscriptionIds.add(idToSubscribe);
        profile.setSubscriptionIds(subscriptionIds);

        return profileRepository.save(profile);
    }

    public Profile patchUnsubscribe(SubscriptionDTO subscriptionDTO, User sessionUser) throws ProfileNotFoundException, UserNotFoundException, NoSuchSubscriberException {
        Profile profile = profileService.getProfileByUser(sessionUser);

        LinkedList<Integer> subscriptionIds = profile.getSubscriptionIds() != null ? profile.getSubscriptionIds() : new LinkedList<>();

        Integer idToUnsubscribe = subscriptionDTO.getId();

        if (!(subscriptionIds.contains(idToUnsubscribe))) 
            throw new NoSuchSubscriberException("Cannot unsubscribe from a user to which you are not subscribed");

        subscriptionIds.remove(idToUnsubscribe);
        profile.setSubscriptionIds(subscriptionIds);


        return profileRepository.save(profile);
    }
}
