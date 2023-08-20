package com.effectivemobile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.effectivemobile.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Integer> {
}
