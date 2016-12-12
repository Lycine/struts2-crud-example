package org.apache.struts.example.crud.utils;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Created by hongyu on 16/12/12.
 */
public class HibernateUtil {
    public static final SessionFactory sessionFactory;

    static
    {
        try
        {
            final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sessionFactory.openSession();
        }
        catch (Throwable ex)
        {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static final ThreadLocal<Session> session
            = new ThreadLocal<Session>();

    public static Session getSession()
            throws HibernateException
    {
        //通过线程对象.get()方法安全创建Session
        Session s = session.get();
        // 如果该线程还没有Session,则创建一个新的Session
        if (s == null)
        {
            s = sessionFactory.openSession();
            // 将获得的Session变量存储在ThreadLocal变量session里
            session.set(s);
        }
        return s;
    }
    //关闭Session
    public static void closeSession()
            throws HibernateException
    {
        Session s = session.get();
        if (s != null)
            s.close();
        session.set(null);
    }
}
