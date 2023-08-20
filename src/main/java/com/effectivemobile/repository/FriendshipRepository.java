package com.effectivemobile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.effectivemobile.entity.Friendship;

import java.util.List;

public interface FriendshipRepository extends JpaRepository<Friendship, Integer> {

    List<Friendship> findAllByUser_Login(String username);

    Boolean existsFriendshipByUser_IdAndUserFriend_Id(Long id, Long friendId);

    void deleteByUser_IdAndUserFriend_Id(Long id, Long friendId);
}
