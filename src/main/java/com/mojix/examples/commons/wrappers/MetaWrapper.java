package com.mojix.examples.commons.wrappers;

import java.io.Serializable;

public class MetaWrapper implements Serializable {
    private static final long serialVersionUID = 1L;
    private String bridgeCode;
    private Long sqn;
    private String specName;
    private Float[] origin;
    private String units;
    private Long partition;
    private Long numPartitions;
    private Boolean reblinked;
    private Boolean outOfOrder;
    private Boolean newBlink;

    public String getBridgeCode() {
        return bridgeCode;
    }

    public void setBridgeCode(String bridgeCode) {
        this.bridgeCode = bridgeCode;
    }

    public Long getSqn() {
        return sqn;
    }

    public void setSqn(Long sqn) {
        this.sqn = sqn;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public Float[] getOrigin() {
        return origin;
    }

    public void setOrigin(Float[] origin) {
        this.origin = origin;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public Long getPartition() {
        return partition;
    }

    public void setPartition(Long partition) {
        this.partition = partition;
    }

    public Long getNumPartitions() {
        return numPartitions;
    }

    public void setNumPartitions(Long numPartitions) {
        this.numPartitions = numPartitions;
    }

    public Boolean getReblinked() {
        return reblinked;
    }

    public void setReblinked(Boolean reblinked) {
        this.reblinked = reblinked;
    }

    public Boolean getOutOfOrder() {
        return outOfOrder;
    }

    public void setOutOfOrder(Boolean outOfOrder) {
        this.outOfOrder = outOfOrder;
    }

    public Boolean getNewBlink() {
        return newBlink;
    }

    public void setNewBlink(Boolean newBlink) {
        this.newBlink = newBlink;
    }

}
