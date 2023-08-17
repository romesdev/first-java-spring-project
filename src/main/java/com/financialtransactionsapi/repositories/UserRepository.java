package com.financialtransactionsapi.repositories;

import com.financialtransactionsapi.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUserDocument(String userDocument);
  Optional<User> findByUserId(Long userId);
}
