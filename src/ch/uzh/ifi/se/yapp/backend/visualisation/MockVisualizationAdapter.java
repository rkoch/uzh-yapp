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

import java.util.ArrayList;
import java.util.List;

import ch.uzh.ifi.se.yapp.backend.accif.IVisualizationDataAdapter;
import ch.uzh.ifi.se.yapp.model.visualisation.Visualization;
import ch.uzh.ifi.se.yapp.model.visualisation.VisualizationType;
import ch.uzh.ifi.se.yapp.util.BaseObject;


public class MockVisualizationAdapter
        extends BaseObject
        implements IVisualizationDataAdapter {

    public MockVisualizationAdapter() {
    }

    @Override
    public void cleanup() {
        // TODO Auto-generated method stub
    }

    @Override
    public Visualization getVisualizationById(String pId) {
        Visualization v = new Visualization();
        v.setElectionId("552.1");
        v.setType(VisualizationType.TABLE);
        return v;
    }

    @Override
    public List<Visualization> getAllVisualizations() {
        List<Visualization> tmpList = new ArrayList<>();

        Visualization v = new Visualization();
        v.setElectionId("552.1");
        v.setType(VisualizationType.TABLE);

        Visualization v2 = new Visualization();
        v2.setElectionId("552.1");
        v2.setType(VisualizationType.TABLE);

        tmpList.add(v);
        tmpList.add(v2);

        return tmpList;
    }

    @Override
    public void insertVisualization(Visualization pVisualization) {
        // TODO: implementation of jdo
    }

}
