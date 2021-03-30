package model.builder;

import DTO.ActivityDTO;
import model.Role;
import model.User;
import repository.security.RightsRolesRepositoryMySQL;

import java.util.Collections;
import java.util.List;

import static database.Constants.Roles.EMPLOYEE;


public class UserBuilder {

    private final User user;

    public UserBuilder() {
        user = new User();
    }

    public UserBuilder setId(Long id){
        user.setId ( id );
        return this;
    }

    public UserBuilder setUsername(String username) {
        user.setUsername(username);
        return this;
    }

    public UserBuilder setPassword(String password) {
        user.setPassword(password);
        return this;
    }

    public UserBuilder setRoles(List<Role> roles) {
        user.setRoles(roles);
        return this;
    }
    public UserBuilder fromActivityDTO(ActivityDTO activityDTO){
        user.setUsername ( activityDTO.getUsername () );
        user.setPassword ( activityDTO.getUsername () );
        return this;
    }

    public User build() {
        return user;
    }

}
