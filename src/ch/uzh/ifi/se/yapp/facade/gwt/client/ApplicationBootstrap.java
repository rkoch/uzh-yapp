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
package ch.uzh.ifi.se.yapp.facade.gwt.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import ch.uzh.ifi.se.yapp.model.dto.ElectionDTO;
import ch.uzh.ifi.se.yapp.model.dto.VisualisationDTO;


/**
 * @author rko
 */
public class ApplicationBootstrap
        implements EntryPoint {

    private final VerticalPanel     mMainPanel;
    private final IYappServiceAsync mService;

    private TextBox                 mTitleInput;
    private TextBox                 mAuthorInput;
    private ListBox                 mYearInput;
    private ListBox                 mElectionInput;
    private TextArea                mCommentInput;


    public ApplicationBootstrap() {
        mMainPanel = new VerticalPanel();
        mService = GWT.create(IYappService.class);
    }


    @Override
    public void onModuleLoad() {
        // Get basic data

        // TODO Create main layout

        // Get display type (display or create)
        String id = Window.Location.getParameter("id");
        if (id == null) {
            // We are in creation state
            mService.getElections(new AsyncCallback<ElectionDTO[]>() {

                @Override
                public void onSuccess(ElectionDTO[] pResult) {
                    buildCreateMask(pResult);
                }

                @Override
                public void onFailure(Throwable pCaught) {
                    // TODO Display error message
                }
            });
        } else {
            // We are in visualisation state
            mService.getVisualisation(id, new AsyncCallback<VisualisationDTO>() {

                @Override
                public void onSuccess(VisualisationDTO pResult) {
                    buildVisualizeMask(pResult);
                }

                @Override
                public void onFailure(Throwable pCaught) {
                    // TODO Display error message
                }

            });
        }

        RootPanel.get("main-panel").add(mMainPanel);
    }


    private void buildCreateMask(final ElectionDTO[] pData) {
        mMainPanel.clear();

        KeyDownHandler enterHandler = new KeyDownHandler() {

            @Override
            public void onKeyDown(KeyDownEvent pEvent) {
                if (pEvent.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    validateAndSubmitForm();
                }
            }
        };

        // heading
        HorizontalPanel panelHeading = new HorizontalPanel();
        panelHeading.add(new HTML("<h1>Visualisierung erzeugen</h1>"));
        mMainPanel.add(panelHeading);

        // title
        HorizontalPanel panelTitle = new HorizontalPanel();
        mMainPanel.add(panelTitle);

        addFormLabel(panelTitle, "Titel");
        mTitleInput = new TextBox();
        panelTitle.add(mTitleInput);
        mTitleInput.addKeyDownHandler(enterHandler);

        // author
        HorizontalPanel panelAuthor = new HorizontalPanel();
        mMainPanel.add(panelAuthor);

        addFormLabel(panelAuthor, "Autor");
        mAuthorInput = new TextBox();
        panelAuthor.add(mAuthorInput);
        mAuthorInput.addKeyDownHandler(enterHandler);

        // year
        HorizontalPanel panelYear = new HorizontalPanel();
        mMainPanel.add(panelYear);

        addFormLabel(panelYear, "Jahr");
        mYearInput = new ListBox();
        panelYear.add(mYearInput);
        mYearInput.addKeyDownHandler(enterHandler);
        mYearInput.addItem(""); // empty item
        List<String> years = extractYears(pData);
        for (String y : years) {
            mYearInput.addItem(y);
        }

        // Change handler for year and election
        mYearInput.addChangeHandler(new ChangeHandler() {

            @Override
            public void onChange(ChangeEvent pEvent) {
                String year = mYearInput.getValue(mYearInput.getSelectedIndex());
                mElectionInput.clear();
                if ((year != null) && (year.length() == 4)) {
                    for (ElectionDTO e : pData) {
                        if (e.getDate().substring(0, 4).equals(year)) {
                            mElectionInput.addItem(e.getId() + ": " + e.getTitle(), e.getId());
                        }
                    }
                }
            }

        });

        // election
        HorizontalPanel panelElection = new HorizontalPanel();
        mMainPanel.add(panelElection);

        addFormLabel(panelElection, "Abstimmung");
        mElectionInput = new ListBox();
        panelElection.add(mElectionInput);
        mElectionInput.addKeyDownHandler(enterHandler);

        // Comment
        HorizontalPanel panelCommentTitle = new HorizontalPanel();
        mMainPanel.add(panelCommentTitle);
        addFormLabel(panelCommentTitle, "Kommentar");

        HorizontalPanel panelComment = new HorizontalPanel();
        mMainPanel.add(panelComment);
        mCommentInput = new TextArea();
        panelComment.add(mCommentInput);

        // Save button
        HorizontalPanel panelSave = new HorizontalPanel();
        mMainPanel.add(panelSave);

        Button saveBtn = new Button("Speichern");
        panelSave.add(saveBtn);
        saveBtn.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent pEvent) {
                validateAndSubmitForm();
            }

        });

        mTitleInput.setFocus(true);
    }


    private void buildVisualizeMask(final VisualisationDTO pData) {
        mMainPanel.clear();

        // heading
//        HorizontalPanel panelHeading = new HorizontalPanel();
//        panelHeading.add(new HTML("<h1>Visualisierung erzeugen</h1>"));
//        mMainPanel.add(panelHeading);

        // title
//        HorizontalPanel panelTitle = new HorizontalPanel();
//        mMainPanel.add(panelTitle);

    }


    private void validateAndSubmitForm() {
        // validate
        if (mTitleInput.getValue().isEmpty()) {
            mTitleInput.setFocus(true);
            mTitleInput.addStyleName(HTMLConst.CSS_FORM_ERROR);
            return;
        } else {
            mTitleInput.removeStyleName(HTMLConst.CSS_FORM_ERROR);
        }

        if (mAuthorInput.getValue().isEmpty()) {
            mAuthorInput.setFocus(true);
            mAuthorInput.addStyleName(HTMLConst.CSS_FORM_ERROR);
            return;
        } else {
            mAuthorInput.removeStyleName(HTMLConst.CSS_FORM_ERROR);
        }

        if (mYearInput.getValue(mYearInput.getSelectedIndex()).isEmpty()) {
            mYearInput.setFocus(true);
            mYearInput.addStyleName(HTMLConst.CSS_FORM_ERROR);
            return;
        } else {
            mYearInput.removeStyleName(HTMLConst.CSS_FORM_ERROR);
        }

        if (mElectionInput.getValue(mElectionInput.getSelectedIndex()).isEmpty()) {
            mElectionInput.setFocus(true);
            mElectionInput.addStyleName(HTMLConst.CSS_FORM_ERROR);
            return;
        } else {
            mElectionInput.removeStyleName(HTMLConst.CSS_FORM_ERROR);
        }

        // Submit

    }


    private void addFormLabel(Panel pParent, String pTitle) {
        Label l = new Label(pTitle);
        l.addStyleName(HTMLConst.CSS_FORM_LABEL);
        pParent.add(l);
    }


    private List<String> extractYears(ElectionDTO[] pElections) {
        List<String> ret = new ArrayList<String>();
        for (ElectionDTO e : pElections) {
            String date = e.getDate();
            String year = date.substring(0, 4);
            if (!ret.contains(year)) {
                ret.add(year);
            }
        }

        return ret;
    }

}
