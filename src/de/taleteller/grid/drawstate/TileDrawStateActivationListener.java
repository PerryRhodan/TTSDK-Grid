/**
 * www.taleteller.de
 * 
 * TaletellerGrid
 *   TileDrawStateActivationListener
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

public interface TileDrawStateActivationListener {

	/**
	 * To be fired by a @TileDrawState whenever its activation 
	 * flag was changed.
	 */
	public void onTileDrawState_ActivationChange();
	
}
