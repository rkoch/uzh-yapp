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
package ch.uzh.ifi.se.yapp.backend.landscape;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
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

import ch.uzh.ifi.se.yapp.backend.accif.ILandscapeDataAdapter;
import ch.uzh.ifi.se.yapp.backend.base.EntityConst;
import ch.uzh.ifi.se.yapp.backend.persistence.DatastoreFactory;
import ch.uzh.ifi.se.yapp.model.landscape.District;
import ch.uzh.ifi.se.yapp.util.BaseObject;


public class LandscapeAdapter
        extends BaseObject
        implements ILandscapeDataAdapter {

    /**
     * Logger to list exceptions and errors for this class.
     */
    private static final Logger LOGGER = BaseObject.getLogger(LandscapeAdapter.class);


    @Override
    public void cleanup() {
    }

    @Override
    public District getDistrictById(String pId) {
        Filter idFilter = new FilterPredicate(EntityConst.ID, FilterOperator.EQUAL, pId);
        Query districtQuery = new Query(EntityConst.DISTRICT).setFilter(idFilter);
        PreparedQuery pq = DatastoreFactory.getDistrictDatastore().prepare(districtQuery);
        District tmp = new District();

        for (Entity result : pq.asIterable()) {
            tmp.setId((String) result.getProperty(EntityConst.ID));
            tmp.setName((String) result.getProperty(EntityConst.NAME));
            tmp.setCantonId((String) result.getProperty(EntityConst.CANTON_ID));
            tmp.setCanton((String) result.getProperty(EntityConst.CANTON));
            tmp.setLocalDate((LocalDate) result.getProperty(EntityConst.LOCAL_DATE));
        }
        return tmp;
    }

    @Override
    public District getDistrictByIdAndDate(String pId, LocalDate pDate) {
        // create a new datastore query with a filter to retrieve only
        // results older or equal than pDate
        District newestDistrict = new District();

        try {
            // IllegalArgumentException - If the provided filter values are not supported.
            Filter dateMaxFilter = new FilterPredicate(EntityConst.LOCAL_DATE, FilterOperator.LESS_THAN_OR_EQUAL, pDate.toString());

            Query districtQuery = new Query(EntityConst.DISTRICT).setFilter(dateMaxFilter);
            PreparedQuery pq = DatastoreFactory.getDistrictDatastore().prepare(districtQuery);

            // tmpMaxDate is newest Change of District pDistrictId
            LocalDate tmpMaxDate = new LocalDate(1848, 1, 1); // year, month, dayOfmonth

            // go over all received results
            for (Entity result : pq.asIterable()) {
                District tmp = new District();
                tmp.setId((String) result.getProperty(EntityConst.ID));
                tmp.setName((String) result.getProperty(EntityConst.NAME));
                tmp.setCantonId((String) result.getProperty(EntityConst.CANTON_ID));
                tmp.setCanton((String) result.getProperty(EntityConst.CANTON));
                // create LocalDate out of a String
                String str = (String) result.getProperty(EntityConst.LOCAL_DATE);
                LocalDate ld = new LocalDate(str);
                tmp.setLocalDate(ld);
                // check if tmp.localDate is newer or not
                if (tmpMaxDate.isBefore(tmp.getLocalDate())) {
                    tmpMaxDate = tmp.getLocalDate();
                    newestDistrict = tmp;
                }
            }
        } catch (IllegalArgumentException iae) {
            LOGGER.log(Level.WARNING, iae.toString(), iae);
        }
        return newestDistrict;
    }

    @Override
    public List<District> getAllDistricts() {
        List<District> tmpList = new ArrayList<>();
        Query districtQuery = new Query(EntityConst.DISTRICT);

        try {
            // NullPointerException - If any argument is null.
            districtQuery.addSort(EntityConst.ID);
        } catch (NullPointerException npe) {
            LOGGER.log(Level.WARNING, npe.toString(), npe);
        }

        PreparedQuery pq = DatastoreFactory.getDistrictDatastore().prepare(districtQuery);

        for (Entity result : pq.asIterable()) {
            District tmp = new District();
            tmp.setId((String) result.getProperty(EntityConst.ID));
            tmp.setName((String) result.getProperty(EntityConst.NAME));
            tmp.setCantonId((String) result.getProperty(EntityConst.CANTON_ID));
            tmp.setCanton((String) result.getProperty(EntityConst.CANTON));
            // create LocalDate out of a String
            String str = (String) result.getProperty(EntityConst.LOCAL_DATE);
            LocalDate ld = new LocalDate(str);
            tmp.setLocalDate(ld);

            try {
                // UnsupportedOperationException - if the add operation is not supported by this list
                // ClassCastException - if the class of the specified element prevents it from being added to this list
                // NullPointerException - if the specified element is null and this list does not permit null elements
                // IllegalArgumentException - if some property of this element prevents it from being added to this list
                tmpList.add(tmp);
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

    @Override
    public void insertDistrict(District pDistrict) {
        Entity district = new Entity(EntityConst.DISTRICT, pDistrict.getId());

        district.setProperty(EntityConst.ID, pDistrict.getId());
        district.setProperty(EntityConst.NAME, pDistrict.getName());
        district.setProperty(EntityConst.CANTON_ID, pDistrict.getCantonId());
        district.setProperty(EntityConst.CANTON, pDistrict.getCanton());
        district.setProperty(EntityConst.LOCAL_DATE, pDistrict.getLocalDate().toString());

        try {
            // IllegalArgumentException - If the specified entity was incomplete.
            // ConcurrentModificationException - If the entity group to which the entity belongs was modified concurrently.
            // DatastoreFailureException - If any other datastore error occurs.
            DatastoreFactory.getDistrictDatastore().put(district);
        } catch (IllegalArgumentException iae) {
            LOGGER.log(Level.WARNING, iae.toString(), iae);
        } catch (ConcurrentModificationException cme) {
            LOGGER.log(Level.WARNING, cme.toString(), cme);
        } catch (DatastoreFailureException dfe) {
            LOGGER.log(Level.WARNING, dfe.toString(), dfe);
        }
    }
}
