package com.projectment.security;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "token")
@NoArgsConstructor
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String value;

    private int subjectId;

    private long expiresAt;

    private boolean isRevoked = false;

    public Token(String value, int subjectId, long expiresAt) {
        this.value = value;
        this.subjectId = subjectId;
        this.expiresAt = expiresAt;
    }

    public long getExpirationDate() {
        return expiresAt;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > expiresAt;
    }

    @Override
    public String toString() {
        return "Token{" +
                "value='" + value + '\'' +
                ", subjectId=" + subjectId +
                ", expiresAt=" + expiresAt +
                ", isRevoked=" + isRevoked +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return id == token.id && subjectId == token.subjectId && expiresAt == token.expiresAt && Objects.equals(value, token.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, subjectId, expiresAt);
    }

}
