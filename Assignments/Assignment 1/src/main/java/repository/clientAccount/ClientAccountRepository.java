package repository.clientAccount;

import model.ClientAccount;
import repository.EntityNotFoundException;

import java.util.List;

public interface ClientAccountRepository {
    List<ClientAccount> findAll();

    ClientAccount findById(Long id) throws EntityNotFoundException;

    boolean save(ClientAccount clientAccount);

    boolean delete(ClientAccount clientAccount);

    boolean update(ClientAccount clientAccount);

    void removeAll();
}
