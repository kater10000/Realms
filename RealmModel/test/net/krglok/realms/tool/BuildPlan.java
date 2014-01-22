package net.krglok.realms.tool;

import org.bukkit.Material;

import net.krglok.realms.core.BuildingType;
import net.krglok.realms.core.ConfigBasis;

/**
 * The BuildPlan represent a simple cubic building Instruction
 * with this plan you can buildup a simple Build of a special BuildingType
 * the cube is represented by with 
 * - Level = Row x col
 * - Cube  = Height x level
 * - byte [height] [row] [col] 
 * @author oduda
 *
 */
public abstract class BuildPlan
{
	private BuildingType buildingType;
	private int radius;
	private byte[][][] cube;
	private boolean isInit;
	private int edge; 
	private char[][] signText ;
	private int offsetY;
	
	public BuildPlan(BuildingType buildingType, int radius, int offsetY)
	{
		super();
		this.buildingType = buildingType;
		this.radius = (int) Math.sqrt((double)(radius*radius));
		this.edge  = (radius > 0) ?  2 * radius -1 : 2;
		this.cube = new byte[edge][edge][edge];
		this.cube = clearCube(this.cube);
		this.setSignText(new char[4][15]);
		this.setOffsetY(offsetY);
	}

	public static  byte[][][] clearCube(byte[][][] cube)
	{
		for (int h = 0; h < cube.length; h++)
		{
			for (int r = 0; r < cube.length; r++)
			{
				for (int c = 0; c < cube.length; c++)
				{
					cube[h][r][c] = ConfigBasis.getMaterialId(Material.AIR);
				} 
			}
		}
		return cube; 
	}
	
	
	public abstract byte[][][] initCube( int edge);
//	{
//		return cube;
//	}

	public byte[][][] getCube()
	{
		return cube;
	}

	public void setCube(byte[][][] cube)
	{
		this.cube = cube;
	}

	public boolean isInit()
	{
		return isInit;
	}

	public void setInit(boolean isInit)
	{
		this.isInit = isInit;
	}

	public BuildingType getBuildingType()
	{
		return buildingType;
	}

	public int getRadius()
	{
		return radius;
	}

	public int getEdge()
	{
		return edge;
	}
	
	public int getOffsetY()
	{
		return offsetY;
	}

	public void setOffsetY(int offsetY)
	{
		this.offsetY = offsetY;
	}

	/**
	 * Fills any column with the material
	 * @param column
	 * @param mat
	 * @return
	 */
	public static byte[] fillRow(byte[] column , Material mat)
	{
		return fillRowPart(column, mat, 0, column.length-1);
	}
	
	/**
	 * Fills hole level with the material
	 * @param level
	 * @param mat
	 * @return
	 */
	public static byte[][] fillLevel(byte[][] level , Material mat )
	{
		int edge = level[0].length;

		for (int r = 0; r < edge; r++)
		{
			for (int c = 0; c < edge; c++)
			{
				level[r][c] = ConfigBasis.getMaterialId(mat);
			}
		}
		return level;
	}
	
	/**
	 * Fill the part from start to end with the material
	 * limits of the col will be checked and replace with
	 * the defined limits of the col
	 * @param column
	 * @param mat
	 * @param start
	 * @param end
	 * @return
	 */
	public static byte[] fillRowPart(byte[] column , Material mat, int start, int end)
	{
		int edge = column.length;
		int first = (start < 0) ? 0 : start;
		int last  = (end >= edge) ? edge-1 : end;
		
		for (int c = first; c <= last; c++)
		{
			column[c] =  ConfigBasis.getMaterialId(mat);
		}
		
		return column;
	}
	
	/**
	 * Set Material on Position of Column
	 * if position out of bounds nothing will do
	 * @param column
	 * @param mat
	 * @param position
	 * @return
	 */
	public static byte[] setPos(byte[] column, Material mat, int position)
	{
		if ((position >= 0) && (position < column.length))
		{
			column[position] = ConfigBasis.getMaterialId(mat);
		}
		
		return column;
	}
	
	public static byte[][] fillColPart(byte[][] level, Material mat, int col, int start, int end)
	{
		int edge = level[0].length;
		int first = (start < 0) ? 0 : start;
		int last  = (end >= edge) ? edge-1 : end;
		if ((col < 0) || (col >= edge)) return level;
		
		for (int i = first; i <= last; i++)
		{
			level[i][col] = ConfigBasis.getMaterialId(mat);
		}
		
		return level;
	}
	
	/**
	 * Set Material in vertical line at given Position with length 
	 * @param cube
	 * @param mat
	 * @param row
	 * @param col
	 * @param level
	 * @param Heigth
	 * @return
	 */
	public static byte[][][] setHeight(byte[][][] cube, Material mat, int level, int row, int col, int Heigth )
	{
		// check the bounds of cube
		int edge = cube[0][0].length;
		if ((col < 0) || (col >= edge)) return cube;
		if ((row < 0) || (row >= edge)) return cube;
		if ((level < 0) || (level >= edge)) return cube;
		if ((level+Heigth < 0) || (level+Heigth >= edge)) return cube;
		
		for (int i = 0; i < Heigth; i++)
		{
			cube[level+i][row][col] = ConfigBasis.getMaterialId(mat);
		}
		
		return cube;
	}

	public char[][] getSignText()
	{
		return signText;
	}

	public void setSignText(char[][] signText)
	{
		this.signText = signText;
	}
	
	public boolean setLine(int line, String s)
	{
		if (line > 4) return false;
		int len = (s.length() > 15) ? 15 : s.length();
		char[] sChar = s.toCharArray();
		for (int i = 0; i < len; i++)
		{
			signText[line][i] = sChar[i];
		}
		return true;
	}
	
}
