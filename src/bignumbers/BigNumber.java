package bignumbers;

import java.util.ArrayList;
import java.util.List;

public class BigNumber {
	public static final int MAX_LENGTH = 10000;

	private int[] digits = new int[MAX_LENGTH];
	private int length = 0;

	private BigNumber() {
	}

	public BigNumber(int value) {
		if (value == 0) {
			return;
		}
		while (true) {
			digits[length] = value % 10;
			++length;
			value /= 10;
			if (value == 0) {
				break;
			}
		}
	}

	public BigNumber(BigNumber number) {
		length = number.length;
		System.arraycopy(number.digits, 0, digits, 0, length);
	}

	public void plusThis(BigNumber number) {
		int a = 0;
		length = Math.max(length, number.length);
		for (int i = 0; i < length; ++i) {
			digits[i] += number.digits[i] + a;
			if (digits[i] >= 10) {
				digits[i] -= 10;
				a = 1;
			} else {
				a = 0;
			}
		}
		if (a == 1) {
			digits[length] = 1;
			++length;
		}
	}

	public BigNumber plusNew(BigNumber number) {
		BigNumber result = new BigNumber();
		result.length = Math.max(length, number.length);
		int a = 0;
		for (int i = 0; i < result.length; ++i) {
			result.digits[i] = digits[i] + number.digits[i] + a;
			if (result.digits[i] >= 10) {
				result.digits[i] -= 10;
				a = 1;
			} else {
				a = 0;
			}
		}
		if (a == 1) {
			result.digits[result.length] = 1;
			++result.length;
		}
		return result;
	}

	public void minusThis(BigNumber number) {
		int maxLength = Math.max(length, number.length);
		for (int i = 0; i < maxLength; ++i) {
			digits[i] -= number.digits[i];
			if (digits[i] < 0) {
				for (int j = i + 1; j < maxLength; ++j) {
					if (digits[j] == 0) {
						digits[j] = 9;
						continue;
					}
					--digits[j];
					break;
				}
				digits[i] += 10;
			}
		}
		length = 0;
		for (int i = maxLength - 1; i >= 0; --i) {
			if (digits[i] > 0) {
				length = i + 1;
				break;
			}
		}
	}

	public BigNumber minusNew(BigNumber number2) {
		BigNumber number1 = new BigNumber(this);
		BigNumber result = new BigNumber();
		int maxLength = Math.max(number1.length, number2.length);
		for (int i = 0; i < maxLength; ++i) {
			result.digits[i] = number1.digits[i] - number2.digits[i];
			if (result.digits[i] < 0) {
				for (int j = i + 1; j < maxLength; ++j) {
					if (number1.digits[j] == 0) {
						number1.digits[j] = 9;
						continue;
					}
					--number1.digits[j];
					break;
				}
				result.digits[i] += 10;
			}
		}
		for (int i = maxLength - 1; i >= 0; --i) {
			if (result.digits[i] > 0) {
				result.length = i + 1;
				break;
			}
		}
		return result;
	}

	public BigNumber multiplyNew(BigNumber number) {
		BigNumber result = null;
		for (int i = 0; i < number.length; ++i) {
			BigNumber tmp = new BigNumber();
			int a = 0;
			for (int j = 0; j < length; ++j) {
				int index = j + i;
				tmp.digits[index] = number.digits[i] * digits[j] + a;
				if (tmp.digits[index] >= 10) {
					a = tmp.digits[index] / 10;
					tmp.digits[index] = tmp.digits[index] % 10;
				} else {
					a = 0;
				}
			}
			tmp.length = length + i;
			if (a > 0) {
				tmp.digits[tmp.length] = a;
				++tmp.length;
			}
			if (i == 0) {
				result = tmp;
				continue;
			}
			result.plusThis(tmp);
		}
		return result;
	}

	public int compareTo(BigNumber number) {
		if (length > number.length) {
			return 1;
		}
		if (length < number.length) {
			return -1;
		}
		for (int i = length - 1; i >= 0; --i) {
			if (digits[i] > number.digits[i]) {
				return 1;
			}
			if (digits[i] < number.digits[i]) {
				return -1;
			}
		}
		return 0;
	}

	public BigNumber divideNew(BigNumber number, boolean isResidual) {
		if (number.isOne()) {
			return isResidual ? new BigNumber() : new BigNumber(this);
		}
		List<Integer> resultList = new ArrayList<Integer>();
		BigNumber tmp = new BigNumber();
		System.arraycopy(digits, length - number.length, tmp.digits, 0, number.length);
		tmp.length = number.length;
		int index = length - number.length;
		while (true) {
			int compareResult = tmp.compareTo(number);
			if (compareResult < 0) {
				if (!resultList.isEmpty()) {
					resultList.add(0);
				}
				if (index == 0) {
					break;
				}
				--index;
				System.arraycopy(tmp.digits, 0, tmp.digits, 1, tmp.length);
				if (tmp.length == 0 && digits[index] == 0) {
					tmp = new BigNumber();
				} else {
					tmp.digits[0] = digits[index];
					++tmp.length;
				}
			} else if (compareResult == 0) {
				resultList.add(1);
				if (index == 0) {
					if (isResidual) {
						tmp = new BigNumber();
					}
					break;
				}
				--index;
				tmp = new BigNumber(digits[index]);
			} else {
				int counter = 0;
				while (true) {
					tmp.minusThis(number);
					++counter;
					if (tmp.compareTo(number) < 0) {
						break;
					}
				}
				resultList.add(counter);
				if (index == 0) {
					break;
				}
				--index;
				System.arraycopy(tmp.digits, 0, tmp.digits, 1, tmp.length);
				if (tmp.length == 0 && digits[index] == 0) {
					tmp = new BigNumber();
				} else {
					tmp.digits[0] = digits[index];
					++tmp.length;
				}
			}
		}
		if (isResidual) {
			return tmp;
		}
		BigNumber result = new BigNumber();
		result.length = resultList.size();
		for (int i = 0; i < resultList.size(); ++i) {
			result.digits[i] = resultList.get(resultList.size() - i - 1);
		}
		return result;
	}

	public boolean isOne() {
		return length == 1 && digits[0] == 1;
	}

	public boolean isZero() {
		return length == 0 || length == 1 && digits[0] == 0;
	}

	public String getTrueValue() {
		StringBuilder builder = new StringBuilder();
		for (int i = length - 1; i >= 0; --i) {
			builder.append(digits[i]);
		}
		return builder.toString();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < length; ++i) {
			builder.append(digits[i]);
		}
		builder.append("|").append(length);
		return builder.toString();
	}
}
