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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

import ch.uzh.ifi.se.yapp.backend.accif.IElectionDataAdapter;
import ch.uzh.ifi.se.yapp.model.landscape.Election;
import ch.uzh.ifi.se.yapp.util.BaseObject;


public class MockElectionAdapter
        extends BaseObject
        implements IElectionDataAdapter {

    private DatastoreService electionDatastore = DatastoreServiceFactory.getDatastoreService();


    @Override
    public void cleanup() {
        // TODO Auto-generated method stub
    }


    @Override
    public Election getElectionById(String pId) {
        Election e = new Election();
        e.setId(pId);
        e.setTitle("Dummy Election");
        e.setDescription("leere Beschreibung");
        return e;
    }

    @Override
    public List<Election> getElectionsByDateRange(LocalDate pDate1, LocalDate pDate2) {
        Election e = new Election();
        e.setId("552.1");
        e.setTitle("Volksinitiative «Für die Ausschaffung krimineller Ausländer»");
        e.setDescription("leere Beschreibung");

        Election b = new Election();
        b.setId("552.2");
        b.setTitle("Bundesbeschluss über die Aus- und Wegweisung krimineller Ausländerinnen und Ausländer im Rahmen der Bundesverfassung (Gegenentwurf zur Ausschaffungsinitiative)");
        b.setDescription("leere Beschreibung");

        List<Election> list = new ArrayList<Election>();
        list.add(e);
        list.add(b);
        return list;
    }

    @Override
    public Map<String, Election> listElections() {
        Map<String, Election> tmpMap = new HashMap<>();
//        tmpMap.put("552.1", "Volksinitiative «Für die Ausschaffung krimineller Ausländer»");
//        tmpMap.put("552.2", "Bundeschbeschluss über die Aus- und Wegweisung krimineller Ausländerinnen und Ausländer im Rahmen der Bundesverfassung (Gegenentwurf zur Ausschaffungsinitiative)");
        return tmpMap;
    }


    @Override
    public void insertElection(Election pElection) {
        // DateTime is in object pElection

        Entity election = new Entity("Election", pElection.getId());

        election.setProperty("id", pElection.getId());
        election.setProperty("title", pElection.getTitle());
        election.setProperty("description", pElection.getDescription());
        election.setProperty("results", pElection.getResults());

        electionDatastore.put(election);
    }
}
