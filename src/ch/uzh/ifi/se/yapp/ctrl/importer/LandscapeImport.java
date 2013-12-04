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
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import ch.uzh.ifi.se.yapp.backend.accif.ILandscapeDataAdapter;
import ch.uzh.ifi.se.yapp.model.landscape.Canton;
import ch.uzh.ifi.se.yapp.model.landscape.District;
import ch.uzh.ifi.se.yapp.util.BaseObject;


public class LandscapeImport
        extends BaseObject {

    private static final Logger         LOGGER = getLogger(LandscapeImport.class);

    private final ILandscapeDataAdapter mStorageAdapter;


    public LandscapeImport(ILandscapeDataAdapter pStorageAdapter) {
        mStorageAdapter = pStorageAdapter;
    }


    public void runImport(InputStream pCantonStream, InputStream pDistrictStream)
            throws IOException {
        // Import cantons
        Map<String, Canton> cantons = createCantonMap(pCantonStream);

        // Import districts
        BufferedReader br = new BufferedReader(new InputStreamReader(pDistrictStream));

        String line;
        while ((line = br.readLine()) != null) {
            // 101;1;Affoltern
            String[] split = line.split(";");
            if (split.length != 3) {
                LOGGER.log(Level.WARNING, String.format("Could not parse the following line to a district: %s", line));
                continue;
            }

            District d = new District(split[0], split[2], split[1]);
            d.connectCanton(cantons.get(split[1]));

            // Persist district
            mStorageAdapter.insertDistrict(d);
        }

        br.close();

        // Persist cantons
        for (Canton c : cantons.values()) {
//            mStorageAdapter.insertCanton(c);
        }
    }

    private Map<String, Canton> createCantonMap(InputStream pIn)
            throws IOException {
        Map<String, Canton> ret = new HashMap<>();

        BufferedReader br = new BufferedReader(new InputStreamReader(pIn));

        String line;
        while ((line = br.readLine()) != null) {
            // 1;Zürich
            String[] split = line.split(";");
            if (split.length != 2) {
                LOGGER.log(Level.WARNING, String.format("Could not parse the following line to a canton: %s", line));
                continue;
            }
            ret.put(split[0], new Canton(split[0], split[1]));
        }

        br.close();

        return ret;
    }

}
