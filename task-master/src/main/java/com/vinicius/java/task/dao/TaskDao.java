package com.vinicius.java.task.dao;

import javax.persistence.EntityManager;

import com.vinicius.java.task.bean.JpaResourceBean;
import com.vinicius.java.task.model.Task;

import java.util.List;

/**
 * DAO - Task
 * @author mavti
 *
 */
public class TaskDao {

    public List<Task> listAll() throws Exception {
        EntityManager em = JpaResourceBean.getEntityManagerFactory().createEntityManager();
        List<Task> tasks = null;

        try {
            tasks = em.createQuery("from Task").getResultList();
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            em.close();
        }

        return tasks;
    }
    
    public List<Task> listByUser(String email) throws Exception {
    	EntityManager em = JpaResourceBean.getEntityManagerFactory().createEntityManager();
    	List<Task> tasks = null;
    	
    	
    	try {
    		tasks = em.createQuery("from Task where email = :param1").setParameter("param1", email).getResultList();
					
		} catch (Exception e) {
			throw new Exception(e);
		}finally {
			em.close();
		}
    	return tasks;
    }

    public void insert(Task task) throws Exception {
        EntityManager em = JpaResourceBean.getEntityManagerFactory().createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(task);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();

            throw new Exception(e);
        } finally {
            em.close();
        }
    }

    public void refresh(Task task) throws Exception {
        EntityManager em = JpaResourceBean.getEntityManagerFactory().createEntityManager();

        try {
            em.getTransaction().begin();
            em.merge(task);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();

            throw new Exception(e);
        } finally {
            em.close();
        }
    }

    public void delete(long id) throws Exception {
        EntityManager em = JpaResourceBean.getEntityManagerFactory().createEntityManager();

        try {
            em.getTransaction().begin();
            Task task = em.find(Task.class, id);
            em.remove(task);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();

            throw new Exception(e);
        } finally {
            em.close();
        }
    }

    public Task select(long id) throws Exception {
        Task task;

        EntityManager em = JpaResourceBean.getEntityManagerFactory().createEntityManager();

        try {
            task = em.find(Task.class, id);
        } finally {
            em.close();
        }

        return task;
    }
}
