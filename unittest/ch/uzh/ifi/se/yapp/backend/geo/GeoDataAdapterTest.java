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
package ch.uzh.ifi.se.yapp.backend.geo;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import ch.uzh.ifi.se.yapp.model.geo.GeoBoundary;
import ch.uzh.ifi.se.yapp.model.geo.GeoPoint;
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
public class GeoDataAdapterTest {

        private final LocalServiceTestHelper helper =
                new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

        private             GeoBoundary gb = new GeoBoundary();
        private             GeoPoint gp = new GeoPoint();
        private             GeoPoint gp2 = new GeoPoint();
        private             List<GeoPoint> geoPoints = new ArrayList<>();
        private             GeoDataAdapter gda = new GeoDataAdapter();


        @Before
        public void setUp() {
            helper.setUp();
        }

        @After
        public void tearDown() {
            helper.tearDown();
        }



        @Test
        public void insertGeoBoundary() {
            gb.setId("1");
            gb.setLocalDate(new LocalDate(2012, 11, 24));

            gp.setX(new BigDecimal(12));
            gp.setY(new BigDecimal(12));
            gp2.setX(new BigDecimal(24));
            gp2.setY(new BigDecimal(24));
            geoPoints.add(gp);
            geoPoints.add(gp2);

            gb.setGeoPoints(geoPoints);
            gda.insertGeoBoundary(gb);
        }

        @Test
        public void getGeoBoundaryByDistricAndDate() {
            insertGeoBoundary();
            GeoBoundary res =  gda.getGeoBoundaryByDistrictAndDate("1", new LocalDate(2012, 11, 24));

            assertEquals("1", res.getId());
            assertEquals(new LocalDate(2012, 11, 24), res.getLocalDate());
            System.out.println(res.getGeoPoints().get(0).toString());
            assertEquals(gp.toString(), res.getGeoPoints().get(0).toString());
        }

        @Test
        public void getAllGeoBoundaryByDate() {
           insertGeoBoundary();
           List<GeoBoundary> tmpList = gda.getAllGeoBoundaryByDate(new LocalDate(2012, 11, 24));
           assertEquals("1", tmpList.get(0).getId());
           assertEquals(new LocalDate(2012, 11, 24), tmpList.get(0).getLocalDate());
           // TODO: zwei elemente?
           gb.setId("2");
           gb.setLocalDate(new LocalDate(2012, 11, 23));

           gp.setX(new BigDecimal(11));
           gp.setY(new BigDecimal(11));
           gp2.setX(new BigDecimal(23));
           gp2.setY(new BigDecimal(23));
           geoPoints.add(gp);
           geoPoints.add(gp2);

           gb.setGeoPoints(geoPoints);
           gda.insertGeoBoundary(gb);

           tmpList = gda.getAllGeoBoundaryByDate(new LocalDate(2012, 11, 24));
           assertEquals("1", tmpList.get(0).getId());
        }

        @Test
        public void getAllGeoBoundary() {
            insertGeoBoundary();
            List<GeoBoundary> tmpList = gda.getAllGeoBoundaryByDate(new LocalDate(2012, 11, 24));
            assertEquals("1", tmpList.get(0).getId());
            assertEquals(new LocalDate(2012, 11, 24), tmpList.get(0).getLocalDate());
            // TODO: zwei elemente?
            gb.setId("2");
            gb.setLocalDate(new LocalDate(2012, 11, 23));

            gp.setX(new BigDecimal(11));
            gp.setY(new BigDecimal(11));
            gp2.setX(new BigDecimal(23));
            gp2.setY(new BigDecimal(23));
            geoPoints.add(gp);
            geoPoints.add(gp2);

            gb.setGeoPoints(geoPoints);
            gda.insertGeoBoundary(gb);
            // now there should be 2 geoboundaries in the datastore
            List<GeoBoundary> resultList = gda.getAllGeoBoundary();
            assertEquals("1", resultList.get(0).getId());
            assertEquals("2", resultList.get(1).getId());
        }
}
