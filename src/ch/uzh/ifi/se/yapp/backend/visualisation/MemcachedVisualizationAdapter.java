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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.uzh.ifi.se.yapp.backend.accif.IVisualizationDataAdapter;
import ch.uzh.ifi.se.yapp.backend.base.EntityNotFoundException;
import ch.uzh.ifi.se.yapp.model.visualisation.Visualization;
import ch.uzh.ifi.se.yapp.util.BaseObject;


public class MemcachedVisualizationAdapter
        extends BaseObject
        implements IVisualizationDataAdapter {

    private final Map<String, Visualization> mStorage;


    public MemcachedVisualizationAdapter() {
        mStorage = new HashMap<>();
    }

    @Override
    public void cleanup() {
    }

    @Override
    public Visualization getVisualizationById(String pId)
            throws EntityNotFoundException {
        Visualization ret = mStorage.get(pId);
        if (ret == null) {
            throw new EntityNotFoundException("Visualisation " + pId + " not found.");
        }
        return new Visualization(ret);
    }

    @Override
    public List<Visualization> getAllVisualizations() {
        List<Visualization> ret = new ArrayList<>();

        for (Visualization entry : mStorage.values()) {
            ret.add(new Visualization(entry));
        }

        return ret;
    }

    @Override
    public Visualization insertVisualization(Visualization pVisualization) {
        String id = pVisualization.getId().toString();

        mStorage.put(id, new Visualization(pVisualization));

        return new Visualization(pVisualization);
    }

    @Override
    public void deleteVisualizationById(String pId) {
        mStorage.remove(pId);
    }

}
