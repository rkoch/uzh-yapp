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
import java.util.Calendar;
import java.util.List;

import ch.uzh.ifi.se.yapp.backend.accif.ILandscapeDataAdapter;
import ch.uzh.ifi.se.yapp.model.landscape.District;
import ch.uzh.ifi.se.yapp.model.landscape.DistrictResult;
import ch.uzh.ifi.se.yapp.util.BaseObject;


public class MockLandscapeAdapter
        extends BaseObject
        implements ILandscapeDataAdapter {

    @Override
    public void cleanup() {
        // TODO Auto-generated method stub
    }

    @Override
    public District getDistrictById(String pId, Calendar pDate) {
        District d = new District();
        d.setId("Imboden");
        d.setCanton("Graubünden");
        return d;
    }

    @Override
    public List<District> getAllDistricts() {
        List<District> tmpList = new ArrayList<District>();

        District d1 = new District();
        d1.setId("Imboden");
        d1.setCanton("Graubünden");

        District d2 = new District();
        d2.setId("Maloja");
        d2.setCanton("Graubünden");

        District d3 = new District();
        d3.setId("Winterthur");
        d3.setCanton("Zürich");

        tmpList.add(d1);
        tmpList.add(d2);
        tmpList.add(d3);
        return tmpList;
    }

    @Override
    public DistrictResult getDistrictResultById(String pId) {
        District d = new District();
        d.setId("Imboden");
        d.setCanton("Graubünden");

        DistrictResult dr = new DistrictResult();
        dr.setDistrict(d);
        dr.setTotalEligibleCount(230);
        dr.setInvalidVoteCount(50);
        dr.setEmptyVoteCount(50);
        dr.setDeliveredVoteCount(100);
        dr.setNoVoteCount(10);
        dr.setYesVoteCount(70);

        return dr;
    }

    @Override
    public List<DistrictResult> getAllDistrictResults() {
        List<DistrictResult> tmpList = new ArrayList<DistrictResult>();

        District d = new District();
        d.setId("Zürich");
        d.setCanton("Zürich");

        District d2 = new District();
        d2.setId("Genève");
        d2.setCanton("Genève");

        DistrictResult dr = new DistrictResult();
        dr.setDistrict(d);
        dr.setTotalEligibleCount(230);
        dr.setInvalidVoteCount(50);
        dr.setEmptyVoteCount(50);
        dr.setDeliveredVoteCount(100);
        dr.setNoVoteCount(10);
        dr.setYesVoteCount(70);

        DistrictResult dr2 = new DistrictResult();
        dr2.setDistrict(d2);
        dr2.setTotalEligibleCount(230);
        dr2.setInvalidVoteCount(50);
        dr2.setEmptyVoteCount(50);
        dr2.setDeliveredVoteCount(100);
        dr2.setNoVoteCount(10);
        dr2.setYesVoteCount(70);

        tmpList.add(dr);
        tmpList.add(dr2);

        return tmpList;
    }



}
