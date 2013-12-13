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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.uzh.ifi.se.yapp.backend.accif.ILandscapeDataAdapter;
import ch.uzh.ifi.se.yapp.backend.base.EntityNotFoundException;
import ch.uzh.ifi.se.yapp.model.landscape.Canton;
import ch.uzh.ifi.se.yapp.model.landscape.District;
import ch.uzh.ifi.se.yapp.util.BaseObject;


public class MemcachedLandscapeAdapter
        extends BaseObject
        implements ILandscapeDataAdapter {

    private final Map<String, District> mDistrictStorage;
    private final Map<String, Canton> mCantonStorage;
    private final Map<String, String> mDistrictNameStorage;

    public MemcachedLandscapeAdapter() {
        mDistrictStorage = new HashMap<>();
        mCantonStorage = new HashMap<>();
        mDistrictNameStorage = new HashMap<>();
    }

    @Override
    public void cleanup() {
    }

    @Override
    public District getDistrictById(String pId)
            throws EntityNotFoundException {
        District ret = mDistrictStorage.get(pId);
        if (ret == null) {
            throw new EntityNotFoundException("District " + pId + " not found.");
        }
        return new District(ret);
    }

    @Override
    public String getDistrictIdByName(String pName)
            throws EntityNotFoundException {
        String ret = mDistrictNameStorage.get(pName);
        if(ret == null) {
            throw new EntityNotFoundException("District " + pName + " not found.");
        }
        return ret;
    }

    @Override
    public List<District> getAllDistricts() {
        List<District> retList = new ArrayList<>();
        for (Map.Entry<String, District> entry : mDistrictStorage.entrySet()) {
            retList.add(entry.getValue());
        }
        Collections.sort(retList);
        return retList;
    }

    @Override
    public District insertDistrict(District pDistrict) {
        District tmp = new District(pDistrict);
        String id = pDistrict.getId();
        String name =pDistrict.getName();
        mDistrictStorage.put(id, tmp);
        mDistrictNameStorage.put(name, id);
        return tmp;
    }

    @Override
    public Canton getCantonById(String pId)
            throws EntityNotFoundException {
        Canton ret = mCantonStorage.get(pId);
        if (ret == null) {
            throw new EntityNotFoundException("Canton " + pId + "not found.");
        }
        return ret;
    }

    @Override
    public List<Canton> getAllCantons() {
        List<Canton> ret = new ArrayList<>();
        for (Map.Entry<String, Canton> entry : mCantonStorage.entrySet()) {
            ret.add(new Canton(entry.getValue()));
        }
        return ret;
    }

    @Override
    public Canton insertCanton(Canton pCanton) {
        Canton tmp = new Canton(pCanton);
        String tmpId = pCanton.getId();
        mCantonStorage.put(tmpId, tmp);
        return tmp;
    }
}
