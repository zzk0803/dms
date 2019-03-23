package zzk.project.dms.domain.services;

import zzk.project.dms.domain.entities.Account;

public interface AccountService {
    Account register(Account newAccount);
    Account login(String username, String password);
}
