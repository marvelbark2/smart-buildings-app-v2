package edu.episen.si.ing1.pds.backend.server.workspace.cards.models;


import com.fasterxml.jackson.annotation.JsonFormat;
import edu.episen.si.ing1.pds.backend.server.workspace.users.models.UsersResponse;

import java.io.Serializable;
import java.time.LocalDate;

public class CardsResponse implements Serializable {
    private Long cardId;
    private String cardUId;
    private boolean expirable;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate expiredDate;
    private UsersResponse user;
    private boolean active;

    public CardsResponse(Cards card) {
        this.cardId = card.getCardId();
        this.cardUId = card.getCardUId();
        this.expirable = card.isExpirable();
        this.expiredDate = card.getExpiredDate();
        this.user = new UsersResponse(card.getUser());
        this.active = card.isActive();
    }

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

    public UsersResponse getUser() {
        return user;
    }

    public void setUser(UsersResponse user) {
        this.user = user;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "CardsResponse{" +
                "cardId=" + cardId +
                ", cardUId='" + cardUId + '\'' +
                ", expirable=" + expirable +
                ", expiredDate=" + expiredDate +
                ", active=" + active +
                '}';
    }
}
