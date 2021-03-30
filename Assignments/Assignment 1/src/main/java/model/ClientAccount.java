package model;

import java.util.Date;
import java.time.LocalDate;

public class ClientAccount {
    private Long id;
    private Long idClient;
    private Long idCard;
    private String type;
    private Long moneyAmount;
    private Date creationDate;

    public Long getIdCard() {
        return idCard;
    }

    public void setIdCard(Long idCard) {
        this.idCard = idCard;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public Long getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(Long moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public String toString() {
        return "ClientAccount{" +
                "id=" + id +
                ", idClient=" + idClient +
                ", idCard=" + idCard +
                ", type='" + type + '\'' +
                ", moneyAmount=" + moneyAmount +
                ", creationDate=" + creationDate +
                '}';
    }
}
