package org.example;

import org.example.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        // First, create Configuration (all connection properties in 'hibernate.properties' file
        Configuration configuration = new Configuration().addAnnotatedClass(Person.class);
        // Second, create sessionFactory
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        // Third, get session to wor from sessionFactory
        Session session = sessionFactory.getCurrentSession();

        try {
            // open transaction
            session.beginTransaction();

            // create some persons
            Person person1 = new Person("Mike", 25);
            Person person2 = new Person("Tom", 30);
            Person person3 = new Person("Piter", 35);

            // save our objects in table in database
            session.save(person1);
            session.save(person2);
            session.save(person3);

            // get all records from table from database
            List<Person> persons = session.createQuery("SELECT a FROM Person a", Person.class).getResultList(); // SELECT * FROM Person

            // show all objects in console
            for (Person person : persons) {
                System.out.println(person.getId() + " | " + person.getName() + " | " + person.getAge());
            }

            // close transaction
            session.getTransaction().commit();

        } finally {
            // close sessionFactory
            sessionFactory.close();

        }

    }
}
