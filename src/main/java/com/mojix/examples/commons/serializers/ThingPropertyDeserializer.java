package com.mojix.examples.commons.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.mojix.examples.commons.wrappers.ThingPropertyWrapper;
import com.mojix.examples.commons.assertions.Assertions;
import com.mojix.examples.commons.utils.FormatUtil;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.Method;


/**
 * Created by achambi on 11/7/16.
 * Thing Property Deserializer class.
 */

public class ThingPropertyDeserializer extends StdDeserializer<ThingPropertyWrapper> {

    private Logger logger = Logger.getLogger(ThingPropertyDeserializer.class);

    @SuppressWarnings("unused")
    private ThingPropertyDeserializer() {
        this(null);
    }

    private ThingPropertyDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ThingPropertyWrapper deserialize(JsonParser jp, DeserializationContext context)
            throws IOException {

        try {
            JsonNode node = jp.getCodec().readTree(jp);
            Assertions.voidNotNull("id", node.get("id"));
            Assertions.voidNotNull("time", node.get("time"));
            Assertions.voidNotNull("dataTypeId", node.get("dataTypeId"));
            Assertions.isTrueArgument("time", "String", node.get("time").isTextual());
            Assertions.isTrueArgument("time", node.get("time").textValue(), Constants.DATE_REG_EXP);
            Assertions.isTrueArgument("dataTypeId", "integer", node.get("dataTypeId").isInt());

            Boolean blinked = (!Assertions.isNull(node.get("blinked")) && node.get("blinked").toString()
                    .equalsIgnoreCase("TRUE"));
            Boolean modified = (!Assertions.isNull(node.get("modified")) && node.get("modified").toString()
                    .equalsIgnoreCase("TRUE"));
            Boolean timeSeries = (!Assertions.isNull(node.get("timeSeries")) && node.get("timeSeries").toString()
                    .equalsIgnoreCase("TRUE"));

            Object value;
            DataType dateType = DataType.getDataTypeById(node.get("dataTypeId").longValue());
            JsonNode valueNode = node.get("value");
            if (dateType.getType().equals("NATIVE")) {
                value = jp.getCodec().treeToValue(valueNode, dateType.getClazz());
            } else {
                @SuppressWarnings("unchecked")
                Method m = dateType.getClazz().getMethod(dateType.getParseMethodName(), dateType.getArgumentType());
                if (valueNode.isArray()) {
                    value = m.invoke(null, valueNode.toString());
                } else {
                    value = m.invoke(null, valueNode.textValue());
                }

            }
            return new ThingPropertyWrapper(
                    node.get("id").longValue(),
                    FormatUtil.parse(node.get("time").textValue()),
                    blinked,
                    modified,
                    timeSeries,
                    value,
                    dateType.getId());

        } catch (Exception ex) {
            logger.error(ex);
            throw new IOException(ex);
        }
    }
}
