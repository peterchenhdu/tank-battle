package cn.edu.hdu.tankbattle.model;

/**
 * 砖块类，由东西类继承而来
 * 
 * @author 1052067939
 * @version 1.0
 * @since Java-1.6
 *
 */
public class Brick extends Stuff {
	/**
	 * 砖块类的构造函数
	 * 
	 * @param x
	 *            砖块的x坐标
	 * @param y
	 *            砖块的y坐标
	 */
	public Brick(int x, int y) {
		super(x, y);
		this.setType(Stuff.BRICK);
		this.setWidth(20);
		this.setHeight(20);
	}

}
