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
package ch.uzh.ifi.se.yapp.model.visualisation;

import java.util.UUID;

import ch.uzh.ifi.se.yapp.model.base.AdministrativeUnit;
import ch.uzh.ifi.se.yapp.model.base.VisualizationType;
import ch.uzh.ifi.se.yapp.util.BaseObject;


public class Visualisation
        extends BaseObject
        implements Comparable<Visualisation> {

    private String             mId;
    private String             mTitle;
    private VisualizationType  mType;
    private AdministrativeUnit mDetail;
    private String             mAuthor;
    private String             mComment;
    private String             mElection;


    public Visualisation() {
        mId = UUID.randomUUID().toString();
    }


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

    public VisualizationType getType() {
        return mType;
    }

    public void setType(VisualizationType pType) {
        mType = pType;
    }

    public AdministrativeUnit getDetail() {
        return mDetail;
    }

    public void setDetail(AdministrativeUnit pDetail) {
        mDetail = pDetail;
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

    public String getElection() {
        return mElection;
    }

    public void setElection(String pElection) {
        mElection = pElection;
    }


    @Override
    public int compareTo(Visualisation pOther) {
        if (Double.parseDouble(mElection) < Double.parseDouble(pOther.mElection)) {
            return -1;
        } else if (Double.parseDouble(mElection) == Double.parseDouble(pOther.mElection)) {
            return 0;
        } else if (Double.parseDouble(mElection) > Double.parseDouble(pOther.mElection)) {
            return 1;
        }
        return 0;
    }

}
