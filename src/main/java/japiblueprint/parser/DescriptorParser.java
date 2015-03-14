package japiblueprint.parser;

import japiblueprint.domain.ControllerDescriptor;
import japiblueprint.domain.FieldDescriptor;
import japiblueprint.domain.MethodDescriptor;
import japiblueprint.domain.PojoDescriptor;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Translates descriptor objects into API Blueprints syntax
 *
 * @author Fernando Nogueira
 * @since 3/6/15 10:52 AM
 */
public class DescriptorParser {

    private static final String TAB = " ";

    public String parse(Map<String, PojoDescriptor> descriptorMap, Map<String, List<ControllerDescriptor>> controllerDescriptorMap) {

        StringBuilder builder = new StringBuilder();
        appendApiIntro(builder);

        Set<Map.Entry<String, PojoDescriptor>> entrySet = descriptorMap.entrySet();

        for (Map.Entry<String, PojoDescriptor> stringPojoDescriptorEntry : entrySet) {

            PojoDescriptor pojoDescriptor = stringPojoDescriptorEntry.getValue();

            builder.append("# Group ").append(pojoDescriptor.getSimpleName()).append("\n\n");

            if (pojoDescriptor.getDescription() != null && !pojoDescriptor.getDescription().isEmpty()) {
                builder.append("```\n").append(pojoDescriptor.getDescription()).append("\n").append("```\n");
            }

            List<FieldDescriptor> fields = pojoDescriptor.getFields();
            if (fields != null && !fields.isEmpty()) {
                builder
                        .append("\n")
                        .append(fieldTableParser(fields))
                        .append("\n");
            }

            List<ControllerDescriptor> controllerDescriptorList = controllerDescriptorMap.get(stringPojoDescriptorEntry.getKey());

            if (controllerDescriptorList != null) {

                for (ControllerDescriptor controllerDescriptor : controllerDescriptorList) {
                    String controllerEndpoint = controllerDescriptor.getEndPoint();

                    List<MethodDescriptor> methods = controllerDescriptor.getMethods();
                    for (MethodDescriptor method : methods) {
                        builder.append("## ")
                                .append(method.getDescription())
                                .append(" [")
                                .append(controllerEndpoint)
                                .append(method.getEndPoint())
                                .append("]\n")
                                .append("### ")
                                .append(method.getHttpMethod())
                                .append(" [")
                                .append(method.getHttpMethod())
                                .append("]")
                                .append("\n")
                                .append("+ Response ")
                                .append(method.getResponseStatusCode())
                                .append(" (")
                                .append(method.getResponseContentType())
                                .append(")\n\n");
                    }
                }

            }

        }

        return builder.toString();
    }

    private void appendApiIntro(StringBuilder builder) {
        builder.append("FORMAT: 1A");
        builder.append("\n\n");
        builder.append("# API \n\n");
    }

    private String returnTabs(int number) {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < number; i++) {
            buffer.append(TAB);
        }
        return buffer.toString();
    }

    private String fieldTableParser(List<FieldDescriptor> fields) {

        StringBuilder builder = new StringBuilder();

        builder.append("| # | Attribute | Description | isMandatory |\n");
        builder.append("|---|-----------|-------------|-------------|\n");

        int count = 1;
        for (FieldDescriptor field : fields) {
            builder
                    .append("| ")
                    .append(count++)
                    .append(" | ")
                    .append(field.getName())
                    .append(" | ")
                    .append(field.getDescription())
                    .append(" | ")
                    .append(field.isMandatory())
                    .append(" |\n");
        }

        return builder.toString();
    }

}