package model;

import org.apache.log4j.Logger;
import org.apache.struts.example.crud.model.Department;
import org.apache.struts.example.crud.model.Employee;
import org.apache.struts.example.crud.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;


/**
 * Created by hongyu on 16/12/12.
 */
public class TestHibernate {

    private static Logger logger = Logger.getLogger(TestHibernate.class);

    /**
     * .hbm.xml转换成数据库的表
     */
    @Test
    public void testSchemaExport() {
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(serviceRegistry).buildMetadata();
        SchemaExport schemaExport = new SchemaExport();
        schemaExport.create(EnumSet.of(TargetType.DATABASE), metadata);
    }

    /**
     * 添加操作
     */
    @Test
    public void create() {
        Session session = null;
        try {
            session = HibernateUtil.getSession();
            Transaction tx = session.beginTransaction();//开启事务
            Employee employee = new Employee();
            employee.setAge(123456);
            employee.setDepartment(new Department(3, "test-departmentName"));
            employee.setFirstName("first");
            employee.setLastName("last");
            session.save(employee);
            tx.commit();//提交事务
            System.out.println("*****result*****");
        } catch (Exception e) {
            logger.error("create error");
            e.printStackTrace();
        } finally {
            if (session != null)
                session.close();
        }
    }

    /**
     * 用HQL带条件（by id）删除操作
     */
    @Test
    public void hqlDelete() {
        Session session = null;
        try {
            session = HibernateUtil.getSession();
            String hql = "delete from Employee where id in (:idList)";
            Transaction tx = session.beginTransaction();//开启事务
            List<Integer> list = new ArrayList<Integer>();
            list.add(1);
            list.add(2);
            Query<?> query = session.createQuery(hql).setParameter("idList", list);
            int i = query.executeUpdate();
            tx.commit();//提交事务
            System.out.println("*****result*****");
            System.out.println(i);
        } catch (Exception e) {
            logger.error("hqlDelete error");
            e.printStackTrace();
        } finally {
            if (session != null)
                session.close();
        }
    }

    /**
     * 用HQL带条件（by id）更新操作
     */
    @Test
    public void hqlUpdate() {
        Session session = null;
        try {
            session = HibernateUtil.getSession();
            String hql = "update Employee set age = ? where id = ?";
            Transaction tx = session.beginTransaction();//开启事务
            Query<?> query = session.createQuery(hql).setParameter(0, 99).setParameter(1,3);
            int i = query.executeUpdate();
            tx.commit();//提交事务
            System.out.println("*****result*****");
            System.out.println(i);
        } catch (Exception e) {
            logger.error("hqlUpdate error");
            e.printStackTrace();
        } finally {
            if (session != null)
                session.close();
        }

    }

    /**
     * 用HQL带条件（by 字符串）查询操作
     */
    @Test
    public void hqlRetrieve() {
        Session session = null;
        try {
            session = HibernateUtil.getSession();
            String hql = "from Employee where firstName=?";
            Transaction tx = session.beginTransaction();//开启事务
            Query query = session.createQuery(hql);
            query.setParameter(0, "first2");
            List<Employee> list = query.list();
            tx.commit();//提交事务
            System.out.println("*****result*****");
            for (Employee employee : list) {
                System.out.println(employee.getEmployeeId());
            }
        } catch (Exception e) {
            logger.error("hqlRetrieve error");
            e.printStackTrace();
        } finally {
            if (session != null)
                session.close();
        }
    }

    @Test
    public void tesr(){
        int id = 1;
        Transaction tx = null;
        Employee employee = new Employee();
        List<Employee> list = new ArrayList<Employee>();
        String hql = "";
        System.out.println("Employee id is: " + id);
        try {
            Session session = HibernateUtil.getSession();
            tx = session.beginTransaction();//开启事务
            hql = "FROM Employee E WHERE E.id = :id";
            Query query = session.createQuery(hql);
            query.setParameter("id",id);
            list = query.list();
            tx.commit();//提交事务
            System.out.println("*****result*****");
            for (Employee e : list) {
//                logger.debug(employee.toString());
                System.out.println(e.toString());
            }
            employee = list.get(0);
            if (list.size() > 1) {
                logger.error("getEmployee error, not unique result.");
            } else {
                logger.debug("getEmployee success");
            }
        } catch (Exception e) {
            logger.error("getEmployee error");
            e.printStackTrace();
        }
    }
}