package com.ngbp.scte.scte35.encoder.model;

import java.nio.ByteBuffer;

import com.ngbp.scte.scte35.utils.BitField;
import com.ngbp.scte.scte35.utils.UnsignedInteger;
import com.ngbp.scte.scte35.utils.UnsignedLong;

/**
 * ngbp
 */
public class SegmentationDescriptor extends SpliceDescriptor {

	/**
	 *
segmentation_descriptor() {

		//in superclass
		 splice_descriptor_tag 							8 uimsbf 	default 0x02
		 descriptor_length								8 uimsbf
		 identifier 							 	   32 uimsbf
		 //in superclass
		  
 segmentation_event_id 							 	   32 uimsbf
 
 //8 bits
 segmentation_event_cancel_indicator 					1 bslbf 	Bitmask: 1000 0000 = 0x80
 reserved 												7 bslbf		Bitmask: 0111 1111 = 0x7F
 
 if(segmentation_event_cancel_indicator == ‘0’) {
 
 //8 bits
  program_segmentation_flag 							1 bslbf		Bitmask: 1000 0000 = 0x80
  segmentation_duration_flag 							1 bslbf		Bitmask: 0100 0000 = 0x40
  delivery_not_restricted_flag 							1 bslbf		Bitmask: 0010 0000 = 0x20
 
  if(delivery_not_restricted_flag == ‘0’) {
   web_delivery_allowed_flag 							1 bslbf		Bitmask: 0001 0000 = 0x10
   no_regional_blackout_flag 							1 bslbf		Bitmask: 0000 1000 = 0x08
   archive_allowed_flag 								1 bslbf		Bitmask: 0000 0100 = 0x04
   device_restrictions 									2 bslbf		Bitmask: 0000 0011 = 0x03
   //5 bits, plus 3 in above 
  } else {
   Reserved 											5 bslbf		Bitmask: 0001 1111 = 0x1F
  //5 bits
   * 
  }
//end 8 bits  
  
 
  if(program_segmentation_flag == ‘0’) {
   component_count 8 uimsbf
   for(i=0;i<component_count;i++) {
    component_tag 8 uimsbf
    reserved 7 bslbf
    pts_offset 33 uimsbf
   }
  }
  
  if(segmentation_duration_flag == ‘1’)
   	segmentation_duration 						   40 uimsbf
   
   segmentation_upid_type 							8 uimsbf
   segmentation_upid_length 						8 uimsbf
   segmentation_upid()								
   segmentation_type_id 							8 uimsbf
   segment_num 										8 uimsbf
   segments_expected 								8 uimsbf
   if(segmentation_type_id == ‘0X34’ ||
      segmentation_type_id == ‘0X36’) {
    sub_segment_num 8 uimsbf
    sub_segments_expected 8 uimsbf
   }
  }
} 
	 */
	
	public SegmentationDescriptor() {
		super();
		spliceDescriptorTag.setValue(0x02);
	}
	
	//start building inner payload here
    public UnsignedInteger segmentationEventID = 			UnsignedInteger.fromIntBits(0x00000000, 32);
    
    //8 bits
    public BitField segmentationEventCancelIndicator = 		new BitField(0x80) {{ set(0); }};
    public BitField reserved1 = 							new BitField(0x7F) {{ set(0xff); }};
    
    
    public BitField programSegmentationFlag = 				new BitField(0x80) {{ set(1); }}; //treat this timesignal for a full program rather than component
    public BitField segmentationDurationFlag = 				new BitField(0x40) {{ set(0); }};;
    public BitField deliveryNotRestricted = 				new BitField(0x20) {{ set(1); }};;
    
    //if(delivery_not_restricted_flag == 0)
    public BitField webDeliveryAllowedFlag = 				new BitField(0x10) {{ set(0); }};;
    public BitField noRegionalBlackoutFlag = 				new BitField(0x08) {{ set(0); }};;
    public BitField archiveAllowed = 						new BitField(0x04) {{ set(0); }};;
    public BitField deviceRestriction = 					new BitField(0x03) {{ set(0); }};;
    
    //alt remaining 5 bits
    public BitField reserved2 = 							new BitField(0x1F) {{ set(0xFF); }};;
    
    //TODO: program_segmentation_flag chomp?
    
    public UnsignedLong segmentationDuration = 				UnsignedLong.fromIntBits(0x0000000000000000L, 40);
    
    public UnsignedInteger segmentationUPIDtype = 			UnsignedInteger.fromIntBits(0x00, 8);
    
    public static final int SEGMENTATION_UPID_TYPE_USER_DEFINED = 0x01; //deprecated, use 0x0C
    public static final int SEGMENTATION_UPID_TYPE_AD_ID = 0x03; 

    public static final int SEGMENTATION_UPID_TYPE_MPU = 0x0C; //Managed Private UPID structure
    public static final int SEGMENTATION_UPID_TYPE_URL = 0x0F; //url


    public UnsignedInteger segmentationUPIDlength = 		UnsignedInteger.fromIntBits(0x00, 8);

    //TODO - wire segmentationUPIDlength as setter on segmentationUPID
    public ByteBuffer	segmentationUPID; //payload
    
    //see below for segmentation type ids
    
    public UnsignedInteger segmentationTypeID = 			UnsignedInteger.fromIntBits(0x00, 8);
    public static final int SEGMENTATION_TYPE_CONTENT_IDENTIFICATION = 0x01;
    
