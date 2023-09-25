package com.revature.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.dtos.ImagePostDTO;
import com.revature.exceptions.ImageNotFoundException;
import com.revature.models.Image;
import com.revature.repositories.ImageRepository;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public Image getImage(int id) throws ImageNotFoundException {
        Optional<Image> optionalImage = imageRepository.findById(id);
        
        if (optionalImage.isEmpty()) 
            throw new ImageNotFoundException("An image with the " + id + " id has not been found");

        return optionalImage.get();
    }

    public Image uploadImage(ImagePostDTO imagePost) {
        Image image = new Image();

        image.setType(imagePost.getType());
        image.setContent(imagePost.getContent());

        return imageRepository.save(image);
    }
    
}
