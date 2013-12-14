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
package ch.uzh.ifi.se.yapp.ctrl.importer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.joda.time.LocalDate;

import ch.uzh.ifi.se.yapp.backend.accif.IGeoDataAdapter;
import ch.uzh.ifi.se.yapp.model.geo.Coordinate;
import ch.uzh.ifi.se.yapp.model.geo.GeoBoundary;
import ch.uzh.ifi.se.yapp.model.geo.Polygon;
import ch.uzh.ifi.se.yapp.util.BaseObject;


public class GeoTextImport
        extends BaseObject {

    private static final Logger   LOGGER = getLogger(GeoTextImport.class);

    private final IGeoDataAdapter mStorageAdapter;


    public GeoTextImport(IGeoDataAdapter pStorageAdapter) {
        mStorageAdapter = pStorageAdapter;
    }


    public void runImport(InputStream pGeoStream)
            throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(pGeoStream, "utf-8"));

        // read boundaries in each line
        String line;
        while ((line = br.readLine()) != null) {
            String[] split = line.split(";");
            if (split.length < 3) {
                LOGGER.log(Level.WARNING, String.format("Could not parse the following line to a geo boundary: %s", line));
                continue;
            }

            GeoBoundary boundary = new GeoBoundary(split[0], new LocalDate(split[1]));

            // Parse polygons
            for (int i = 2; i < split.length; i++) {
                String[] coords = split[i].split("\\s");
                Polygon p = new Polygon();
                boolean skip = false;
                if ((coords.length > 0) && "inner".equalsIgnoreCase(coords[0])) {
                    p.setInner(true);
                    skip = true;
                }
                for (String co : coords) {
                    if (skip) {
                        skip = false;
                        continue; // skip first if there is an inner text
                    }
                    int idx = co.indexOf(",");
                    Coordinate c = new Coordinate(Double.parseDouble(co.substring(idx + 1)), Double.parseDouble(co.substring(0, idx)));
                    if (p.isInner()) {
                        p.addCoordinateFront(c);
                    } else {
                        p.addCoordinateBack(c);
                    }
                }
                boundary.addPolygon(p);
            }

            mStorageAdapter.insertGeoBoundary(boundary);
        }

        br.close();
    }

}
