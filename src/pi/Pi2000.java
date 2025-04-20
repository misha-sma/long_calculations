package pi;

public class Pi2000 {

	public static final BigNumber TWO = new BigNumber(2);

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		BigNumber s = BigNumber.getOne();
		BigNumber p = new BigNumber(3);
		boolean isMinus = true;
		for (long i = 1; i <= 1000001; ++i) {
			BigNumber v = p.divideOne();
			if (isMinus) {
				s.minusThis(v);
			} else {
				s.plusThis(v);
			}
			p.plusThis(TWO);
			isMinus = !isMinus;
		}
		s = s.multiplyNew(new BigNumber(4));
		System.out.println("pi=" + s.getTrueValue());
		System.out.println("Time=" + (System.currentTimeMillis() - startTime));
	}
}
