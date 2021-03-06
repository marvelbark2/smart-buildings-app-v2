package edu.episen.si.ing1.pds.backend.server.test.PoolOverload;

import edu.episen.si.ing1.pds.backend.server.pool.DataSource;
import edu.episen.si.ing1.pds.backend.server.test.TestPool;
import edu.episen.si.ing1.pds.backend.server.test.persistence.Contacts;
import edu.episen.si.ing1.pds.backend.server.test.persistence.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class TestPoolOverLoad {
    private final Logger logger = LoggerFactory.getLogger(TestPool.class.getName());

    private final DataSource ds;
    private final Repository contacts;

    public TestPoolOverLoad(DataSource ds) {
        this.ds = ds;
        contacts = new Contacts(ds);
    }

    private int lastId() {
        List<Map> listContact = contacts.readAll();
        int id = (contacts.readAll().size() > 0 )
                ? (int) listContact.get(contacts.readAll().size() - 1).get("id")
                : 1;
        return id;
    }

    public void create() {
        logger.info("Create method was called! ");

        boolean result = contacts.create(new String[]{"ludovic", "ludo@gmail.com", "0101012345"});

        if (result)
            logger.info("Data created successfully! ");
        else
            logger.warn("Error! data wasn't created");
    }

    public void read() {
        logger.info("Read method was called! ");
        logger.info(String.valueOf(contacts.readAll().size() > 0 ? contacts.readAll():"Empty"));
        logger.info("Read method done! ");
    }

    public void update() {
        int id = lastId();
        boolean result = contacts.update(id, new String[]{"paul", "paul@gmail.com", "0102030405"});
        if (result)
            logger.info("Contact {} was updated successfully!", id);
        else
            logger.warn("No contact was updated");
    }

    public void delete() {
        logger.info("Destroy method was called");
        int id = lastId();
        boolean resultDelete = contacts.delete(id);
        if (resultDelete)
            logger.info("The contact {} has been destroyed successfully! ", id);
        else
            logger.warn("No contact has been destroyed");
    }

}
