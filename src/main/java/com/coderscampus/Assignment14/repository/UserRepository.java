package com.coderscampus.Assignment14.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.coderscampus.Assignment14.domain.User;


//UserRepository.java
@Repository
public class UserRepository {
 private Map<String, User> users;

 public UserRepository() {
     users = new HashMap<>();
 }

 public void save(User user) {
     users.put(user.getId(), user);
 }

 public User findById(String id) {
     return users.get(id);
 }

 public boolean existsById(String id) {
     return users.containsKey(id);
 }
 
 public List<User> findAll() {
     return users.values().stream().collect(Collectors.toList());
 }
 
 public User findByName(String name) {
     return users.values().stream()
         .filter(user -> user.getName().equals(name))
         .findFirst()
         .orElse(null);
 }
 
}