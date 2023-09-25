package com.revature.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.models.Image;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    
}
