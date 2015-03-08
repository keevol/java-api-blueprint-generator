package io.fernandonogueira.apiblueprint.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Fernando Nogueira
 * @since 3/6/15 10:25 AM
 */
public class ControllerDescriptor {

    private String simpleName;
    private String controllerClassFullName;
    private String pojoClass;
    private String description;
    private String endPoint;
    private List<MethodDescriptor> methods = new ArrayList<>();

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public String getControllerClassFullName() {
        return controllerClassFullName;
    }

    public void setControllerClassFullName(String controllerClassFullName) {
        this.controllerClassFullName = controllerClassFullName;
    }

    public String getPojoClass() {
        return pojoClass;
    }

    public void setPojoClass(String pojoClass) {
        this.pojoClass = pojoClass;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public void addMethod(MethodDescriptor methodDescriptor) {
        if (!methods.contains(methodDescriptor)) {
            methods.add(methodDescriptor);
        }
    }

    public List<MethodDescriptor> getMethods() {
        return methods;
    }

    public void setMethods(List<MethodDescriptor> methods) {
        this.methods = methods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ControllerDescriptor)) return false;

        ControllerDescriptor that = (ControllerDescriptor) o;

        return controllerClassFullName.equals(that.controllerClassFullName);

    }

    @Override
    public int hashCode() {
        return controllerClassFullName.hashCode();
    }

    @Override
    public String toString() {
        return "ControllerDescriptor{" +
                "simpleName='" + simpleName + '\'' +
                ", controllerClassFullName='" + controllerClassFullName + '\'' +
                ", pojoClass='" + pojoClass + '\'' +
                ", description='" + description + '\'' +
                ", endPoint='" + endPoint + '\'' +
                ", methods=" + methods +
                '}';
    }
}