package com.scaler.userserviceauth.repositories;

import com.scaler.userserviceauth.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
//    @Override
//    Optional<Token> findByTokenValue(Long aLong);
    Optional<Token> findByTokenValue(String tokenValue);

    Token save(Token token);

    //Validate token
    //check if the token is present in the table and not expired
    //now check if expiry > current time
    //Select * from tokens where token_value = 'abcd' and expiry_date > current_time

//    @Override
    Optional<Token> findByTokenValueAndExpiryAtGreaterThan(String tokenValue, Date expiryDate);

    Optional<Token> findByTokenValueAndUserIdAndExpiryAtGreaterThan(String tokenValue, Long userId, Date expiryDate);
}
