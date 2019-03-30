/**
 * www.taleteller.de
 * 
 * TaletellerGrid
 *   TileDrawStateMember
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;



/**
 * Member to a specific @TileDrawState.
 *
 */
public abstract class TileDrawStateMember {

	
	protected ArrayList<TileDrawStateMemberData> drawstate_data;	
	public TileDrawStateMember() {
		drawstate_data = new ArrayList<>();
	}
	
	public void RegisterTo(TileDrawState state) {
		state.addMember(this);
		drawstate_data.add(new TileDrawStateMemberData(state));
		// make sure they are ordered after their z-values
		Collections.sort(drawstate_data, new Comparator<TileDrawStateMemberData>() {
			@Override
			public int compare(TileDrawStateMemberData o1, TileDrawStateMemberData o2) {
				if(o1.getState().getZ() > o2.getState().getZ())
					return 1;
				if(o2.getState().getZ() > o1.getState().getZ())
					return -1;
				return 0;
			}
		});
	}
	
	// only package wide
	void UnregisterFrom(TileDrawState _state) {
		TileDrawStateMemberData olddata = null;
		for (TileDrawStateMemberData data : drawstate_data) {
			if(data.state.equals(_state)) {
				olddata = data;
				break;
			}
		}
		if(olddata != null)
			drawstate_data.remove(olddata);
		else 
			drawstate_data.removeIf(Objects::isNull);
	}
	
	/** Sets the values for the draw states assigned to this owner,
	 *  which are used for drawing the tiles.
	 *  This function iterates thorugh all states this tile has
	 *  registered, so it should not be called every UI update. */
	public abstract void SetDrawStateValuesFor(TileDrawState state
			, double value, double value_max, double alpha);
	
	
	public abstract void SetDrawBordersFor(TileDrawState state, 
			boolean top, boolean topright, boolean bottomright,
			boolean bottom, boolean bottomleft, boolean topleft);

	public ArrayList<TileDrawStateMemberData> getDrawStateMemberData() {
		return drawstate_data;
	}
	
}
