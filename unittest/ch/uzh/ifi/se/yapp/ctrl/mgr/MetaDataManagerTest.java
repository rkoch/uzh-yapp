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
package ch.uzh.ifi.se.yapp.ctrl.mgr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import ch.uzh.ifi.se.yapp.ctrl.accif.AccessorFactory;
import ch.uzh.ifi.se.yapp.ctrl.accif.IMetadataAccessor;
import ch.uzh.ifi.se.yapp.model.dto.ElectionDTO;


public class MetaDataManagerTest {

    @Test
    public void testGetElectionList() {

        IMetadataAccessor metAdpt = AccessorFactory.getMetaDataAccessor();

        List<ElectionDTO> list = metAdpt.getElectionList();

        assertEquals(0, list.size());
    }

    @Test
    public void testGetElectionsByDateRange() {
        IMetadataAccessor metAdpt = AccessorFactory.getMetaDataAccessor();

        List<ElectionDTO> list = metAdpt.getElectionsByDateRange("1900-01-01", "1999-01-01");

        assertTrue(list.get(0).getId().equals("552.1"));
        assertTrue(list.get(1).getId().equals("552.2"));
        assertTrue(list.get(0).getTitle().equals("Volksinitiative «Für die Ausschaffung krimineller Ausländer»"));
        assertEquals(2, list.size());
    }

    @Test
    public void testGetElectoinById() {
        IMetadataAccessor metAdpt = AccessorFactory.getMetaDataAccessor();

        ElectionDTO elec = metAdpt.getElectionById("id");
        assertTrue(elec.getId().equals("id"));
        assertTrue(elec.getTitle().equals("Dummy Election"));
    }

}
