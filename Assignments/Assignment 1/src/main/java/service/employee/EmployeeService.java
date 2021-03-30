package service.employee;

import model.ClientAccount;
import model.ClientInfo;

import java.util.List;

public interface EmployeeService {
    public boolean addClient(ClientInfo clientInfo);
    public boolean updateClient(ClientInfo clientInfo);
    public List<ClientInfo>viewClients();
    public boolean addAccount(ClientAccount clientAccount);
    public boolean updateAccount(ClientAccount clientAccount);
    public boolean deleteAccount(ClientAccount clientAccount);
    public List<ClientAccount>viewAccounts();
    public boolean transferMoney(ClientAccount from,ClientAccount to,long amount);
}
