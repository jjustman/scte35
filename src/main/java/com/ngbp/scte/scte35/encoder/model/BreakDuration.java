package com.ngbp.scte.scte35.encoder.model;

import com.ngbp.scte.scte35.utils.LongBitField;

/**
 * Created by andres.aguilar on 6/16/16.
 */
public class BreakDuration {
	
/**should look very similar to the spliceTime
 
break_duration() 


//40 bytes total
 auto_return 					1
 reserved						6
 duration					   33

	 */
	
    public LongBitField autoReturn = 		new LongBitField(0x8000000000L) {{ set(0); }};
    public LongBitField reserved1 = 		new LongBitField(0x7E00000000L) {{ set(0x3F); }};
    public LongBitField duration = 			new LongBitField(0x1FFFFFFFFFL) {{ set(0); }};
}
