package com.andersen.diffiehelman.calculator;

import java.math.BigDecimal;

/**
 * Calculator provides Diffie-Hellman mathematical algorithm.
 * 
 * @author Sorokin Vadim
 *
 */
public class DiffieHellmanAlgorithmCalculator {
	/**
	 * Actually Diffie-Hellman algorithm uses only one mathematical formula for
	 * calculation. But on different steps - different values are put into. The
	 * formula is: <code>pBase</code><b> ^
	 * </b><code>pPower</power><b> mod </b><code>pModulus</code>
	 * 
	 * @param pBase    - it could be 'g' and also it could be calculated public keys
	 *                 for exchanging.
	 * @param pPower   - this parameter represents nodes' private keys.
	 * @param pModulus - 'p' modulus.
	 * 
	 * @return result of calculation
	 * 
	 * @throws NullPointerException if
	 *                              {@code pBase==null or pPower==null or pModulus==null}
	 * @throws ArithmeticException  if {@code pModulus==0}
	 */
	public static BigDecimal calculate(final BigDecimal pBase, final BigDecimal pPower, final BigDecimal pModulus) {
		return pBase.pow(pPower.intValue()).remainder(pModulus);
	}
}