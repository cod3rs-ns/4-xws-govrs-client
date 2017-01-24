//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.12 at 01:04:06 AM CET 
//


package rs.acs.uns.sw.govrs.client.fx.serverdomain;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import rs.acs.uns.sw.govrs.client.fx.domain.Element;
import rs.acs.uns.sw.govrs.client.fx.editor.property_sheet.StringPropertyItem;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.adapters.AmendmentTypeAdapter;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.adapters.StringPropertyAdapter;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.enums.AmendmentType;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.managers.AmendmentStateManager;
import rs.acs.uns.sw.govrs.client.fx.util.ElementType;
import rs.acs.uns.sw.govrs.client.fx.validation.ErrorMessage;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;


/**
 * <p>Java class for amandman element declaration.
 * <p>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;element name="amandman">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element name="head">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="rjesenje">
 *                       &lt;simpleType>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                           &lt;enumeration value="brisanje"/>
 *                           &lt;enumeration value="izmjena"/>
 *                           &lt;enumeration value="dopuna"/>
 *                         &lt;/restriction>
 *                       &lt;/simpleType>
 *                     &lt;/element>
 *                     &lt;element name="predmet">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence>
 *                               &lt;element ref="{http://www.parlament.gov.rs/schema/elementi}ref"/>
 *                             &lt;/sequence>
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="body">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="odredba" minOccurs="0">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;choice>
 *                               &lt;element ref="{http://www.parlament.gov.rs/schema/elementi}clan"/>
 *                               &lt;element ref="{http://www.parlament.gov.rs/schema/elementi}stav"/>
 *                               &lt;element ref="{http://www.parlament.gov.rs/schema/elementi}tacka"/>
 *                               &lt;element ref="{http://www.parlament.gov.rs/schema/elementi}podtacka"/>
 *                               &lt;element ref="{http://www.parlament.gov.rs/schema/elementi}alineja"/>
 *                             &lt;/choice>
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                     &lt;element ref="{http://www.parlament.gov.rs/schema/amandman}obrazlozenje"/>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *         &lt;/sequence>
 *         &lt;attGroup ref="{http://www.parlament.gov.rs/schema/elementi}standard_attrs"/>
 *       &lt;/restriction>
 *     &lt;/complexContent>
 *   &lt;/complexType>
 * &lt;/element>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "head",
        "body"
})
@XmlRootElement(name = "amandman", namespace = "http://www.parlament.gov.rs/schema/amandman")
public class Amendment extends Element {

    @XmlElement(namespace = "http://www.parlament.gov.rs/schema/amandman", required = true)
    protected Head head;
    @XmlElement(namespace = "http://www.parlament.gov.rs/schema/amandman", required = true)
    protected Body body;

    @XmlAttribute(name = "id", required = true)
    @XmlSchemaType(name = "anyURI")
    @XmlJavaTypeAdapter(StringPropertyAdapter.class)
    protected StringProperty id = new SimpleStringProperty();

    @XmlAttribute(name = "name")
    @XmlJavaTypeAdapter(StringPropertyAdapter.class)
    protected StringProperty name = new SimpleStringProperty("Amandman");

    /**
     * Gets the value of the head property.
     *
     * @return possible object is
     * {@link Head }
     */
    public Head getHead() {
        return head;
    }

    /**
     * Sets the value of the head property.
     *
     * @param value allowed object is
     *              {@link Head }
     */
    public void setHead(Head value) {
        this.head = value;
    }

    /**
     * Gets the value of the body property.
     *
     * @return possible object is
     * {@link Body }
     */
    public Body getBody() {
        return body;
    }

    /**
     * Sets the value of the body property.
     *
     * @param value allowed object is
     *              {@link Body }
     */
    public void setBody(Body value) {
        this.body = value;
    }

    /**
     * Gets the value of the id property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getId() {
        return id.get();
    }

    /**
     * Sets the value of the id property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setId(String value) {
        this.id.set(value);
    }

    public StringProperty idProperty() {
        return id;
    }

    /**
     * Gets the value of the name property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getName() {
        return name.get();
    }

    /**
     * Sets the value of the name property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setName(String value) {
        this.name.set(value);
    }

    public StringProperty nameProperty() {
        return name;
    }

    @Override
    public String getElementName() {
        return name.get();
    }

    @Override
    public void setElementName(String name) {
        this.name.set(name);
    }

    @Override
    public StringProperty elementNameProperty() {
        return name;
    }

    @Override
    public void initElement() {
        createPropertyAttrs();
    }

    @Override
    public void createAndAddChild(Element element) {

    }

    @Override
    public void removeChild(Element element) {

    }

    @Override
    public void createPropertyAttrs() {
        // create property list for context
        StringPropertyItem idPropertyItem = new StringPropertyItem(
                idProperty(),
                "Generalno",
                "ID ",
                "Jedinstveni identifikator amandmana",
                false);
        StringPropertyItem namePropertyItem = new StringPropertyItem(
                nameProperty(),
                "Generalno",
                "Naziv ",
                "Naziv amandmana",
                true);
        // create property list for context
        StringPropertyItem razlogPropertyItem = new StringPropertyItem(
                getBody().getObrazlozenje().razlogProperty(),
                "Objašnjenje",
                "Razlog ",
                "Razlog podnetog amandmana",
                true);
        StringPropertyItem objasnjenjePropertyItem = new StringPropertyItem(
                getBody().getObrazlozenje().objasnjenjePredlozenogRjesenjaProperty(),
                "Objašnjenje",
                "Objašnjenje rešenja",
                "Obrazloženje podnetog rešenja",
                true);
        StringPropertyItem ciljPropertyItem = new StringPropertyItem(
                getBody().getObrazlozenje().ciljProperty(),
                "Objašnjenje",
                "Cilj",
                "Obrazloženje cilja",
                true);
        StringPropertyItem uticajPropertyItem = new StringPropertyItem(
                getBody().getObrazlozenje().uticajNaBudzetskaSredstvaProperty(),
                "Objašnjenje",
                "Uticaj na budžet",
                "Uticaj na budžetska sredstva",
                true);
        AmendmentStateManager stateManager = new AmendmentStateManager(this);
        getPropertyItems().add(idPropertyItem);
        getPropertyItems().add(namePropertyItem);
        getPropertyItems().add(razlogPropertyItem);
        getPropertyItems().add(objasnjenjePropertyItem);
        getPropertyItems().add(ciljPropertyItem);
        getPropertyItems().add(uticajPropertyItem);
        getPropertyItems().add(stateManager.getResenjePropertyItem());
        getPropertyItems().add(stateManager.getSelectionInfoPropertyItem());
        getPropertyItems().add(stateManager.getEditorAttrsPropertyItem());
        stateManager.checkIfStartWithoutOdredba();
    }

    @Override
    public void preMarshaller() {
        if (getBody().getOdredba() != null) {
            if (getBody().getOdredba().getClan() != null) {
                getBody().getOdredba().getClan().preMarshaller();
            }
            if (getBody().getOdredba().getPodtacka() != null) {
                getBody().getOdredba().getPodtacka().preMarshaller();
            }
            if (getBody().getOdredba().getStav() != null) {
                getBody().getOdredba().getStav().preMarshaller();
            }
            if (getBody().getOdredba().getTacka() != null) {
                getBody().getOdredba().getTacka().preMarshaller();
            }
        }
    }

    /**
     * <p>Java class for anonymous complex type.
     * <p>
     * <p>The following schema fragment specifies the expected content contained within this class.
     * <p>
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="odredba" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;choice>
     *                   &lt;element ref="{http://www.parlament.gov.rs/schema/elementi}clan"/>
     *                   &lt;element ref="{http://www.parlament.gov.rs/schema/elementi}stav"/>
     *                   &lt;element ref="{http://www.parlament.gov.rs/schema/elementi}tacka"/>
     *                   &lt;element ref="{http://www.parlament.gov.rs/schema/elementi}podtacka"/>
     *                   &lt;element ref="{http://www.parlament.gov.rs/schema/elementi}alineja"/>
     *                 &lt;/choice>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element ref="{http://www.parlament.gov.rs/schema/amandman}obrazlozenje"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "odredba",
            "obrazlozenje"
    })
    public static class Body {

        @XmlElement(namespace = "http://www.parlament.gov.rs/schema/amandman")
        protected Odredba odredba;
        @XmlElement(namespace = "http://www.parlament.gov.rs/schema/amandman", required = true)
        protected Explanation obrazlozenje;

        /**
         * Gets the value of the odredba property.
         *
         * @return possible object is
         * {@link Odredba }
         */
        public Odredba getOdredba() {
            return odredba;
        }

        /**
         * Sets the value of the odredba property.
         *
         * @param value allowed object is
         *              {@link Odredba }
         */
        public void setOdredba(Odredba value) {
            this.odredba = value;
        }

        /**
         * Gets the value of the obrazlozenje property.
         *
         * @return possible object is
         * {@link Explanation }
         */
        public Explanation getObrazlozenje() {
            return obrazlozenje;
        }

        /**
         * Sets the value of the obrazlozenje property.
         *
         * @param value allowed object is
         *              {@link Explanation }
         */
        public void setObrazlozenje(Explanation value) {
            this.obrazlozenje = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * <p>
         * <p>The following schema fragment specifies the expected content contained within this class.
         * <p>
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;choice>
         *         &lt;element ref="{http://www.parlament.gov.rs/schema/elementi}clan"/>
         *         &lt;element ref="{http://www.parlament.gov.rs/schema/elementi}stav"/>
         *         &lt;element ref="{http://www.parlament.gov.rs/schema/elementi}tacka"/>
         *         &lt;element ref="{http://www.parlament.gov.rs/schema/elementi}podtacka"/>
         *         &lt;element ref="{http://www.parlament.gov.rs/schema/elementi}alineja"/>
         *       &lt;/choice>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "clan",
                "stav",
                "tacka",
                "podtacka",
                "alineja"
        })
        public static class Odredba {

            @XmlElement(namespace = "http://www.parlament.gov.rs/schema/elementi")
            protected Article clan;
            @XmlElement(namespace = "http://www.parlament.gov.rs/schema/elementi")
            protected Paragraph stav;
            @XmlElement(namespace = "http://www.parlament.gov.rs/schema/elementi")
            protected Clause tacka;
            @XmlElement(namespace = "http://www.parlament.gov.rs/schema/elementi")
            protected Subclause podtacka;
            @XmlElement(namespace = "http://www.parlament.gov.rs/schema/elementi")
            protected Item alineja;

            /**
             * Gets the value of the clan property.
             *
             * @return possible object is
             * {@link Article }
             */
            public Article getClan() {
                return clan;
            }

            /**
             * Sets the value of the clan property.
             *
             * @param value allowed object is
             *              {@link Article }
             */
            public void setClan(Article value) {
                this.clan = value;
            }

            /**
             * Gets the value of the stav property.
             *
             * @return possible object is
             * {@link Paragraph }
             */
            public Paragraph getStav() {
                return stav;
            }

            /**
             * Sets the value of the stav property.
             *
             * @param value allowed object is
             *              {@link Paragraph }
             */
            public void setStav(Paragraph value) {
                this.stav = value;
            }

            /**
             * Gets the value of the tacka property.
             *
             * @return possible object is
             * {@link Clause }
             */
            public Clause getTacka() {
                return tacka;
            }

            /**
             * Sets the value of the tacka property.
             *
             * @param value allowed object is
             *              {@link Clause }
             */
            public void setTacka(Clause value) {
                this.tacka = value;
            }

            /**
             * Gets the value of the podtacka property.
             *
             * @return possible object is
             * {@link Subclause }
             */
            public Subclause getPodtacka() {
                return podtacka;
            }

            /**
             * Sets the value of the podtacka property.
             *
             * @param value allowed object is
             *              {@link Subclause }
             */
            public void setPodtacka(Subclause value) {
                this.podtacka = value;
            }

            /**
             * Gets the value of the alineja property.
             *
             * @return possible object is
             * {@link Item }
             */
            public Item getAlineja() {
                return alineja;
            }

            /**
             * Sets the value of the alineja property.
             *
             * @param value allowed object is
             *              {@link Item }
             */
            public void setAlineja(Item value) {
                this.alineja = value;
            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * <p>
     * <p>The following schema fragment specifies the expected content contained within this class.
     * <p>
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="rjesenje">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;enumeration value="brisanje"/>
     *               &lt;enumeration value="izmjena"/>
     *               &lt;enumeration value="dopuna"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="predmet">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element ref="{http://www.parlament.gov.rs/schema/elementi}ref"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "rjesenje",
            "predmet"
    })
    public static class Head {

        @XmlElement(namespace = "http://www.parlament.gov.rs/schema/amandman", required = true)
        @XmlJavaTypeAdapter(AmendmentTypeAdapter.class)
        protected ObjectProperty<AmendmentType> rjesenje = new SimpleObjectProperty<>(AmendmentType.Brisanje);

        @XmlElement(namespace = "http://www.parlament.gov.rs/schema/amandman", required = true)
        protected Predmet predmet;

        /**
         * Gets the value of the rjesenje property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getRjesenje() {
            if (rjesenje.get() == AmendmentType.Dopuna) {
                return "dopuna";
            }

            if (rjesenje.get() == AmendmentType.Izmena) {
                return "izmjena";
            }

            if (rjesenje.get() == AmendmentType.Brisanje) {
                return "brisanje";
            }
            return "";
        }

        /**
         * Sets the value of the rjesenje property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setRjesenje(String value) {
            if (value.equals("dopuna")) {
                this.rjesenje.set(AmendmentType.Dopuna);
            }
            if (value.equals("izmjena")) {
                this.rjesenje.set(AmendmentType.Izmena);
            }
            if (value.equals("brisanje")) {
                this.rjesenje.set(AmendmentType.Brisanje);
            }
        }

        public ObjectProperty<AmendmentType> rjesenjeProperty() {
            return rjesenje;
        }

        /**
         * Gets the value of the predmet property.
         *
         * @return possible object is
         * {@link Predmet }
         */
        public Predmet getPredmet() {
            return predmet;
        }

        /**
         * Sets the value of the predmet property.
         *
         * @param value allowed object is
         *              {@link Predmet }
         */
        public void setPredmet(Predmet value) {
            this.predmet = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * <p>
         * <p>The following schema fragment specifies the expected content contained within this class.
         * <p>
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element ref="{http://www.parlament.gov.rs/schema/elementi}ref"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "ref"
        })
        public static class Predmet {

            @XmlElement(namespace = "http://www.parlament.gov.rs/schema/elementi", required = true)
            protected Ref ref;

            /**
             * Gets the value of the ref property.
             *
             * @return possible object is
             * {@link Ref }
             */
            public Ref getRef() {
                return ref;
            }

            /**
             * Sets the value of the ref property.
             *
             * @param value allowed object is
             *              {@link Ref }
             */
            public void setRef(Ref value) {
                this.ref = value;
            }

        }
    }

    @Override
    public void validate(List<ErrorMessage> errorMessageList) {
        if (name.get() == null || "".equals(name.get()))
            errorMessageList.add(new ErrorMessage(id.get(), name.getName(), ElementType.Amendment, "Amandman na odredbu zakona mora imati ime."));
        if (getHead().getPredmet() == null || getHead().getPredmet().getRef() == null)
            errorMessageList.add(new ErrorMessage(id.get(), name.getName(), ElementType.Amendment, "Amandman mora imati odredbu na koju se odnosi."));
        else {
            if (getHead().getRjesenje() != null && getBody().getOdredba() == null) {
                if (getHead().getRjesenje().equals("izmjena") || getHead().getRjesenje().equals("dopuna")) {
                    errorMessageList.add(new ErrorMessage(id.get(), name.getName(), ElementType.Amendment, "Amandman koji mijenja/dodaje neku odredbu \nmora imati zamjensku/dopunsku odredbu."));
                }
            }
            if (getBody().getOdredba() != null){
                if (getBody().getOdredba().getClan() != null)
                    getBody().getOdredba().getClan().validate(errorMessageList);
                if (getBody().getOdredba().getPodtacka() != null)
                    getBody().getOdredba().getPodtacka().validate(errorMessageList);
                if (getBody().getOdredba().getStav() != null)
                    getBody().getOdredba().getStav().validate(errorMessageList);
                if (getBody().getOdredba().getTacka() != null)
                    getBody().getOdredba().getTacka().validate(errorMessageList);
            }
        }
    }
}
