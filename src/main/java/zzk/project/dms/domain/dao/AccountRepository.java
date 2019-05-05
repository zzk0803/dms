package zzk.project.dms.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zzk.project.dms.domain.entities.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsAccountByUsername(String username);

    Account findFirstByUsernameAndPassword(String username, String password);

}
