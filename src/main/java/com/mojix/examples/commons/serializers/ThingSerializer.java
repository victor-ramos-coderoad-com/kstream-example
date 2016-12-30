package com.mojix.examples.commons.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mojix.examples.commons.wrappers.*;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by dbascope on 12/29/16.
 * ThingDeserializer class.
 */

public class ThingSerializer extends JsonSerializer<ThingWrapper> {

    private static Logger logger = Logger.getLogger(ThingSerializer.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");


    @Override
    public void serialize(ThingWrapper value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode thing = mapper.createObjectNode();
        // Generates meta node
        thing.set("meta", getMetaNode(value.getMeta()));
        // Generates nonUDFs fields
        thing.put("id", value.getId());
        thing.put("serialNumber", value.getSerialNumber());
        thing.put("name", value.getName());
        thing.put("createdTime", dateFormat.format(value.getCreatedTime()));
        thing.put("modifiedTime", dateFormat.format(value.getModifiedTime()));
        thing.put("time", dateFormat.format(value.getTime()));
        // Generate group node
        thing.set("group", getGroupNode(value.getGroup()));
        // Generate thingType node
        thing.set("thingType", getThingTypeNode(value.getThingType()));
        // Generate UDF properties
        thing.set("properties", getProperties(value.getProperties()));
        gen.writeObject(thing);
    }

    private ObjectNode getMetaNode(MetaWrapper meta){
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode metaNode = mapper.createObjectNode();
        metaNode.put("bridgeCode", meta.getBridgeCode());
        metaNode.put("sqn", meta.getSqn());
        metaNode.put("specName", meta.getSpecName());
        ArrayNode origin = mapper.createArrayNode();
        for (float coord : meta.getOrigin()) {
            origin.add(coord);
        }
        metaNode.set("origin", origin);
        metaNode.put("units", meta.getUnits());
        metaNode.put("partition", meta.getPartition());
        metaNode.put("numPartitions", meta.getNumPartitions());
        metaNode.put("reblinked", meta.getReblinked());
        metaNode.put("outOfOrder", meta.getOutOfOrder());
        metaNode.put("newBlink", meta.getNewBlink());
        return metaNode;
    }

    private ObjectNode getGroupNode (GroupThingWrapper group) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode groupNode = mapper.createObjectNode();
        ObjectNode groupTypeNode = mapper.createObjectNode();
        groupTypeNode.put("id", group.getGroupType().getId());
        groupTypeNode.put("name", group.getGroupType().getName());
        groupTypeNode.put("code", group.getGroupType().getCode());
        groupNode.set("groupType", groupTypeNode);
        groupNode.put("id", group.getId());
        groupNode.put("name", group.getName());
        groupNode.put("code", group.getCode());
        return groupNode;
    }

