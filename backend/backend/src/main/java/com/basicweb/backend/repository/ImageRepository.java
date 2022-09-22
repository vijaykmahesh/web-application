package com.basicweb.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.basicweb.backend.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
