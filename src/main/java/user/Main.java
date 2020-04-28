package user;

import com.github.javafaker.Faker;
import user.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

    private static Faker faker = new Faker();

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("legoset-mysql");
        EntityManager em = emf.createEntityManager();

        User user = User.builder().username("test").password("123123").build();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        System.out.println(user);

        em.getTransaction().begin();
        user.setPassword("testPass");
        em.getTransaction().commit();

        System.out.println(em.find(User.class, "test"));
        System.out.println(user == em.find(User.class, "test"));

        em.clear();

        System.out.println(em.find(User.class, "test"));
        System.out.println(user == em.find(User.class, "test"));

        em.getTransaction().begin();
        User managedUser = em.merge(user);
        managedUser.setPassword("password");
        em.getTransaction().commit();
        System.out.println(em.find(User.class, "test"));

        em.close();
        emf.close();
    }

}
