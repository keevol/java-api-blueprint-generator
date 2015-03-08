package io.fernandonogueira.apiblueprint.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Fernando Nogueira
 * @since 3/6/15 10:25 AM
 */
public class PojoDescriptor {

    private String simpleName;
    private String classFullName;
    private String description;
    private List<FieldDescriptor> fields = new ArrayList<>();

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public String getClassFullName() {
        return classFullName;
    }

    public void setClassFullName(String classFullName) {
        this.classFullName = classFullName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public List<FieldDescriptor> getFields() {
        return fields;
    }

    public void setFields(List<FieldDescriptor> fields) {
        this.fields = fields;
    }

    public void addFields(FieldDescriptor fieldDescriptor) {
        if (!fields.contains(fieldDescriptor)) {
            fields.add(fieldDescriptor);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PojoDescriptor)) return false;

        PojoDescriptor that = (PojoDescriptor) o;

        return classFullName.equals(that.classFullName);

    }

    @Override
    public int hashCode() {
        return classFullName.hashCode();
    }

    @Override
    public String toString() {
        return "PojoDescriptor{" +
                "simpleName='" + simpleName + '\'' +
                ", classFullName='" + classFullName + '\'' +
                ", description='" + description + '\'' +
                ", fields=" + fields +
                '}';
    }
}