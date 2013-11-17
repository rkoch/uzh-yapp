/*
 * The MIT License (MIT)
 * Copyright © 2013 different authors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the “Software”), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package ch.uzh.ifi.se.yapp.backend.election;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import ch.uzh.ifi.se.yapp.model.landscape.District;
import ch.uzh.ifi.se.yapp.model.landscape.DistrictResult;
import ch.uzh.ifi.se.yapp.model.landscape.Election;


public class ElectionAdapterTest {

    private final LocalServiceTestHelper helper =
            new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    private Election e = new Election();
    private ElectionAdapter ea= new ElectionAdapter();
    private List<DistrictResult> drl = new ArrayList<>();


    @Before
    public void setUp() {
        helper.setUp();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

    @Test
    public void insert() {
        e.setDate(new LocalDate(2013, 03, 02));
        e.setDescription("Test-Beschreibung");
        e.setId("550");

        DistrictResult dr = new DistrictResult();
        dr.setDeliveredVoteCount(1);
        dr.setNoVoteCount(1);
        dr.setTotalEligibleCount(0);
        dr.setValidVoteCount(1);
        dr.setYesVoteCount(0);

        District d = new District();
        d.setCanton("Zürich");
        d.setCantonId("1");
        d.setId("2603");
        d.setName("Test-Bezirk");
        d.setLocalDate(new LocalDate(2013, 03, 02));

        dr.setDistrict(d);

        drl.add(dr);
        e.setResults(drl);
        ea.insertElection(e);
    }

    @Test
    public void insert2() {
        e.setDate(new LocalDate(2013, 01, 02));
        e.setDescription("Test-Beschreibung2");
        e.setId("551");

        DistrictResult dr = new DistrictResult();
        dr.setDeliveredVoteCount(1);
        dr.setNoVoteCount(1);
        dr.setTotalEligibleCount(0);
        dr.setValidVoteCount(1);
        dr.setYesVoteCount(0);

        District d = new District();
        d.setCanton("Graubünden");
        d.setCantonId("2");
        d.setId("2602");
        d.setName("Test-Bezirk2");
        d.setLocalDate(new LocalDate(2013, 01, 02));

        dr.setDistrict(d);

        drl.add(dr);
        e.setResults(drl);
        ea.insertElection(e);
    }


    @Test
    public void list() {
        insert();
        Map<String, Election> res = ea.listElections();
        assertEquals(true, res.containsKey("550"));
    }

    @Test
    public void getElectionsByDateRange() {
        insert();
        insert2();

        List<Election> result = ea.getElectionsByDateRange(new LocalDate(2013, 03, 02), new LocalDate(2013, 01, 02));
        assertEquals("551", result.get(0).getId());
        assertEquals("550", result.get(1).getId());
    }

    @Test
    public void getElectionById() {
        insert();
        insert2();

        Election result = ea.getElectionById("551");
        assertEquals("551", result.getId());
    }

}
