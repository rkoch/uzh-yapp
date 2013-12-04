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

import java.io.IOException;
import java.io.InputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;


public class GeoImportTest {

    private final LocalServiceTestHelper mHelper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());


    @Before
    public void setUp() {
        mHelper.setUp();
    }

    @After
    public void tearDown() {
        mHelper.tearDown();
    }

    @Test
    public void test()
            throws IOException {

        try {

            String[] ids = { "BZ_13.txt", "KT_09.txt" };
            InputStream districts = getClass().getResourceAsStream(ids[0]);
            InputStream cantons = getClass().getResourceAsStream(ids[1]);

            LandscapeImport imp = new LandscapeImport(districts, cantons);

            GeoImport test = new GeoImport(imp);

            test.parseKml(getClass().getResourceAsStream("geo_bound_district.kml"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
