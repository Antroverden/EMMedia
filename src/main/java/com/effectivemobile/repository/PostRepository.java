package com.effectivemobile.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.effectivemobile.entity.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByUser_IdIn(List<Long> users, Pageable pageable);

    List<Post> findAllByUser_Id(Long userId, Pageable pageable);
}
