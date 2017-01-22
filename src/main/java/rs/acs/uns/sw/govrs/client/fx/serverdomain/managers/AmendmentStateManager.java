package rs.acs.uns.sw.govrs.client.fx.serverdomain.managers;


import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import rs.acs.uns.sw.govrs.client.fx.domain.Element;
import rs.acs.uns.sw.govrs.client.fx.editor.help.PopupEditorOptions;
import rs.acs.uns.sw.govrs.client.fx.editor.property_sheet.AmendmentTypePropertyItem;
import rs.acs.uns.sw.govrs.client.fx.editor.property_sheet.ButtonPropertyItem;
import rs.acs.uns.sw.govrs.client.fx.editor.property_sheet.PopupButtonPropertyItem;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.*;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.container.SelectionInfo;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.enums.AmendmentType;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.wrapper.ItemWrapper;
import rs.acs.uns.sw.govrs.client.fx.util.ElementTypes;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AmendmentStateManager {
    private Amendment amendment;
    private ButtonPropertyItem predmetPickerPropertyItem;
    private PopupButtonPropertyItem odredbaEditorPropertyItem;
    private AmendmentTypePropertyItem resenjePropertyItem;
    private PopupEditorOptions editorAttrs;
    private SelectionInfo selectionInfo;
    private ObjectProperty<AmendmentType> resenjeProperty;
    private ObjectProperty<SelectionInfo> selectionInfoProperty;
    private ObjectProperty<PopupEditorOptions> editorAttrsProperty;
    private String lawParent;

    public AmendmentStateManager(Amendment amendment) {
        this.amendment = amendment;
        lawParent = ((Amendments) amendment.getElementParent()).getHead().getPropis().getRef().getId();

        resenjeProperty = amendment.getHead().rjesenjeProperty();
        resenjePropertyItem = new AmendmentTypePropertyItem(
                resenjeProperty,
                "Predmet",
                "Rešenje",
                "Predlog rešenja",
                true);

        String predmetId = null;
        if (amendment.getHead().getPredmet().getRef() != null) {
            predmetId = amendment.getHead().getPredmet().getRef().getId();
        }
        selectionInfo = new SelectionInfo(
                lawParent,
                predmetId,
                getElementTypes(amendment)
        );
        selectionInfoProperty = new SimpleObjectProperty<>(selectionInfo);
        predmetPickerPropertyItem = new ButtonPropertyItem(
                selectionInfoProperty,
                "Predmet",
                "Predmet",
                "Element na koji se odnosi",
                true
        );
        System.out.println(selectionInfo);

        // ==================================== default selected case =================================================
        selectionInfo.elementTypeProperty().addListener((observable, oldValue, newValue) -> {
            if (amendment.getHead().rjesenjeProperty().get() == AmendmentType.Brisanje) {
                amendment.getBody().setOdredba(null);
            }
            if (amendment.getHead().rjesenjeProperty().get() == AmendmentType.Dopuna) {
                amendment.getBody().setOdredba(new Amendment.Body.Odredba());
                editorAttrs = new PopupEditorOptions("string", true, null, selectionInfo.getElementType());
                editorAttrsProperty.set(editorAttrs);
                odredbaEditorPropertyItem.property.set(editorAttrs);
                setNewOdredbaElement(editorAttrs.getElement(), editorAttrs.getTypeOfElement());
                editorAttrs.savedProperty().addListener((observable1, oldValue1, newValue1) -> {
                    if (newValue1) {
                        setNewOdredbaElement(editorAttrs.getElement(), editorAttrs.getTypeOfElement());
                        System.out.println("Saved");
                    }
                });
            }
            if (amendment.getHead().rjesenjeProperty().get() == AmendmentType.Izmena) {
                editorAttrs.savedProperty().addListener((observable1, oldValue1, newValue1) -> {
                    if (newValue1) {
                        amendment.getBody().setOdredba(new Amendment.Body.Odredba());
                        setNewOdredbaElement(editorAttrs.getElement(), editorAttrs.getTypeOfElement());
                        editorAttrsProperty.set(editorAttrs);
                        odredbaEditorPropertyItem.property.set(editorAttrs);
                        System.out.println("Saved Edit");
                    }
                });
            }
        });

        Element e = getElement(amendment, selectionInfo.getElementType());
        if (e != null) e.initElement();
        editorAttrs = new PopupEditorOptions("string", e == null, e, selectionInfo.getElementType());
        editorAttrsProperty = new SimpleObjectProperty<>(editorAttrs);
        odredbaEditorPropertyItem = new PopupButtonPropertyItem(
                editorAttrsProperty,
                "Predmet",
                "Odredba",
                "Nova ili izmenjena odredba",
                true
        );
        System.out.println(editorAttrs);


        resenjeProperty.addListener((observable, oldValue, newValue) -> {
            selectionInfo = new SelectionInfo(
                    lawParent,
                    null,
                    ElementTypes.None
            );
            editorAttrs = new PopupEditorOptions("string", true, null, ElementTypes.None);
            selectionInfoProperty.set(selectionInfo);
            editorAttrsProperty.set(editorAttrs);

            selectionInfoProperty.get().elementIdProperty().addListener((o, ov, nv) -> {
                if (nv != null) {
                    Ref ref = new Ref();
                    ref.setContent("");
                    ref.setId(nv);
                    Amendment.Head.Predmet p = new Amendment.Head.Predmet();
                    p.setRef(ref);
                    amendment.getHead().setPredmet(p);
                }
            });

            odredbaEditorPropertyItem.property.set(editorAttrs);
            predmetPickerPropertyItem.property.set(selectionInfo);

            amendment.getBody().setOdredba(new Amendment.Body.Odredba());
            amendment.getHead().setPredmet(new Amendment.Head.Predmet());

            // hide 'Odredba' field if not needed
            if (newValue == AmendmentType.Brisanje) {
                amendment.getPropertyItems().remove(odredbaEditorPropertyItem);
                amendment.getBody().setOdredba(null);
            } else {
                amendment.getBody().setOdredba(new Amendment.Body.Odredba());
                amendment.getPropertyItems().remove(odredbaEditorPropertyItem);
                odredbaEditorPropertyItem = new PopupButtonPropertyItem(
                        editorAttrsProperty,
                        "Predmet",
                        "Odredba",
                        "Nova ili izmenjena odredba",
                        true
                );
                amendment.getPropertyItems().add(odredbaEditorPropertyItem);
                if (newValue == AmendmentType.Dopuna) {
                    selectionInfo = new SelectionInfo(
                            lawParent,
                            null,
                            ElementTypes.None
                    );
                    selectionInfoProperty.set(selectionInfo);
                    selectionInfo.elementTypeProperty().addListener((observable1, oldValue1, newValue1) -> {
                        editorAttrs.setTypeOfElement(newValue1);
                        amendment.getBody().setOdredba(new Amendment.Body.Odredba());

                        odredbaEditorPropertyItem.property.set(editorAttrs);
                    });
                    odredbaEditorPropertyItem.property.set(editorAttrs);
                } else if (newValue == AmendmentType.Izmena) {
                    selectionInfo = new SelectionInfo(
                            lawParent,
                            null,
                            ElementTypes.None
                    );
                    selectionInfoProperty.set(selectionInfo);
                    selectionInfo.savedProperty().addListener((observable1, oldValue1, newValue1) -> {
                        editorAttrs.setTypeOfElement(selectionInfo.getElementType());
                        amendment.getBody().setOdredba(new Amendment.Body.Odredba());
                        editorAttrs.setElement(selectionInfo.getElement());
                        editorAttrs.setCreateNew(false);
                        System.out.println("EVO MENE");
                        System.out.println(selectionInfo.getElement());
                        System.out.println(editorAttrs);
                        odredbaEditorPropertyItem.property.set(editorAttrs);
                        setNewOdredbaElement(editorAttrs.getElement(), editorAttrs.getTypeOfElement());

                        Ref ref = new Ref();
                        ref.setContent("");
                        ref.setId(selectionInfo.getElementId());
                        Amendment.Head.Predmet p = new Amendment.Head.Predmet();
                        p.setRef(ref);
                        System.out.println(p.getRef().getId());
                        amendment.getHead().setPredmet(p);

                    });
                    odredbaEditorPropertyItem.property.set(editorAttrs);
                }
            }

        });

        selectionInfoProperty.get().elementIdProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("AAAAAAAAAA");
            if (newValue != null) {
                System.out.println(newValue);
                Ref ref = new Ref();
                ref.setContent("");
                ref.setId(newValue);
                Amendment.Head.Predmet p = new Amendment.Head.Predmet();
                p.setRef(ref);
                System.out.println(p.getRef().getId());
                amendment.getHead().setPredmet(p);
            }
        });
    }

    public Amendment getAmendment() {
        return amendment;
    }

    public ButtonPropertyItem getPredmetPickerPropertyItem() {
        return predmetPickerPropertyItem;
    }

    public PopupButtonPropertyItem getOdredbaEditorPropertyItem() {
        return odredbaEditorPropertyItem;
    }

    public AmendmentTypePropertyItem getResenjePropertyItem() {
        return resenjePropertyItem;
    }

    public PopupEditorOptions getEditorAttrs() {
        return editorAttrs;
    }

    public SelectionInfo getSelectionInfo() {
        return selectionInfo;
    }

    private ElementTypes getElementTypes(Amendment amendment) {
        Amendment.Body.Odredba odredba = amendment.getBody().getOdredba();
        if (odredba != null) {
            if (odredba.getAlineja() != null) {
                return ElementTypes.Item;
            } else if (odredba.getClan() != null) {
                return ElementTypes.Article;
            } else if (odredba.getPodtacka() != null) {
                return ElementTypes.Subclause;
            } else if (odredba.getStav() != null) {
                return ElementTypes.Paragraph;
            } else if (odredba.getTacka() != null) {
                return ElementTypes.Clause;
            } else {
                return ElementTypes.None;
            }
        } else {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Odredba ne postoji!");
            return ElementTypes.None;
        }
    }

    private Element getElement(Amendment amendment, ElementTypes type) {
        if (type == ElementTypes.Article) {
            if (amendment.getBody().getOdredba().getClan() != null) {
                return amendment.getBody().getOdredba().getClan();
            } else {
                return null;
            }
        }
        if (type == ElementTypes.Paragraph) {
            if (amendment.getBody().getOdredba().getStav() != null) {
                return amendment.getBody().getOdredba().getStav();
            } else {
                return null;
            }
        }
        if (type == ElementTypes.Clause) {
            if (amendment.getBody().getOdredba().getTacka() != null) {
                return amendment.getBody().getOdredba().getTacka();
            } else {
                return null;
            }
        }
        if (type == ElementTypes.Subclause) {
            if (amendment.getBody().getOdredba().getPodtacka() != null) {
                return amendment.getBody().getOdredba().getPodtacka();
            } else {
                return null;
            }
        }
        if (type == ElementTypes.Item) {
            if (amendment.getBody().getOdredba().getAlineja() != null) {
                return new ItemWrapper(amendment.getBody().getOdredba().getAlineja());
            } else {
                return null;
            }
        }
        return null;
    }

    public void setNewOdredbaElement(Element newElement, ElementTypes type) {
        if (type == ElementTypes.Article) {
            amendment.getBody().getOdredba().setClan((Article)newElement);
        }
        if (type == ElementTypes.Paragraph) {
            amendment.getBody().getOdredba().setStav((Paragraph)newElement);
        }
        if (type == ElementTypes.Clause) {
            amendment.getBody().getOdredba().setTacka((Clause)newElement);
        }
        if (type == ElementTypes.Subclause) {
            amendment.getBody().getOdredba().setPodtacka((Subclause)newElement);
        }
        if (type == ElementTypes.Item) {
            amendment.getBody().getOdredba().setAlineja(((ItemWrapper)newElement).getWrappedItem());
        }
    }
}
