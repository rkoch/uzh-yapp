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
package ch.uzh.ifi.se.yapp.backend.visualisation;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.DatastoreFailureException;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;

import ch.uzh.ifi.se.yapp.backend.accif.IVisualizationDataAdapter;
import ch.uzh.ifi.se.yapp.backend.base.EntityConst;
import ch.uzh.ifi.se.yapp.backend.persistence.DatastoreFactory;
import ch.uzh.ifi.se.yapp.model.base.VisualizationType;
import ch.uzh.ifi.se.yapp.model.visualisation.Visualization;
import ch.uzh.ifi.se.yapp.util.BaseObject;


public class VisualizationAdapter
        extends BaseObject
        implements IVisualizationDataAdapter {

    /**
     * Logger to list exceptions and errors for this class.
     */
    private Logger LOGGER = BaseObject.getLogger(VisualizationAdapter.class);

    @Override
    public void cleanup() {
    }

    @Override
    public Visualization getVisualizationById(String pId) {
        Filter idFilter = new FilterPredicate(EntityConst.ID, FilterOperator.EQUAL, pId);

        Query visQuery = new Query(EntityConst.VISUALIZATION);
        visQuery.setFilter(idFilter);
        PreparedQuery pq = DatastoreFactory.getVisualizationDatastore().prepare(visQuery);

        Visualization resVis = new Visualization();
        for (Entity result : pq.asIterable()) {
            // set id
            String id = (String) result.getProperty(EntityConst.ID);
            resVis.setId(id);
            // set electionid
            resVis.setElectionId((String) result.getProperty(EntityConst.ELECTION_ID));
            // set type
            VisualizationType vt = VisualizationType.valueOf((String) result.getProperty(EntityConst.VISUALIZATION_TYPE));
            resVis.setType(vt);
        }
        return resVis;
    }

    @Override
    public List<Visualization> getAllVisualizations() {
        List<Visualization> tmpList = new ArrayList<>();

        Query visQuery = new Query(EntityConst.VISUALIZATION);
        try {
            // NullPointerException - If any argument is null.
            visQuery.addSort(EntityConst.ELECTION_ID, SortDirection.ASCENDING);
        } catch (NullPointerException npe) {
            LOGGER.log(Level.WARNING, npe.toString(), npe);
        }

        PreparedQuery pq = DatastoreFactory.getVisualizationDatastore().prepare(visQuery);

        for (Entity result : pq.asIterable()) {
            Visualization tmp = new Visualization();
            // set id
            String id = (String) result.getProperty(EntityConst.ID);
            tmp.setId(id);
            // setElection Id
            tmp.setElectionId((String) result.getProperty(EntityConst.ELECTION_ID));
            // set type
            VisualizationType vt = VisualizationType.valueOf((String) result.getProperty(EntityConst.VISUALIZATION_TYPE));
            tmp.setType(vt);
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
    public void insertVisualization(Visualization pVisualization) {
        Entity visualization = new Entity(EntityConst.VISUALIZATION);
        visualization.setProperty(EntityConst.ID, pVisualization.getId().toString());
        visualization.setProperty(EntityConst.ELECTION_ID, pVisualization.getElectionId());
        visualization.setProperty(EntityConst.VISUALIZATION_TYPE, pVisualization.getType().toString());

        try {
            // IllegalArgumentException - If the specified entity was incomplete.
            // ConcurrentModificationException - If the entity group to which the entity belongs was modified concurrently.
            // DatastoreFailureException - If any other datastore error occurs.
            DatastoreFactory.getVisualizationDatastore().put(visualization);
        } catch (IllegalArgumentException iae) {
            LOGGER.log(Level.WARNING, iae.toString(), iae);
        } catch (ConcurrentModificationException cme) {
            LOGGER.log(Level.WARNING, cme.toString(), cme);
        } catch (DatastoreFailureException dfe) {
            LOGGER.log(Level.WARNING, dfe.toString(), dfe);
        }
    }

}
