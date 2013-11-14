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
package ch.uzh.ifi.se.yapp.backend.election;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.joda.time.LocalDate;

import com.google.appengine.api.datastore.DatastoreFailureException;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;

import ch.uzh.ifi.se.yapp.backend.accif.IElectionDataAdapter;
import ch.uzh.ifi.se.yapp.backend.base.EntityConst;
import ch.uzh.ifi.se.yapp.model.landscape.DistrictResult;
import ch.uzh.ifi.se.yapp.model.landscape.Election;
import ch.uzh.ifi.se.yapp.util.BaseObject;


public class ElectionAdapter
        extends BaseObject
        implements IElectionDataAdapter {

    /**
     * Datastore for Elections.
     */
    private DatastoreService electionDatastore = DatastoreServiceFactory.getDatastoreService();

    /**
     * Logger to list exceptions and errors for this class.
     */
    private Logger           log               = BaseObject.getLogger(ElectionAdapter.class);




    @Override
    public void cleanup() {
    }


    @SuppressWarnings("unchecked")
    @Override
    public Election getElectionById(String pId) {
        Election tmpElection = new Election();
        Filter idFilter = new FilterPredicate(EntityConst.ID, FilterOperator.EQUAL, pId);

        Query electionQuery = new Query(EntityConst.ELECTION).setFilter(idFilter);
        PreparedQuery pq = electionDatastore.prepare(electionQuery);

        int ctr = 0;
        for (Entity result : pq.asIterable()) {
            // more than one result -> Error
            if (ctr > 1) {
                // TODO throw Exception: more than one result for the same id
            }
            tmpElection.setId((String) result.getProperty(EntityConst.ID));
            tmpElection.setTitle((String) result.getProperty(EntityConst.TITLE));
            tmpElection.setDescription((String) result.getProperty(EntityConst.DESCRIPTION));
            // Note: conversion from object to List<DistrictResult> may be unsafely
            tmpElection.setResults((List<DistrictResult>) result.getProperty(EntityConst.DISTRICT_RESULT));
            tmpElection.setDate((LocalDate) result.getProperty(EntityConst.LOCAL_DATE));
            ++ctr;
        }
        return tmpElection;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Election> getElectionsByDateRange(LocalDate pDate1, LocalDate pDate2) {
        List<Election> tmpList = new ArrayList<>();

        // create a new datastore query with a filter to retreive only
        // results between pDate1 and pDate2
        try {
            // IllegalArgumentException - If the provided filter values are not supported.
            Filter dateMinFilter = new FilterPredicate(EntityConst.LOCAL_DATE, FilterOperator.GREATER_THAN_OR_EQUAL, pDate1);
            Filter dateMaxFilter = new FilterPredicate(EntityConst.LOCAL_DATE, FilterOperator.LESS_THAN_OR_EQUAL, pDate2);
            Filter dateRangeFilter = new CompositeFilter(CompositeFilterOperator.AND, Arrays.asList(dateMinFilter, dateMaxFilter));


            Query electionQuery = new Query(EntityConst.ELECTION).setFilter(dateRangeFilter);
            PreparedQuery pq = electionDatastore.prepare(electionQuery);

            // go over all received results
            for (Entity result : pq.asIterable()) {
                Election tmpElection = new Election();
                tmpElection.setId((String) result.getProperty(EntityConst.ID));
                tmpElection.setTitle((String) result.getProperty(EntityConst.TITLE));
                tmpElection.setDescription((String) result.getProperty(EntityConst.DESCRIPTION));
                // TODO: Note: conversion from object to List<DistrictResult> may be unsafely
                tmpElection.setResults((List<DistrictResult>) result.getProperty(EntityConst.DISTRICT_RESULT));
                tmpElection.setDate((LocalDate) result.getProperty(EntityConst.LOCAL_DATE));
                // add tmpElection to tmpMap
                // UnsupportedOperationException - if the put operation is not supported by this map
                // ClassCastException - if the class of the specified key or value prevents it from being stored in this map
                // NullPointerException - if the specified key or value is null and this map does not permit null keys or values
                // IllegalArgumentException - if some property of the specified key or value prevents it from being stored in this map
                tmpList.add(tmpElection);
            }

        } catch (IllegalArgumentException iae) {
            log.log(Level.WARNING, iae.toString(), iae);
        } catch (UnsupportedOperationException uoe) {
            log.log(Level.WARNING, uoe.toString(), uoe);
        } catch (ClassCastException cce) {
            log.log(Level.WARNING, cce.toString(), cce);
        } catch (NullPointerException npe) {
            log.log(Level.WARNING, npe.toString(), npe);
        }
        return tmpList;
    }

    @Override
    public Map<String, Election> listElections() {
        Map<String, Election> tmpMap = new HashMap<>();

        // Query of Kind "Election", sort results by ID (ascending)
        Query electionQuery = new Query(EntityConst.ELECTION);

        try {
            // NullPointerException - If any argument is null.
            electionQuery.addSort(EntityConst.ID, SortDirection.ASCENDING);
        } catch (NullPointerException npe) {
            log.log(Level.WARNING, npe.toString(), npe);
        }
        // retreive results
        PreparedQuery pq = electionDatastore.prepare(electionQuery);

        // go over all received results
        for (Entity result : pq.asIterable()) {
            Election tmpElection = new Election();
            tmpElection.setId((String) result.getProperty(EntityConst.ID));
            tmpElection.setTitle((String) result.getProperty(EntityConst.TITLE));
            tmpElection.setDescription((String) result.getProperty(EntityConst.DESCRIPTION));
            tmpElection.setDate((LocalDate) result.getProperty(EntityConst.LOCAL_DATE));

            // add tmpElection to tmpMap
            try {
                // UnsupportedOperationException - if the put operation is not supported by this map
                // ClassCastException - if the class of the specified key or value prevents it from being stored in this map
                // NullPointerException - if the specified key or value is null and this map does not permit null keys or values
                // IllegalArgumentException - if some property of the specified key or value prevents it from being stored in this map
                tmpMap.put(tmpElection.getId(), tmpElection);
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
        return tmpMap;
    }


    @Override
    public void insertElection(Election pElection) {
        Entity election = new Entity(EntityConst.ELECTION, pElection.getId());

        election.setProperty(EntityConst.ID, pElection.getId());
        election.setProperty(EntityConst.TITLE, pElection.getTitle());
        election.setProperty(EntityConst.DESCRIPTION, pElection.getDescription());
        election.setProperty(EntityConst.DISTRICT_RESULT, pElection.getResults());
        election.setProperty(EntityConst.LOCAL_DATE, pElection.getDate());

        try {
            // IllegalArgumentException - If the specified entity was incomplete.
            // ConcurrentModificationException - If the entity group to which the entity belongs was modified concurrently.
            // DatastoreFailureException - If any other datastore error occurs.
            electionDatastore.put(election);
        } catch (IllegalArgumentException iae) {
            log.log(Level.WARNING, iae.toString(), iae);
        } catch (ConcurrentModificationException cme) {
            log.log(Level.WARNING, cme.toString(), cme);
        } catch (DatastoreFailureException dfe) {
            log.log(Level.WARNING, dfe.toString(), dfe);
        }
    }
}
