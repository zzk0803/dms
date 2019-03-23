package zzk.project.dms.domain.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zzk.project.dms.domain.DormitoryManageException;
import zzk.project.dms.domain.dao.AccountRepository;
import zzk.project.dms.domain.entities.Account;
import zzk.project.dms.domain.services.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account register(Account newAccount) {
        if (accountRepository.existsAccountByUsername(newAccount.getUsername())) {
            throw new DormitoryManageException("用户名已存在");
        }

        return accountRepository.save(newAccount);
    }

    @Override
    public Account login(String username, String password) {
        return accountRepository.findFirstByUsernameAndPassword(username, password);
    }
}
