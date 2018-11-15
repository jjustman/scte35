package com.ngbp.scte.scte35.encoder.model;


import com.ngbp.scte.scte35.utils.BitField;
import com.ngbp.scte.scte35.utils.LongBitField;

/**
 */
public class SpliceTime {

	/**
	 * 
	 * 
time_specified_flag 			1 bslbf 	Bitmask: 1000 0000 = 0x80

if(time_specified_flag == 1) {
	//40 bits
	reserved 					6 bslbf 	Bitmask: 0111 1110 0000 0000 0000 0000 0000 0000 0000 0000 = 0x7E00000000	value 0
	pts_time 				   33 uimsbf	Bitmask: 0000 0001 1111 1111 1111 1111 1111 1111 1111 1111 = 0x1FFFFFFFFF	value 0 for no pts time

 }  else {
	reserved 					7 bslbf 	Bitmask: 0111 1111 = 0x7F
	
}
	
	assume time_specified_flag will be false for now
	 */
	
	
    public BitField timeSpecifiedFlag =		new BitField(0x80) {{ set(0); }};
    
    public LongBitField reserved1 = 		new LongBitField(0x7E00000000L) {{ set(0x3F); }};
    public LongBitField ptsTime = 			new LongBitField(0x1FFFFFFFFFL) {{ set(0); }};
    
    public BitField reserved2 = 			new BitField(0x7F) {{ set(0x7F); }};

    @Override
    public String toString() {
        return "SpliceTime{" +
                "timeSpecifiedFlag=" + timeSpecifiedFlag +
                ", reserved1=" + reserved1 +
                ", ptsTime=" + ptsTime +
                ", reserved2=" + reserved2 +
                '}';
    }
}