    public static final int SEGMENTATION_TYPE_PROGRAM_START = 0x10;
    public static final int SEGMENTATION_TYPE_PROGRAM_END = 0x11;
    
    
    public static final int SEGMENTATION_TYPE_DISTRIBUTOR_ADVERTISEMENT_START = 0x32; //Distributor Advertisement Start
    public static final int SEGMENTATION_TYPE_DISTRIBUTOR_ADVERTISEMENT_END = 0x33; //Distributor Advertisement End
    
    public UnsignedInteger segmentNum = 					UnsignedInteger.fromIntBits(0x00, 8);;
    public UnsignedInteger segmentsExpected = 				UnsignedInteger.fromIntBits(0x00, 8);;
    
    //only if segmentation_type_id == ‘0X34’ ||   segmentation_type_id == ‘0X36’
    public UnsignedInteger sub_segment_num =				UnsignedInteger.fromIntBits(0x00, 8);
    public UnsignedInteger sub_segments_expected = 			UnsignedInteger.fromIntBits(0x00, 8);
    
    
    /** 
     * 
     * 

segmentation_upid_type table
 

segmentation_upid_type	segmentation_upid_length	segmentation_upid		Description

0x00 					0 							Not Used 				The segmentation_upid is not defined and is not present in the descriptor.
0x01 					variable 					User Defined 			Deprecated: use type 0x0C; the segmentation_upid does not follow a standard naming scheme.
0x02 					8 							ISCI 					Deprecated: use type 0x03 8 characters; 4 alpha characters followed by 4 numbers.
0x03 					12 							Ad-ID 					Defined by the Advertising Digital Identification, LLC group. 12 characters; 4 alpha characters (company identification prefix) followed by 8 alphanumeric characters. (See [Ad Id])
0x04 					32 							UMID 					See [SMPTE 330M]
0x05 					8 							ISAN 					Deprecated: use type 0x06 ISO 15706 binary encoding.
0x06 					12 							ISAN 					Formerly known as V-ISAN. ISO 15706-2 binary encoding (“versioned” ISAN). See [ISO 15706-2 ].
0x07 					12 							TID 					Tribune Media Systems Program identifier. 12 characters; 2 alpha characters followed by 10 numbers.
0x08 					8 							TI 						AiringID (Formerly Turner ID) used to indicate a specific airing of a program that is unique within a network.
0x09					variable 					ADI 					CableLabs metadata identifier as defined in Section 10.3.3.2.
0x0A 					12 							EIDR 					An EIDR (see [EIDRA]) represented in Compact Binary encoding as defined in Section 2.1.1 in EIDR ID Format (see [EIDR ID FORMAT])
0x0B 					variable 					ATSC Content Identifier	ATSC_content_identifier() structure as defined in [ATSC A/57B].
0x0C 					variable 					MPU() 					Managed Private UPID structure as defined in section Error!
0x0D 					variable 					MID() 					Multiple UPID types structure as defined in section 10.3.3.4. 
0x0E 					variable 					ADS Information 		Advertising information. The specific usage is out of scope of this standard.
0x0F 					variable 					URI 					Universal Resource Identifier (see [RFC 3986]).
0x10-0xFF 				variable 					Reserved 				Reserved for future standardization. 



segmentation type id:

Segmentation Message 		Segmentation_type_id 	segment_num 	segments_expected	sub_segment_num 	sub_segments_expected

Not Indicated 				0x00 					0 				0 					Not used 			Not Used
Content Identification 		0x01 					0 				0 					Not used 			Not Used
Program Start 				0x10 1 1 Not used Not Used
Program End					0x11 1 1 Not used Not Used
Program Early Termination	0x12 1 1 Not used Not Used
Program Breakaway			0x13 1 1 Not used Not Used
Program Resumption 			0x14 1 1 Not used Not Used 

Program Runover Planned		0x15 1 1 Not used Not Used
Program Runover Unplanned	0x16 1 1 Not used Not Used
Program Overlap Start		0x17 1 1 Not used Not Used
Program Blackout Override	0x18 0 0 Not used Not Used
Program Start–In Progress 	0x19 1 1 Not used Not used
Chapter Start 				0x20 Non-zero Non-zero Not used Not Used
Chapter End					0x21 Non-zero Non-zero Not used Not Used

Provider Advertisement Start			0x30 0 or Non-zero 		0 or Non-zero 		Not used 		Not Used
Provider Advertisement End				0x31 0 or Non-zero 		0 or Non-zero 		Not used 		Not Used

Distributor Advertisement Start 		0x32 0 or Non-zero 		0 or Non-zero 		Not used 		Not Used
Distributor Advertisement End 			0x33 0 or Non-zero 		0 or Non-zero 		Not used		Not Used

Provider Placement Opportunity Start 	0x34 0 or Non-zero 		0 or Non-zero 		0 or Non-zero 	0 or Non-zero
Provider Placement Opportunity End 		0x35 0 or Non-zero 		0 or Non-zero 		Not used 		Not Used
Distributor Placement Opportunity Start 0x36 0 or Non-zero 		0 or Non-zero 		0 or Non-zero 	0 or Non-zero
Distributor Placement Opportunity End 	0x37 0 or Non-zero 		0 or Non-zero 		Not used 		Not Used
Unscheduled Event Start 				0x40 0 					0 					Not used 		Not Used
Unscheduled Event End 					0x41 0 					0 					Not used 		Not Used
Network Start 							0x50 0					0 					Not used 		Not Used
Network End 							0x51 0 					0 					Not used 		Not Used 

     */
}
