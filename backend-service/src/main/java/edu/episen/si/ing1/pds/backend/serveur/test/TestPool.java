package edu.episen.si.ing1.pds.backend.serveur.test;

import edu.episen.si.ing1.pds.backend.serveur.pool.DataSource;
import edu.episen.si.ing1.pds.backend.serveur.test.persistence.Contacts;
import edu.episen.si.ing1.pds.backend.serveur.test.persistence.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

public class TestPool {
    private final Logger logger = LoggerFactory.getLogger(TestPool.class.getName());
    private final static Scanner scanner = new Scanner(System.in);

    private final DataSource ds; 
    private final Repository contacts;

    public TestPool(DataSource ds) {
        this.ds = ds;
        contacts = new Contacts(ds);
    }

    private CompletableFuture<Connection> getConnection() {
        return CompletableFuture.supplyAsync(ds::getConnection);
    }

    private List<CompletableFuture<Connection>> getTest(int nLoop) {
        List<CompletableFuture<Connection>> futures = new ArrayList<>();
        for (int i = 0; i < nLoop; i++) {
            futures.add(getConnection());
        }
        return futures;
    }

    public void testPool(int nLoop) {
        logger.info("The pool test with loop is starting");
        CompletableFuture.allOf(
                getTest(nLoop)
                        .stream()
                        .map(future -> future.thenAccept(connection -> {
                            logger.info(String.valueOf(connection.hashCode()));
                            ds.release(connection);
                        })).toArray(CompletableFuture[]::new)
        ).thenRun(ds::shutdownPool);
        logger.info("The pool test with loop done !");
    }

    public void create() {
        logger.info("Create method was called! ");
        List<String> vals = new ArrayList<>();

        System.out.print("Name: ");
        vals.add(scanner.nextLine());

        System.out.print("Email: ");
        vals.add(scanner.nextLine());

        System.out.print("Tel: ");
        vals.add(scanner.nextLine());

        boolean result = contacts.create(vals.toArray(new String[0]));

        if (result)
            logger.info("data created successfully! ");
        else
            logger.warn("Error! data wasn't created");

    }

    public void read() {
        logger.info("read method was called! ");
        logger.info(String.valueOf(contacts.readAll()));
    }

    public void update() {
        logger.info("Update method was called! ");
        List<Map<String, Object>> all = contacts.readAll();
        Map<String, String> selected = null;
        logger.info(String.valueOf(all));
        System.out.print("ID contact: ");
        int id = scanner.nextInt();
        for (Map tuple : all) {
            if (tuple.get("id").equals(id)) {
                selected = tuple;
            }
        }
        if (selected != null) {
            System.out.print("Name: ");
            scanner.nextLine();
            String name = scanner.nextLine();
            if (!name.isEmpty()) ;
            selected.put("name", name);

            System.out.print("Email: ");
            String email = scanner.nextLine();
            if (!email.isEmpty())
                selected.put("email", email);

            System.out.print("Tel: ");
            String tel = scanner.nextLine();
            if (!tel.isEmpty())
                selected.put("telephone", tel);

            selected.remove("id");
            boolean result = contacts.update(id, selected.values().toArray());
            if (result)
                logger.info("data {} updated successfully! ", id);
            else
                logger.warn("Error! data {} doesn't updated", id);
        } else {
            logger.error("Error ID sent !");
        }

    }

    public void delete() {
        logger.info("Delete method was called! ");
        logger.info(String.valueOf(contacts.readAll()));

        System.out.print("ID contact: ");
        int id = scanner.nextInt();
        boolean result = contacts.delete(id);

        if (result)
            logger.info("data {} deleted successfully! ", id);
        else
            logger.warn("Error! data {} wasn't deleted", id);
    }

    public void handleError() {
        logger.error("testType args accepts: ");
        System.out.println("1 - Create operation");
        System.out.println("2 - Read operation");
        System.out.println("3 - Update operation");
        System.out.println("4 - Delete operation");
        System.out.println("5 - Loop operation");
    }
    
    public void testModeTest() {
    	logger.info("testMode is running! ");
    	logger.info("Creation of a contact ");
    	contacts.create(new String[]{"ludovic","ludo@gmail.com","0101012345"});
    	logger.info("Contact has been created ");
    	logger.info("Let's read data ");
    	List<Map> listContact = contacts.readAll();
    	logger.info(String.valueOf(listContact));
    	logger.info("Data has been read ");
    	logger.info("Let's update ");
    	contacts.update(9,new String[] { "paul","paul@gmail.com","0102030405"});
    	logger.info("Data has been updated ");
    	logger.info("Let's delete the contact ");
      	boolean resultDelete = contacts.delete((int) listContact.get(contacts.readAll().size()-1).get("id"));
      	if (resultDelete)
      		logger.info("The contact has been destroyed ");
      	else
      		logger.warn("No contact has been destroyed");
      	logger.info("Loop method has been called");
      	testPool(15);
    	
    }

}
