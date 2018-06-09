/**
 * www.taleteller.de
 * 
 * TaletellerGrid
 *   TileDrawStateMemberData
 * 
 * Summary:
 *   
 * 
 * History:
 *   25.11.2017 - Cleaning of code
 *   
 * 
 * Ideas:
 *   
 * 
 * Stephan Hogrefe, Edinburgh, 2017
 */
package de.taleteller.grid.drawstate;


public class TileDrawStateMemberData {

	/** connected state */
	TileDrawState state;
	/** represented value wrapper */
	double draw_value;
	/** max value wrapper */
	double draw_value_max;
	/** alpha value */
	double alpha;
	
	/////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////
	
	/* border data if the drawstate is set to draw borders */
	/** flag to draw top border */
	boolean drawTop;
	/** flag to draw top right border */
	boolean drawTopright;
	/** flag to draw top left border */
	boolean drawTopleft;
	/** flag to draw bottom border */
	boolean drawBottom;
	/** flag to draw bottom right border */
	boolean drawBottomright;
	/** flag to draw bottom left border */
	boolean drawBottomleft;
	
	public TileDrawStateMemberData(TileDrawState state) {
		this.state = state;
	}
		
	/////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////
	
	public void setDraw_values(double value, double value_max, double alpha) {
		this.draw_value = value;
		this.draw_value_max = value_max;
		this.alpha = alpha;
	}
	
	public void setDrawBorders(boolean top, boolean topright, boolean bottomright,
			boolean bottom, boolean bottomleft, boolean topleft) {
		this.drawTop = top;
		this.drawTopright = topright;
		this.drawBottomright = bottomright;
		this.drawBottom = bottom;
		this.drawBottomleft = bottomleft;
		this.drawTopleft = topleft;
	}
		
	/////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////
	
	public TileDrawState getState() {
		return state;
	}
	
	public double getDraw_value() {
		return draw_value;
	}

	public double getDraw_value_max() {
		return draw_value_max;
	}

	public double getAlpha() {
		return alpha;
	}

	public boolean isDrawTop() {
		return drawTop;
	}

	public boolean isDrawTopright() {
		return drawTopright;
	}

	public boolean isDrawTopleft() {
		return drawTopleft;
	}

	public boolean isDrawBottom() {
		return drawBottom;
	}

	public boolean isDrawBottomright() {
		return drawBottomright;
	}

	public boolean isDrawBottomleft() {
		return drawBottomleft;
	}
	
}
