/**
 * www.taleteller.de
 * 
 * TaletellerGrid
 *   TileDrawState
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

import de.taleteller.animation.TileImageData;
import de.taleteller.core.abstraction.IDed;


public class TileDrawState extends IDed {

	/** list of activation listeners, ie those who are responsible of re-drawing 
	 *  the member tiles of this state once this state gets activated. */
	private ArrayList<TileDrawStateActivationListener> activation_listeners;
	
	/** active flag */
	boolean active;
	
	/** list of members */
	ArrayList<TileDrawStateMember> members;
	
	/** image data to be drawn on given values */
	TileImageData image_data;
	
	/** z value */
	int z;
	
	//////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////

	public TileDrawState(TileImageData image_data
			, boolean active) {
		this.image_data = image_data;
		this.activation_listeners = new ArrayList<>();
		this.active = active;
		this.members = new ArrayList<>();
	}
	
	public TileDrawState(TileImageData image_data
			, boolean active
			, int z) {
		this.image_data = image_data;
		this.activation_listeners = new ArrayList<>();
		this.active = active;
		this.members = new ArrayList<>();
	}
	
	public TileDrawState(TileImageData image_data
			, boolean active
			, TileDrawStateActivationListener activationListener) {
		this.image_data = image_data;
		this.activation_listeners = new ArrayList<>();
		this.active = active;
		this.members = new ArrayList<>();
		
		RegisterActivationListener(activationListener);
	}
	
	public TileDrawState(TileImageData image_data
			, boolean active
			, int z
			, TileDrawStateActivationListener activationListener) {
		this.image_data = image_data;
		this.activation_listeners = new ArrayList<>();
		this.active = active;
		this.z = z;
		this.members = new ArrayList<>();
		RegisterActivationListener(activationListener);
	}
	
	//////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////

	public boolean isActive() {
		return active;
	}
	
	public int getZ() {
		return z;
	}
	
	public void setActive(boolean flag) {
		if(this.active != flag) {
			/* if the activation is changing, inform all listeners */
			for (TileDrawStateActivationListener listener : activation_listeners) {
				listener.onTileDrawState_ActivationChange();
			}
		}
		this.active = flag;
	}
	
	/**
	 * Removes all members of this tile draw state,
	 * ie cleares this drawstate of its members.
	 */
	public void clearAllMembers() {
		for (TileDrawStateMember member : members) {
			member.UnregisterFrom(this);
		}
		members.clear(); // <-- this should be redundant
	}

	/** Only package wide */
	void addMember(TileDrawStateMember member) {
		this.members.add(member);
	}

	public ArrayList<TileDrawStateMember> getTileDrawStateMembers() {
		return members;
	}

	public void RegisterActivationListener(TileDrawStateActivationListener listener) {
		this.activation_listeners.add(listener);
	}
	
	public TileImageData getImage_data() {
		return image_data;
	}

	public void setImage_data(TileImageData image_data) {
		this.image_data = image_data;
	}
	
	//////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + z;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		TileDrawState other = (TileDrawState) obj;
		if (getId() != other.getId())
			return false;
		return true;
	}
	
}
