package bignumbers;

public class BallsXY {

	public static final BigNumber ONE = new BigNumber(1);

	public static void main(String[] args) {
		int x = 13;
		int y = 37;
		int n = 228;

		BigNumber p = new BigNumber(x * y);
		BigNumber q = new BigNumber((x + 1) * (y + 1));
		BigNumber nod = getNod(p, q);
		p = p.divideNew(nod, false);
		q = q.divideNew(nod, false);
		BigNumber c1 = new BigNumber(x + 1);
		BigNumber c2 = new BigNumber(y + 1);
		BigNumber pM = new BigNumber(x + y + 1);
		BigNumber qM = new BigNumber((x + 1) * (y + 1));
		nod = getNod(pM, qM);
		pM = pM.divideNew(nod, false);
		qM = qM.divideNew(nod, false);
		for (int i = 2; i <= n; ++i) {
			System.out.println("i=" + i);
			c1.plusThis(ONE);
			c2.plusThis(ONE);
			BigNumber pX = new BigNumber(x * y);
			BigNumber qX = c1.multiplyNew(c2);
			BigNumber m1 = qX.minusNew(pX);
			BigNumber m2 = new BigNumber(qX);
			pX = pX.multiplyNew(pM);
			qX = qX.multiplyNew(qM);
			BigNumber pAll = p.multiplyNew(qX).plusNew(q.multiplyNew(pX));
			BigNumber qAll = q.multiplyNew(qX);
			nod = getNod(pAll, qAll);
			p = pAll.divideNew(nod, false);
			q = qAll.divideNew(nod, false);
			pM = pM.multiplyNew(m1);
			qM = qM.multiplyNew(m2);
			nod = getNod(pM, qM);
			pM = pM.divideNew(nod, false);
			qM = qM.divideNew(nod, false);
		}
		System.out.println(p.getTrueValue() + " " + q.getTrueValue());
	}

	public static BigNumber getNod(BigNumber a, BigNumber b) {
		BigNumber minV = null;
		BigNumber maxV = null;
		if (a.compareTo(b) < 0) {
			minV = a;
			maxV = b;
		} else {
			minV = b;
			maxV = a;
		}
		while (true) {
			BigNumber r = maxV.divideNew(minV, true);
			if (r.isZero()) {
				return minV;
			}
			maxV = minV;
			minV = r;
		}
	}
}
