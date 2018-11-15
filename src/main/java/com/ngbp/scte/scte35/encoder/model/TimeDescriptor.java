package com.ngbp.scte.scte35.encoder.model;

/**
*
* ngbp - 
* todo - implement data structs
*/
public class TimeDescriptor extends SpliceDescriptor {

	private TimeDescriptor() {
		spliceDescriptorTag.setValue(0x03);

	}
}
