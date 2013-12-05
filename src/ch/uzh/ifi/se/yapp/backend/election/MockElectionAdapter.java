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

    private Election mElectionA = new Election();
    private Election mElectionB = new Election();



    @Override
    public void cleanup() {
        // TODO Auto-generated method stub
    }


    @Override
    public Election getElectionById(String pId) {
        mElectionA.setId(pId);
        mElectionA.setTitle("Dummy Election");
        mElectionA.setDescription("leere Beschreibung");
        return mElectionA;
    }

    @Override
    public List<Election> getElectionsByDateRange(LocalDate pDate1, LocalDate pDate2) {
        Election e = new Election();
        e.setId("552.1");
        e.setTitle("Volksinitiative «Für die Ausschaffung krimineller Ausländer»");
        e.setDescription("leere Beschreibung");

        mElectionB.setId("552.2");
        mElectionB
                .setTitle("Bundesbeschluss über die Aus- und Wegweisung krimineller Ausländerinnen und Ausländer im Rahmen der Bundesverfassung (Gegenentwurf zur Ausschaffungsinitiative)");
        mElectionB.setDescription("leere Beschreibung");

        List<Election> list = new ArrayList<>();
        list.add(e);
        list.add(mElectionB);
        return list;
    }

    @Override
    public Map<String, Election> listElections() {
        Map<String, Election> tmpMap = new HashMap<>();
        Election el = new Election();
        el.setId("552.1");
        el.setDate(new LocalDate(2009, 9, 23));
        el.setTitle("Volksinitiative «Für die Ausschaffung krimineller Ausländer»");
        tmpMap.put(el.getId(), el);
        el = new Election();
        el.setId("552.2");
        el.setDate(new LocalDate(2009, 9, 24));
        el.setTitle("Bundeschbeschluss über die Aus- und Wegweisung krimineller Ausländerinnen und Ausländer im Rahmen der Bundesverfassung (Gegenentwurf zur Ausschaffungsinitiative)");
        tmpMap.put(el.getId(), el);
        el = new Election();
        el.setId("553");
        el.setDate(new LocalDate(2010, 9, 23));
        el.setTitle("Volksinitiative «Für die Ausschaffung krimineller Ausländer»");
        tmpMap.put(el.getId(), el);
        return tmpMap;
    }


    @Override
    public void insertElection(Election pElection) {
        // DateTime is in object pElection
        mElectionB.setId(pElection.getId());
        mElectionB.setDescription(pElection.getDescription());
        mElectionB.setDate(pElection.getDate());
        mElectionB.setTitle(pElection.getTitle());
    }

}
