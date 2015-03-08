package io.fernandonogueira.apiblueprint.domain;

/**
 * @author Fernando Nogueira
 * @since 3/8/15 12:58 PM
 */
public class FieldDescriptor {

    private String name;
    private String description;
    private boolean mandatory;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FieldDescriptor)) return false;

        FieldDescriptor that = (FieldDescriptor) o;

        if (!name.equals(that.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "FieldDescriptor{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", mandatory=" + mandatory +
                '}';
    }
}