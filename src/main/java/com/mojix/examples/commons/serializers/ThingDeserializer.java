package com.mojix.examples.commons.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.mojix.examples.commons.*;
import com.mojix.examples.commons.assertions.Assertions;
import com.mojix.examples.commons.wrappers.*;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by achambi on 11/7/16.
 * ThingDeserializer class.
 */

public class ThingDeserializer extends StdDeserializer<ThingWrapper> {

    private static Logger logger = Logger.getLogger(ThingDeserializer.class);

    /**
     * Constructor calling via reflection.
     */
    @SuppressWarnings("unused")
    private ThingDeserializer() {
        this(null);
    }

    /**
     * Constructor calling via reflection with class parameter.
     *
     * @param vc vc is the class to convert the wrapper.
     */
    private ThingDeserializer(Class<?> vc) {
        super(vc);
    }

    /**
     * Overridden method to deserialize json string to java classes.
     *
     * @param jp      Object to parse the Json
     * @param context deserializer context.
     * @return Thing Wrapper instance with contains the json parsed.
     * @throws IOException input/output exception.
     */
    @Override
    public ThingWrapper deserialize(JsonParser jp, DeserializationContext context)
            throws IOException {

        try {
            JsonNode node = jp.getCodec().readTree(jp);
            Assertions.voidNotNull("id", node.get("id"));
            Assertions.voidNotNull("serialNumber", node.get("serialNumber"));
            Assertions.voidNotNull("name", node.get("name"));
            Assertions.voidNotNull("createdTime", node.get("createdTime"));
            Assertions.voidNotNull("modifiedTime", node.get("modifiedTime"));
            Assertions.voidNotNull("time", node.get("modifiedTime"));
            Assertions.voidNotNull("group", node.get("group"));
            Assertions.voidNotNull("thingType", node.get("thingType"));

            Assertions.isTrueArgument("id", "Long", node.get("id").isNumber());
            Assertions.isTrueArgument("serialNumber", "String", node.get("serialNumber").isTextual());
            Assertions.isTrueArgument("name", "String", node.get("name").isTextual());
            Assertions.isTrueArgument("createdTime", node.get("createdTime").asText(), Constants.DATE_REG_EXP);
            Assertions.isTrueArgument("modifiedTime", node.get("modifiedTime").asText(), Constants.DATE_REG_EXP);
            Assertions.isTrueArgument("time", node.get("time").asText(), Constants.DATE_REG_EXP);
            Assertions.isTrueArgument("group", "Group Class", node.get("group").isObject());
            Assertions.isTrueArgument("thingType", "ThingType Class", node.get("thingType").isObject());
            SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);

            ObjectMapper mapper = (ObjectMapper) jp.getCodec();

            return new ThingWrapper(
                    (node.get("meta") == null) ? null : mapper.treeToValue(node.get("meta"), MetaWrapper.class),
                    node.get("id").longValue(),
                    node.get("serialNumber").asText(),
                    node.get("name").asText(),
                    format.parse(node.get("createdTime").asText()),
                    format.parse(node.get("modifiedTime").asText()),
                    format.parse(node.get("time").asText()),
                    mapper.treeToValue(node.get("group"), GroupThingWrapper.class),
                    mapper.treeToValue(node.get("thingType"), ThingPropertyValueWrapper.class),
                    deserializeProperties(mapper, (ArrayNode) node.get("properties"))
            );
        } catch (IllegalArgumentException ex) {
            logger.error("Error to validate a thing fields:", ex);
            throw new IOException("Error to validate a thing fields:", ex);
        } catch (ParseException ex) {
            logger.error("Error to parse a thing  fields:", ex);
            throw new IOException("Error to parse a thing fields:", ex);
        }
    }

    /**
     * Deserialize properties to list class.
     *
     * @param mapper     class to mapper the json String.
     * @param properties properties to convert in java class.
     * @return list of map[{@link String}, {@link ThingPropertyWrapper}]
     * @throws JsonProcessingException Exception to parse json string.
     */
    private PropertyWrapper deserializeProperties(ObjectMapper mapper, ArrayNode properties)
            throws
            JsonProcessingException {
        List<Map<String, ThingPropertyWrapper>> propertyList = new ArrayList<>();
        Map<String, ThingPropertyWrapper> current = new HashMap<>();
        Map<String, ThingPropertyWrapper> previous = new HashMap<>();
        if (properties != null) {
            int idx = 0;
            for (JsonNode propertyItem : properties) {
                Map<String, ThingPropertyWrapper> thingPropertyWrapperMap = new HashMap<>();
                Iterator<String> keys = propertyItem.fieldNames();
                while (keys.hasNext()) {
                    String currentKey = keys.next();
                    JsonNode entry = propertyItem.get(currentKey);
                    thingPropertyWrapperMap.put(currentKey, mapper.treeToValue(entry, ThingPropertyWrapper
                            .class));
                }
                propertyList.add(thingPropertyWrapperMap);
                if (idx == 0){
                    current = thingPropertyWrapperMap;
                } else {
                    previous = thingPropertyWrapperMap;
                }
                idx ++;
            }
        }
        return new PropertyWrapper(current, previous);
    }
}
