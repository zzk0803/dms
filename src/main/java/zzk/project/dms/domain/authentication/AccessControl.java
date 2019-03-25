package zzk.project.dms.domain.authentication;

import zzk.project.dms.domain.entities.Account;

import java.io.Serializable;

public interface AccessControl extends Serializable {
    Account signIn(String username, String password);

    boolean isUserSignedIn();

    String getPrincipalName();

    void signOut();
}
