package com.infosys.test.automation.dto;

import org.json.simple.parser.ParseException;

public interface CondConfig {

    public boolean evaluateCondition(String parentRecord, String childRecord) throws ParseException;
}
