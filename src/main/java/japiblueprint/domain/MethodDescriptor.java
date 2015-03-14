package japiblueprint.domain;

/**
 * @author Fernando Nogueira
 * @since 3/6/15 2:56 PM
 */
public class MethodDescriptor {

    private String controllerName;
    private String methodName;

    private String description;
    private String endPoint;
    private String httpMethod;
    private String responseStatusCode;
    private String responseContentType;

    public String getControllerName() {
        return controllerName;
    }

    public void setControllerName(String controllerName) {
        this.controllerName = controllerName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
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

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getResponseStatusCode() {
        return responseStatusCode;
    }

    public void setResponseStatusCode(String responseStatusCode) {
        this.responseStatusCode = responseStatusCode;
    }

    public String getResponseContentType() {
        return responseContentType;
    }

    public void setResponseContentType(String responseContentType) {
        this.responseContentType = responseContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MethodDescriptor)) return false;

        MethodDescriptor that = (MethodDescriptor) o;

        if (!controllerName.equals(that.controllerName)) return false;
        return description.equals(that.description);

    }

    @Override
    public int hashCode() {
        int result = controllerName.hashCode();
        result = 31 * result + description.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "MethodDescriptor{" +
                "controllerName='" + controllerName + '\'' +
                ", description='" + description + '\'' +
                ", endPoint='" + endPoint + '\'' +
                ", httpMethod='" + httpMethod + '\'' +
                ", responseStatusCode='" + responseStatusCode + '\'' +
                ", responseContentType='" + responseContentType + '\'' +
                '}';
    }
}
