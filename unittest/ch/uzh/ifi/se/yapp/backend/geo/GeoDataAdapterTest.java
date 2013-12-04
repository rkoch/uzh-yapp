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
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ch.uzh.ifi.se.yapp.backend.accif.BackendAccessorFactory;
import ch.uzh.ifi.se.yapp.backend.accif.IGeoDataAdapter;
import ch.uzh.ifi.se.yapp.backend.base.EntityNotFoundException;
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

    private GeoBoundary     mBoundary = new GeoBoundary();
    private GeoPoint        mPoint    = new GeoPoint();
    private GeoPoint        mPoint2   = new GeoPoint();
    private List<GeoPoint>  mPoints   = new ArrayList<>();
    private IGeoDataAdapter mGeoAdpt  = BackendAccessorFactory.getGeoDataAdapter();


    @Test
    public void insertGeoBoundary() {
        mBoundary.setId("1");
        mBoundary.setLocalDate(new LocalDate(2012, 11, 24));

        mPoint.setX(new BigDecimal(12));
        mPoint.setY(new BigDecimal(12));
        mPoint2.setX(new BigDecimal(24));
        mPoint2.setY(new BigDecimal(24));
        mPoints.add(mPoint);
        mPoints.add(mPoint2);

        mBoundary.setGeoPoints(mPoints);
        mGeoAdpt.insertGeoBoundary(mBoundary);
    }

    @Test
    public void getGeoBoundaryByDistricAndDate() throws EntityNotFoundException {
        insertGeoBoundary();
        GeoBoundary res = mGeoAdpt.getGeoBoundaryByDistrictAndDate("1", new LocalDate(2012, 11, 24));

        assertEquals("1", res.getId());
        assertEquals(new LocalDate(2012, 11, 24), res.getLocalDate());
        System.out.println(res.getGeoPoints().get(0).toString());
        assertEquals(mPoint.toString(), res.getGeoPoints().get(0).toString());
    }

    @Test
    public void getAllGeoBoundaryByDate() {
        insertGeoBoundary();
        List<GeoBoundary> tmpList = mGeoAdpt.getAllGeoBoundaryByDate(new LocalDate(2012, 11, 24));
        assertEquals("1", tmpList.get(0).getId());
        assertEquals(new LocalDate(2012, 11, 24), tmpList.get(0).getLocalDate());

        GeoBoundary mBoundary2 = new GeoBoundary();
        mBoundary2.setId("2");
        mBoundary2.setLocalDate(new LocalDate(2012, 11, 23));

        mPoint.setX(new BigDecimal(11));
        mPoint.setY(new BigDecimal(11));
        mPoint2.setX(new BigDecimal(23));
        mPoint2.setY(new BigDecimal(23));
        mPoints.add(mPoint);
        mPoints.add(mPoint2);

        mBoundary2.setGeoPoints(mPoints);
        mGeoAdpt.insertGeoBoundary(mBoundary2);

        tmpList = mGeoAdpt.getAllGeoBoundaryByDate(new LocalDate(2012, 11, 24));
        assertEquals("1", tmpList.get(0).getId());
    }

    @Test
    public void getAllGeoBoundary() {
        insertGeoBoundary();
        List<GeoBoundary> tmpList = mGeoAdpt.getAllGeoBoundaryByDate(new LocalDate(2012, 11, 24));
        assertEquals("1", tmpList.get(0).getId());
        assertEquals(new LocalDate(2012, 11, 24), tmpList.get(0).getLocalDate());

        GeoBoundary mBoundary2 = new GeoBoundary();
        mBoundary2.setId("2");
        mBoundary2.setLocalDate(new LocalDate(2012, 11, 23));

        mPoint.setX(new BigDecimal(11));
        mPoint.setY(new BigDecimal(11));
        mPoint2.setX(new BigDecimal(23));
        mPoint2.setY(new BigDecimal(23));
        mPoints.add(mPoint);
        mPoints.add(mPoint2);

        mBoundary2.setGeoPoints(mPoints);
        mGeoAdpt.insertGeoBoundary(mBoundary2);
        // now there should be 2 geoboundaries in the datastore
        List<GeoBoundary> resultList = mGeoAdpt.getAllGeoBoundary();
        assertEquals("1", resultList.get(0).getId());
        assertEquals("2", resultList.get(1).getId());
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testEntityNotFoundException() throws EntityNotFoundException {
        insertGeoBoundary();
        exception.expect(EntityNotFoundException.class);
        // should throw EntityNotFoundException
        GeoBoundary res = mGeoAdpt.getGeoBoundaryByDistrictAndDate("1", new LocalDate(2012, 11, 22));
    }

}
