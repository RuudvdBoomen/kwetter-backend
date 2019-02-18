/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import dao.JPA;
import dao.UserDao;
import domain.User;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Singleton
@Startup
public class StartUp {

    @Inject
    @JPA
    UserDao userDoa;

    public StartUp() {
    }

    @PostConstruct
    private void intData() {
        userDoa.addUser(new User("Ruud"));
        userDoa.addUser(new User("Henk"));
        userDoa.addUser(new User("Fred"));
    }

}
