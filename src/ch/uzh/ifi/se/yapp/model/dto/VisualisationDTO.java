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

import java.io.Serializable;
import java.util.List;

import ch.uzh.ifi.se.yapp.model.base.VisualizationType;


/**
 * @author rko
 */
public class VisualisationDTO
        extends BaseDTO
        implements Serializable {

    private String            mId;
    private String            mTitle;
    private String            mAuthor;
    private String            mComment;
    private VisualizationType mType;
    private ElectionDTO       mElection;
    private List<ResultDTO>   mResults;


    public String getId() {
        return mId;
    }

    public void setId(String pId) {
        mId = pId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String pTitle) {
        mTitle = pTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String pAuthor) {
        mAuthor = pAuthor;
    }

    public String getComment() {
        return mComment;
    }

    public void setComment(String pComment) {
        mComment = pComment;
    }

    public VisualizationType getType() {
        return mType;
    }

    public void setType(VisualizationType pType) {
        mType = pType;
    }

    public ElectionDTO getElection() {
        return mElection;
    }

    public void setElection(ElectionDTO pElection) {
        mElection = pElection;
    }

    public List<ResultDTO> getResults() {
        return mResults;
    }

    public void setResults(List<ResultDTO> pResults) {
        mResults = pResults;
    }

}
