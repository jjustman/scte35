package com.ngbp.scte.scte35.encoder.model;
import com.ngbp.scte.scte35.utils.UnsignedInteger;

/*
 * ngbp - scte35 -
 */
public class SpliceDescriptor {

	/**
	 * 
	 * 
	 * 
0x00 AvailDescriptor avail_descriptor
0x01 DTMFDescriptor DTMF_descriptor
0x02 SegmentationDescriptor segmentation_descriptor
0x03 TimeDescriptor time_descriptor
0x04 – 0xFF Reserved for future SCTE splice_descriptors 

splice_descriptor_tag 			8 uimsbf
descriptor_length 				8 uimsbf
identifier 					   32 uimsbf  (fixed as 0x43554549)
 
private_byte				    N of *descriptor (must be less than 2^8-1 bits)
	 */
	
	//create as SegmentationDescriptor
    public UnsignedInteger spliceDescriptorTag = 	UnsignedInteger.fromIntBits(0xFF, 8); //set to none by default, subclasses are required to set this value properly
    public UnsignedInteger descriptorLength =		UnsignedInteger.fromIntBits(0x00, 8);
    public UnsignedInteger identifier = 			UnsignedInteger.fromIntBits(0x43554549, 32);
    
    @Override
    public String toString() {
        return "SpliceDescriptor{" +
                "spliceDescriptorTag=" + spliceDescriptorTag +
                ", descriptorLength=" + descriptorLength +
                ", identifier=" + identifier +
                '}';
    }
}
