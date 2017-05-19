package models;

import javax.persistence.MappedSuperclass;

/**
 * Created by John V on 5/15/2017.
 */

@MappedSuperclass
public class UserLoginForm
{
    public String username;
    public String password;
}
