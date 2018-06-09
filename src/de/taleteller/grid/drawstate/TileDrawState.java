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

import de.taleteller.core.abstraction.IDed;


public class TileDrawState extends IDed {

	/** list of activation listeners, ie those who are responsible of re-drawing 
	 *  the member tiles of this state once this state gets activated. */
	private ArrayList<TileDrawStateActivationListener> activation_listeners;
	
	/** active flag */
	boolean active;
	
	/** list of members */
	ArrayList<TileDrawStateMember> members;
	
	/** flag to use an image directly instead of using the values */
	boolean useDirectImage;
	
	/** z value */
	int z;
	
	//////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////

	public TileDrawState(boolean active) {
		this.activation_listeners = new ArrayList<>();
		this.active = active;
		this.members = new ArrayList<>();
	}
	
	public TileDrawState(boolean active, int z) {
		this.activation_listeners = new ArrayList<>();
		this.active = active;
		this.members = new ArrayList<>();
	}
	
	public TileDrawState(boolean active, TileDrawStateActivationListener activationListener) {
		this.activation_listeners = new ArrayList<>();
		this.active = active;
		this.members = new ArrayList<>();
		
		RegisterActivationListener(activationListener);
	}
	
	public TileDrawState(boolean active, int z, TileDrawStateActivationListener activationListener) {
		this.activation_listeners = new ArrayList<>();
		this.active = active;
		this.z = z;
		this.members = new ArrayList<>();
		RegisterActivationListener(activationListener);
	}
	
	public TileDrawState(boolean active,
			boolean useDirectImage) {
		this.activation_listeners = new ArrayList<>();
		this.active = active;
		this.members = new ArrayList<>();
		this.useDirectImage = useDirectImage;
	}
	
	public TileDrawState(boolean active, int z,
			boolean useDirectImage) {
		this.activation_listeners = new ArrayList<>();
		this.active = active;
		this.z = z;
		this.members = new ArrayList<>();
		this.useDirectImage = useDirectImage;
	}
	
	public TileDrawState(boolean active, TileDrawStateActivationListener activationListener,
			boolean useDirectImage) {
		this.activation_listeners = new ArrayList<>();
		this.active = active;
		this.members = new ArrayList<>();
		this.useDirectImage = useDirectImage;
		RegisterActivationListener(activationListener);
	}
	
	public TileDrawState(boolean active, TileDrawStateActivationListener activationListener,
			int z, boolean useDirectImage) {
		this.activation_listeners = new ArrayList<>();
		this.active = active;
		this.z = z;
		this.members = new ArrayList<>();
		this.useDirectImage = useDirectImage;
		RegisterActivationListener(activationListener);
	}
	
	//////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////

	public void setUseDirectImage(boolean useDirectImage) {
		this.useDirectImage = useDirectImage;
	}
	
	public boolean isUseDirectImage() {
		return useDirectImage;
	}

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
			member.drawstate_data.remove(this);
		}
		members.clear();
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
