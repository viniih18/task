package com.vinicius.java.task.dao;

import javax.persistence.EntityManager;

import com.vinicius.java.task.bean.JpaResourceBean;
import com.vinicius.java.task.model.Users;

import java.util.List;

/**
 * DAO - User
 * @author mavti
 *
 */
public class UsersDao {

    public List<Users> listAll() throws Exception {
        EntityManager em = JpaResourceBean.getEntityManagerFactory().createEntityManager();
        List<Users> users = null;

        try {
        	users = em.createQuery("from Users").getResultList();
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            em.close();
        }

        return users;
    }

    public void insert(Users user) throws Exception {
        EntityManager em = JpaResourceBean.getEntityManagerFactory().createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();

            throw new Exception(e);
        } finally {
            em.close();
        }
    }

    public void update(Users users) throws Exception {
        EntityManager em = JpaResourceBean.getEntityManagerFactory().createEntityManager();

        try {
            em.getTransaction().begin();
            em.merge(users);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();

            throw new Exception(e);
        } finally {
            em.close();
        }
    }

    public void delete(String email) throws Exception {
        EntityManager em = JpaResourceBean.getEntityManagerFactory().createEntityManager();

        try {
            em.getTransaction().begin();
            Users user = em.find(Users.class, email);
            em.remove(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();

            throw new Exception(e);
        } finally {
            em.close();
        }
    }

    public Users select(String email) throws Exception {
        Users user;

        EntityManager em = JpaResourceBean.getEntityManagerFactory().createEntityManager();

        try {
        	user = em.find(Users.class, email);
        } finally {
            em.close();
        }

        return user;
    }
}
