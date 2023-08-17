package com.financialtransactionsapi.services;

import com.financialtransactionsapi.domain.transaction.Transaction;
import com.financialtransactionsapi.domain.user.User;
import com.financialtransactionsapi.dtos.TransactionDTO;
import com.financialtransactionsapi.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TransactionService {
  @Autowired
  private UserService userService;
  @Autowired
  private TransactionRepository repository;
  @Autowired
  private RestTemplate restTemplate;
  
  public Transaction createTransaction(TransactionDTO data) throws Exception {
    User sender = this.userService.findById(data.senderId());
    User receiver = this.userService.findById(data.receiverId());
    
    userService.validateTransaction(sender, data.value());
    
    boolean isAuthorized = this.authorizeTransaction(sender, data.value());
    if(!isAuthorized) {
      throw new Exception("Transaction not authorized");
    }
    
    Transaction transaction = new Transaction();
    transaction.setAmount(data.value());
    transaction.setSender(sender);
    transaction.setReceiver(receiver);
    transaction.setTimestamp(LocalDateTime.now());
    
    sender.setBalance(sender.getBalance().subtract(data.value()));
    receiver.setBalance(receiver.getBalance().add(data.value()));
    
    this.repository.save(transaction);
    this.userService.save(sender);
    this.userService.save(receiver);
    
    return transaction;
  }
  
  public boolean authorizeTransaction(User sender, BigDecimal value) {
   ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity("https://run.mocky.io/v3/8fafdd68-a090-496f-8c9a-3442cf30dae6", Map.class);
   
   if (authorizationResponse.getStatusCode() == HttpStatus.OK) {
    String message = (String) authorizationResponse.getBody().get("message");
     String AUTHORIZED_STATUS = "Autorizado";
     return AUTHORIZED_STATUS.equalsIgnoreCase(message);
   } else return false;
  }
}
