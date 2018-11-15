package com.ngbp.scte.scte35.utils;

public class LongBitField {
	
	
	/**
	 * <p>Supports operations on bit-mapped fields. Instances of this class can be
	 * used to store a flag or data within an {@code int}, {@code short} or
	 * {@code byte}.</p>
	 *
	 * <p>Each {@code BitField} is constructed with a mask value, which indicates
	 * the bits that will be used to store and retrieve the data for that field.
	 * For instance, the mask {@code 0xFF} indicates the least-significant byte
	 * should be used to store the data.</p>
	 *
	 * <p>As an example, consider a car painting machine that accepts
	 * palong instructions as integers. Bit fields can be used to encode this:</p>
	 *
	 *<pre>
	 *    // blue, green and red are 1 byte values (0-255) stored in the three least
	 *    // significant bytes
	 *    BitField blue = new BitField(0xFF);
	 *    BitField green = new BitField(0xFF00);
	 *    BitField red = new BitField(0xFF0000);
	 *
	 *    // anyColor is a flag triggered if any color is used
	 *    BitField anyColor = new BitField(0xFFFFFF);
	 *
	 *    // isMetallic is a single bit flag
	 *    BitField isMetallic = new BitField(0x1000000);
	 *</pre>
	 *
	 * <p>Using these {@code BitField} instances, a palong instruction can be
	 * encoded into an integer:</p>
	 *
	 *<pre>
	 *    long paintInstruction = 0;
	 *    paintInstruction = red.setValue(paintInstruction, 35);
	 *    paintInstruction = green.setValue(paintInstruction, 100);
	 *    paintInstruction = blue.setValue(paintInstruction, 255);
	 *</pre>
	 *
	 * <p>Flags and data can be retrieved from the integer:</p>
	 *
	 *<pre>
	 *    // Prints true if red, green or blue is non-zero
	 *    System.out.println(anyColor.isSet(paintInstruction));   // prints true
	 *
	 *    // Prints value of red, green and blue
	 *    System.out.println(red.getValue(paintInstruction));     // prints 35
	 *    System.out.println(green.getValue(paintInstruction));   // prints 100
	 *    System.out.println(blue.getValue(paintInstruction));    // prints 255
	 *
	 *    // Prints true if isMetallic was set
	 *    System.out.println(isMetallic.isSet(paintInstruction)); // prints false
	 *</pre>
	 *
	 * @since 2.0
	 */
		private long value;
		
	    private final long _mask;
	    private final long _shift_count;

	    /**
	     * <p>Creates a BitField instance.</p>
	     *
	     * @param mask the mask specifying which bits apply to this
	     *  BitField. Bits that are set in this mask are the bits
	     *  that this BitField operates on
	     */
	    public LongBitField(final long mask) {
	        _mask = mask;
	        _shift_count = mask != 0 ? Long.numberOfTrailingZeros(mask) : 0;
	    }

	    /**
	     * <p>Obtains the value for the specified BitField, appropriately
	     * shifted right.</p>
	     *
	     * <p>Many users of a BitField will want to treat the specified
	     * bits as an long value, and will not want to be aware that the
	     * value is stored as a BitField (and so shifted left so many
	     * bits).</p>
	     *
	     * @see #setValue(int,int)
	     * @param holder the long data containing the bits we're interested
	     *  in
	     * @return the selected bits, shifted right appropriately
	     */
	    public long getValue() {
	        return getRawValue() >> _shift_count;
	    }

	    /**
	     * <p>Obtains the value for the specified BitField, appropriately
	     * shifted right, as a short.</p>
	     *
	     * <p>Many users of a BitField will want to treat the specified
	     * bits as an long value, and will not want to be aware that the
	     * value is stored as a BitField (and so shifted left so many
	     * bits).</p>
	     *
	     * @see #setShortValue(short,short)
	     * @param holder the short data containing the bits we're
	     *  interested in
	     * @return the selected bits, shifted right appropriately
	     */
	    public short getShortValue() {
	        return (short) getValue();
	    }

	    /**
	     * <p>Obtains the value for the specified BitField, unshifted.</p>
	     *
	     * @param holder the long data containing the bits we're
	     *  interested in
	     * @return the selected bits
	     */
	    public long getRawValue() {
	        return value;
	    }

	    /**
	     * <p>Obtains the value for the specified BitField, unshifted.</p>
	     *
	     * @param holder the short data containing the bits we're
	     *  interested in
	     * @return the selected bits
	     */
	    public short getShortRawValue() {
	        return (short) getRawValue();
	    }

	    
	    public long getLongRawValue() {
	        return (long) getRawValue();
	    }
	    /**
	     * <p>Returns whether the field is set or not.</p>
	     *
	     * <p>This is most commonly used for a single-bit field, which is
	     * often used to represent a boolean value; the results of using
	     * it for a multi-bit field is to determine whether *any* of its
	     * bits are set.</p>
	     *
	     * @param holder the long data containing the bits we're interested
	     *  in
	     * @return {@code true} if any of the bits are set,
	     *  else {@code false}
	     */
	    public boolean isSet(final long holder) {
	        return (holder & _mask) != 0;
	    }

