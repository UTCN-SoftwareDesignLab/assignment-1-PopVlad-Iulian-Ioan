package service.employee;

import model.ClientAccount;
import model.ClientInfo;
import repository.EntityNotFoundException;

import java.util.List;

public interface EmployeeService {
    public boolean addClient(ClientInfo clientInfo);
    public boolean updateClient(ClientInfo clientInfo);
    ClientInfo findByCNP(ClientInfo clientInfo) throws EntityNotFoundException;
    ClientAccount findByIdCard(Long idCard) throws EntityNotFoundException;
    public List<ClientInfo>viewClients();
    public boolean addAccount(ClientAccount clientAccount);
    public boolean updateAccount(ClientAccount clientAccount);
    public boolean deleteAccount(ClientAccount clientAccount);
    public List<ClientAccount>viewAccounts();
    public boolean transferMoney(ClientAccount from,ClientAccount to,long amount);

}
