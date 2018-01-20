package com.tagBuddy.Service;

import com.tagBuddy.Dao.CredentialDao;
import com.tagBuddy.Entity.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by abhinavdas on 1/20/18.
 */
@Service
public class CredentialService {

    @Autowired
    CredentialDao credentialDao;

    public void attemptLogin(Credential input)
    {
        this.credentialDao.attemptLogin(input);
    }
}
