package model.builder;

import model.ClientInfo;

public class ClientInfoBuilder {
    private final ClientInfo clientInfo;
    public  ClientInfoBuilder(){
        clientInfo=new ClientInfo ();
    }
    public ClientInfoBuilder setId(Long id) {
        clientInfo.setId ( id );
        return this;
    }

    public ClientInfoBuilder setName(String name) {
       clientInfo.setName ( name );
        return this;
    }
    public ClientInfoBuilder setAddress(String address) {
        clientInfo.setAddress ( address );
        return this;
    }
    public ClientInfoBuilder setCnp(String cnp) {
        clientInfo.setCnp ( cnp );
        return this;
    }
    public ClientInfo build() {
        return clientInfo;
    }
}
