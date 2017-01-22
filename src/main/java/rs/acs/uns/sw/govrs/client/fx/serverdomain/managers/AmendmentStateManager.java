package rs.acs.uns.sw.govrs.client.fx.serverdomain.managers;


import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import rs.acs.uns.sw.govrs.client.fx.domain.Element;
import rs.acs.uns.sw.govrs.client.fx.editor.help.PopupEditorInit;
import rs.acs.uns.sw.govrs.client.fx.editor.property_sheet.AmendmentTypePropertyItem;
import rs.acs.uns.sw.govrs.client.fx.editor.property_sheet.ButtonPropertyItem;
import rs.acs.uns.sw.govrs.client.fx.editor.property_sheet.PopupButtonPropertyItem;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.Amendment;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.Amendments;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.container.SelectionInfo;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.enums.AmendmentType;
import rs.acs.uns.sw.govrs.client.fx.util.ElementTypes;

public class AmendmentStateManager {
    private Amendment amendment;
    private ButtonPropertyItem predmetPickerPropertyItem;
    private PopupButtonPropertyItem odredbaEditorPropertyItem;
    private AmendmentTypePropertyItem resenjePropertyItem;
    private PopupEditorInit editorAttrs;
    private SelectionInfo alac;
    private ObjectProperty<AmendmentType> resenjeProperty;
    private ObjectProperty<SelectionInfo> alacProperty;
    private ObjectProperty<PopupEditorInit> editorAttrsProperty;

    public AmendmentStateManager(Amendment amendment) {
        this.amendment = amendment;
        resenjeProperty = amendment.getHead().rjesenjeProperty();
        resenjePropertyItem = new AmendmentTypePropertyItem(
                resenjeProperty,
                "Predmet",
                "Rešenje",
                "Predlog rešenja",
                true);

        alac = new SelectionInfo(
                ((Amendments) amendment.getElementParent()).getHead().getPropis().getRef().getId(),
                amendment.getHead().getPredmet().getRef().getId(),
                ElementTypes.Article
        );
        alacProperty = new SimpleObjectProperty<>(alac);
        predmetPickerPropertyItem = new ButtonPropertyItem(
                alacProperty,
                "Predmet",
                "Predmet",
                "Element na koji se odnosi",
                true
        );
        Element e = amendment.getBody().getOdredba().getClan();
        e.initElement();
        editorAttrs = new PopupEditorInit("string", false, e, ElementTypes.Article);
        editorAttrsProperty = new SimpleObjectProperty<>(editorAttrs);
        odredbaEditorPropertyItem = new PopupButtonPropertyItem(
                editorAttrsProperty,
                "Predmet",
                "Odredba",
                "Nova ili izmenjena odredba",
                true
        );
        resenjeProperty.addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);

            editorAttrs = new PopupEditorInit("string", true, null, ElementTypes.Article);
            alac = new SelectionInfo(
                    ((Amendments) amendment.getElementParent()).getHead().getPropis().getRef().getId(),
                    null,
                    ElementTypes.Article
            );

            odredbaEditorPropertyItem.property.set(editorAttrs);
            predmetPickerPropertyItem.property.set(alac);

            // hide 'Odredba' field if not needed
            if (newValue == AmendmentType.Brisanje) {
                amendment.getPropertyItems().remove(odredbaEditorPropertyItem);
            } else {
                amendment.getPropertyItems().remove(odredbaEditorPropertyItem);
                odredbaEditorPropertyItem = new PopupButtonPropertyItem(
                        editorAttrsProperty,
                        "Predmet",
                        "Odredba",
                        "Nova ili izmenjena odredba",
                        true
                );
                amendment.getPropertyItems().add(odredbaEditorPropertyItem);
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

    public PopupEditorInit getEditorAttrs() {
        return editorAttrs;
    }

    public SelectionInfo getAlac() {
        return alac;
    }
}
