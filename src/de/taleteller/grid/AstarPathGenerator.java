/**
 * www.taleteller.de
 * 
 * TaletellerGrid
 *   AstarPathGenerator
 * 
 * Summary:
 *   
 * 
 * History:
 *   25.11.2017 - Cleaning of code
 *   
 * 
 * Ideas:
 *   - TODO if max total cost as defined in the options is too low, the path 
 *      will return a list including the start tile AND the target tile, no 
 *      matter how far apart they are!
 * 
 * Stephan Hogrefe, Edinburgh, 2017
 */
package de.taleteller.grid;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Pathfinder using the A* algorithm.
 * 
 * Use the reset() method before calling getPath() again, unless you 
 * are reusing the same start tile in multiple subsequent calls.
 */
class AstarPathGenerator<T> {

	/* open and closed lists to keep track of which tiles have been considered */
	PriorityQueue<Tile<T>> openlist = new PriorityQueue<>(new Fcomparator());
	Set<Tile<T>> closedlist = new HashSet<>();
	
	/** final path to be returned, from target to start */
	ArrayList<Tile<T>> path = new ArrayList<>();
	int totalcost = 0;
	
	/** Flag to indicate whether or not the target was found.
	 *  Will be false also if the total cost became too high. */
	boolean found_target;
	
	/**
	 * Calculates and returns the shortest path using given options. 
	 * The start and target tiles must be part of the connected 
	 * two-dimensional array of tiles.
	 * 
	 * TODO if max total cost as defined in the options is too low, the path 
	 * will return a list including
	 * the start tile AND the target tile, no matter how far apart they are!
	 * 
	 * @param tiles
	 * @param start
	 * @param target
	 * @param options
	 * @return Shortest path
	 */
	ArrayList<Tile<T>> getPath(Tile<T> start , Tile<T> target
			, PathFinder_Options<T> options) {

		// call astar algorithm to calculate the path
		astar(start, target, options);
		
		// return the path in a new list
		ArrayList<Tile<T>> returnlist = new ArrayList<Tile<T>>();
		for (Tile<T> tile : path) {
			returnlist.add(tile);
		}
		return returnlist;
	}
	
	/**
	 * Returns the total cost of the last calculated shortest path.
	 * @return
	 */
	int getTotalCost() {
		return totalcost;
	}

	/** Returns whether or not the target was found.
	 *  Will also be false if the total cost was higher
	 *  then the limit specified in the options. */
	public boolean didFindTarget() {
		return found_target;
	}

	/**
	 * Returns a list of Integers storing the cost of for each individual node. The list has
	 * the same size as the last calculated path, and the values correspond to each other.
	 * @return
	 */
	ArrayList<Integer> getIndividualCosts() {
		ArrayList<Integer> costs = new ArrayList<Integer>();
		for (Tile<T> tile : path) {
			costs.add(new Integer(tile.astar_g_value));
		}
		return costs;
	}
	
	void reset() {
		
		for (Tile<T> tile : closedlist) {
			tile.astar_g_value = 0;
			tile.astar_f_value = 0;
			tile.astar_predecessor = null;
		}
		for (Tile<T> tile : openlist) {
			tile.astar_g_value = 0;
			tile.astar_f_value = 0;
			tile.astar_predecessor = null;
		}
		closedlist.clear();
		openlist.clear();
		path.clear();
		totalcost = 0;
	}
	
	//////////////////////////////////////////////////////
	
	// https://de.wikipedia.org/wiki/A*-Algorithmus
	void astar(Tile<T> start , Tile<T> target
			, PathFinder_Options<T> options) {
		
		// Initialisierung der Open List, die Closed List ist noch leer
	    // (die Priorität bzw. der f Wert des Startknotens ist unerheblich)
		openlist.add(start);
		
		// diese Schleife wird durchlaufen bis entweder
	    // - die optimale Lösung gefunden wurde oder
	    // - feststeht, dass keine Lösung existiert
		Tile<T> current = null;
		while(openlist.size() >=  1) {
			// Knoten mit dem geringsten f Wert aus der Open List entfernen
			current = openlist.poll();
			//System.out.println("Pollen from open list: " + current.toString());
			 // Wurde das Ziel gefunden?
			if(current.equals(target)) {
				break;	//end while loop here and continue with returning path
			}
			// Der aktuelle Knoten soll durch nachfolgende Funktionen
	        // nicht weiter untersucht werden damit keine Zyklen entstehen
			closedlist.add(current);
			// Wenn das Ziel noch nicht gefunden wurde: Nachfolgeknoten
	        // des aktuellen Knotens auf die Open List setzen
			expandNode(current, target, options);
		}
		
		int k=0;	// try to detect and break loops 
		if(current != null) {
			/** remember total cost */
			totalcost = current.astar_g_value;
			Tile<T> node = current;
			while(node != null && k < 50) {
				//System.out.println("added to path " + node.toString());
				path.add(node);
				node = node.astar_predecessor;

				k++;
			}
		}
		
		// were we successful?
		found_target = true;
		if(!path.contains(target))
			found_target = false;
		if(totalcost > options.getMaxTotalCost())
			found_target = false;
	}
	
	// überprüft alle Nachfolgeknoten und fügt sie der Open List hinzu, wenn entweder
	// - der Nachfolgeknoten zum ersten Mal gefunden wird oder
	// - ein besserer Weg zu diesem Knoten gefunden wird
	private void expandNode(Tile<T> node, Tile<T> target, PathFinder_Options<T> options) {
		//System.out.println("Expand node: " + node.toString());
		for (Tile<T> neighbor : node.getNeighbors()) {
			// wenn der Nachfolgeknoten bereits auf der Closed List ist  tue nichts
			if(closedlist.contains(neighbor))
				continue;
			// g Wert für den neuen Weg berechnen: g Wert des Vorgängers plus
	        // die Kosten der gerade benutzten Kante
			int tentative_g = node.astar_g_value + options.getCost(node, neighbor);
			//System.out.println("neighbor: " + neighbor + " has " + tentative_g);
			// wenn der Nachfolgeknoten bereits auf der Open List ist,
	        // aber der neue Weg nicht besser ist als der alte  tue nichts
			if(openlist.contains(neighbor) && tentative_g >= neighbor.astar_g_value)
				continue;

			if(tentative_g > options.getMaxTotalCost())
				continue;

			// Vorgängerzeiger setzen und g Wert merken
			neighbor.astar_predecessor = node;
			neighbor.astar_g_value = tentative_g;
			// f Wert des Knotens in der Open List aktualisieren
	        // bzw. Knoten mit f Wert in die Open List einfügen
			int f = tentative_g + options.getEstimation(neighbor, target);
			neighbor.astar_f_value = f;
			if(!openlist.contains(neighbor))
				openlist.add(neighbor);
		}
	}

	//////////////////////////////////////////////////////
	
	private class Fcomparator implements Comparator<Tile<T>> {

		@Override
		public int compare(Tile<T> o1, Tile<T> o2) {
			if(o1.astar_f_value > o2.astar_f_value)
				return 1;
			if(o1.astar_f_value < o2.astar_f_value)
				return -1;
			return 0;
		}
	}
}
