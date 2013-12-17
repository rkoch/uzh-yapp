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
package ch.uzh.ifi.se.yapp.ctrl.mgr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import ch.uzh.ifi.se.yapp.backend.accif.BackendAccessorFactory;
import ch.uzh.ifi.se.yapp.backend.accif.IElectionDataAdapter;
import ch.uzh.ifi.se.yapp.backend.accif.IVisualisationDataAdapter;
import ch.uzh.ifi.se.yapp.model.base.VisualizationType;
import ch.uzh.ifi.se.yapp.model.dto.VisualisationCreationDTO;
import ch.uzh.ifi.se.yapp.model.dto.VisualisationDTO;
import ch.uzh.ifi.se.yapp.model.election.Election;
import ch.uzh.ifi.se.yapp.model.election.Result;
import ch.uzh.ifi.se.yapp.model.visualisation.Visualisation;


public class VisualisationManagerTest {

    private Visualisation        mVisualisation        = new Visualisation();
    private Election             mElection             = new Election();
    private VisualisationManager mVisualisationManager = new VisualisationManager();

    @Before
    public void setUp() {
        IElectionDataAdapter elecAdpt = BackendAccessorFactory.getElectionDataAdapter();
        IVisualisationDataAdapter visAdpt = BackendAccessorFactory.getVisualisationDataAdapter();

        mElection.setId("555");
        mElection.setTitle("title");
        mElection.setDescription("Description");
        mElection.setDate(new LocalDate(2013, 01, 01));

        Result res1 = new Result();
        res1.setDeliveredCount(1);
        res1.setNoCount(1);
        res1.setTotalEligibleCount(1);
        res1.setValidCount(1);
        res1.setYesCount(0);
        res1.setLandscape("2603");

        Result res2 = new Result();
        res2.setDeliveredCount(2);
        res2.setNoCount(2);
        res2.setTotalEligibleCount(2);
        res2.setValidCount(2);
        res2.setYesCount(0);
        res2.setLandscape("2604");

        mElection.addResult(res1);
        mElection.addResult(res2);

        elecAdpt.insertElection(mElection);

        mVisualisation.setId("visId");
        mVisualisation.setElection("555");
        mVisualisation.setType(VisualizationType.TABLE);

        visAdpt.insertVisualisation(mVisualisation);
    }


    @Test
    public void testGetVisualisationById() {
        String id = mVisualisation.getId();

        VisualisationDTO dto = null;
        try {
            dto = mVisualisationManager.getVisualisationById(id);
        } catch (Exception pEx) {
            fail();
        }

        assertEquals(id, dto.getId());
        assertEquals("555", dto.getElection().getId());

//        assertTrue(dto.getResults().size() >= 1);
//        assertEquals(1, dto.getResults().get(0).getLabel().getDeliveredCount());
        assertEquals("2013-01-01", dto.getElection().getDate());
    }

    @Test
    public void testCreateVisualisation() {
        VisualisationCreationDTO visCre = new VisualisationCreationDTO();
        visCre.setTitle("title");
        visCre.setAuthor("author");
        visCre.setComment("comment");
        visCre.setElectionId("555");
        visCre.setVisualizationType(VisualizationType.TABLE);

        VisualisationDTO dto = null;
        try {
            dto = mVisualisationManager.createVisualisation(visCre);
        } catch (Exception pEx) {
            fail();
        }

        assertEquals("author", dto.getAuthor());
        assertEquals("comment", dto.getComment());
        assertEquals("555", dto.getElection().getId());
    }

}
