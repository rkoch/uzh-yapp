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

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.joda.time.LocalDate;

import com.google.appengine.api.datastore.DatastoreFailureException;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;

import ch.uzh.ifi.se.yapp.backend.accif.IGeoDataAdapter;
import ch.uzh.ifi.se.yapp.backend.base.EntityConst;
import ch.uzh.ifi.se.yapp.backend.persistence.DatastoreFactory;
import ch.uzh.ifi.se.yapp.model.geo.GeoBoundary;
import ch.uzh.ifi.se.yapp.model.geo.GeoPoint;
import ch.uzh.ifi.se.yapp.util.BaseObject;


public class GeoDataAdapter
        extends BaseObject
        implements IGeoDataAdapter {

    /**
     * Logger to list exceptions and errors for this class.
     */
    private static final Logger LOGGER = BaseObject.getLogger(GeoDataAdapter.class);


    @Override
    public void cleanup() {
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<GeoBoundary> getAllGeoBoundary() {
        List<GeoBoundary> tmpList = new ArrayList<>();
        // sort result alphabetically (ascending)
        Query geoBoundaryQuery = new Query("GeoBoundary");
        try {
            // NullPointerException - If any argument is null.
            geoBoundaryQuery.addSort(EntityConst.ID, SortDirection.ASCENDING);
        } catch (NullPointerException npe) {
            LOGGER.log(Level.WARNING, npe.toString(), npe);
        }

        PreparedQuery pq = DatastoreFactory.getGeoBoundaryDatastore().prepare(geoBoundaryQuery);
        for (Entity result : pq.asIterable()) {
            GeoBoundary tmpGeoBoundary = new GeoBoundary();
            tmpGeoBoundary.setId((String) result.getProperty("Id"));
            // set localdate out of string
            String str = (String) result.getProperty(EntityConst.LOCAL_DATE);
            LocalDate ld = new LocalDate(str);
            tmpGeoBoundary.setLocalDate(ld);
            // create geopoints out of a string
            List<GeoPoint> geoPoints = new ArrayList<>();
            List<String> geoPointsAsString = new ArrayList<>();
            geoPointsAsString = (List<String>) result.getProperty(EntityConst.GEO_POINT);
            GeoPoint tmpGP;
            for (int i = 0; i < geoPointsAsString.size(); i++) {
                tmpGP = new GeoPoint(geoPointsAsString.get(i));
                geoPoints.add(tmpGP);
            }
            tmpGeoBoundary.setGeoPoints(geoPoints);
            try {
                // UnsupportedOperationException - if the add operation is not supported by this list
                // ClassCastException - if the class of the specified element prevents it from being added to this list
                // NullPointerException - if the specified element is null and this list does not permit null elements
                // IllegalArgumentException - if some property of this element prevents it from being added to this list
                tmpList.add(tmpGeoBoundary);
            } catch (UnsupportedOperationException uoe) {
                LOGGER.log(Level.WARNING, uoe.toString(), uoe);
            } catch (ClassCastException cce) {
                LOGGER.log(Level.WARNING, cce.toString(), cce);
            } catch (NullPointerException npe) {
                LOGGER.log(Level.WARNING, npe.toString(), npe);
            } catch (IllegalArgumentException iae) {
                LOGGER.log(Level.WARNING, iae.toString(), iae);
            }
        }
        return tmpList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<GeoBoundary> getAllGeoBoundaryByDate(LocalDate pDate) {
        List<GeoBoundary> tmpList = new ArrayList<>(); // receives all stored GeoBoundaries from the datastore
        List<GeoBoundary> resultList = new ArrayList<>(); // store results to return
        List<List<GeoBoundary>> subLists = new ArrayList<>(); // list for each GeoBoundary (by Id), newest at 0

        try {
            // get newest and older GeoBoundary
            // IllegalArgumentException - If the provided filter values are not supported
            Filter dateFilter = new FilterPredicate(EntityConst.LOCAL_DATE, FilterOperator.LESS_THAN_OR_EQUAL, pDate.toString());

            // sort alphabetically (ascending) and max LocalDate first of each geoBoundary
            Query geoBoundaryQuery = new Query(EntityConst.GEO_BOUNDARY);
            geoBoundaryQuery.setFilter(dateFilter);
            // NullPointerException - If any argument is null.
            geoBoundaryQuery.addSort(EntityConst.LOCAL_DATE, SortDirection.DESCENDING);
            // NullPointerException - If any argument is null.
            geoBoundaryQuery.addSort(EntityConst.ID, SortDirection.ASCENDING);

            PreparedQuery pq = DatastoreFactory.getGeoBoundaryDatastore().prepare(geoBoundaryQuery);

            // create all geoboundaries out of elements stored in the datastore
            for (Entity result : pq.asIterable()) {
                GeoBoundary tmpGeoBoundary = new GeoBoundary();
                tmpGeoBoundary.setId((String) result.getProperty(EntityConst.ID));
                // create new GeoPoints out of Strings
                List<GeoPoint> geoPoints = new ArrayList<>();
                List<String> geoPointsAsString = new ArrayList<>();
                geoPointsAsString = (List<String>) result.getProperty(EntityConst.GEO_POINT);
                GeoPoint tmpGP;
                for (int i = 0; i < geoPointsAsString.size(); i++) {
                    tmpGP = new GeoPoint(geoPointsAsString.get(i));
                    geoPoints.add(tmpGP);
                }
                tmpGeoBoundary.setGeoPoints(geoPoints);
                // create LocalDate out of a String
                String str = (String) result.getProperty(EntityConst.LOCAL_DATE);
                LocalDate ld = new LocalDate(str);
                tmpGeoBoundary.setLocalDate(ld);

                // UnsupportedOperationException - if the add operation is not supported by this list
                // ClassCastException - if the class of the specified element prevents it from being added to this list
                // NullPointerException - if the specified element is null and this list does not permit null elements
                // IllegalArgumentException - if some property of this element prevents it from being added to this list
                tmpList.add(tmpGeoBoundary);
            }


            // check List for newest date
            // make sublist to get first element which is the newest
            int counter = 1;
            int start = 0;
            int end = 0;
            for (Iterator<GeoBoundary> it = tmpList.iterator(); it.hasNext();) {
                GeoBoundary tmp = it.next();
                if (it.hasNext()) {
                    GeoBoundary tmp2 = it.next();
                    if (tmp.getId() != tmp2.getId()) {
                        // next District/Canton found
                        end = counter;
                        List<GeoBoundary> tmpSubList = new ArrayList<>();
                        tmpSubList = tmpList.subList(start, end); // if start == end ->, tmpSubList will be empty
                        subLists.add(tmpSubList);
                        start = end;
                    }
                } else {
                    // only one element in list
                    resultList.add(tmp);
                    return resultList;
                }
                ++counter;
            }

            // get first element, which is the newest, from each subList
            for (Iterator<List<GeoBoundary>> it = subLists.iterator(); it.hasNext();) {
                List<GeoBoundary> tmpSubList = new ArrayList<>();

                tmpSubList = it.next();
                resultList.add(tmpSubList.get(0));
            }
        } catch (IllegalArgumentException iae) {
            LOGGER.log(Level.WARNING, iae.toString(), iae);
        } catch (NullPointerException npe) {
            LOGGER.log(Level.WARNING, npe.toString(), npe);
        } catch (UnsupportedOperationException uoe) {
            LOGGER.log(Level.WARNING, uoe.toString(), uoe);
        } catch (ClassCastException cce) {
            LOGGER.log(Level.WARNING, cce.toString(), cce);
        }
        return resultList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public GeoBoundary getGeoBoundaryByDistrictAndDate(String pDistrictId, LocalDate pDate) {
        GeoBoundary newestGeoBoundary = new GeoBoundary();
        System.out.println("received date: " + pDate.toString());
        try {
            // IllegalArgumentException - If the provided filter values are not supported.
            Filter dateMaxFilter = new FilterPredicate(EntityConst.LOCAL_DATE, FilterOperator.LESS_THAN_OR_EQUAL, pDate.toString()); // YYYY-MM-DD

            Query geoBoundaryQuery = new Query(EntityConst.GEO_BOUNDARY).setFilter(dateMaxFilter);
            PreparedQuery pq = DatastoreFactory.getGeoBoundaryDatastore().prepare(geoBoundaryQuery);

            // tmpMaxDate is newest Change of District pDistrictId
            LocalDate tmpMaxDate = new LocalDate(1848, 1, 1); // year, month, dayOfmonth

            // go over all received results
            for (Entity result : pq.asIterable()) {
                GeoBoundary tmpGeoBoundary = new GeoBoundary();

                // set LocalDate out of a string
                String str = (String) result.getProperty(EntityConst.LOCAL_DATE);
                LocalDate ld = new LocalDate(str);
                tmpGeoBoundary.setLocalDate(ld);

                // check if is older or newer
                if (tmpMaxDate.isBefore(tmpGeoBoundary.getLocalDate())) {
                    // possible newest geoboundary found
                    tmpMaxDate = tmpGeoBoundary.getLocalDate();

                    // set membervariables
                    tmpGeoBoundary.setId((String) result.getProperty(EntityConst.ID));
                    // create geopoints out of a string
                    List<GeoPoint> geoPoints = new ArrayList<>();
                    List<String> geoPointsAsString = new ArrayList<>();
                    geoPointsAsString = (List<String>) result.getProperty(EntityConst.GEO_POINT);
                    GeoPoint tmpGP;
                    for (int i = 0; i < geoPointsAsString.size(); i++) {
                        tmpGP = new GeoPoint(geoPointsAsString.get(i));
                        geoPoints.add(tmpGP);
                    }
                    tmpGeoBoundary.setGeoPoints(geoPoints);

                    newestGeoBoundary = tmpGeoBoundary;
                }
            }
        } catch (IllegalArgumentException iae) {
            LOGGER.log(Level.WARNING, iae.toString(), iae);
        }
        return newestGeoBoundary;
    }

    @Override
    public void insertGeoBoundary(GeoBoundary pGeoBoundary) {
        Entity geoBoundary = new Entity(EntityConst.GEO_BOUNDARY, pGeoBoundary.getId());
        System.out.println("inserted date: " + pGeoBoundary.getLocalDate().toString());
        geoBoundary.setProperty(EntityConst.ID, pGeoBoundary.getId());
        geoBoundary.setProperty(EntityConst.LOCAL_DATE, pGeoBoundary.getLocalDate().toString());

        // save GeoPoints in a List<String> and use this list as an entity.
        List<String> geoPoints = new ArrayList<>();
        for (int i = 0; i < pGeoBoundary.getGeoPoints().size(); i++) {
            geoPoints.add(pGeoBoundary.getGeoPoints().get(i).toString()); // x/y
        }
        geoBoundary.setProperty(EntityConst.GEO_POINT, geoPoints);

        try {
            // IllegalArgumentException - If the specified entity was incomplete.
            // ConcurrentModificationException - If the entity group to which the entity belongs was modified concurrently.
            // DatastoreFailureException - If any other datastore error occurs.
            DatastoreFactory.getGeoBoundaryDatastore().put(geoBoundary);
        } catch (IllegalArgumentException iae) {
            LOGGER.log(Level.WARNING, iae.toString(), iae);
        } catch (ConcurrentModificationException cme) {
            LOGGER.log(Level.WARNING, cme.toString(), cme);
        } catch (DatastoreFailureException dfe) {
            LOGGER.log(Level.WARNING, dfe.toString(), dfe);
        }
    }

}
