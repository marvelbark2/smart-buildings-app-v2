package edu.episen.si.ing1.pds.backend.server.workspace.cards.card.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.episen.si.ing1.pds.backend.server.workspace.cards.user.models.Users;

import java.time.LocalDate;

/*
* Class Entity
* Could act like DTO to copty props from request class to response class
* */
public class Cards {
    private Long cardId;
    private String cardUId;
    private boolean expirable;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate expiredDate;
    private boolean active;

    private Users user;

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public String getCardUId() {
        return cardUId;
    }

    public void setCardUId(String cardUId) {
        this.cardUId = cardUId;
    }

    public boolean isExpirable() {
        return expirable;
    }

    public void setExpirable(boolean expirable) {
        this.expirable = expirable;
    }

    public LocalDate getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(LocalDate expiredDate) {
        this.expiredDate = expiredDate;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
