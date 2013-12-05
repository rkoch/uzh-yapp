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

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ch.uzh.ifi.se.yapp.backend.accif.BackendAccessorFactory;
import ch.uzh.ifi.se.yapp.backend.accif.IElectionDataAdapter;
import ch.uzh.ifi.se.yapp.backend.base.EntityNotFoundException;
import ch.uzh.ifi.se.yapp.model.election.Election;
import ch.uzh.ifi.se.yapp.model.election.Result;


public class ElectionAdapterTest {


    private Election             mElection     = new Election();
    private IElectionDataAdapter mElectionAdpt = BackendAccessorFactory.getElectionDataAdapter();
    private List<Result>         mResults      = new ArrayList<>();

    @Test
    public void insert() {
        mElection.setDescription("Test-Beschreibung");
        mElection.setId("550");

        Result res = new Result();
        res.setTotalEligibleCount(10);
        res.setDeliveredCount(50);
        res.setValidCount(45);
        res.setYesCount(10);
        res.setNoCount(35);
        res.setLandscape("2603");

        mElection.addResult(res);
        mElectionAdpt.insertElection(mElection);
    }

    @Test
    public void insert2() {
        mElection.setDescription("Test-Beschreibung2");
        mElection.setId("551");

        Result res = new Result();
        res.setTotalEligibleCount(10);
        res.setDeliveredCount(50);
        res.setValidCount(45);
        res.setYesCount(10);
        res.setNoCount(35);
        res.setLandscape("2602");
        mElection.addResult(res);

        mElectionAdpt.insertElection(mElection);
    }


    @Test
    public void list() {
        insert();
        Map<String, Election> res = mElectionAdpt.listElections();
        assertEquals(true, res.containsKey("550"));
    }

    @Test
    public void getElectionById()
            throws EntityNotFoundException {
        // insert();
        insert2();

        Election result = mElectionAdpt.getElectionById("551");
        // assertEquals("551", result.getId());

        // check result
        Result dr1 = result.getResults().iterator().next();

        assertEquals(dr1.getTotalEligibleCount(), dr1.getTotalEligibleCount());
        assertEquals(dr1.getDeliveredCount(), dr1.getDeliveredCount());
        assertEquals(dr1.getValidCount(), dr1.getValidCount());
        assertEquals(dr1.getYesCount(), dr1.getYesCount());
        assertEquals(dr1.getNoCount(), dr1.getNoCount());
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testEntityNotFoundException()
            throws EntityNotFoundException {
        insert();
        exception.expect(EntityNotFoundException.class);
        Election ret = mElectionAdpt.getElectionById("560"); // should throw a EntityNotFoundException
    }

}
