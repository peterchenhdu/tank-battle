package cn.edu.hdu.tankbattle.model;

/**
 * 铁块类，继承自东西类
 * 
 * @author 1052067939
 * @version 1.0
 * @since JavaSe-1.6
 */
public class Iron extends Stuff {
	/**
	 * 构造方法
	 * 
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
	 */
	public Iron(int x, int y) {
		super(x, y);
		this.setType(IRON);
		this.setWidth(20);
		this.setHeight(20);
	}

}
