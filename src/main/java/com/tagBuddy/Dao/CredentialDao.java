package com.tagBuddy.Dao;

import com.tagBuddy.Entity.Credential;
import org.springframework.stereotype.Repository;

/**
 * Created by abhinavdas on 1/20/18.
 */
@Repository
public class CredentialDao {

    public void attemptLogin(Credential input)
    {
        System.out.println(input.getUsername());
    }
}
