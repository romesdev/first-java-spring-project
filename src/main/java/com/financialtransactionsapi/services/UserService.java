package com.financialtransactionsapi.services;

import com.financialtransactionsapi.domain.user.User;
import com.financialtransactionsapi.domain.user.UserType;
import com.financialtransactionsapi.dtos.UserDTO;
import com.financialtransactionsapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {
  @Autowired
  private UserRepository repository;
  
  public void validateTransaction(User sender, BigDecimal amount) throws Exception {
    if(sender.getUserType() == UserType.MERCHANT) {
      throw new Exception("User is not authorized to make transactions");
    }
    if(sender.getBalance().compareTo(amount) < 0) {
      throw new Exception("User does not have enough balance");
    }
  }
  
  public User create(UserDTO data) {
    User user = new User(data);
    this.save(user);
    return user;
  }
  
  public User findById(Long id) throws Exception{
    return this.repository.findByUserId(id).orElseThrow(() -> new Exception("User not found"));
  }
  
  public List<User> getAll() {
    return this.repository.findAll();
  }
  
  public void save(User user) {
    this.repository.save(user);
  }
}
