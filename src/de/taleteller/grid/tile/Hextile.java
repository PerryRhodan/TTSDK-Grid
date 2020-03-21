/**
 * www.taleteller.de
 * 
 * TaletellerGrid
 *   Hextile
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
package de.taleteller.grid.tile;


import java.util.ArrayList;
import java.util.List;
import de.taleteller.grid.Tile;
import de.taleteller.grid.drawstate.TileDrawState;
import de.taleteller.grid.drawstate.TileDrawStateMemberData;


/**
 * A hextile making up a hex world.
 * 
 */
public class Hextile<T_Tiledata> extends Tile<T_Tiledata> {

	
	/* neighbor tiles */
	/** top neighbor */
	Tile<T_Tiledata> nb_top;
	/** top right neighbor */
	Tile<T_Tiledata> nb_topright;
	/** top left neighbor */
	Tile<T_Tiledata> nb_topleft;
	/** bottom neighbor */
	Tile<T_Tiledata> nb_bottom;
	/** bottom right neighbor */
	Tile<T_Tiledata> nb_bottomright;
	/** bottom left neighbor */
	Tile<T_Tiledata> nb_bottomleft;


	/////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////
	
	/**
	 * Creates new Tile with given parameters.
	 * 
	 * @param x
	 * @param y
	 */
	public Hextile(int x, int y, T_Tiledata data) {
		super(x, y, data);
	}
	
	@Override
	public void SetDrawBordersFor(TileDrawState state
			, boolean top, boolean topright, boolean bottomright
			, boolean bottom, boolean bottomleft, boolean topleft) {
		for (TileDrawStateMemberData stateData : this.drawstate_data) {
			if(stateData.getState().equals(state)) {
				stateData.setDrawBorders(top, topright, bottomright, bottom, bottomleft, topleft);
				return;
			}
		}
	}
	
//	@Override
//	public List<Tile<T_Tiledata>> getNearbyTiles(int depth) {
//		// TODO
//	}
//	
//	@Override
//	public List<Tile<T_Tiledata>> getNearbyTiles(int depth, PathFinder_Options<T_Tiledata> options) {
//		//TODO
//	}
	
	@Override
	protected void connectToNeighbors(Tile<T_Tiledata>[][] alltiles) {
		int y = this.getY();
		int x = this.getX();
		int width = alltiles.length;
		int height = alltiles[0].length;
		
		
		// set neighbor_top
		if (y - 1 >= 0)
			this.nb_top = alltiles[x][y - 1];
		// set neighbor_bottom
		if (y + 1 < height)
			this.nb_bottom = alltiles[x][y + 1];
		
		// if x is even number
		if (x % 2 == 0) {
			// set nb_righttop
			if (x + 1 < width && y - 1 >= 0)
				this.nb_topright = alltiles[x + 1][y - 1];
			// set nb_rightbottom
			if (x + 1 < width)
				this.nb_bottomright = alltiles[x + 1][y];
			// set nb_lefttop
			if (x - 1 >= 0 && y - 1 >= 0)
				this.nb_topleft = alltiles[x - 1][y - 1];
			// set nb_leftbottom
			if (x - 1 >= 0)
				this.nb_bottomleft = alltiles[x - 1][y];
		}
		// if x is uneven number
		else {
			// set nb_righttop
			if (x + 1 < width)
				this.nb_topright = alltiles[x + 1][y];
			// set nb_rightbottom
			if (x + 1 < width && y + 1 < height)
				this.nb_bottomright = alltiles[x + 1][y + 1];
			// set nb_lefttop
			if (x - 1 >= 0)
				this.nb_topleft = alltiles[x - 1][y];
			// set nb_leftbottom
			if (x - 1 >= 0 && y + 1 < height)
				this.nb_bottomleft = alltiles[x - 1][y + 1];
		}
		
	}
	

	
	
	/////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////

	@Override
	public int getPixelX(int tilewidth) {
		int x_px = (int) (this.getX() * (64.0/74.0) * tilewidth);
		return x_px;
	}

	@Override
	public int getPixelY(int tileheight) {
		int y_px = (int) (this.getY() * tileheight);
		if(this.getX() % 2 != 0) {
			y_px += tileheight/2.0;
		}
		return y_px;
	}
	
	/////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////

	@Override
	public List<Tile<T_Tiledata>> getNeighbors() {
		List<Tile<T_Tiledata>> neighbors = new ArrayList<>();
		if (nb_top != null)
			neighbors.add(nb_top);
		if (nb_topright != null)
			neighbors.add(nb_topright);
		if (nb_topleft != null)
			neighbors.add(nb_topleft);
		if (nb_bottom != null)
			neighbors.add(nb_bottom);
		if (nb_bottomright != null)
			neighbors.add(nb_bottomright);
		if (nb_bottomleft != null)
			neighbors.add(nb_bottomleft);
		return neighbors;
	}
	
	/////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////

	public Tile<T_Tiledata> getNb_top() {
		return nb_top;
	}

	public Tile<T_Tiledata> getNb_righttop() {
		return nb_topright;
	}

	public Tile<T_Tiledata> getNb_lefttop() {
		return nb_topleft;
	}

	public Tile<T_Tiledata> getNb_bottom() {
		return nb_bottom;
	}

	public Tile<T_Tiledata> getNb_rightbottom() {
		return nb_bottomright;
	}

	public Tile<T_Tiledata> getNb_leftbottom() {
		return nb_bottomleft;
	}
	

}
