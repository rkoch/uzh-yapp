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

import ch.uzh.ifi.se.yapp.backend.accif.IElectionDataAdapter;
import ch.uzh.ifi.se.yapp.model.landscape.Election;
import ch.uzh.ifi.se.yapp.util.BaseObject;


public class MockElectionAdapter
        extends BaseObject
        implements IElectionDataAdapter {

    private Election e = new Election();
    private Election b = new Election();



    @Override
    public void cleanup() {
        // TODO Auto-generated method stub
    }


    @Override
    public Election getElectionById(String pId) {
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
        tmpMap.put("552.1", e);
        tmpMap.put("552.2", b);
        return tmpMap;
    }


    @Override
    public void insertElection(Election pElection) {
        // DateTime is in object pElection

        b.setId(pElection.getId());
        b.setDescription(pElection.getDescription());
        b.setDate(pElection.getDate());
        b.setTitle(pElection.getTitle());
        b.setResults(pElection.getResults());
    }
}