	    /**
	     * <p>Returns whether all of the bits are set or not.</p>
	     *
	     * <p>This is a stricter test than {@link #isSet(int)},
	     * in that all of the bits in a multi-bit set must be set
	     * for this method to return {@code true}.</p>
	     *
	     * @param holder the long data containing the bits we're
	     *  interested in
	     * @return {@code true} if all of the bits are set,
	     *  else {@code false}
	     */
	    public boolean isAllSet(final long holder) {
	        return (holder & _mask) == _mask;
	    }

	    /**
	     * <p>Replaces the bits with new values.</p>
	     *
	     * @see #getValue(int)
	     * @param holder the long data containing the bits we're
	     *  interested in
	     * @param value the new value for the specified bits
	     * @return the value of holder with the bits from the value
	     *  parameter replacing the old bits
	     */
	    public long setValue(final long holder, final long value) {
	        return (holder & ~_mask) | ((value << _shift_count) & _mask);
	    }

	    /**
	     * <p>Replaces the bits with new values.</p>
	     *
	     * @see #getShortValue(short)
	     * @param holder the short data containing the bits we're
	     *  interested in
	     * @param value the new value for the specified bits
	     * @return the value of holder with the bits from the value
	     *  parameter replacing the old bits
	     */
	    public short setShortValue(final short holder, final short value) {
	        return (short) setValue(holder, value);
	    }

	    /**
	     * <p>Clears the bits.</p>
	     *
	     * @param holder the long data containing the bits we're
	     *  interested in
	     * @return the value of holder with the specified bits cleared
	     *  (set to {@code 0})
	     */
	    public long clear(final long holder) {
	        return holder & ~_mask;
	    }

	    /**
	     * <p>Clears the bits.</p>
	     *
	     * @param holder the short data containing the bits we're
	     *  interested in
	     * @return the value of holder with the specified bits cleared
	     *  (set to {@code 0})
	     */
	    public short clearShort(final short holder) {
	        return (short) clear(holder);
	    }

	    /**
	     * <p>Clears the bits.</p>
	     *
	     * @param holder the byte data containing the bits we're
	     *  interested in
	     *
	     * @return the value of holder with the specified bits cleared
	     *  (set to {@code 0})
	     */
	    public byte clearByte(final byte holder) {
	        return (byte) clear(holder);
	    }

	    /**
	     * <p>Sets the bits.</p>
	     *
	     * @param holder the long data containing the bits we're
	     *  interested in
	     * @return the value of holder with the specified bits set
	     *  to {@code 1}
	     */
	    public long set(final long holder) {
	    	//AND
	    	value = (holder << _shift_count) & _mask ;
	        return value;
	    }

	    /**
	     * <p>Sets the bits.</p>
	     *
	     * @param holder the short data containing the bits we're
	     *  interested in
	     * @return the value of holder with the specified bits set
	     *  to {@code 1}
	     */
	    public short setShort(final short holder) {
	        return (short) set(holder);
	    }

	    /**
	     * <p>Sets the bits.</p>
	     *
	     * @param holder the byte data containing the bits we're
	     *  interested in
	     *
	     * @return the value of holder with the specified bits set
	     *  to {@code 1}
	     */
	    public byte setByte(final byte holder) {
	        return (byte) set(holder);
	    }

	    /**
	     * <p>Sets a boolean BitField.</p>
	     *
	     * @param holder the long data containing the bits we're
	     *  interested in
	     * @param flag indicating whether to set or clear the bits
	     * @return the value of holder with the specified bits set or
	     *         cleared
	     */
	    public long setBoolean(final long holder, final boolean flag) {
	        return flag ? set(holder) : clear(holder);
	    }

	    /**
	     * <p>Sets a boolean BitField.</p>
	     *
	     * @param holder the short data containing the bits we're
	     *  interested in
	     * @param flag indicating whether to set or clear the bits
	     * @return the value of holder with the specified bits set or
	     *  cleared
	     */
	    public short setShortBoolean(final short holder, final boolean flag) {
	        return flag ? setShort(holder) : clearShort(holder);
	    }

	    /**
	     * <p>Sets a boolean BitField.</p>
	     *
	     * @param holder the byte data containing the bits we're
	     *  interested in
	     * @param flag indicating whether to set or clear the bits
	     * @return the value of holder with the specified bits set or
	     *  cleared
	     */
	    public byte setByteBoolean(final byte holder, final boolean flag) {
	        return flag ? setByte(holder) : clearByte(holder);
	    }


}
