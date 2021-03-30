package service.employee;

import model.ClientAccount;
import model.ClientInfo;
import repository.EntityNotFoundException;
import repository.clientAccount.ClientAccountRepository;
import repository.clientAccount.ClientAccountRepositoryMySQL;
import repository.clientInfo.ClientInfoRepository;
import repository.clientInfo.ClientInfoRepositoryMySQL;

import java.util.List;


public class EmployeeServiceImpl implements EmployeeService{
    private final ClientAccountRepository accountRepository;
    private final ClientInfoRepository clientRepository;

    public EmployeeServiceImpl(ClientAccountRepository accountRepository, ClientInfoRepository clientRepository) {
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public boolean addClient(ClientInfo clientInfo) {
       return clientRepository.save ( clientInfo );
    }

    @Override
    public boolean updateClient(ClientInfo clientInfo) {
        return clientRepository.update ( clientInfo );
    }

    @Override
    public ClientInfo findByCNP(ClientInfo clientInfo) throws EntityNotFoundException {
        return clientRepository.findByCNP ( clientInfo.getCnp () );
    }

    @Override
    public ClientAccount findByIdCard(Long idCard) throws EntityNotFoundException {
        return accountRepository.findByIdCard ( idCard );
    }

    @Override
    public List<ClientInfo> viewClients() {
        return clientRepository.findAll ();
    }

    @Override
    public boolean addAccount(ClientAccount clientAccount) {
        return accountRepository.save ( clientAccount );
    }

    @Override
    public boolean updateAccount(ClientAccount clientAccount) {
        return accountRepository.update ( clientAccount );
    }

    @Override
    public boolean deleteAccount(ClientAccount clientAccount) {
        return accountRepository.delete ( clientAccount );
    }

    @Override
    public List<ClientAccount> viewAccounts() {
        return accountRepository.findAll ();
    }

    @Override
    public boolean transferMoney(ClientAccount from , ClientAccount to,long amount) {
        if(from.getMoneyAmount ()>=amount){
            from.setMoneyAmount ( from.getMoneyAmount ()-amount );
            to.setMoneyAmount ( to.getMoneyAmount ()+amount );
            accountRepository.update ( from );
            accountRepository.update ( to );
            return true;
        }
        else
            return false;
    }
}