    private ObjectNode getThingTypeNode (ThingPropertyValueWrapper thingType) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode thingTypeNode = mapper.createObjectNode();
        thingTypeNode.put("id", thingType.getId());
        thingTypeNode.put("name", thingType.getName());
        thingTypeNode.put("code", thingType.getCode());
        return thingTypeNode;
    }

    private ArrayNode getProperties (List<Map<String, ThingPropertyWrapper>> properties) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode propertiesNode = mapper.createArrayNode();
        ObjectNode currentNode = mapper.createObjectNode();
        for (String udf : properties.get(0).keySet()){
            ObjectNode udfNode = mapper.createObjectNode();
            udfNode.put("id", properties.get(0).get(udf).getId());
            udfNode.put("time", dateFormat.format(properties.get(0).get(udf).getTime()));
            udfNode.put("blinked", properties.get(0).get(udf).isBlinked());
            udfNode.put("modified", properties.get(0).get(udf).isModified());
            udfNode.put("timeSeries", properties.get(0).get(udf).isTimeSeries());
            getPropertyValue(udfNode, properties.get(0).get(udf).getValue(), properties.get(0).get(udf).getDataTypeId());
            udfNode.put("dataTypeId", properties.get(0).get(udf).getDataTypeId());
            currentNode.set(udf, udfNode);
        }
        propertiesNode.add(currentNode);
        ObjectNode previousNode = mapper.createObjectNode();
        for (String udf : properties.get(1).keySet()){
            ObjectNode udfNode = mapper.createObjectNode();
            udfNode.put("id", properties.get(1).get(udf).getId());
            udfNode.put("time", dateFormat.format(properties.get(1).get(udf).getTime()));
            udfNode.put("blinked", properties.get(1).get(udf).isBlinked());
            udfNode.put("modified", properties.get(1).get(udf).isModified());
            udfNode.put("timeSeries", properties.get(1).get(udf).isTimeSeries());
            getPropertyValue(udfNode, properties.get(1).get(udf).getValue(), properties.get(1).get(udf).getDataTypeId());
            udfNode.put("dataTypeId", properties.get(1).get(udf).getDataTypeId());
            previousNode.set(udf, udfNode);
        }
        propertiesNode.add(previousNode);
        return propertiesNode;
    }

    private void getPropertyValue (ObjectNode udfNode, Object value, Long dataTypeId) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode propertyValue = mapper.createObjectNode();
        switch (DataType.getDataTypeById(dataTypeId)){
            case TYPE_LON_LAT_ALT:
            case TYPE_XYZ:
                udfNode.put("value", value.toString());
                break;
            case TYPE_SHIFT:
                propertyValue.put("id", ((ShiftWrapper) value).getId());
                propertyValue.put("code", ((ShiftWrapper) value).getCode());
                propertyValue.put("name", ((ShiftWrapper) value).getName());
                propertyValue.put("active", ((ShiftWrapper) value).isActive());
                propertyValue.put("startTimeOfDay", ((ShiftWrapper) value).getStartTimeOfDay());
                propertyValue.put("endTimeOfDay", ((ShiftWrapper) value).getEndTimeOfDay());
                propertyValue.put("daysOfWeek", ((ShiftWrapper) value).getDaysOfWeek());
                udfNode.set("value", propertyValue);
                break;
            case TYPE_ZONE:
                propertyValue.put("id", ((ZoneWrapper) value).getId());
                propertyValue.put("code", ((ZoneWrapper) value).getCode());
                propertyValue.put("name", ((ZoneWrapper) value).getName());
                ObjectNode facilityNode = mapper.createObjectNode();
                facilityNode.put("time", dateFormat.format(((ZoneWrapper) value).getFacilityMap().getTime()));
                facilityNode.put("name", ((ZoneWrapper) value).getFacilityMap().getName());
                facilityNode.put("code", ((ZoneWrapper) value).getFacilityMap().getCode());
                facilityNode.put("blinked", ((ZoneWrapper) value).getFacilityMap().getBlinked());
                facilityNode.put("modified", ((ZoneWrapper) value).getFacilityMap().getModified());
                propertyValue.set("facilityMap", facilityNode);
                ObjectNode zoneTypeNode = mapper.createObjectNode();
                zoneTypeNode.put("time", dateFormat.format(((ZoneWrapper) value).getZoneType().getTime()));
                zoneTypeNode.put("name", ((ZoneWrapper) value).getZoneType().getName());
                zoneTypeNode.put("code", ((ZoneWrapper) value).getZoneType().getCode());
                zoneTypeNode.put("blinked", ((ZoneWrapper) value).getZoneType().getBlinked());
                zoneTypeNode.put("modified", ((ZoneWrapper) value).getZoneType().getModified());
                propertyValue.set("zoneType", zoneTypeNode);
                ObjectNode zoneGroupNode = mapper.createObjectNode();
                zoneGroupNode.put("time", dateFormat.format(((ZoneWrapper) value).getZoneGroup().getTime()));
                zoneGroupNode.put("name", ((ZoneWrapper) value).getZoneGroup().getName());
                zoneGroupNode.put("code", ((ZoneWrapper) value).getZoneGroup().getCode());
                zoneGroupNode.put("blinked", ((ZoneWrapper) value).getZoneGroup().getBlinked());
                zoneGroupNode.put("modified", ((ZoneWrapper) value).getZoneGroup().getModified());
                propertyValue.set("zoneGroup", zoneGroupNode);
                udfNode.set("value", propertyValue);
                break;
            case TYPE_GROUP:
                propertyValue.put("id", ((GroupThingFieldWrapper) value).getId());
                propertyValue.put("code", ((GroupThingFieldWrapper) value).getCode());
                propertyValue.put("name", ((GroupThingFieldWrapper) value).getName());
                propertyValue.put("description", ((GroupThingFieldWrapper) value).getDescription());
                propertyValue.put("hierarchyName", ((GroupThingFieldWrapper) value).getHierarchyName());
                propertyValue.put("archived", ((GroupThingFieldWrapper) value).isArchived());
                propertyValue.put("treeLevel", ((GroupThingFieldWrapper) value).getTreeLevel());
                udfNode.set("value", propertyValue);
                break;
            case TYPE_LOGICAL_READER:
                propertyValue.put("code", ((LogicalReaderWrapper) value).getCode());
                propertyValue.put("name", ((LogicalReaderWrapper) value).getName());
                propertyValue.put("zoneInId", ((LogicalReaderWrapper) value).getZoneInId());
                propertyValue.put("zoneOutId", ((LogicalReaderWrapper) value).getZoneOutId());
                propertyValue.put("x", ((LogicalReaderWrapper) value).getX());
                propertyValue.put("y", ((LogicalReaderWrapper) value).getY());
                propertyValue.put("z", ((LogicalReaderWrapper) value).getZ());
                udfNode.set("value", propertyValue);
                break;
            case TYPE_THING_TYPE:
                propertyValue.put("id", ((ThingWrapper) value).getId());
                propertyValue.put("serialNumber", ((ThingWrapper) value).getSerialNumber());
                propertyValue.put("name", ((ThingWrapper) value).getName());
                propertyValue.put("createdTime", dateFormat.format(((ThingWrapper) value).getCreatedTime()));
                propertyValue.put("modifiedTime", dateFormat.format(((ThingWrapper) value).getModifiedTime()));
                propertyValue.put("time", dateFormat.format(((ThingWrapper) value).getTime()));
                // Generate group node
                propertyValue.set("group", getGroupNode(((ThingWrapper) value).getGroup()));
                // Generate thingType node
                propertyValue.set("thingType", getThingTypeNode(((ThingWrapper) value).getThingType()));
                // Generate UDF properties
                if (((ThingWrapper) value).getProperties().size() > 0) {
                    ArrayNode propertiesNode = mapper.createArrayNode();
                    Map<String, ThingPropertyWrapper> property = ((ThingWrapper) value).getProperties().get(0);
                    ObjectNode currentNode = mapper.createObjectNode();
                    for (String udf : property.keySet()) {
                        ObjectNode udfThingNode = mapper.createObjectNode();
                        udfThingNode.put("id", property.get(udf).getId());
                        udfThingNode.put("time", dateFormat.format(property.get(udf).getTime()));
                        udfThingNode.put("blinked", property.get(udf).isBlinked());
                        udfThingNode.put("modified", property.get(udf).isModified());
                        udfThingNode.put("timeSeries", property.get(udf).isTimeSeries());
                        getPropertyValue(udfThingNode, property.get(udf).getValue(), property.get(udf).getDataTypeId());
                        udfThingNode.put("dataTypeId", property.get(udf).getDataTypeId());
                        currentNode.set(udf, udfThingNode);
                    }
                    propertiesNode.add(currentNode);
                    propertyValue.set("properties", propertiesNode);
                }
                udfNode.set("value", propertyValue);
                break;
            default:
                udfNode.put("value", value.toString());
        }
    }

}
