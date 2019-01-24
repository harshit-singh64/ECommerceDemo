package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;

@Repository
public interface IUserRepo extends JpaRepository<User, Integer> {
	//Optional<User> findByEmail(String userName);
	//User findByEmailAndPassword(String username, String password);
	User findByEmail(String email);
}