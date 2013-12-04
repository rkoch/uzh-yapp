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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;

import ch.uzh.ifi.se.yapp.backend.accif.IElectionDataAdapter;
import ch.uzh.ifi.se.yapp.backend.base.EntityNotFoundException;
import ch.uzh.ifi.se.yapp.model.landscape.Election;
import ch.uzh.ifi.se.yapp.util.BaseObject;


public class MemcachedElectionAdapter
        extends BaseObject
        implements IElectionDataAdapter {

    private final Map<String, Election> mStorage;

    public MemcachedElectionAdapter() {
        mStorage = new HashMap<>();
    }

    @Override
    public void cleanup() {
    }

    @Override
    public Election getElectionById(String pId)
            throws EntityNotFoundException {
        Election ret = mStorage.get(pId);
        if (ret == null) {
            throw new EntityNotFoundException("Election " + pId + " not found.");
        }
        return ret;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Election> getElectionsByDateRange(LocalDate pDate1, LocalDate pDate2) {
        List<Election> retList = new ArrayList<>();
        for (Map.Entry<String, Election> entry : mStorage.entrySet()) {
            if (pDate1.isBefore(entry.getValue().getDate()) && pDate2.isAfter(entry.getValue().getDate())) {
                retList.add(entry.getValue());
            }
        }
        Collections.sort(retList);
        return retList;
    }

    @Override
    public Map<String, Election> listElections() {
        Map<String, Election> ret = new HashMap<>(mStorage);
        return ret;
    }

    @Override
    public void insertElection(Election pElection) {
        mStorage.put(pElection.getId(), pElection);
    }
}
