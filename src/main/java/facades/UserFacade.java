package facades;

import security.IUserFacade;
import entity.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import security.IUser;
import security.PasswordStorage;

public class UserFacade implements IUserFacade {

    /*When implementing your own database for this seed, you should NOT touch any of the classes in the security folder
    Make sure your new facade implements IUserFacade and keeps the name UserFacade, and that your Entity User class implements 
    IUser interface, then security should work "out of the box" with users and roles stored in your database */
    private final Map<String, IUser> users = new HashMap<>();

    private EntityManagerFactory emf;

    public UserFacade() {
        emf = Persistence.createEntityManagerFactory("lam_seedMaven_war_1.0-SNAPSHOTPU");
        try {
            //Test Users
            User user = new User("user", PasswordStorage.createHash("test"));
            user.addRole("User");
//    users.put(user.getUserName(),user );
            addUser(user);
            User admin = new User("admin", PasswordStorage.createHash("test"));
            admin.addRole("Admin");
//    users.put(admin.getUserName(),admin);
            addUser(admin);

            User both = new User("user_admin", PasswordStorage.createHash("test"));
            both.addRole("User");
            both.addRole("Admin");
//        users.put(both.getUserName(), both);
            addUser(both);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public IUser getUserByUserId(String id
    ) {
        EntityManager em = emf.createEntityManager();
        try{
            
            em.getTransaction().begin();
            IUser user = em.find(User.class, id);
            em.getTransaction().commit();

            return user;
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    };

    /*
  Return the Roles if users could be authenticated, otherwise null
     */
    @Override
    public List<String> authenticateUser(String userName, String password
    ) {
        IUser user = getUserByName(userName);
        try {
            return user != null && PasswordStorage.verifyPassword(password, user.getPassword()) ? user.getRolesAsStrings() : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addUser(User user) {
        EntityManager em = emf.createEntityManager();
        try {

            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public User getUserByName(String name) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createQuery("SELECT u FROM User u WHERE u.userName=:name", User.class);
            q.setParameter("name", name);
            em.getTransaction().commit();

            User u = (User) q.getSingleResult();            
            return u;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return new User();
        } finally {
            em.close();
        }
    }

}
