/**
 * www.taleteller.de
 * 
 * TaletellerGrid
 *   Tile
 * 
 * Summary:
 *   
 * 
 * History:
 *   25.11.2017 - Cleaning of code
 *   
 * 
 * Ideas:
 *   - getNearbyTiles(int depth) function that returns
 *     the depth-deep neighboring tiles
 * 
 * Stephan Hogrefe, Edinburgh, 2017
 */
package de.taleteller.grid;


import java.util.ArrayList;
import java.util.List;

import de.taleteller.grid.drawstate.TileDrawState;
import de.taleteller.grid.drawstate.TileDrawStateMember;
import de.taleteller.grid.drawstate.TileDrawStateMemberData;



public abstract class Tile<T_Tiledata> extends TileDrawStateMember {
	
	/** Individual path generator for this tile. This way paths can 
	 *  be calculated in parallel for different tiles. */
	private AstarPathGenerator<T_Tiledata> pathgenerator = new AstarPathGenerator<>();
	
	/** x coordinate */
	private int x;
	/** y coordinate */
	private int y;
	
	/** USED FOR astar PATHFINDING */
	public int astar_f_value;
	public int astar_g_value;
	public Tile<T_Tiledata> astar_predecessor;
	
	/** For interactions */
	boolean isHovered;
	boolean isSelected;

	/** game dependent tile data, ie level of infrastructure, etc */
	T_Tiledata tiledata;
	
	public Tile(int x, int y, T_Tiledata data) {
		this.x = x;
		this.y = y;
		this.tiledata = data;
		
	}
	
	//////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////
	
	/**
	 * Given list of tiles are the new neighbors of this tile.
	 * @param alltiles
	 */
	protected abstract void connectToNeighbors(Tile<T_Tiledata>[][] alltiles);
	
	//////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////

	public abstract List<Tile<T_Tiledata>> getNeighbors();
	
//  TODO
//	public abstract List<Tile<T_Tiledata>> getNearbyTiles(int depth);
//	
//	public abstract List<Tile<T_Tiledata>> getNearbyTiles
//					(int depth, PathFinder_Options<T_Tiledata> options);
	
	//////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////

	
	@Override
	public void SetDrawStateValuesFor(TileDrawState state
			, double value, double value_max, double alpha) {
		for (TileDrawStateMemberData stateData : this.drawstate_data) {
			if(stateData.getState().equals(state)) {
				stateData.setDraw_values(value, value_max, alpha);
				return;
			}
		}
	}
	
	
	/** get x position in pixels of this tile */
	public abstract int getPixelX(int tilewidth);
	
	/** get y position in pixels of this tile */
	public abstract int getPixelY(int tileheight);
	
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void SetX(int x) {
		this.x = x;
	}
	
	public void SetY(int y) {
		this.y = y;
	}

	public boolean isHovered() {
		return isHovered;
	}

	public boolean isSelected() {
		return isSelected;
	}
	
	public T_Tiledata getData() {
		return tiledata;
	}

	public void setHovered(boolean isHovered) {
		this.isHovered = isHovered;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	
	//////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////
	
	public List<Tile<T_Tiledata>> calculatePathTo(Tile<T_Tiledata> target, PathFinder_Options<T_Tiledata> options) {
		pathgenerator.reset();
		return pathgenerator.getPath(this, target, options);
	}
	
	/** Returns the total cost of the last calculated shortest path. */
	public int getTotalPathCost() {
		return pathgenerator.getTotalCost();
	}
	
	/** Returns a list of Integers storing the cost of for each individual node.
	 *  The list has the same size as the last calculated path, and the values 
	 *  correspond to each other. */
	public ArrayList<Integer> getIndividualPathCosts() {
		return pathgenerator.getIndividualCosts();
	}
	
	/**
	 * Returns Eucledian distance in tiles.
	 * Beware that this only works semi-optimal
	 * for hex-tiles!
	 * @param other
	 * @return
	 */
	public double getEucledianDistanceTo(Tile<T_Tiledata> other) {
		return Math.sqrt(Math.pow(other.x-this.x, 2)
				+ Math.pow(other.y-this.y, 2));
	}
}
