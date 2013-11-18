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

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ch.uzh.ifi.se.yapp.ctrl.accif.AccessorFactory;
import ch.uzh.ifi.se.yapp.ctrl.accif.IVisualisationAccessor;
import ch.uzh.ifi.se.yapp.model.base.VisualizationType;
import ch.uzh.ifi.se.yapp.model.dto.VisualisationCreationDTO;
import ch.uzh.ifi.se.yapp.model.dto.VisualisationDTO;


public class VisualisationManagerTest {

    @Test
    public void testGetVisualisationById() {

        IVisualisationAccessor visAcc = AccessorFactory.getVisualisationAccessor();

        VisualisationDTO visualDTO = visAcc.getVisualisationById("test");

        assertTrue(visualDTO.getId().equals("test"));
        assertTrue(visualDTO.getElection().getDate().equals("2003-12-21"));
        assertEquals(3, visualDTO.getCantonResultList().size());
    }

    @Test
    public void testCreateVisualisation() {

        IVisualisationAccessor visAcc = AccessorFactory.getVisualisationAccessor();

        VisualisationCreationDTO visCreDTO = new VisualisationCreationDTO();
        visCreDTO.setElectionId("ElectionId");
        visCreDTO.setVisualizationType(VisualizationType.TABLE);
        VisualisationDTO visualDTO = visAcc.getVisualisationById("test");

        assertTrue(visualDTO.getId().equals("test"));
        assertTrue(visualDTO.getElection().getId().equals("ElectionId"));
        assertEquals(3, visualDTO.getCantonResultList().size());
    }
}
