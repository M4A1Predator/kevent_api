package com.gamitology.kevent.kevent.repository;

import com.gamitology.kevent.kevent.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    List<User> findByRolesIdIn(List<Integer> roleIds);
}
