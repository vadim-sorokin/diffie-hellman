package com.andersen.aws.lambda.handler;

import java.math.BigDecimal;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.andersen.diffiehelman.facade.DiffieHellmanSymmetricKeyCalculationFacade;

public class DiffieHellmanAWSLambdaHandler {
	private static final String G_PUBLIC_KEY_PARAMETER = "gShared";
	private static final String P_PUBLIC_KEY_PARAMETER = "pShared";
	private static final String ALICE_PRIVATE_KEY_PARAMETER = "alicePrivate";
	private static final String BOB_PRIVATE_KEY_PARAMETER = "bobPrivate";

	public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent pApiGatewayProxyRequestEvent,
			Context context) {
		APIGatewayProxyResponseEvent apiGatewayProxyResponseEvent = new APIGatewayProxyResponseEvent();

		final DiffieHellmanSymmetricKeyCalculationFacade facade = new DiffieHellmanSymmetricKeyCalculationFacade();
		final StringBuilder message = new StringBuilder();
		message.append("Hello! Here is Diffie-Hellman alghoritm calculation.\n\n");

		final Map<String, String> parameters = pApiGatewayProxyRequestEvent.getQueryStringParameters();

		if (parameters != null) {
			final String gValue = parameters.get(G_PUBLIC_KEY_PARAMETER);
			final String pValue = parameters.get(P_PUBLIC_KEY_PARAMETER);
			final String aValue = parameters.get(ALICE_PRIVATE_KEY_PARAMETER);
			final String bValue = parameters.get(BOB_PRIVATE_KEY_PARAMETER);

			if (gValue != null && pValue != null && aValue != null && bValue != null) {
				try {
					final BigDecimal commonPublicNumberG = new BigDecimal(gValue);
					final BigDecimal commonPublicNumberP = new BigDecimal(pValue);
					final BigDecimal alicePrivateNumber = new BigDecimal(aValue);
					final BigDecimal bobPrivateNumber = new BigDecimal(bValue);

					message.append("Parameters for calculation:");
					message.append("\nThe 'g' common public  number: ");
					message.append(commonPublicNumberG);
					message.append("\nThe 'p' common public  number: ");
					message.append(commonPublicNumberP);
					message.append("\nAlice's private key: ");
					message.append(alicePrivateNumber);
					message.append("\nBob's private key: ");
					message.append(bobPrivateNumber);
					message.append("\n\n");

					final BigDecimal symmetricKey = facade.calculateSymmetricPrivateKey(commonPublicNumberG,
							commonPublicNumberP, alicePrivateNumber, bobPrivateNumber);
					message.append("Symmetric key: ");
					message.append(symmetricKey);
					message.append("\n\n");
				} catch (NumberFormatException e) {
					message.append("Mandatory values couldn't be parsed.\n\n");
				} catch (ArithmeticException e) {
					message.append("P couldn't be ZERO.\n\n");
				} catch (Exception e) {
					message.append("Exception occured during calculating: ");
					message.append(e.getMessage());
					message.append("\n\n");
				}
			} else {
				message.append("Mandatory parameters are not presented.");
				message.append("\nPlease provide 'gShared', 'pShared', 'alicePrivate', 'bobPrivate'.\n\n");
			}
		} else {
			message.append("Parameters were not found.");
			message.append("\nPlease provide 'gShared', 'pShared', 'alicePrivate', 'bobPrivate'.\n\n");
		}

		message.append("Calculation was developed by Vadim Sorokin.\n");
		message.append("Hosted on AWS with help of API Gateway and Lambda services.");
		apiGatewayProxyResponseEvent.setBody(message.toString());

		return apiGatewayProxyResponseEvent;
	}
}
