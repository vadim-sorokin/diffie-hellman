package com.andersen.diffiehelman.facade;

import java.math.BigDecimal;

import com.andersen.diffiehelman.calculator.DiffieHellmanAlgorithmCalculator;

/**
 * Facade provides full calculation of symmetric key in order to encrypt data
 * during transferring between nodes. Symmetric key calculation implemented
 * according to Diffie-Hellman algorithm.
 * 
 * @author Sorokin Vadim
 *
 */
public class DiffieHellmanSymmetricKeyCalculationFacade {
	/**
	 * Method makes all required steps between Alice and Bob in order to get
	 * final symmetric key for future encryption.
	 * 
	 * @param pG - it's 'g' (base) from Diffie-Hellman algorithm description.
	 * @param pP - it's 'p' (modulus) from Diffie-Hellman algorithm description.
	 * @param pA - it's Alice's private number.
	 * @param pB - it's Bob's private number.
	 * 
	 * @return final symmetric key
	 */
	public BigDecimal calculateSymmetricPrivateKey(final BigDecimal pG, final BigDecimal pP, final BigDecimal pA,
			final BigDecimal pB) {
		BigDecimal result = null;

		final BigDecimal alicePublicKey = DiffieHellmanAlgorithmCalculator.calculate(pG, pA, pP);
		final BigDecimal bobPublicKey = DiffieHellmanAlgorithmCalculator.calculate(pG, pB, pP);

		final BigDecimal aliceSymmetricKey = DiffieHellmanAlgorithmCalculator.calculate(bobPublicKey, pA, pP);
		final BigDecimal bobSymmetricKey = DiffieHellmanAlgorithmCalculator.calculate(alicePublicKey, pB, pP);

		if (aliceSymmetricKey.equals(bobSymmetricKey)) {
			result = aliceSymmetricKey;
		} else {
			result = BigDecimal.ONE.negate();
		}

		return result;
	}
}
