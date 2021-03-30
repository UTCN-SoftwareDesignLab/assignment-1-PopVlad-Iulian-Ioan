package repository.clientInfo;

import model.ClientInfo;
import repository.EntityNotFoundException;

import java.util.List;

public interface ClientInfoRepository {
    List<ClientInfo> findAll();

    ClientInfo findById(Long id) throws EntityNotFoundException;

    boolean save(ClientInfo clientInfo);

    boolean update(ClientInfo clientInfo);

    void removeAll();
}
