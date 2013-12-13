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
import java.util.List;

import ch.uzh.ifi.se.yapp.backend.accif.ILandscapeDataAdapter;
import ch.uzh.ifi.se.yapp.backend.base.EntityNotFoundException;
import ch.uzh.ifi.se.yapp.model.landscape.Canton;
import ch.uzh.ifi.se.yapp.model.landscape.District;
import ch.uzh.ifi.se.yapp.util.BaseObject;


public class MockLandscapeAdapter
        extends BaseObject
        implements ILandscapeDataAdapter {


    @Override
    public void cleanup() {

    }

    @Override
    public District getDistrictById(String pId) {
        District d = new District();
//          d.setId("Imboden");
//          d.setCanton("Graubünden");
        return d;
    }


    @Override
    public String getDistrictIdByName(String pName)
            throws EntityNotFoundException {
        String ret = new String();
        return ret;
    }

    @Override
    public List<District> getAllDistricts() {
        List<District> tmpList = new ArrayList<>();
        /*
         * District d1 = new District();
         * d1.setId("Imboden");
         * d1.setCanton("Graubünden");
         *
         * District d2 = new District();
         * d2.setId("Maloja");
         * d2.setCanton("Graubünden");
         *
         * District d3 = new District();
         * d3.setId("Winterthur");
         * d3.setCanton("Zürich");
         *
         * tmpList.add(d1);
         * tmpList.add(d2);
         * tmpList.add(d3);
         */
        return tmpList;
    }

    @Override
    public District insertDistrict(District pDistrict) {
        // DateTime is stored in District.mDateTime
        // using java data object (jdo)
        // google data store
        return null;
    }

    @Override
    public Canton getCantonById(String pId)
            throws EntityNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Canton> getAllCantons() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Canton insertCanton(Canton pCanton) {
        // TODO Auto-generated method stub
        return null;
    }

}
