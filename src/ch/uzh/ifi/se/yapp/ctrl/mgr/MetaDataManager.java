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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import ch.uzh.ifi.se.yapp.backend.accif.BackendAccessorFactory;
import ch.uzh.ifi.se.yapp.backend.accif.IElectionDataAdapter;
import ch.uzh.ifi.se.yapp.ctrl.accif.IMetadataAccessor;
import ch.uzh.ifi.se.yapp.model.dto.ElectionDTO;
import ch.uzh.ifi.se.yapp.model.landscape.Election;


public class MetaDataManager
        extends AbstractManager
        implements IMetadataAccessor {

    @Override
    public List<ElectionDTO> getElectionList() {

        IElectionDataAdapter elecAdpt = BackendAccessorFactory.getElectionDataAdapter();

        List<ElectionDTO> list = new ArrayList<>();

        Map<String, Election> map = elecAdpt.listElections();

        for (Map.Entry<String, Election> entry : map.entrySet()) {
            Election elec = entry.getValue();
            ElectionDTO elecDTO = new ElectionDTO();
            elecDTO.setId(elec.getId());
            elecDTO.setTitle(elec.getTitle());
            elecDTO.setDate(elec.getDate().toString());
            list.add(elecDTO);
        }

        Collections.sort(list);

        return list;
    }

    @Override
    public List<ElectionDTO> getElectionsByDateRange(String pDate1, String pDate2) {

        IElectionDataAdapter elecAdpt = BackendAccessorFactory.getElectionDataAdapter();

        List<ElectionDTO> elecDtoList = new ArrayList<>();
        // convert String to LocalDate
        final DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
        final LocalDate dt1 = dtf.parseLocalDate(pDate1);
        final LocalDate dt2 = dtf.parseLocalDate(pDate2);
        List<Election> elecList = elecAdpt.getElectionsByDateRange(dt1, dt2);

        for (Election e : elecList) {
            ElectionDTO elecDTO = new ElectionDTO();
            elecDTO.setId(e.getId());
            elecDTO.setTitle(e.getTitle());
            // elecDTO.setDate(e.getDate().toString()); //not specified in MockElectionAdapter
            elecDtoList.add(elecDTO);
        }

        // Collections.sort(elecDtoList); //consequence of the above

        return elecDtoList;
    }

    @Override
    public ElectionDTO getElectionById(String pId) {
        ElectionDTO elecDTO = new ElectionDTO();
        Election elec = BackendAccessorFactory.getElectionDataAdapter().getElectionById(pId);
        elecDTO.setId(pId);
        elecDTO.setTitle(elec.getTitle());
        // elecDTO.setDate(elec.getDate().toString()); //not specified in MockElectionAdapter

        return elecDTO;
    }

}
