/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.Jpa;

import dao.interfaces.UserDao;
import domain.User;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import qualifier.JPA;

@JPA
@Stateless
public class UserDaoJpa implements UserDao {

    @PersistenceContext(unitName = "kwetterPU")
    private EntityManager em;

    public UserDaoJpa() {
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public User getUser(String name) {
        TypedQuery<User> query = em.createNamedQuery("user.findByName", User.class);
        query.setParameter("name", name);
        List<User> users = query.getResultList();
        if (users != null && !users.isEmpty()) {
            return users.get(0);
        }
        return null;
    }

    @Override
    public int count() {
        Query query = em.createQuery("SELECT u FROM User u");
        return new ArrayList<>(query.getResultList()).size();
    }

    @Override
    public User create(User user) {
        try {
            em.persist(user);
            return user;
        } catch (PersistenceException e) {
            return null;
        }
    }

    @Override
    public void edit(User user) {
        em.merge(user);
    }

}
