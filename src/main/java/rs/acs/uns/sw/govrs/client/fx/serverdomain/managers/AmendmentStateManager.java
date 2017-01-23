package rs.acs.uns.sw.govrs.client.fx.serverdomain.managers;


import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import rs.acs.uns.sw.govrs.client.fx.domain.Element;
import rs.acs.uns.sw.govrs.client.fx.editor.help.PopupEditorOptions;
import rs.acs.uns.sw.govrs.client.fx.editor.property_sheet.AmendmentTypePropertyItem;
import rs.acs.uns.sw.govrs.client.fx.editor.property_sheet.ButtonPropertyItem;
import rs.acs.uns.sw.govrs.client.fx.editor.property_sheet.PopupButtonPropertyItem;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.*;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.enums.AmendmentType;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.wrapper.ItemWrapper;
import rs.acs.uns.sw.govrs.client.fx.util.ElementType;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AmendmentStateManager {
    private Amendment amendment;
    private ButtonPropertyItem selectionInfoPropertyItem;
    private PopupButtonPropertyItem editorAttrsPropertyItem;
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

        // ========== initial resenje property
        resenjeProperty = amendment.getHead().rjesenjeProperty();
        resenjePropertyItem = new AmendmentTypePropertyItem(
                resenjeProperty,
                "Predmet",
                "Rešenje",
                "Predlog rešenja",
                true);

        // ========== initial selection info property
        String predmetId = null;
        if (amendment.getHead().getPredmet() != null) {
            if (amendment.getHead().getPredmet().getRef() != null)
                predmetId = amendment.getHead().getPredmet().getRef().getId();
        }
        selectionInfo = new SelectionInfo(
                lawParent,
                predmetId,
                getElementTypes(amendment)
        );
        selectionInfoProperty = new SimpleObjectProperty<>(selectionInfo);
        selectionInfoPropertyItem = new ButtonPropertyItem(
                selectionInfoProperty,
                "Predmet",
                "Predmet",
                "Element na koji se odnosi",
                true
        );
        System.out.println(selectionInfo);

        // ========== initial editor property
        Element e = getElement(amendment, selectionInfo.getElementType());
        if (e != null) e.initElement();
        editorAttrs = new PopupEditorOptions("string", e == null, e, selectionInfo.getElementType());
        editorAttrsProperty = new SimpleObjectProperty<>(editorAttrs);
        editorAttrsPropertyItem = new PopupButtonPropertyItem(
                editorAttrsProperty,
                "Predmet",
                "Odredba",
                "Nova ili izmenjena odredba",
                true
        );
        System.out.println(editorAttrs);

        // ==================================== default selected case =================================================
        selectionInfo.elementIdProperty().addListener((observable, oldValue, newValue) -> {
            if (amendment.getHead().rjesenjeProperty().get() == AmendmentType.Brisanje) {
                amendment.getBody().setOdredba(null);
                updateEditorAttrs(false);
            }

            if (amendment.getHead().rjesenjeProperty().get() == AmendmentType.Dopuna) {
                amendment.getBody().setOdredba(new Amendment.Body.Odredba());
                updateEditorAttrs(true);
            }

            if (amendment.getHead().rjesenjeProperty().get() == AmendmentType.Izmena) {
                updateEditorAttrs(false);
            }

            // update predmet
            if (newValue != null) {
                Ref ref = new Ref();
                ref.setContent("");
                ref.setId(newValue);
                Amendment.Head.Predmet p = new Amendment.Head.Predmet();
                p.setRef(ref);
                amendment.getHead().setPredmet(p);
            }
        });

        editorAttrs.savedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                setNewOdredbaElement(editorAttrs.getElement(), editorAttrs.getTypeOfElement());
                System.out.println("Saved!");
            }
        });


        resenjeProperty.addListener((observable, oldValue, newValue) -> {
            selectionInfo = new SelectionInfo(
                    lawParent,
                    null,
                    ElementType.None
            );
            selectionInfo.elementIdProperty().addListener((observable1, oldValue1, nv) -> {
                if (amendment.getHead().rjesenjeProperty().get() == AmendmentType.Brisanje) {
                    amendment.getBody().setOdredba(null);
                    updateEditorAttrs(false);
                }

                if (amendment.getHead().rjesenjeProperty().get() == AmendmentType.Dopuna) {
                    amendment.getBody().setOdredba(new Amendment.Body.Odredba());
                    updateEditorAttrs(true);
                }

                if (amendment.getHead().rjesenjeProperty().get() == AmendmentType.Izmena) {
                    updateEditorAttrs(false);
                }

                // update predmet
                if (nv != null) {
                    Ref ref = new Ref();
                    ref.setContent("");
                    ref.setId(nv);
                    Amendment.Head.Predmet p = new Amendment.Head.Predmet();
                    p.setRef(ref);
                    amendment.getHead().setPredmet(p);
                }
            });
            editorAttrs = new PopupEditorOptions("string", true, null, ElementType.None);
            selectionInfoProperty.set(selectionInfo);
            editorAttrsProperty.set(editorAttrs);
            editorAttrsPropertyItem.property.set(editorAttrs);
            selectionInfoPropertyItem.property.set(selectionInfo);

            // reset model
            amendment.getBody().setOdredba(new Amendment.Body.Odredba());
            amendment.getHead().setPredmet(new Amendment.Head.Predmet());

            // hide 'Odredba' field if not needed
            if (newValue == AmendmentType.Brisanje) {
                amendment.getPropertyItems().remove(editorAttrsPropertyItem);
                amendment.getBody().setOdredba(null);
            } else {
                amendment.getBody().setOdredba(new Amendment.Body.Odredba());
                amendment.getPropertyItems().remove(editorAttrsPropertyItem);
                editorAttrsPropertyItem = new PopupButtonPropertyItem(
                        editorAttrsProperty,
                        "Predmet",
                        "Odredba",
                        "Nova ili izmenjena odredba",
                        true
                );
                amendment.getPropertyItems().add(editorAttrsPropertyItem);

            }

        });

    }

    public Amendment getAmendment() {
        return amendment;
    }

    public ButtonPropertyItem getSelectionInfoPropertyItem() {
        return selectionInfoPropertyItem;
    }

    public PopupButtonPropertyItem getEditorAttrsPropertyItem() {
        return editorAttrsPropertyItem;
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

    private ElementType getElementTypes(Amendment amendment) {
        Amendment.Body.Odredba odredba = amendment.getBody().getOdredba();
        if (odredba != null) {
            if (odredba.getAlineja() != null) {
                return ElementType.Item;
            } else if (odredba.getClan() != null) {
                return ElementType.Article;
            } else if (odredba.getPodtacka() != null) {
                return ElementType.Subclause;
            } else if (odredba.getStav() != null) {
                return ElementType.Paragraph;
            } else if (odredba.getTacka() != null) {
                return ElementType.Clause;
            } else {
                return ElementType.None;
            }
        } else {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Odredba ne postoji!");
            return ElementType.None;
        }
    }

    private Element getElement(Amendment amendment, ElementType type) {
        if (type == ElementType.Article) {
            if (amendment.getBody().getOdredba().getClan() != null) {
                return amendment.getBody().getOdredba().getClan();
            } else {
                return null;
            }
        }
        if (type == ElementType.Paragraph) {
            if (amendment.getBody().getOdredba().getStav() != null) {
                return amendment.getBody().getOdredba().getStav();
            } else {
                return null;
            }
        }
        if (type == ElementType.Clause) {
            if (amendment.getBody().getOdredba().getTacka() != null) {
                return amendment.getBody().getOdredba().getTacka();
            } else {
                return null;
            }
        }
        if (type == ElementType.Subclause) {
            if (amendment.getBody().getOdredba().getPodtacka() != null) {
                return amendment.getBody().getOdredba().getPodtacka();
            } else {
                return null;
            }
        }
        if (type == ElementType.Item) {
            if (amendment.getBody().getOdredba().getAlineja() != null) {
                return new ItemWrapper(amendment.getBody().getOdredba().getAlineja());
            } else {
                return null;
            }
        }
        return null;
    }

    public void setNewOdredbaElement(Element newElement, ElementType type) {
        if (type == ElementType.Article) {
            amendment.getBody().getOdredba().setClan((Article) newElement);
        }
        if (type == ElementType.Paragraph) {
            amendment.getBody().getOdredba().setStav((Paragraph) newElement);
        }
        if (type == ElementType.Clause) {
            amendment.getBody().getOdredba().setTacka((Clause) newElement);
        }
        if (type == ElementType.Subclause) {
            amendment.getBody().getOdredba().setPodtacka((Subclause) newElement);
        }
        if (type == ElementType.Item) {
            amendment.getBody().getOdredba().setAlineja(((ItemWrapper) newElement).getWrappedItem());
        }
    }

    private void updateEditorAttrs(boolean createNew) {
        System.out.println("___UPDATE___");
        System.out.println(selectionInfo);
        editorAttrs = new PopupEditorOptions();
        editorAttrs.setCreateNew(createNew);
        if (!createNew) editorAttrs.setElement(selectionInfo.getElement());
        editorAttrs.setParentElement(selectionInfo.getElement().getElementParent());
        editorAttrs.setTypeOfElement(selectionInfo.getElementType());
        editorAttrs.savedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                setNewOdredbaElement(editorAttrs.getElement(), editorAttrs.getTypeOfElement());
                System.out.println("Saved!");
            }
        });
        editorAttrsProperty.set(editorAttrs);
        editorAttrsPropertyItem.setValue(editorAttrs);
    }

    public void checkIfStartWithoutOdredba() {
        if (resenjeProperty.get() == AmendmentType.Brisanje) {
            System.out.println(amendment.getPropertyItems().size());
            amendment.getPropertyItems().remove(editorAttrsPropertyItem);
            System.out.println(amendment.getPropertyItems().size());
            amendment.getBody().setOdredba(null);
        }
    }
}
