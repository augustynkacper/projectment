package com.projectment.repository;

import com.projectment.security.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, String> {

    Optional<List<Token>> findAllBySubjectId(int subjectId);

    Optional<Token> findByValue(String value);
}
