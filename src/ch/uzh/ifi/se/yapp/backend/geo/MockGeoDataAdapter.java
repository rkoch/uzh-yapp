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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

import ch.uzh.ifi.se.yapp.backend.accif.IGeoDataAdapter;
import ch.uzh.ifi.se.yapp.model.geo.GeoBoundary;
import ch.uzh.ifi.se.yapp.model.geo.GeoPoint;
import ch.uzh.ifi.se.yapp.util.BaseObject;


public class MockGeoDataAdapter
        extends BaseObject
        implements IGeoDataAdapter {

    private DatastoreService  mGeoDatastore = DatastoreServiceFactory.getDatastoreService();

    private List<GeoBoundary> mTmpList      = new ArrayList<>();
    private List<GeoPoint>    mTmpGpList    = new ArrayList<>();
    private List<GeoPoint>    mTmpGpList2   = new ArrayList<>();
    private GeoBoundary       mGb           = new GeoBoundary();
    private GeoBoundary       mGb2          = new GeoBoundary();


    public MockGeoDataAdapter() {
        // set new geopoints
        GeoPoint gp1 = new GeoPoint();
        gp1.setX(new BigDecimal(1.0));
        gp1.setY(new BigDecimal(3.0));

        GeoPoint gp2 = new GeoPoint();
        gp2.setX(new BigDecimal(5.0));
        gp2.setY(new BigDecimal(6.0));

        mTmpGpList.add(gp1);
        mTmpGpList.add(gp2);

        GeoPoint gp3 = new GeoPoint();
        gp3.setX(new BigDecimal(1.4));
        gp3.setY(new BigDecimal(3.5));

        GeoPoint gp4 = new GeoPoint();
        gp4.setX(new BigDecimal(5.9));
        gp4.setY(new BigDecimal(6.6));

        mTmpGpList2.add(gp3);
        mTmpGpList2.add(gp4);

        // set new GeoBoundary
        mGb.setGeoPoints(mTmpGpList);

        mGb.setGeoPoints(mTmpGpList2);

        mTmpList.add(mGb);
        mTmpList.add(mGb2);
    }

    @Override
    public void cleanup() {
        // TODO Auto-generated method stub
    }

    @Override
    public List<GeoBoundary> getAllGeoBoundary() {
        return mTmpList;
    }

    @Override
    public List<GeoBoundary> getAllGeoBoundaryByDate(LocalDate pDate) {
        return mTmpList;
    }

    @Override
    public GeoBoundary getGeoBoundaryByDistrictAndDate(String pDistrictId, LocalDate pDate) {
        return mGb;
    }

    @Override
    public void insertGeoBoundary(GeoBoundary pGeoBoundary) {
        // DateTime is stored in object pGeoBoundary
        Entity gb = new Entity("GeoBoundary", pGeoBoundary.getId());

        gb.setProperty("id", pGeoBoundary.getId());
        gb.setProperty("geopoints", pGeoBoundary.getGeoPoints());

        mGeoDatastore.put(gb);
    }

}
