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
package ch.uzh.ifi.se.yapp.backend.accif;

import ch.uzh.ifi.se.yapp.backend.election.MemcachedElectionAdapter;
import ch.uzh.ifi.se.yapp.backend.geo.MemcachedGeoDataAdapter;
import ch.uzh.ifi.se.yapp.backend.landscape.MemcachedLandscapeAdapter;
import ch.uzh.ifi.se.yapp.backend.visualisation.MemcachedVisualizationAdapter;


public abstract class BackendAccessorFactory {

    private static final IElectionDataAdapter      sElectionAdpt;
    private static final IGeoDataAdapter           sGeoAdpt;
    private static final ILandscapeDataAdapter     sLandAdpt;
    private static final IVisualisationDataAdapter sVisAdpt;


    static {
        sElectionAdpt = new MemcachedElectionAdapter();
        sGeoAdpt = new MemcachedGeoDataAdapter();
        sLandAdpt = new MemcachedLandscapeAdapter();
        sVisAdpt = new MemcachedVisualizationAdapter();
    }


    public static IElectionDataAdapter getElectionDataAdapter() {
        return sElectionAdpt;
    }

    public static IGeoDataAdapter getGeoDataAdapter() {
        return sGeoAdpt;
    }

    public static ILandscapeDataAdapter getLandscapeDataAdapter() {
        return sLandAdpt;
    }

    public static IVisualisationDataAdapter getVisualisationDataAdapter() {
        return sVisAdpt;
    }

}
