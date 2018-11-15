package com.ngbp.scte.scte35.encoder.model;

import java.util.ArrayList;

import com.ngbp.scte.scte35.utils.BitField;
import com.ngbp.scte.scte35.utils.UnsignedInteger;

/**
 * 
 * 

splice_insert() {  
//
	splice_event_id 							   32	
	
	//8	bit		
	splice_event_cancel_indicator					1   bslbf 	Bitmask: 1000 0000 = 0x80
	reserved 										7			Bitmask: 0111 1111 = 0x7F 
	
		
	if(splice_event_cancel_indicator == ‘0’) {
		//8 bit
		out_of_network_indicator 					1			Bitmask: 1000 0000 = 0x80
		program_splice_flag 						1			Bitmask: 0100 0000 = 0x40
		duration_flag								1			Bitmask: 0010 0000 = 0x20
		splice_immediate_flag						1			Bitmask: 0001 0000 = 0x10
		reserved									4			Bitmask: 0000 1111 = 0x0F
		
		if((program_splice_flag == ‘1’) 
			&& (splice_immediate_flag == ‘0’))
			splice_time() 							?
	
		if(program_splice_flag == ‘0’) {
			component_count							8
			for(i=0;i<component_count;i++) {
				component_tag						8
					if(splice_immediate_flag == ‘0’) 
						splice_time()
			} 
		}
		
		if(duration_flag == ‘1’) 
			break_duration()						?
		unique_program_id							16
		avail_num 									8
		avails_expected								8
	} 
}
32 1 7
1 1 1 1 4
8 8
16 8 8
 
uimsbf bslbf bslbf
bslbf bslbf bslbf bslbf bslbf
uimsbf uimsbf
uimsbf uimsbf uimsbf
                            
 */
public class SpliceInsert {

	//32 bits
    public UnsignedInteger spliceEventID = 				UnsignedInteger.fromIntBits(0,  32);
    
    //1 + 7 = byte
    public BitField spliceEventCancelIndicator =		new BitField(0x80) {{ set(0); }};	
    public BitField reserved1 = 						new BitField(0x7F) {{ set(0xFF); }};
    
    //1+1+1+1 + 4
    public BitField outOfNetworkIndicator = 			new BitField(0x80) {{ set(0); }};	
    public BitField programSpliceFlag = 				new BitField(0x40) {{ set(0); }};	;
    public BitField durationFlag = 						new BitField(0x80) {{ set(0); }};	;
    public BitField spliceImmediateFlag = 				new BitField(0x10) {{ set(0); }};	;
    public BitField reserved2 = 						new BitField(0x0F) {{ set(0x0F); }};	;

    //if((program_splice_flag == ‘1’) && (splice_immediate_flag == ‘0’))
    public SpliceTime sisp = new SpliceTime();
    
    //if(program_splice_flag == ‘0’) {
    //8 bits
    private UnsignedInteger componentCount = 			UnsignedInteger.fromIntBits(0,  8);

    public UnsignedInteger getComponentCount() {
		return componentCount;
	}

	private ArrayList<ComponentTagSpliceTime> componentTagSpliceTimeContainer = new ArrayList<ComponentTagSpliceTime>();
    //8 bits * componentCount
    
    public ArrayList<ComponentTagSpliceTime> getComponentTagSpliceTimeContainer() {
		return componentTagSpliceTimeContainer;
	}

	public void setComponentTagSpliceTimeContainer(ArrayList<ComponentTagSpliceTime> componentTagSpliceTimeContainer) {
    	this.componentTagSpliceTimeContainer = componentTagSpliceTimeContainer;
    	this.componentCount.setValue(componentTagSpliceTimeContainer.size());
    }
    
    
    //if(splice_immediate_flag == '0')
	public SpliceTime sisp2 = new SpliceTime();

	//if(duration_flag == 1)
	public BreakDuration breakDuration = new BreakDuration();

	//16
    public UnsignedInteger uniqueProgramID =			UnsignedInteger.fromIntBits(0,  16);
    
    //8
    public UnsignedInteger availNum = 					UnsignedInteger.fromIntBits(0,  8);

    //8
    public UnsignedInteger availsExpected = 			UnsignedInteger.fromIntBits(0,  8);;

    @Override
    public String toString() {
        return "SpliceInsert{" +
                "spliceEventID=" + spliceEventID +
                ", spliceEventCancelIndicator=" + spliceEventCancelIndicator +
                ", reserved1=" + reserved1 +
                ", outOfNetworkIndicator=" + outOfNetworkIndicator +
                ", programSpliceFlag=" + programSpliceFlag +
                ", sisp=" + sisp +
                ", durationFlag=" + durationFlag +
                ", spliceImmediateFlag=" + spliceImmediateFlag +
                ", breakDuration=" + breakDuration +
                ", reserved2=" + reserved2 +
                ", uniqueProgramID=" + uniqueProgramID +
                ", availNum=" + availNum +
                ", availsExpected=" + availsExpected +
                '}';
    }
    
    public class ComponentTagSpliceTime {
        public UnsignedInteger componentTag = UnsignedInteger.fromIntBits(0,  8);
        public SpliceTime spliceTime;
        
		public UnsignedInteger getComponentTag() {
			return componentTag;
		}
		public void setComponentTag(UnsignedInteger componentTag) {
			this.componentTag = componentTag;
		}
		public SpliceTime getSpliceTime() {
			return spliceTime;
		}
		public void setSpliceTime(SpliceTime spliceTime) {
			this.spliceTime = spliceTime;
		}
        
        

    }
}
