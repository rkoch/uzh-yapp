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
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;

import ch.uzh.ifi.se.yapp.backend.accif.IGeoDataAdapter;
import ch.uzh.ifi.se.yapp.backend.base.EntityConst;
import ch.uzh.ifi.se.yapp.model.geo.GeoBoundary;
import ch.uzh.ifi.se.yapp.model.geo.GeoPoint;
import ch.uzh.ifi.se.yapp.util.BaseObject;


public class GeoDataAdapter
extends BaseObject
implements IGeoDataAdapter {

    /**
     * Datastore for GeoBoundaries.
     */
    private DatastoreService geoBoundaryDatastore = DatastoreServiceFactory.getDatastoreService();

    /**
     * Logger to list exceptions and errors for this class.
     */
    private Logger           log                  = BaseObject.getLogger(GeoDataAdapter.class);


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
            log.log(Level.WARNING, npe.toString(), npe);
        }

        PreparedQuery pq = geoBoundaryDatastore.prepare(geoBoundaryQuery);
        for (Entity result : pq.asIterable()) {
            GeoBoundary tmpGeoBoundary = new GeoBoundary();
            tmpGeoBoundary.setId((String) result.getProperty("Id"));
            tmpGeoBoundary.setLocalDate((LocalDate) result.getProperty("LocalDate"));
            tmpGeoBoundary.setGeoPoints((List<GeoPoint>) result.getProperty("GeoPoint"));
            try {
                // UnsupportedOperationException - if the add operation is not supported by this list
                // ClassCastException - if the class of the specified element prevents it from being added to this list
                // NullPointerException - if the specified element is null and this list does not permit null elements
                // IllegalArgumentException - if some property of this element prevents it from being added to this list
                tmpList.add(tmpGeoBoundary);
            } catch (UnsupportedOperationException uoe) {
                log.log(Level.WARNING, uoe.toString(), uoe);
            } catch (ClassCastException cce) {
                log.log(Level.WARNING, cce.toString(), cce);
            } catch (NullPointerException npe) {
                log.log(Level.WARNING, npe.toString(), npe);
            } catch (IllegalArgumentException iae) {
                log.log(Level.WARNING, iae.toString(), iae);
            }
        }
        return tmpList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<GeoBoundary> getAllGeoBoundaryByDate(LocalDate pDate) {
        List<GeoBoundary> tmpList = new ArrayList<>(); // receives all stored GeoBoundaries from the datastore
        List<GeoBoundary> resultList = new ArrayList<>(); // store results to return
        List<List<GeoBoundary>> subLists = new ArrayList<List<GeoBoundary>>(); // list for each GeoBoundary (by Id), newest at 0

        try {
            // get newest and older GeoBoundary
            // IllegalArgumentException - If the provided filter values are not supported
            Filter dateFilter = new FilterPredicate(EntityConst.LOCAL_DATE, FilterOperator.LESS_THAN_OR_EQUAL, pDate);

            // sort alphabetically (ascending) and max LocalDate first of each geoBoundary
            // TODO: to be tested
            Query geoBoundaryQuery = new Query(EntityConst.GEO_BOUNDARY);
            geoBoundaryQuery.setFilter(dateFilter);
            // NullPointerException - If any argument is null.
            geoBoundaryQuery.addSort(EntityConst.ID, SortDirection.ASCENDING);
            // NullPointerException - If any argument is null.
            geoBoundaryQuery.addSort(EntityConst.LOCAL_DATE, SortDirection.DESCENDING);

            PreparedQuery pq = geoBoundaryDatastore.prepare(geoBoundaryQuery);

            for (Entity result : pq.asIterable()) {
                GeoBoundary tmpGeoBoundary = new GeoBoundary();
                tmpGeoBoundary.setId((String) result.getProperty(EntityConst.ID));
                tmpGeoBoundary.setGeoPoints((List<GeoPoint>) result.getProperty(EntityConst.GEO_POINT));
                tmpGeoBoundary.setLocalDate((LocalDate) result.getProperty(EntityConst.LOCAL_DATE));
                // UnsupportedOperationException - if the add operation is not supported by this list
                // ClassCastException - if the class of the specified element prevents it from being added to this list
                // NullPointerException - if the specified element is null and this list does not permit null elements
                // IllegalArgumentException - if some property of this element prevents it from being added to this list
                tmpList.add(tmpGeoBoundary);
            }


            // check List for newest date
            // make sublist to get first element which is the newest
            int counter = 0;
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
                        tmpSubList = tmpList.subList(start, end);
                        subLists.add(tmpSubList);
                        start = end + 1;
                    }
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
            log.log(Level.WARNING, iae.toString(), iae);
        } catch (NullPointerException npe) {
            log.log(Level.WARNING, npe.toString(), npe);
        } catch (UnsupportedOperationException uoe) {
            log.log(Level.WARNING, uoe.toString(), uoe);
        } catch (ClassCastException cce) {
            log.log(Level.WARNING, cce.toString(), cce);
        }
        return resultList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public GeoBoundary getGeoBoundaryByDistrictAndDate(String pDistrictId, LocalDate pDate) {
        GeoBoundary newestGeoBoundary = new GeoBoundary();

        try {
            // IllegalArgumentException - If the provided filter values are not supported.
            Filter dateMaxFilter = new FilterPredicate(EntityConst.LOCAL_DATE, FilterOperator.LESS_THAN_OR_EQUAL, pDate);

            Query geoBoundaryQuery = new Query(EntityConst.GEO_BOUNDARY).setFilter(dateMaxFilter);
            PreparedQuery pq = geoBoundaryDatastore.prepare(geoBoundaryQuery);

            // tmpMaxDate is newest Change of District pDistrictId
            LocalDate tmpMaxDate = new LocalDate(1848, 1, 1); // year, month, dayOfmonth

            // go over all received results
            for (Entity result : pq.asIterable()) {
                GeoBoundary tmpGeoBoundary = new GeoBoundary();
                tmpGeoBoundary.setId((String) result.getProperty(EntityConst.ID));
                tmpGeoBoundary.setGeoPoints((List<GeoPoint>) result.getProperty(EntityConst.GEO_POINT));
                tmpGeoBoundary.setLocalDate((LocalDate) result.getProperty(EntityConst.LOCAL_DATE));
                // tmpMaxDate is not initialised
                if (tmpMaxDate.isBefore(tmpGeoBoundary.getLocalDate())) {
                    tmpMaxDate = tmpGeoBoundary.getLocalDate();
                    newestGeoBoundary = tmpGeoBoundary;
                }
            }
        } catch (IllegalArgumentException iae) {
            log.log(Level.WARNING, iae.toString(), iae);
        }
        return newestGeoBoundary;
    }

    @Override
    public void insertGeoBoundary(GeoBoundary pGeoBoundary) {
        Entity geoBoundary = new Entity(EntityConst.GEO_BOUNDARY, pGeoBoundary.getId());

        geoBoundary.setProperty(EntityConst.ID, pGeoBoundary.getId());
        geoBoundary.setProperty(EntityConst.LOCAL_DATE, pGeoBoundary.getLocalDate());
        geoBoundary.setProperty(EntityConst.GEO_POINT, pGeoBoundary.getGeoPoints());

        try {
            // IllegalArgumentException - If the specified entity was incomplete.
            // ConcurrentModificationException - If the entity group to which the entity belongs was modified concurrently.
            // DatastoreFailureException - If any other datastore error occurs.
            geoBoundaryDatastore.put(geoBoundary);
        } catch (IllegalArgumentException iae) {
            log.log(Level.WARNING, iae.toString(), iae);
        } catch (ConcurrentModificationException cme) {
            log.log(Level.WARNING, cme.toString(), cme);
        } catch (DatastoreFailureException dfe) {
            log.log(Level.WARNING, dfe.toString(), dfe);
        }
    }
}
