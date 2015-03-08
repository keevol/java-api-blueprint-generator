package io.fernandonogueira.apiblueprint;

import io.fernandonogueira.apiblueprint.annotations.ApiController;
import io.fernandonogueira.apiblueprint.annotations.ApiRequest;
import io.fernandonogueira.apiblueprint.annotations.ApiResource;
import io.fernandonogueira.apiblueprint.annotations.ApiResourceAttr;
import io.fernandonogueira.apiblueprint.domain.ControllerDescriptor;
import io.fernandonogueira.apiblueprint.domain.FieldDescriptor;
import io.fernandonogueira.apiblueprint.domain.MethodDescriptor;
import io.fernandonogueira.apiblueprint.domain.PojoDescriptor;
import io.fernandonogueira.apiblueprint.parser.DescriptorParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.*;

/**
 * @author Fernando Nogueira
 * @since 3/4/15 3:58 PM
 */
@SupportedAnnotationTypes(
        {
                "io.fernandonogueira.apiblueprint.annotations.ApiController",
                "io.fernandonogueira.apiblueprint.annotations.ApiRequest",
                "io.fernandonogueira.apiblueprint.annotations.ApiResource",
                "io.fernandonogueira.apiblueprint.annotations.ApiResourceAttr"
        }
)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class MainProcessor extends AbstractProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainProcessor.class);

    private Map<String, PojoDescriptor> classPojoDescriptorMap = new HashMap<>();
    private Map<String, List<ControllerDescriptor>> classControllerDescriptorMap = new HashMap<>();
    private Map<String, String> controllerNamePojoName = new HashMap<>();

    private List<MethodDescriptor> orphanMethods = new ArrayList<>();

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        LOGGER.info("Starting api-blueprint annotation processor...");

        if (annotations == null) {
            return false;
        }

        for (TypeElement te : annotations) {
            for (Element e : roundEnv.getElementsAnnotatedWith(te)) {

                ApiResource resourceAnnotation = e.getAnnotation(ApiResource.class);
                ApiRequest requestAnnotation = e.getAnnotation(ApiRequest.class);
                ApiController controllerAnnotation = e.getAnnotation(ApiController.class);
                ApiResourceAttr fieldAnnotation = e.getAnnotation(ApiResourceAttr.class);
                LOGGER.info(" -=-=-=- " + e.toString() + " == " + e.getKind().toString());
                if (resourceAnnotation != null || requestAnnotation != null || controllerAnnotation != null || fieldAnnotation != null) {
                    switch (e.getKind()) {
                        case CLASS:

                            boolean controllerFound = controllerAnnotation != null;
                            boolean pojoFound = resourceAnnotation != null;

                            if (controllerFound) {
                                ControllerDescriptor controllerDescriptor = new ControllerDescriptor();
                                controllerDescriptor.setControllerClassFullName(e.toString());
                                controllerDescriptor.setPojoClass(controllerAnnotation.resouceClass());
                                controllerDescriptor.setDescription(controllerAnnotation.description());
                                controllerDescriptor.setEndPoint(controllerAnnotation.endPoint());
                                if (!classControllerDescriptorMap.containsKey(controllerAnnotation.resouceClass())) {
                                    List<ControllerDescriptor> list = new ArrayList<>();
                                    list.add(controllerDescriptor);
                                    classControllerDescriptorMap.put(controllerAnnotation.resouceClass(), list);
                                } else {
                                    List<ControllerDescriptor> list = classControllerDescriptorMap.get(controllerAnnotation.resouceClass());
                                    if (!list.contains(controllerDescriptor)) {
                                        list.add(controllerDescriptor);
                                    }
                                }

                                if (!controllerNamePojoName.containsKey(e.getSimpleName().toString())) {
                                    controllerNamePojoName.put(e.getSimpleName().toString(), controllerAnnotation.resouceClass());
                                }

                            } else if (pojoFound) {
                                if (!classPojoDescriptorMap.containsKey(e.toString())) {
                                    PojoDescriptor pojoDescriptor = new PojoDescriptor();
                                    pojoDescriptor.setClassFullName(e.toString());
                                    pojoDescriptor.setDescription(resourceAnnotation.description());
                                    pojoDescriptor.setSimpleName(e.getSimpleName().toString());
                                    classPojoDescriptorMap.put(e.toString(), pojoDescriptor);
                                }
                            }
                            break;
                        case FIELD:

                            Element parent = e.getEnclosingElement();
                            String parentName = parent.toString();

                            boolean fieldFound = fieldAnnotation != null;

                            if (fieldFound) {
                                PojoDescriptor pojoDescriptor = classPojoDescriptorMap.get(parentName);
                                if (pojoDescriptor != null) {
                                    FieldDescriptor fieldDescriptor = new FieldDescriptor();
                                    fieldDescriptor.setName(e.toString());
                                    fieldDescriptor.setDescription(fieldAnnotation.description());
                                    fieldDescriptor.setMandatory(fieldAnnotation.mandatory());
                                    pojoDescriptor.addFields(fieldDescriptor);
                                }
                            }
                            break;
                        case METHOD:
                            boolean methodFound = requestAnnotation != null;
                            if (methodFound) {

                                String methodControllerName = e.getEnclosingElement().toString();

                                String pojoName = controllerNamePojoName.get(e.getEnclosingElement().getSimpleName().toString());
                                List<ControllerDescriptor> controllers = classControllerDescriptorMap.get(pojoName);

                                ControllerDescriptor sampleDescriptor = new ControllerDescriptor();
                                sampleDescriptor.setControllerClassFullName(methodControllerName);

                                MethodDescriptor methodDescriptor = new MethodDescriptor();
                                methodDescriptor.setHttpMethod(requestAnnotation.method().name());
                                methodDescriptor.setDescription(requestAnnotation.description());
                                methodDescriptor.setEndPoint(requestAnnotation.endPoint());
                                methodDescriptor.setResponseContentType(requestAnnotation.responseContentType());
                                methodDescriptor.setResponseStatusCode(requestAnnotation.responseCode());
                                methodDescriptor.setControllerName(methodControllerName);
                                methodDescriptor.setMethodName(e.toString());

                                if (controllers.contains(sampleDescriptor)) {
                                    ControllerDescriptor controller = controllers.get(controllers.indexOf(sampleDescriptor));
                                    controller.addMethod(methodDescriptor);
                                } else {
                                    orphanMethods.add(methodDescriptor);
                                }

                            }
                            break;
                    }
                }

                verifyOrphanElements();

                DescriptorParser descriptorParser = new DescriptorParser();
                LOGGER.info(descriptorParser.parse(classPojoDescriptorMap, classControllerDescriptorMap));

            }
        }

        LOGGER.info("api-blueprint annotation processing finished.");

        return false;
    }

    private void verifyOrphanElements() {

        LOGGER.debug("Verifying orphan elements...");

        if (!orphanMethods.isEmpty()) {
            for (MethodDescriptor next : orphanMethods) {

                LOGGER.info("Analysing orphan method: " + next);

                String controllerName = next.getControllerName();

                List<ControllerDescriptor> controllers;
                if (controllerNamePojoName.containsKey(controllerName)) {
                    controllers = classControllerDescriptorMap.get(controllerNamePojoName.get(controllerName));

                    ControllerDescriptor sampleDescriptor = new ControllerDescriptor();
                    sampleDescriptor.setControllerClassFullName(controllerName);

                    if (controllers.contains(sampleDescriptor)) {
                        ControllerDescriptor controller = controllers.get(controllers.indexOf(sampleDescriptor));
                        controller.addMethod(next);
                        LOGGER.info("Orphan methd added: " + next);
                        orphanMethods.remove(next);
                    }
                }

            }
        }
        LOGGER.debug("Orphan elements verified");
    }

}