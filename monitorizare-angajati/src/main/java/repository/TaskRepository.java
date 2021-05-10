package repository;

import domain.Task;
import domain.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class TaskRepository implements ITaskRepository {
    private static SessionFactory sessionFactory;

    public TaskRepository() {
        try{
            initialize();
        }
        catch (Exception e){
            System.err.println("Exception creating TaskRepository: "+e);
            e.printStackTrace();
        }
    }

    private static void initialize() {
        final StandardServiceRegistry registry=new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try{
            sessionFactory=new MetadataSources(registry).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            System.err.println("Initialize Exception: " + e);
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static void close(){
        if (sessionFactory!=null){
            sessionFactory.close();
        }
        System.out.println("SessionFactory hibernate closed");
    }


    @Override
    public Task findOne( Integer integer ) {
        Task t=null;
        try(Session session=sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx=session.beginTransaction();
                String query="from Task where id=integer";
                t=session.createQuery(query,Task.class)
                        .setMaxResults(1)
                        .uniqueResult();
                tx.commit();
            }
            catch (Exception ex){
                if(tx!=null)
                    tx.rollback();
                System.err.println("Transaction rollback: "+ex);
            }
        }
        return t;
    }

    @Override
    public Iterable<Task> findAll() {
        try(Session session=sessionFactory.openSession()){
            session.beginTransaction();
            List<Task> list=session.createQuery("from Task",Task.class).list();
            session.getTransaction().commit();
            return list;
        }
    }

    @Override
    public Iterable<Task> getTasks( User us ) {
        try(Session session=sessionFactory.openSession()){
            session.beginTransaction();
            String query="from Task where idEmployee like :id";
            List<Task>list=session.createQuery(query,Task.class)
                    .setParameter("id",us.getId())
                    .list();
            return list;
        }
    }
}
