package rs.acs.uns.sw.govrs.client.fx.validation;


import rs.acs.uns.sw.govrs.client.fx.util.ElementType;

public class ErrorMessage {

    private String id;
    private String name;
    private ElementType elementType;
    private String message;

    public ErrorMessage(String id, String name, ElementType elementType, String message) {
        this.id = id;
        this.name = name;
        this.elementType = elementType;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ElementType getElementType() {
        return elementType;
    }

    public void setElementType(ElementType elementType) {
        this.elementType = elementType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorMessage{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", elementType=" + elementType +
                ", message='" + message + '\'' +
                '}';
    }
}
