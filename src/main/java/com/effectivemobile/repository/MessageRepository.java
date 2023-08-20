package com.effectivemobile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.effectivemobile.entity.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    List<Message> findAllByFirstUser_IdAndSecondUser_Id(Long userId, Long friendId);
}
