package app.repository.impl;

import app.model.User;
import app.repository.UserDAO;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserRepository implements UserDAO {
    @PersistenceContext
    private final EntityManager entityManager;

    public UserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @Override
    public User getUser(int id) {
        return entityManager.createQuery("select u from User u where u.id = :id", User.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public void saveUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void updateUser(int id, @NotNull User user) {
        User u = getUser(id);
        u.setName(user.getName());
        u.setSurname(user.getSurname());
        u.setEmail(user.getEmail());
        entityManager.merge(u);
    }

    @Override
    public void removeUser(int id) {
        User user = getUser(id);
        entityManager.remove(user);
    }
}
