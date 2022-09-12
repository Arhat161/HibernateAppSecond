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
    // add all record to List<Person>
    public static List<Person> getAllPersonFromDatabase(Session session) {
        List<Person> persons = session.createQuery("SELECT a FROM Person a", Person.class).getResultList();
        return persons;
    }

    // print all records from List<Person>
    public static void printAllPersonsFromList(List<Person> persons) {
        if (persons != null) {
            for (Person person : persons) {
                System.out.println(person.getId() + " | " + person.getName() + " | " + person.getAge());
            }
        } else {
            System.out.println("No persons!");
        }
    }

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
            List<Person> persons = getAllPersonFromDatabase(session); // SELECT * FROM Person

            // show all objects in console
            printAllPersonsFromList(persons);

            // update information in one record
            Person person = session.get(Person.class, 2);
            person.setName("New name");
            person.setAge(59);

            // show information about updated record with id = 2 in console
            List<Person> updatedPersons = getAllPersonFromDatabase(session);
            printAllPersonsFromList(updatedPersons);

            // delete record from table, where id = 2
            session.delete(person);

            // show information about record with id = 2 in console
            List<Person> afterDeletingPersons = getAllPersonFromDatabase(session);
            printAllPersonsFromList(afterDeletingPersons);

            // close transaction
            session.getTransaction().commit();

        } finally {
            // close sessionFactory
            sessionFactory.close();
        }

    }
}
