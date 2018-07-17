package com.template.appname.controller;

public enum RiskModelColor {

	GREEN("Green"), YELLOW("Yellow"), RED("Red");
	
	private final String val;

	RiskModelColor(String val) {
        this.val = val;
    }

    public String getRisk() {
        return val;
    }
	
}
