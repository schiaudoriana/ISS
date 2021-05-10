package repository;

import domain.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class UserRepository implements IUserRepository{
    private static SessionFactory sessionFactory;

    public UserRepository() {
        try{
            initialize();
        }
        catch (Exception ex){
            System.err.println("Exception creating UserRepository: "+ex);
        }
    }

    private static void initialize(){
        final StandardServiceRegistry registry=
                new StandardServiceRegistryBuilder()
                .configure().build();
        try{
            sessionFactory=new MetadataSources(registry)
                    .buildMetadata().buildSessionFactory();
        }
        catch (Exception e){
            System.err.println("Initialize exception: "+e);
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static void close(){
        if(sessionFactory!=null){
            sessionFactory.close();
        }
        System.out.println("SessionFactory closed...");
    }

    @Override
    public User findUser( String username, String password ) {
        User us=null;
        try(Session session=sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx=session.beginTransaction();
                String query="from User where username like :user and password like :pass";
                us=session.createQuery(query,User.class)
                        .setParameter("user",username)
                        .setParameter("pass",password)
                        .setMaxResults(1)
                        .uniqueResult();
                tx.commit();
            }
            catch (Exception e){
                if(tx!=null)
                    tx.rollback();
                System.err.println("Transaction rollback: "+e);
            }

        }
        return us;
    }

    @Override
    public void employeePresent( User user,String hour ) {
        try(Session session=sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx=session.beginTransaction();
                user.setPresent(true);
                user.setHour(hour);
                session.update(user);
                tx.commit();
            }
            catch (Exception ex){
                if(tx!=null)
                    tx.rollback();
            System.err.println("Transaction rollback: "+ex);
            }
        }
    }

    @Override
    public void employeeLeft( User user ) {
        try(Session session=sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx=session.beginTransaction();
                user.setPresent(false);
                session.update(user);
                tx.commit();
            }
            catch (Exception ex){
                if(tx!=null)
                    tx.rollback();
                System.err.println("Transaction rollback: "+ex);
            }
        }
    }

    @Override
    public Iterable<User> getPresentEmployees() {
        try(Session session=sessionFactory.openSession()){
            session.beginTransaction();
            List<User> list=session.createQuery("from User where present is true",User.class).list();
            session.getTransaction().commit();
            return list;

        }
    }

    @Override
    public User findOne( Integer integer ) {
        User us=null;
        try(Session session=sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx=session.beginTransaction();
                String query="from User where id like:idUser";
                us=session.createQuery(query,User.class)
                        .setParameter("idUser",integer)
                        .setMaxResults(1)
                        .uniqueResult();
                tx.commit();
            }
            catch (Exception ex){
                if (tx!=null)
                    tx.rollback();
                System.err.println("Transaction rollback: "+ex);
            }
        }
        return us;
    }

    @Override
    public Iterable<User> findAll() {
        try(Session session=sessionFactory.openSession()){
            session.beginTransaction();
            List<User> list=session.createQuery("from User",User.class).list();
            session.getTransaction().commit();
            return list;

        }
    }
}
