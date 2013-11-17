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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import ch.uzh.ifi.se.yapp.model.base.VisualizationType;
import ch.uzh.ifi.se.yapp.model.visualisation.Visualization;


public class VisualizationAdapterTest {

    private final LocalServiceTestHelper mHelper            = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    private VisualizationAdapter         mVisualizationAdpt = new VisualizationAdapter();
    private Visualization                mVisualization     = new Visualization();


    @Before
    public void setUp() {
        mHelper.setUp();
    }

    @After
    public void tearDown() {
        mHelper.tearDown();
    }


    @Test
    public void insertVisualization() {
        mVisualization.setElectionId("552.1");
        mVisualization.setType(VisualizationType.MAP);
        mVisualizationAdpt.insertVisualization(mVisualization);
    }

    @Test
    public void getAllVisualizations() {
        insertVisualization();
        Visualization v2 = new Visualization();
        v2.setElectionId("552.2");
        v2.setType(VisualizationType.MAP);
        mVisualizationAdpt.insertVisualization(v2);

        List<Visualization> tmpList = mVisualizationAdpt.getAllVisualizations();
        // check size
        assertEquals(tmpList.size(), 2);
        // check member variables
        assertEquals(tmpList.get(0).getElectionId(), "552.1");
        assertEquals(tmpList.get(0).getType(), VisualizationType.MAP);
        assertEquals(tmpList.get(1).getElectionId(), "552.2");
        assertEquals(tmpList.get(0).getType(), VisualizationType.MAP);
    }

    @Test
    public void getVisualizationById() {
        insertVisualization();
        Visualization l = mVisualizationAdpt.getVisualizationById(mVisualization.getId().toString());
        // check member variable
        assertEquals("552.1", l.getElectionId());
        assertEquals(VisualizationType.MAP, l.getType());
    }

}
