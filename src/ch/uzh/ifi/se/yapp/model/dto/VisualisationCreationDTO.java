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

import ch.uzh.ifi.se.yapp.model.base.VisualizationType;


public class VisualisationCreationDTO
        extends BaseDTO
        implements Serializable {

    private String            mTitle;
    private String            mAuthor;
    private String            mElectionId;
    private VisualizationType mVisualizationType;
    private String            mComment;


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

    public String getElectionId() {
        return mElectionId;
    }

    public void setElectionId(String pElectionId) {
        mElectionId = pElectionId;
    }

    public VisualizationType getVisualizationType() {
        return mVisualizationType;
    }

    public void setVisualizationType(VisualizationType pVisualizationType) {
        mVisualizationType = pVisualizationType;
    }

    public String getComment() {
        return mComment;
    }

    public void setComment(String pComment) {
        mComment = pComment;
    }

}
