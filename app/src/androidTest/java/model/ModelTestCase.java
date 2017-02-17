package model;

import junit.framework.TestCase;

import java.util.List;

/**
 * Created by athom909 on 4/12/16.
 */
public class ModelTestCase extends TestCase {
    
    public void modelTest() {

        Person p = UserInfo.getInstance().getUser();

        List<Event> list = p.getPersonEventsInOrder();
        for(int i = 0; i < list.size() - 1; ++i) {
            assertTrue(list.get(i).getYear() < list.get(i + 1).getYear());
        }

    }
}
