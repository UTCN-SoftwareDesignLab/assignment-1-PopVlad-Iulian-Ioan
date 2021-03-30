package model.builder;

import model.ClientAccount;

import java.time.LocalDate;
import java.util.Date;

public class ClientAccountBuilder {
    private final ClientAccount clientAccount;

    public ClientAccountBuilder(){
        clientAccount=new ClientAccount ();
    }
    public ClientAccountBuilder setId(Long id){
        clientAccount.setId ( id );
        return this;
    }
    public ClientAccountBuilder setIdClient(Long idClient){
        clientAccount.setIdClient ( idClient );
        return this;
    }
    public ClientAccountBuilder setType(String type) {
        clientAccount.setType ( type );
        return this;
    }

    public ClientAccountBuilder setMoneyAmount(Long moneyAmount){
        clientAccount.setMoneyAmount ( moneyAmount );
        return this;
    }
    public ClientAccountBuilder setCreationDate(Date creationDate){
        clientAccount.setCreationDate ( creationDate );
        return this;
    }
    public ClientAccount build(){
        return clientAccount;
    }
}
