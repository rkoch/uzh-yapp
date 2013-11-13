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
package ch.uzh.ifi.se.yapp.model.dto;

import java.util.List;

import ch.uzh.ifi.se.yapp.model.geo.GeoBoundary;
import ch.uzh.ifi.se.yapp.model.landscape.DistrictResult;


/**
 * @author rko
 */
public class VisualisationDTO
        extends BaseDTO {

    private String mId;
    private ElectionDTO mElection;
    private List<DistrictResult> mElectionResults;
    private List<GeoBoundary> mBoundary;  //NULL if GeoBoundary not necessary (visualization is a table)

    public void setId(String pId){
        mId = pId;
    }

    public String getId(){
        return mId;
    }

    public void setElectionDTO(ElectionDTO elecDTO){
        mElection = elecDTO;
    }

    public ElectionDTO getElectionDTO(){
        return mElection;
    }

    public void setElectionResults(List<DistrictResult> list){
        mElectionResults = list;
    }

    public List<DistrictResult> getElectionResults(){
        return mElectionResults;
    }

    public void setGeoBoundary(List<GeoBoundary> pBoundary){
        mBoundary = pBoundary;
    }

    public List<GeoBoundary> getGeoBoundary(){
        return mBoundary;
    }
}
