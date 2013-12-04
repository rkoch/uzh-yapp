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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;

import ch.uzh.ifi.se.yapp.backend.accif.IGeoDataAdapter;
import ch.uzh.ifi.se.yapp.backend.base.EntityNotFoundException;
import ch.uzh.ifi.se.yapp.model.geo.GeoBoundary;
import ch.uzh.ifi.se.yapp.util.BaseObject;


public class MemcachedGeoDataAdapter
        extends BaseObject
        implements IGeoDataAdapter {

    private final Map<String, GeoBoundary> mStorage;

    public MemcachedGeoDataAdapter() {
        mStorage = new HashMap<>();
    }

    @Override
    public void cleanup() {
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<GeoBoundary> getAllGeoBoundary() {
        List<GeoBoundary> retList = new ArrayList<>();
        for (Map.Entry<String, GeoBoundary> entry : mStorage.entrySet()) {
            retList.add(entry.getValue());
        }
        Collections.sort(retList);
        return retList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<GeoBoundary> getAllGeoBoundaryByDate(LocalDate pDate) {
        List<GeoBoundary> retList = new ArrayList<>();
        List<String> checkedIds = new ArrayList<>(); // list with already checked ids
        for (Map.Entry<String, GeoBoundary> entry : mStorage.entrySet()) {
            if ((entry.getValue().getLocalDate().isBefore(pDate) || entry.getValue().getLocalDate().isEqual(pDate))
                    && !checkedIds.contains(entry.getValue().getId())) {
                GeoBoundary tmpGb = entry.getValue();
                // get newest GeoBoundary for each GeoBoundary
                for (Map.Entry<String, GeoBoundary> innerEntry :  mStorage.entrySet()) {
                    if (innerEntry.getValue().getLocalDate().isAfter(tmpGb.getLocalDate())) {
                        tmpGb = innerEntry.getValue();
                    }
                }
                checkedIds.add(tmpGb.getId());
                retList.add(tmpGb);
            }
        }
        Collections.sort(retList);
        return retList;
    }

    @Override
    public GeoBoundary getGeoBoundaryByDistrictAndDate(String pDistrictId, LocalDate pDate)
            throws EntityNotFoundException {
        for (Map.Entry<String, GeoBoundary> entry : mStorage.entrySet()) {
            if (entry.getValue().getId() == pDistrictId && entry.getValue().getLocalDate().isEqual(pDate)) {
                return new GeoBoundary(entry.getValue());
            }
        }
        throw new EntityNotFoundException("District with id '" + pDistrictId + "' and LocalDate '" + pDate + "' not found");
    }

    @Override
    public void insertGeoBoundary(GeoBoundary pGeoBoundary) {
        String tmpId = pGeoBoundary.getId() + "_" + pGeoBoundary.getLocalDate().toString();
        mStorage.put(tmpId, pGeoBoundary);
    }
}
