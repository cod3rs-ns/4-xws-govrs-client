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
import rs.acs.uns.sw.govrs.client.fx.serverdomain.container.ALAContainer;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.enums.AmendmentType;
import rs.acs.uns.sw.govrs.client.fx.util.ElementTypes;

public class AmendmentStateManager {
    private Amendment amendment;
    private ButtonPropertyItem predmetPickerPropertyItem;
    private PopupButtonPropertyItem odredbaEditorPropertyItem;
    private AmendmentTypePropertyItem resenjePropertyItem;
    private PopupEditorInit editorAttrs;
    private ALAContainer alac;
    private ObjectProperty<AmendmentType> resenjeProperty;
    private ObjectProperty<ALAContainer> alacProperty;
    private ObjectProperty<PopupEditorInit>  editorAttrsProperty;

    public AmendmentStateManager(Amendment amendment) {
        this.amendment = amendment;
        resenjeProperty = amendment.getHead().rjesenjeProperty();
        resenjePropertyItem = new AmendmentTypePropertyItem(
                resenjeProperty,
                "Generalno",
                "Rešenje",
                "Predlog rešenja",
                true);

        alac = new ALAContainer(
                ((Amendments)amendment.getElementParent()).getHead().getPropis().getRef().getId(),
                amendment.getHead().getPredmet().getRef().getId(),
                amendment
        );
        alacProperty = new SimpleObjectProperty<>(alac);
        predmetPickerPropertyItem= new ButtonPropertyItem(
                alacProperty,
                "Propis",
                "Predmet",
                "Element na koji se odnosi",
                true
        );
        Element e = amendment.getBody().getOdredba().getClan();
        e.initElement();
        editorAttrs = new PopupEditorInit("string", false, e, ElementTypes.Article);
        editorAttrsProperty = new SimpleObjectProperty<>(editorAttrs);
        odredbaEditorPropertyItem = new PopupButtonPropertyItem(
                new SimpleObjectProperty<PopupEditorInit>(editorAttrs),
                "Propis",
                "Odredba",
                "Nova ili izmenjena odredba",
                true
        );
        resenjeProperty.addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);

            editorAttrs = new PopupEditorInit("string", true, null, ElementTypes.Article);
            alac = new ALAContainer(
                    ((Amendments)amendment.getElementParent()).getHead().getPropis().getRef().getId(),
                    null,
                    amendment
            );

            odredbaEditorPropertyItem.property.set(editorAttrs);
            predmetPickerPropertyItem.property.set(alac);
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

    public ALAContainer getAlac() {
        return alac;
    }
}
