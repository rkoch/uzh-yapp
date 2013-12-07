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

import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ch.uzh.ifi.se.yapp.backend.accif.BackendAccessorFactory;
import ch.uzh.ifi.se.yapp.backend.accif.IGeoDataAdapter;
import ch.uzh.ifi.se.yapp.backend.base.EntityNotFoundException;
import ch.uzh.ifi.se.yapp.model.geo.Coordinate;
import ch.uzh.ifi.se.yapp.model.geo.GeoBoundary;
import ch.uzh.ifi.se.yapp.model.geo.Polygon;


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

    private GeoBoundary     mBoundary = new GeoBoundary();
    private Coordinate      mPoint    = new Coordinate(1222.0, 1222.0);
    private Coordinate      mPoint2   = new Coordinate(1333.0, 1333.0);
    private Polygon         mPolygon  = new Polygon();
    private IGeoDataAdapter mGeoAdpt  = BackendAccessorFactory.getGeoDataAdapter();


    public GeoDataAdapterTest() {

        mPolygon.addCoordinateBack(mPoint);
        mPolygon.addCoordinateBack(mPoint2);

        mBoundary.setId("1");
        mBoundary.setDate(new LocalDate(2012, 11, 24));
        mBoundary.addPolygon(mPolygon);
    }


    @Test
    public void insertGeoBoundary() {
        mGeoAdpt.insertGeoBoundary(mBoundary);
    }

    @Test
    public void getGeoBoundaryByDistricAndDate()
            throws EntityNotFoundException {
        mGeoAdpt.insertGeoBoundary(mBoundary);
        GeoBoundary res = mGeoAdpt.getGeoBoundaryByDistrictAndDate("1", new LocalDate(2012, 11, 24));

        assertEquals("1", res.getId());
        assertEquals(new LocalDate(2012, 11, 24), res.getDate());

    }

    @Test
    public void getGeoBoundaryWithYoungerDate()
            throws EntityNotFoundException {
        mGeoAdpt.insertGeoBoundary(mBoundary);
        GeoBoundary res = mGeoAdpt.getGeoBoundaryByDistrictAndDate("1", new LocalDate(2012, 11, 30));

        assertEquals("1", res.getId());
        assertEquals(new LocalDate(2012, 11, 24), res.getDate());
    }

    @Test
    public void getAllGeoBoundaryByDate() {
        mGeoAdpt.insertGeoBoundary(mBoundary);
        List<GeoBoundary> tmpList = mGeoAdpt.getAllGeoBoundaryByDate(new LocalDate(2012, 11, 30));
        assertEquals("1", tmpList.get(0).getId());
        assertEquals(new LocalDate(2012, 11, 24), tmpList.get(0).getDate());

        GeoBoundary mBoundary2 = new GeoBoundary();
        mBoundary2.setId("2");
        mBoundary2.setDate(new LocalDate(2012, 11, 23));
        Polygon p = new Polygon();
        p.addCoordinateBack(new Coordinate(222.0, 333.0));
        mBoundary2.addPolygon(p);
        mGeoAdpt.insertGeoBoundary(mBoundary2);

        tmpList = mGeoAdpt.getAllGeoBoundaryByDate(new LocalDate(2012, 11, 24));
        assertEquals("1", tmpList.get(0).getId());
        assertEquals("2", tmpList.get(1).getId());
    }

    @Test
    public void getAllGeoBoundary() {
        insertGeoBoundary();
        List<GeoBoundary> tmpList = mGeoAdpt.getAllGeoBoundaryByDate(new LocalDate(2012, 11, 24));
        assertEquals("1", tmpList.get(0).getId());
        assertEquals(new LocalDate(2012, 11, 24), tmpList.get(0).getDate());

        GeoBoundary mBoundary2 = new GeoBoundary();
        mBoundary2.setId("2");
        mBoundary2.setDate(new LocalDate(2012, 11, 23));
        Polygon p = new Polygon();
        p.addCoordinateBack(new Coordinate(222.0, 333.0));
        mBoundary2.addPolygon(p);
        mGeoAdpt.insertGeoBoundary(mBoundary2);

        // now there should be 2 geoboundaries in the datastore
        List<GeoBoundary> resultList = mGeoAdpt.getAllGeoBoundary();
        assertEquals("1", resultList.get(0).getId());
        assertEquals("2", resultList.get(1).getId());
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testEntityNotFoundException()
            throws EntityNotFoundException {
        insertGeoBoundary();
        exception.expect(EntityNotFoundException.class);
        // should throw EntityNotFoundException
        GeoBoundary res = mGeoAdpt.getGeoBoundaryByDistrictAndDate("1", new LocalDate(2012, 11, 22));
    }

}
