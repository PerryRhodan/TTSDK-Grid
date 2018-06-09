/**
 * www.taleteller.de
 * 
 * TaletellerGrid
 *   PathFinder_Options
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
package de.taleteller.grid;

public interface PathFinder_Options<T> {
	
	/**
	 * Estimation heurisitcs used in A* search algorithm.
	 * @param current current Tile
	 * @param target Target Tile
	 * @return Integer value of the estimated cost to target.
	 */
	public int getEstimation(Tile<T> current, Tile<T> target);
	
	/**
	 * The cost to traverse from Tile n1 to Tile n2.
	 * @param n1 Tile
	 * @param n2 Tile
	 * @return Integer value of the cost required to move from n1 to n2.
	 */
	public int getCost(Tile<T> n1, Tile<T> n2);
	
	/**
	 * Returns the maximal cost for the path allowed.
	 * @return
	 */
	public int getMaxTotalCost();
	
	/**
	 * Set new maximal cost value.
	 * @param amount
	 */
	public void setMaxTotalCost(int amount);
}
