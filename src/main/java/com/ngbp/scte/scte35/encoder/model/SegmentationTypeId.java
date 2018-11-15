package com.ngbp.scte.scte35.encoder.model;

public class SegmentationTypeId {

	/**
	 * 
	 * segmentation_type_id - The 8 bit value shall contain one of the values in Table 22 to designate type of
segmentation. All unused values are reserved. When the segmentation_type_id is 0x01 (Content
Identification), the value of segmentation_upid_type shall be non-zero. If segmentation_upid_length is
zero then segmentation_type_id shall be set to 0x00 for Not Indicated. 



	 * Segmentation
Message Segmentation_type_id segment_num segments_expected sub_segment_num sub_segments_expected
Not Indicated 0x00 0 0 Not used Not Used
Content
Identification 0x01 0 0 Not used Not Used
Program Start 0x10 1 1 Not used Not Used
Program End 0x11 1 1 Not used Not Used
Program Early
Termination 0x12 1 1 Not used Not Used
Program
Breakaway 0x13 1 1 Not used Not Used
Program
Resumption 0x14 1 1 Not used Not Used 


Segmentation
Message Segmentation_type_id segment_num segments_expected sub_segment_num sub_segments_expected
Program
Runover
Planned
0x15 1 1 Not used Not Used
Program
Runover
Unplanned
0x16 1 1 Not used Not Used
Program
Overlap Start 0x17 1 1 Not used Not Used
Program
Blackout
Override
0x18 0 0 Not used Not Used
Program Start
– In Progress 0x19 1 1 Not used Not used
Chapter Start 0x20 Non-zero Non-zero Not used Not Used
Chapter End 0x21 Non-zero Non-zero Not used Not Used
Provider
Advertisement
Start
0x30 0 or Non-zero 0 or Non-zero Not used Not Used
Provider
Advertisement
End
0x31 0 or Non-zero 0 or Non-zero Not used Not Used
Distributor
Advertisement
Start
0x32 0 or Non-zero 0 or Non-zero Not used Not Used
Distributor
Advertisement
End
0x33 0 or Non-zero 0 or Non-zero Not used Not Used
Provider
Placement
Opportunity
Start
0x34 0 or Non-zero 0 or Non-zero 0 or Non-zero 0 or Non-zero
Provider
Placement
Opportunity
End
0x35 0 or Non-zero 0 or Non-zero Not used Not Used
Distributor
Placement
Opportunity
Start
0x36 0 or Non-zero 0 or Non-zero 0 or Non-zero 0 or Non-zero
Distributor
Placement
Opportunity
End
0x37 0 or Non-zero 0 or Non-zero Not used Not Used
Unscheduled
Event Start 0x40 0 0 Not used Not Used
Unscheduled
Event End 0x41 0 0 Not used Not Used
Network Start 0x50 0 0 Not used Not Used
Network End 0x51 0 0 Not used Not Used


	 */
}
