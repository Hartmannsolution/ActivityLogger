package dk.hartmanndemo.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ActivityDAO implements IDAO<Activity> {
    EntityManagerFactory emf;
    public ActivityDAO(EntityManagerFactory _emf){
        this.emf = _emf;
    }
    @Override
    public Activity create(Activity e) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(e);
            em.getTransaction().commit();
            return e;
        }
    }

    @Override
    public Set<Activity> get() {
        try(EntityManager em = emf.createEntityManager()){
            List<Activity> activities =  em.createQuery("SELECT e FROM Activity e", Activity.class).getResultList();
            return new HashSet<Activity>(activities);
        }
    }

    @Override
    public Activity getByID(Long id) {
        try(EntityManager em = emf.createEntityManager()){
            Activity employee = em.find(Activity.class, id);
            if(employee == null)
                throw new EntityNotFoundException("No entity found with id: "+id);
            return employee;
        }
    }

    @Override
    public Activity update(Activity e) {
        try(EntityManager em = emf.createEntityManager()){
            Activity foundEmployee = em.find(Activity.class, e.getId());
            if(foundEmployee == null)
                throw new EntityNotFoundException("No entity found with id: "+e.getId());
            em.getTransaction().begin();
            Activity employee = em.merge(e);
            em.getTransaction().commit();
            return employee;
        }
    }

    @Override
    public Long delete(Activity e) {
        try(EntityManager em = emf.createEntityManager()){
            Activity employee = em.find(Activity.class, e.getId());
            if(employee == null)
                throw new EntityNotFoundException("No entity found with id: "+e.getId());
            em.getTransaction().begin();
            em.remove(employee);
            em.getTransaction().commit();
            return employee.getId();
        }
    }
}
