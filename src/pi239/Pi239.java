package pi239;

public class Pi239 {

	public static final BigNumber TWO = new BigNumber(2);
	public static final BigNumber FOUR = new BigNumber(4);
	public static final BigNumber TWENTY_FIVE = new BigNumber(25);
	public static final BigNumber NUMBER_239 = new BigNumber(239);
	public static final BigNumber NUMBER_239_2 = NUMBER_239.multiplyNew(NUMBER_239);

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		// 1/5
		BigNumber s = BigNumber.getOneFive();
		BigNumber p = new BigNumber(3);
		BigNumber m = new BigNumber(125);
		boolean isMinus = true;
		for (long i = 1; i <= 1000; ++i) {
			BigNumber v = m.multiplyNew(p).divideOne();
			if (isMinus) {
				s.minusThis(v);
			} else {
				s.plusThis(v);
			}
			p.plusThis(TWO);
			m = m.multiplyNew(TWENTY_FIVE);
			isMinus = !isMinus;
		}
		s = s.multiplyNew(FOUR);

		// 1/239
		BigNumber s239 = NUMBER_239.divideOne();
		BigNumber p239 = new BigNumber(3);
		BigNumber m239 = new BigNumber(NUMBER_239_2.multiplyNew(NUMBER_239));
		isMinus = true;
		for (long i = 1; i <= 100; ++i) {
			BigNumber v = m239.multiplyNew(p239).divideOne();
			if (isMinus) {
				s239.minusThis(v);
			} else {
				s239.plusThis(v);
			}
			p239.plusThis(TWO);
			m239 = m239.multiplyNew(NUMBER_239_2);
			isMinus = !isMinus;
		}

		s.minusThis(s239);
		s = s.multiplyNew(FOUR);
		System.out.println("pi=" + s.getTrueValue());
		System.out.println("Time=" + (System.currentTimeMillis() - startTime));
	}
}
