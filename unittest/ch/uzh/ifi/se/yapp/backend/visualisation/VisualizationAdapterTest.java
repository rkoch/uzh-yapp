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
package ch.uzh.ifi.se.yapp.backend.visualisation;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ch.uzh.ifi.se.yapp.backend.accif.BackendAccessorFactory;
import ch.uzh.ifi.se.yapp.backend.accif.IVisualizationDataAdapter;
import ch.uzh.ifi.se.yapp.backend.base.EntityNotFoundException;
import ch.uzh.ifi.se.yapp.model.base.VisualizationType;
import ch.uzh.ifi.se.yapp.model.visualisation.Visualisation;


public class VisualizationAdapterTest {

    private IVisualizationDataAdapter mVisualizationAdpt = BackendAccessorFactory.getVisualisationDataAdapter();
    private Visualisation             mVisualization     = new Visualisation();


    @Test
    public void testinsertVisualization() {
        mVisualization.setElection("552.1");
        mVisualization.setType(VisualizationType.MAP);
        Visualisation ret = mVisualizationAdpt.insertVisualization(mVisualization);
        mVisualizationAdpt.deleteVisualizationById("552.1");
    }

    @Test
    public void getAllVisualizations() {
        List<Visualisation> tmpList = mVisualizationAdpt.getAllVisualizations();
        for (int i=0; i< tmpList.size(); i++) {
            mVisualizationAdpt.deleteVisualizationById(tmpList.get(i).getId().toString());
        }
        tmpList = null;
        mVisualization.setElection("552.1");
        mVisualization.setType(VisualizationType.MAP);
        Visualisation ret = mVisualizationAdpt.insertVisualization(mVisualization);

        Visualisation mVisualization2 = new Visualisation();
        mVisualization2.setElection("552.2");
        mVisualization2.setType(VisualizationType.MAP);
        Visualisation ret2 = mVisualizationAdpt.insertVisualization(mVisualization2);

        List<Visualisation> tmpList2 = mVisualizationAdpt.getAllVisualizations();

        // check size
        assertEquals(2, tmpList2.size());
        // check member variables
        assertEquals("552.1", tmpList2.get(0).getElection());
        assertEquals(VisualizationType.MAP, tmpList2.get(0).getType());
        assertEquals("552.2", tmpList2.get(1).getElection());
        assertEquals(VisualizationType.MAP, tmpList2.get(1).getType());
        mVisualizationAdpt.deleteVisualizationById("552.1");
        mVisualizationAdpt.deleteVisualizationById("552.2");

    }

    @Test
    public void getVisualizationById()
            throws EntityNotFoundException {
        mVisualization.setElection("552.1");
        mVisualization.setType(VisualizationType.MAP);
        Visualisation ret = mVisualizationAdpt.insertVisualization(mVisualization);

        Visualisation l = mVisualizationAdpt.getVisualizationById(mVisualization.getId().toString());
        // check member variable
        assertEquals("552.1", l.getElection());
        assertEquals(VisualizationType.MAP, l.getType());
        mVisualizationAdpt.deleteVisualizationById("552.1");

    }

    @Rule
    public ExpectedException exception = ExpectedException.none();
    @Test
    public void deleteVisualizationById()
            throws EntityNotFoundException {
        mVisualization.setElection("552.1");
        mVisualization.setType(VisualizationType.MAP);
        Visualisation ret = mVisualizationAdpt.insertVisualization(mVisualization);
        exception.expect(EntityNotFoundException.class);
        mVisualizationAdpt.deleteVisualizationById(mVisualization.getId().toString());
        Visualisation l = mVisualizationAdpt.getVisualizationById(mVisualization.getId().toString());
    }
}
